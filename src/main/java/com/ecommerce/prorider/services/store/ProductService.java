package com.ecommerce.prorider.services.store;

import com.ecommerce.prorider.DTOs.request.store.PriceBySizeRequest;
import com.ecommerce.prorider.DTOs.request.store.ProductRequest;
import com.ecommerce.prorider.DTOs.response.store.*;
import com.ecommerce.prorider.DTOs.update.store.ProductUpdate;
import com.ecommerce.prorider.entities.auth.Store;
import com.ecommerce.prorider.entities.store.Category;
import com.ecommerce.prorider.entities.store.PriceBySize;
import com.ecommerce.prorider.entities.store.Product;
import com.ecommerce.prorider.exceptions.BadRequestException;
import com.ecommerce.prorider.interfaces.store.IProduct;
import com.ecommerce.prorider.repository.store.ProductRepository;
import com.ecommerce.prorider.services.auth.StoreService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProduct {

    private final ProductRepository productRepository;
    private final StoreService storeService;
    private final CategoryService categoryService;
    private final PriceBySizeService priceBySizeService;
    private final SizeService sizeService;
    private final PhotoService photoService;

    @Autowired
    public ProductService(ProductRepository productRepository, StoreService storeService, CategoryService categoryService, PriceBySizeService priceBySizeService, SizeService sizeService, PhotoService photoService) {
        this.productRepository = productRepository;
        this.storeService = storeService;
        this.categoryService = categoryService;
        this.priceBySizeService = priceBySizeService;
        this.sizeService = sizeService;
        this.photoService = photoService;
    }

    ///Add Product Object
    @Override
    public ProductResponse addProduct(ProductRequest productRequest, List<PriceBySizeRequest>priceBySizeList) {
       //Get the objects
        Store store = storeService.findStoreByIdBaseForm(productRequest.getStoreId());
        Category category = categoryService.findCategoryByIdBaseForm(productRequest.getCategoryId());
        //Create the Product obj
        Product product = new Product();
        product.setActive(productRequest.isActive());
        product.setStore(store);
        product.setCategory(category);
        product.setProductName(productRequest.getProductName());
        product.setHasSizes(productRequest.isHasSizes());
        /// Price Configuration
        if (productRequest.isHasSizes()){
            product.setPrice(null);
            /// Price By Size List, this method receives a list of priceBySizeRequest and save it into the database
        priceBySizeService.addProductSize(priceBySizeList);

        }else{
            product.setPrice(productRequest.getPrice());
        }
        // Save the product on the database and store it on "productSaved"
        Product productSaved = productRepository.save(product);
        //Transform "productSaved" into the ProduceResponseType
        return this.ToProductResponse(productSaved);
    }

    ///Edit ProductSingle Objects
    @Override
    public ProductResponse editProduct(ProductUpdate productUpdate) {
        //productId validation
        Product product = productRepository.findById(productUpdate.getProductId())
                .orElseThrow(() ->new BadRequestException("productId was not found on the database"));

        //categoryId validation
        Category category = categoryService.findCategoryByIdBaseForm(productUpdate.getCategoryId());


        //Edit Changes
        product.setCategory(category);
        product.setProductName(productUpdate.getProductName());
        product.setActive(productUpdate.isActive());
        product.setHasSizes(productUpdate.isHasSizes());
        product.setDescription(productUpdate.getDescription());
        //if it is true
        if (productUpdate.isHasSizes()){
            product.setPrice(null);
        //if not
        }else{
            product.setPrice(productUpdate.getPrice());
        }
        // Save changes on the database and stored it on "productSaved"
        Product productSaved = productRepository.save(product);
        //transform "productSaved" into ProductResponse type
        return this.ToProductResponse(productSaved);
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
         return productList.stream().map(this::ToProductResponse).toList();
    }


    /// Retrieve a ProductSingle object by productId
    @Override
    public ProductResponse findProductById(int productId) {
        //productId validation and search
          Product product =  productRepository.findById(productId)
                .orElseThrow(()->new BadRequestException("productId not found on the database"));
          //Transform it into productResponse type
          return this.ToProductResponse(product);
    }
    /// This is like the other findProductById, but this one return the base Product form
    @Override
    public Product findProductByIdBaseForm(int productId) {
        return productRepository.findById(productId).orElseThrow(()-> new BadRequestException("productId was not found on the database"));
    }

    /// Retrieve All the products base on the storeId
    @Override
    public List<ProductResponse> getProductsByStoreId(UUID storeId) {
        return productRepository.findAllProductsByStoreId(storeId)
                .stream()
                .map(this::ToProductResponse)
                .toList();
    }

    /// Retrieve all products objects base on the categoryId and the storeId
    @Override
    public List<ProductResponse> getProductsByCategoryId(int categoryId, UUID storeId) {
        return productRepository.findAllProductsByStoreId(storeId)
                        .stream()
                        .filter(product -> product.getCategory().getCategoryId() == categoryId)
                        .map(this::ToProductResponse)
                        .toList();
    }

    /// Retrieve the product objects base on the categoryId and the storeId and Mix them to have random order
    @Override
    public List<ProductResponse> getProductsRandomByCategory(int categoryId, UUID storeId) {
    List<ProductResponse>productResponse = productRepository.findAllProductsByStoreId(storeId)
                    .stream()
                    .filter(product -> product.getCategory().getCategoryId() == categoryId)
                    .map(this::ToProductResponse)
                    .collect(Collectors.toList()); // mutable list
                    Collections.shuffle(productResponse); //mixes the objects

        return productResponse;
    }

    /// This method retrieves a "product" object and a list of photos, priceBySize by using a "productId", useful to show useful for show the data to the customer on the product information interface
    @Override
    public ProductDetailsResponse findProductDetailsByProductId(int productId) {
        /// Find the "Product" by productId
         ProductResponse productResponse =  this.findProductById(productId);
         /// Find the Photos by  productId
        List<PhotoResponse> photoResponseList = photoService.getAllPhotosByProductID(productId);
        /// Find the PriceBySize by productId
        List<PriceBySizeResponse> priceBySizeResponseList = priceBySizeService.findProductSizesByProductId(productId);
        /// ProductDetailsResponse Initialization
        ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();
        productDetailsResponse.setProductResponse(productResponse);
        productDetailsResponse.setPriceBySizeResponseList(priceBySizeResponseList);
        productDetailsResponse.setPhotoList(photoResponseList);
        return productDetailsResponse;
    }
    /// This method retrieves all the products objects as a list and a list of photos, priceBySize by using a "productId", useful to show useful for show the data store owner
    @Override
    public List<ProductDetailsResponse> findProductDetailsByStoreId(UUID storeId) {
        /// Retrieve all the products that matches with the storeId and transform it into "ProductResponse" type
        List<Product> productList =  productRepository.findAllProductsByStoreId(storeId);
        List<ProductResponse> productResponseList =  productList.stream().map(this::ToProductResponse).toList();
        /// Collect all the photos and ProductBySize Objects
        List<PhotoResponse> photoResponseList = photoService.findAllPhotosByStoreId(storeId);
        List<PriceBySizeResponse> priceBySizeResponseList = priceBySizeService.findAllPriceBySizeByStoreId(storeId);
        /// Group the photos and priceBySize
        Map<Integer, List<PhotoResponse>> photoByProductId = photoResponseList.stream().collect(Collectors.groupingBy(PhotoResponse::getProductId));
        Map<Integer, List<PriceBySizeResponse>> priceBySizeProductId = priceBySizeResponseList.stream().collect(Collectors.groupingBy(prop -> prop.getProduct().getProductId()));
        /// ProductDetails Initialization
        List<ProductDetailsResponse> productDetailsList = new ArrayList<>();
        /// Match with the product.productId each list
        for (ProductResponse product : productResponseList){
            int productId = product.getProductId();
            /// Filter the Original List
            List<PhotoResponse> photoList = photoByProductId.getOrDefault(productId, new ArrayList<>());
            List<PriceBySizeResponse> priceBySizeList = priceBySizeProductId.getOrDefault(productId, new ArrayList<>());
            /// Add the details to the "productDetailsList"
            ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();
            productDetailsResponse.setProductResponse(product);
            productDetailsResponse.setPhotoList(photoList);
            productDetailsResponse.setPriceBySizeResponseList(priceBySizeList);

            productDetailsList.add(productDetailsResponse);
        }

        return productDetailsList;
    }


    @Override
    public ByteArrayOutputStream listProductsExcel(UUID storeId) {
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
                List<Product> productList = productRepository.findAllProductsByStoreId(storeId)
                        .stream().filter(product -> product.getPrice() != null)
                                .toList();

                /// Data
                int rowCount = 1;
                for (Product product : productList) {
                    Row dataRow = productSheet.createRow(rowCount++);
                    dataRow.createCell(0).setCellValue(product.getProductName());
                    dataRow.createCell(1).setCellValue(product.isActive() ? "Activo" : "Inactivo");
                    dataRow.createCell(2).setCellValue(product.getPrice().doubleValue());
                    dataRow.createCell(3).setCellValue(product.getCategory().getCategoryName());
                }

                //Auto-just
                for (int i = 0; i <= 3; i++) {
                    productSheet.autoSizeColumn(i);
                }
                // Store Name
                Store store = storeService.findStoreByIdBaseForm(storeId);


                String fileName = "Productos -" + store.getStoreName() + ".xls";

                /// ProductSize

                //retrieves all the priceBySize objects
                List<PriceBySize> priceBySizeList = priceBySizeService.findAllProductSizesBaseForm();
                //the "priceBySize" is filter with the ones that has the same storeId
                List<PriceBySize> filteredList =  priceBySizeList
                        .stream().filter(products -> products.getProduct().getStore().getStoreId().equals(storeId))
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
                    dataRow.createCell(3).setCellValue(ps.getSize().getSize().concat(" : ").concat(String.valueOf(ps.getPrice())));
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
    }

    @Override
    public ProductResponse ToProductResponse(Product product) {
        /// Create the "StoreLiteResponse" Object
        StoreLiteResponse storeLiteResponse = new StoreLiteResponse();
        storeLiteResponse.setStoreId(product.getStore().getStoreId());
        storeLiteResponse.setStoreName(product.getStore().getStoreName());
        /// "CategoryResponse" Initialization
        CategoryResponse categoryResponse = categoryService.ToCategoryResponse(product.getCategory());
        /// PriceBySize - List
       List<PriceBySizeResponse> priceBySize = priceBySizeService.findProductSizesByProductId(product.getProductId());
        /// "ProductResponse" Initialization
        ProductResponse response = new ProductResponse();
        response.setProductId(product.getProductId());
        response.setProductName(product.getProductName());
        response.setActive(product.isActive());
        response.setStore(storeLiteResponse);
        response.setCategory(categoryResponse);
        response.setHasSizes(product.isHasSizes());
        response.setPrice(product.getPrice());
        response.setPricesBySize(priceBySize);

        return response;
    }

}
