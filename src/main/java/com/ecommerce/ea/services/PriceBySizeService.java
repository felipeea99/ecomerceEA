package com.ecommerce.ea.services;

import com.ecommerce.ea.DTOs.request.PriceBySizeRequest;
import com.ecommerce.ea.DTOs.response.PriceBySizeResponse;
import com.ecommerce.ea.DTOs.update.PriceBySizeUpdate;
import com.ecommerce.ea.entities.PriceBySize;
import com.ecommerce.ea.entities.Product;
import com.ecommerce.ea.entities.Size;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.IPriceSize;
import com.ecommerce.ea.repository.PriceSizeRepository;
import com.ecommerce.ea.repository.ProductRepository;
import com.ecommerce.ea.repository.SizeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class PriceBySizeService implements IPriceSize {
    private final PriceSizeRepository priceSizeRepository;
    private final ProductRepository productRepository;
    private final SizeRepository sizeRepository;

    public PriceBySizeService (PriceSizeRepository priceSizeRepository, ProductRepository productRepository, SizeRepository sizeRepository){
        this.priceSizeRepository = priceSizeRepository;
        this.productRepository = productRepository;
        this.sizeRepository = sizeRepository;
    }

    /// Add a list of productSizes to the database
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
        Product product = new Product();
        product = productRepository.findById(update.getProductId()).orElseThrow(()-> new BadRequestException("productId was not found on the database"));

        //make changes
         editObj.setProduct(product);
         editObj.setSizeName(update.getSizeName());
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
    public List<PriceBySizeResponse> getAllProductSizes() {
        //Retrieves all the PriceBySize For Admin
        List<PriceBySize> priceBySizeList = priceSizeRepository.findAll();
        //Transform tha "priceBySizeList" type into PriceBySizeResponse
       return  priceBySizeList.stream().map(this::ToPriceBySizeResponse).toList();
    }

    /// This is used for retrieve the priceSize individually
    @Override
    public PriceBySizeResponse getPriceSizeById(int priceSizeId) {
        //Retrieves a specific PriceBySize Object base on a "priceSizeId"
        PriceBySize priceBySize = priceSizeRepository.findById(priceSizeId)
                .orElseThrow(() -> new BadRequestException("priceSizeId was not found on the database"));
        //Transform tha "priceBySize" type into PriceBySizeResponse
        return this.ToPriceBySizeResponse(priceBySize);
    }

    /// Used for retrieve al the productBySize objects from the database of a specific productId
    @Override
    public List<PriceBySizeResponse> getProductSizesByProductId(int productId) {
        //Retrieves all the PriceBySize by productId
         List<PriceBySize> priceBySizeList = priceSizeRepository.findAllByProductId(productId);
        //Transform tha "priceBySizeList" type into PriceBySizeResponse
        return priceBySizeList.stream().map(this::ToPriceBySizeResponse).toList();
    }

    @Override
    public PriceBySizeResponse ToPriceBySizeResponse(PriceBySize priceBySize) {
        /// Product Initialization
        Product product = new Product();
        product = productRepository.findById(priceBySize.getProduct().getProductId()).orElseThrow(()-> new BadRequestException("productId was not found on the database"));
        /// Size Initialization
        Size size = new Size();
        size = sizeRepository.findById(priceBySize.getPriceBySizeId()).orElseThrow(()-> new BadRequestException("sizeId was not found on the database"));

        PriceBySizeResponse priceSize = new PriceBySizeResponse();

        priceSize.setPriceBySizeId(priceBySize.getPriceBySizeId());
        priceSize.setProductId(product.getProductId());
        priceSize.setSizeId(size.getSizeId());
        priceSize.setPrice(priceBySize.getPrice());

        return priceSize;    }

    @Override
    public PriceBySize ToPriceBySizeObj(PriceBySizeRequest request) {
        Product product = new Product();
        product = productRepository.findById(request.getProductId()).orElseThrow(()-> new BadRequestException("productId was not found on the database"));
        /// Size Initialization
        Size size = new Size();
        size = sizeRepository.findById(request.getSizeId()).orElseThrow(()-> new BadRequestException("sizeId was not found on the database"));

        PriceBySize priceSize = new PriceBySize();
        priceSize.setSizeName(size);
        priceSize.setPrice(request.getPrice());
        priceSize.setProduct(product);
        return priceSize;
    }
}
