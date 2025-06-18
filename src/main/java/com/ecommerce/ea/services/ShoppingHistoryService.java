package com.ecommerce.ea.services;

import com.ecommerce.ea.DTOs.request.ShoppingHistoryRequest;
import com.ecommerce.ea.DTOs.response.ShoppingHistoryResponse;
import com.ecommerce.ea.DTOs.update.ShoppingHistoryUpdate;
import com.ecommerce.ea.entities.*;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.IShoppingHistory;
import com.ecommerce.ea.repository.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ShoppingHistoryService implements IShoppingHistory {

    private final ShoppingHistoryRepository shoppingHistoryRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    public  ShoppingHistoryService(ShoppingHistoryRepository shoppingHistoryRepository, StoreRepository storeRepository, ProductRepository productRepository, CustomerRepository customerRepository, AddressRepository addressRepository){
        this.shoppingHistoryRepository = shoppingHistoryRepository;
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public CompletableFuture<ShoppingHistoryResponse> addArticleToHistory(ShoppingHistoryRequest shoppingHistoryRequest) {
        //Convert the shoppingHistoryRequest to ShoppingHistoryObject
        ShoppingHistory shoppingHistory = this.ToShoppingHistoryObj(shoppingHistoryRequest);
        //Save the changes and store it on a shoppingHistorySaved
        ShoppingHistory shoppingHistorySaved = shoppingHistoryRepository.save(shoppingHistory);
        //Convert the shoppingHistory Saved into ShoppingHistoryResponse
        ShoppingHistoryResponse shoppingHistoryResponse = this.ToShoppingHistoryResponse(shoppingHistorySaved);
         return CompletableFuture.completedFuture(shoppingHistoryResponse);
    }

    @Override
    public CompletableFuture<ShoppingHistoryResponse> editArticle(ShoppingHistoryUpdate shUpdate) {
        //Find the shoppingHistoryId and retrieve the object on the shoppingHistory variable
        ShoppingHistory sh = shoppingHistoryRepository.findById(shUpdate.getHistoryId())
               .orElseThrow(() -> new BadRequestException("shoppingHistoryId was not found on the database"));
        //productId validation
        Product product = productRepository.findById(shUpdate.getProductId())
                .orElseThrow(() ->new BadRequestException("productId was not found on the database"));

        //Make the changes and save it into the database
        sh.setQuantity(shUpdate.getQuantity());
        sh.setProduct(product);
        sh.setStatus(shUpdate.getStatus());
        sh.setPurchaseUUID(shUpdate.getPurchaseUUID());
        sh.setDateTime(shUpdate.getDateTime());
        //Save the changes in the database
        ShoppingHistory shSaved = shoppingHistoryRepository.save(sh);
        //Transform the "shSaved" into ShoppingHistoryResponse type
        ShoppingHistoryResponse shResponse = this.ToShoppingHistoryResponse(shSaved);
        return CompletableFuture.completedFuture(shResponse);
    }

    @Override
    public Boolean deleteArticle(int historyId) {
        //Find the shoppingHistoryId and retrieve the object on the shoppingHistory variable
        ShoppingHistory sh = shoppingHistoryRepository.findById(historyId)
                .orElseThrow(() -> new BadRequestException("shoppingHistoryId was not found on the database"));
        //delete the object
        shoppingHistoryRepository.deleteById(historyId);
        return true;
    }

    @Override
    public List<ShoppingHistoryResponse> getAllArticlesBought(UUID userId) {
    return shoppingHistoryRepository.findAllItemsBoughtByCustomerId(userId).thenApply(
               shoppingHistories -> shoppingHistories.stream().
                       map(this::ToShoppingHistoryResponse)
                       .toList());
    }

    @Override
    public ShoppingHistoryResponse getArticleById(int historyId) {
        //historyId validation
        ShoppingHistory shObj = shoppingHistoryRepository.findById(historyId).orElseThrow(() -> new BadRequestException("historyId was not found on the database"));
        //transform "shObj" into ShoppingHistoryResponse
        return this.ToShoppingHistoryResponse(shObj);
    }

    @Override
    public List<ShoppingHistoryResponse> getAllArticlesBoughtAdmin() {
        //retrieve all the shoppingHistory for Admin
        List<ShoppingHistory> shList = shoppingHistoryRepository.findAll();
        //Transform it into shoppingHistory response type
        return shList.stream().map(this::ToShoppingHistoryResponse).toList();
    }


    @Override
    public List<ShoppingHistoryResponse> getAllArticlesBoughtByStoreId(UUID storeId) {
        return shoppingHistoryRepository.findAllItemsByStoreId(storeId)
                .thenApply(shoppingHistories -> shoppingHistories.stream().map(this::ToShoppingHistoryResponse)
                .toList());
    }

    @Override
    public ByteArrayOutputStream shoppingHistoryExcel(UUID storeId) {
        return CompletableFuture.supplyAsync(() -> {
            try (Workbook workbook = new HSSFWorkbook()) {
                Sheet shHistorySheet = workbook.createSheet("Historial");

                // Headers
                Row header = shHistorySheet.createRow(0);
                header.createCell(0).setCellValue("Fecha");
                header.createCell(1).setCellValue("Producto");
                header.createCell(2).setCellValue("Cantidad");
                header.createCell(3).setCellValue("Estado");
                header.createCell(4).setCellValue("Id de compra");
                header.createCell(5).setCellValue("Cliente");
                header.createCell(6).setCellValue("Dirección");

                // Get the products bought base on the storeId
                List<ShoppingHistory> shList = shoppingHistoryRepository.findAllItemsByStoreId(storeId).get();

                /// Data
                int rowCount = 1;
                for (ShoppingHistory history : shList) {
                    //Get the full name
                    String fullName = history.getCustomer().getUser().getName() + " " +
                            history.getCustomer().getUser().getUserLastName1() + " " +
                            history.getCustomer().getUser().getUserLastName2();

                    String fullAddress = history.getAddress().getStreet() + " "+
                            history.getAddress().getNumber() + " "+
                            history.getAddress().getColony() + " "+
                            history.getAddress().getTown() + " "+
                            history.getAddress().getCountry() + " ";

                    Row dataRow = shHistorySheet.createRow(rowCount++);
                    dataRow.createCell(0).setCellValue(history.getDateTime());
                    dataRow.createCell(1).setCellValue(history.getProduct().getProductName());
                    dataRow.createCell(2).setCellValue(history.getQuantity());
                    dataRow.createCell(3).setCellValue(history.getStatus().toString());
                    dataRow.createCell(4).setCellValue(history.getPurchaseUUID());
                    dataRow.createCell(5).setCellValue(fullName);
                    dataRow.createCell(6).setCellValue(fullAddress);
                }

                //Auto-just
                for (int i = 0; i <= 6; i++) {
                    shHistorySheet.autoSizeColumn(i);
                }

                // Store Name
                Store store = storeRepository.findById(storeId)
                        .orElseThrow(() -> new BadRequestException("storeId no encontrado en la base de datos"));

                String fileName = "Historial -" + store.getStoreName() + ".xls";

                //Save document
                try (FileOutputStream fos = new FileOutputStream(fileName)) {
                    workbook.write(fos);
                }

                // Create ByteArrayOutputStream to save the Excel on the memory
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                workbook.write(outputStream);

                return outputStream;

            } catch (Exception e) {
                throw new RuntimeException("Error generating  Excel", e);
            }
        });
    }

    @Override
    public ShoppingHistoryResponse ToShoppingHistoryResponse(ShoppingHistory shoppingHistory) {
        /// Transformation
        ShoppingHistoryResponse shr = new ShoppingHistoryResponse();
        shr.setHistoryId(shoppingHistory.getHistoryId());
        shr.setDateTime(shoppingHistory.getDateTime());
        shr.setQuantity(shoppingHistory.getQuantity());
        shr.setStatus(shoppingHistory.getStatus());
        shr.setPurchaseUUID(shoppingHistory.getPurchaseUUID());
        shr.setProductId(shoppingHistory.getProduct().getProductId());
        shr.setCustomerId(shoppingHistory.getCustomer().getCustomerId());
        shr.setAddressId(shoppingHistory.getAddress().getAddressId());

        return shr;
    }

    @Override
    public ShoppingHistory ToShoppingHistoryObj(ShoppingHistoryRequest shoppingHistoryRequest) {
        /// Validations
        Product product = productRepository.findById(shoppingHistoryRequest.getProductId())
                .orElseThrow(()-> new BadRequestException("productId was not found on the database"));
        Customer customer = customerRepository.findById(shoppingHistoryRequest.getCustomerId())
                .orElseThrow(()-> new BadRequestException("customerId was not found on the database"));
        Address address = addressRepository.findById(shoppingHistoryRequest.getAddressId())
                .orElseThrow(()-> new BadRequestException("addressId was not found on the database"));

        ShoppingHistory shoppingHistory = new ShoppingHistory();
        shoppingHistory.setDateTime(shoppingHistoryRequest.getDateTime());
        shoppingHistory.setStatus(shoppingHistoryRequest.getStatus());
        shoppingHistory.setQuantity(shoppingHistoryRequest.getQuantity());
        shoppingHistory.setPurchaseUUID(shoppingHistoryRequest.getPurchaseUUID());
        shoppingHistory.setAddress(address);
        shoppingHistory.setCustomer(customer);
        shoppingHistory.setProduct(product);

        return shoppingHistory;
    }
}
