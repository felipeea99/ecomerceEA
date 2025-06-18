package com.ecommerce.ea.services;

import com.ecommerce.ea.DTOs.request.ProductRequest;
import com.ecommerce.ea.DTOs.response.ProductResponse;
import com.ecommerce.ea.DTOs.update.ProductUpdate;
import com.ecommerce.ea.entities.*;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.IProduct;
import com.ecommerce.ea.repository.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductService implements IProduct {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    private  final PriceSizeRepository priceBySizeRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, StoreRepository storeRepository, CategoryRepository categoryRepository, PriceSizeRepository priceBySizeRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.categoryRepository = categoryRepository;
        this.priceBySizeRepository = priceBySizeRepository;
    }

    ///Add ProductSingle Objects
    @Override
    public ProductResponse addProduct(ProductRequest productRequest) {
       //Get the objects
        Store store = storeRepository.findById(productRequest.getStoreId()).orElseThrow(()-> new BadRequestException("storeId was not found on the database"));
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(()-> new BadRequestException("categoryId was not found on the database"));;
        //Create the Product obj
        Product product = new Product();
        product.setActive(productRequest.isActive());
        product.setStore(store);
        product.setCategory(category);
        product.setProductName(productRequest.getProductName());
        product.setHasSizes(productRequest.isHasSizes());
        if (productRequest.isHasSizes() == true){
            product.setPrice(0);
        }else{
            product.setPrice(productRequest.getPrice());
        }
        // Save the product on the database and store it on "productSaved"
        Product productSaved = productRepository.save(product);
        //Transform "productSaved" into the ProduceResponseType
        return ProductResponse.ToProductResponse(productSaved);
    }

    ///Edit ProductSingle Objects
    @Override
    public ProductResponse editProduct(ProductUpdate productUpdate) {
        //productId validation
        Product product = productRepository.findById(productUpdate.getProductId())
                .orElseThrow(() ->new BadRequestException("productId was not found on the database"));

        //categoryId validation
        Category category = categoryRepository.findById(productUpdate.getCategoryId())
                .orElseThrow(() ->new BadRequestException("categoryId was not found on the database"));

        //Edit Changes
        product.setCategory(category);
        product.setProductName(productUpdate.getProductName());
        product.setActive(productUpdate.isActive());
        product.setHasSizes(productUpdate.isHasSizes());
        //if it is true
        if (productUpdate.isHasSizes()){
            product.setPrice(0);
        //if not
        }else{
            product.setPrice(productUpdate.getPrice());
        }
        // Save changes on the database and stored it on "productSaved"
        Product productSaved = productRepository.save(product);
        //transform "productSaved" into ProductResponse type
        return ProductResponse.ToProductResponse(productSaved);
    }
    ///Delete ProductSingle Objects
    @Override
    public Boolean deleteProduct(int productId) {
        //productId validation
        productRepository.findById(productId)
                .orElseThrow(() -> new BadRequestException("productId was not found on the database"));
        //remove the product object from the database
        productRepository.deleteById(productId);
        return true;
    }

    ///Retrieve All ProductSingle Objects for Admin Calls
    @Override
    public List<ProductResponse> getAllProducts() {
        //Retrieve all the product from the database (for Admin)
        List<Product> productList = productRepository.findAll();
        //Convert the "productList" into ProductResponse type
         List<ProductResponse> productResponse = productList.stream().map(ProductResponse::ToProductResponse).toList();
         return productResponse;
    }


    /// Retrieve a ProductSingle object by productId
    @Override
    public ProductResponse findProductById(int productId) {
        //productId validation and search
          Product product =  productRepository.findById(productId)
                .orElseThrow(()->new BadRequestException("productId not found on the database"));
          //Transform it into productResponse type
          return ProductResponse.ToProductResponse(product);
    }
    /// This is like the other findProductById, but this one return the base Product form
    @Override
    public Product findProductByIdBaseForm(int productId) {
        return productRepository.findById(productId).orElseThrow(()-> new BadRequestException("productId was not found on the database"));
    }

    /// Retrieve All the products base on the storeId
    @Override
    public List<ProductResponse> getProductsByStoreId(UUID storeId) {
        return productRepository.FindAllProductsByStoreId(storeId).thenApply(products ->
                products.stream().map(ProductResponse::ToProductResponse).toList());
    }

    /// Retrieve all products objects base on the categoryId and the storeId
    @Override
    public List<ProductResponse> getProductsByCategoryId(int categoryId, UUID storeId) {
        return productRepository.FindAllProductsByStoreId(storeId)
                .thenApply(products ->
                    products.stream()
                        .filter(product -> product.getCategory().getCategoryId() == categoryId)
                        .map(ProductResponse::ToProductResponse)
                        .toList());
    }

    /// Retrieve the product objects base on the categoryId and the storeId and Mix them to have random order
    @Override
    public List<ProductResponse> getProductsRandomByCategory(int categoryId, UUID storeId) {
        return productRepository.FindAllProductsByStoreId(storeId)
                .thenApply(products -> {
                    List<ProductResponse> filteredList = products.stream()
                            .filter(product -> product.getCategory().getCategoryId() == categoryId)
                            .map(ProductResponse::ToProductResponse)
                            .toList();
                    Collections.shuffle(filteredList); //mixes the objects
                    return filteredList;
                });
    }



    @Override
    public ByteArrayOutputStream listProductsExcel(UUID storeId) {
        return CompletableFuture.supplyAsync(() -> {
            try (Workbook workbook = new HSSFWorkbook()) {
                /// Products with Single Price
                Sheet productSheet = workbook.createSheet("Productos");

                // Headers
                Row header = productSheet.createRow(0);
                header.createCell(0).setCellValue("Nombre del Producto");
                header.createCell(1).setCellValue("Activo/Inactivo");
                header.createCell(2).setCellValue("Precio");
                header.createCell(3).setCellValue("Categoría");

                // Get the products that its price is no 0
                List<Product> productList = productRepository.FindAllProductsByStoreId(storeId)
                        .thenApply(products -> products.stream().filter(product -> product.getPrice() != 0)
                                .toList())
                        .join();

                /// Data
                int rowCount = 1;
                for (Product product : productList) {
                    Row dataRow = productSheet.createRow(rowCount++);
                    dataRow.createCell(0).setCellValue(product.getProductName());
                    dataRow.createCell(1).setCellValue(product.isActive() ? "Activo" : "Inactivo");
                    dataRow.createCell(2).setCellValue(product.getPrice());
                    dataRow.createCell(3).setCellValue(product.getCategory().getCategoryName());
                }

                //Auto-just
                for (int i = 0; i <= 3; i++) {
                    productSheet.autoSizeColumn(i);
                }
                // Store Name
                Store store = storeRepository.findById(storeId)
                        .orElseThrow(() -> new BadRequestException("storeId no encontrado en la base de datos"));

                String fileName = "Productos -" + store.getStoreName() + ".xls";

                /// ProductSize

                //retrieves all the priceBySize objects
                List<PriceBySize> priceBySizeList = priceBySizeRepository.findAll();
                //the "priceBySize" is filter with the ones that has the same storeId
                List<PriceBySize> filteredList =  priceBySizeList.stream().filter(products -> products
                                .getProduct().getStore().getStoreId().equals(storeId))
                        .toList();

                Sheet productSize = workbook.createSheet("Productos y Tallas");

                // Headers
                Row header2 = productSheet.createRow(0);
                header.createCell(0).setCellValue("Nombre del Producto");
                header.createCell(1).setCellValue("Activo/Inactivo");
                header.createCell(2).setCellValue("Categoría");
                header.createCell(3).setCellValue("Talla y Precio");


                /// Data PriceBySize
                int rowCountPs = 1;
                for (PriceBySize ps : filteredList) {
                    Row dataRow = productSheet.createRow(rowCountPs++);
                    dataRow.createCell(0).setCellValue(ps.getProduct().getProductName());
                    dataRow.createCell(1).setCellValue(ps.getProduct().isActive() ? "Activo" : "Inactivo");
                    dataRow.createCell(2).setCellValue(ps.getProduct().getCategory().getCategoryName());
                    dataRow.createCell(3).setCellValue(ps.getSizeName().getSize().concat(" : ").concat(String.valueOf(ps.getPrice())));
                }

                //Auto-just
                for (int i = 0; i <= 3; i++) {
                    productSheet.autoSizeColumn(i);
                }

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



}
