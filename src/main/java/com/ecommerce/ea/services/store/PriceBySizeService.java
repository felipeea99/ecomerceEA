package com.ecommerce.ea.services.store;

import com.ecommerce.ea.DTOs.request.store.PriceBySizeRequest;
import com.ecommerce.ea.DTOs.response.store.PriceBySizeResponse;
import com.ecommerce.ea.DTOs.response.store.ProductResponse;
import com.ecommerce.ea.DTOs.response.store.SizeResponse;
import com.ecommerce.ea.DTOs.update.PriceBySizeUpdate;
import com.ecommerce.ea.entities.store.PriceBySize;
import com.ecommerce.ea.entities.store.Product;
import com.ecommerce.ea.entities.store.Size;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.store.IPriceSize;
import com.ecommerce.ea.repository.store.PriceSizeRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PriceBySizeService implements IPriceSize {

    private final PriceSizeRepository priceSizeRepository;
    private final ProductService productService;
    private final SizeService sizeService;

    public PriceBySizeService (PriceSizeRepository priceSizeRepository, @Lazy ProductService productService, SizeService sizeService){
        this.priceSizeRepository = priceSizeRepository;
        this.productService = productService;
        this.sizeService = sizeService;
    }

    /// Add a list of productSizes to the database, it is used by "productService.addProduct"
    @Override
    public List<PriceBySizeResponse> addProductSize(List<PriceBySizeRequest> request) {
         // Convert the list of request into PriceBySize type
          List<PriceBySize> priceBySizeList =  request.stream()
                  .map(this::ToPriceBySizeObj).toList();
          // Save the objects on the database
         List<PriceBySize> priceSavedList = priceSizeRepository.saveAll(priceBySizeList);
          // Convert the "priceSavedList" into PriceBySizeResponse
         return priceSavedList.stream().map(this::ToPriceBySizeResponse).toList();
    }

    @Override
    public PriceBySizeResponse editProductSize(PriceBySizeUpdate update) {
        // priceBySizeId validation
        PriceBySize editObj = priceSizeRepository.findById(update.getPriceBySizeId())
                .orElseThrow(() -> new BadRequestException("priceBySizeId was not found on the database"));

        // Product Validation
       Product product = productService.findProductByIdBaseForm(update.getProductId());

        //make changes
         editObj.setProduct(product);
         editObj.setSize(update.getSizeName());
         editObj.setPrice(update.getPrice());
         PriceBySize priceBySizeSaved = priceSizeRepository.save(editObj);
         return this.ToPriceBySizeResponse(priceBySizeSaved);

    }

    @Override
    public Boolean deleteProductSize(int priceSizeId) {
        // priceBySizeId validation
        PriceBySize editObj = priceSizeRepository.findById(priceSizeId)
                .orElseThrow(() -> new BadRequestException("priceBySizeId was not found on the database"));
        //remove object from the database
        priceSizeRepository.deleteById(priceSizeId);
        return true;
    }

    /// This is general, more for the Admin
    @Override
    public List<PriceBySizeResponse> findAllProductSizes() {
        //Retrieves all the PriceBySize For Admin
        List<PriceBySize> priceBySizeList = priceSizeRepository.findAll();
        //Transform tha "priceBySizeList" type into PriceBySizeResponse
       return  priceBySizeList.stream().map(this::ToPriceBySizeResponse).toList();
    }

    @Override
    public List<PriceBySize> findAllProductSizesBaseForm() {
        //Retrieves all the PriceBySize For Admin
        return priceSizeRepository.findAll();
    }
    /// This is used for retrieve the priceSize individually
    @Override
    public PriceBySizeResponse findPriceSizeById(int priceSizeId) {
        //Retrieves a specific PriceBySize Object base on a "priceSizeId"
        PriceBySize priceBySize = priceSizeRepository.findById(priceSizeId)
                .orElseThrow(() -> new BadRequestException("priceSizeId was not found on the database"));
        //Transform tha "priceBySize" type into PriceBySizeResponse
        return this.ToPriceBySizeResponse(priceBySize);
    }

    /// Used for retrieve al the productBySize objects from the database of a specific productId
    @Override
    public List<PriceBySizeResponse> findProductSizesByProductId(int productId) {
        //Retrieves all the PriceBySize by productId
         List<PriceBySize> priceBySizeList = priceSizeRepository.findAllByProductId(productId);
        //Transform tha "priceBySizeList" type into PriceBySizeResponse
        return priceBySizeList.stream().map(this::ToPriceBySizeResponse).toList();
    }

    @Override
    public List<PriceBySizeResponse> findAllPriceBySizeByStoreId(UUID storeId) {
         List<PriceBySize> priceBySizeList = priceSizeRepository.findAllPriceBySizeByStoreId(storeId);
        return priceBySizeList.stream().map(this::ToPriceBySizeResponse).toList();
    }

    @Override
    public PriceBySizeResponse ToPriceBySizeResponse(PriceBySize priceBySize) {
        /// ProductResponse and SizeResponse Transformation
        ProductResponse productResponse =  productService.ToProductResponse(priceBySize.getProduct());
        SizeResponse sizeResponse = sizeService.ToSizeResponse(priceBySize.getSize());

        /// PriceBySizeResponse Initialization
        PriceBySizeResponse priceSize = new PriceBySizeResponse();
        priceSize.setPriceBySizeId(priceBySize.getPriceBySizeId());
        priceSize.setProductResponse(productResponse);
        priceSize.setSizeResponse(sizeResponse);
        priceSize.setPrice(priceBySize.getPrice());

        return priceSize;
    }

    @Override
    public PriceBySize ToPriceBySizeObj(PriceBySizeRequest request) {
        Product product = productService.findProductByIdBaseForm(request.getProductId());
        /// Size Initialization
        Size size = sizeService.findSizeByIdBaseForm(request.getSizeId());

        PriceBySize priceSize = new PriceBySize();
        priceSize.setSize(size);
        priceSize.setPrice(request.getPrice());
        priceSize.setProduct(product);
        return priceSize;
    }
}
