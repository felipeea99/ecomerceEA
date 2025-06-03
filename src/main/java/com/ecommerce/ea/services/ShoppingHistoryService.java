package com.ecommerce.ea.services;

import com.ecommerce.ea.DTOs.request.ShoppingHistoryRequest;
import com.ecommerce.ea.DTOs.response.CartResponse;
import com.ecommerce.ea.DTOs.response.ShoppingHistoryResponse;
import com.ecommerce.ea.DTOs.update.ShoppingHistoryUpdate;
import com.ecommerce.ea.entities.ShoppingHistory;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.IShoppingHistory;
import com.ecommerce.ea.repository.ShoppingHistoryRepository;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ShoppingHistoryService implements IShoppingHistory {

    private final ShoppingHistoryRepository shoppingHistoryRepository;

    public  ShoppingHistoryService(ShoppingHistoryRepository shoppingHistoryRepository){
        this.shoppingHistoryRepository = shoppingHistoryRepository;
    }

    @Override
    public CompletableFuture<ShoppingHistoryResponse> AddArticleToHistory(ShoppingHistoryRequest shoppingHistoryRequest) {
        //Convert the shoppingHistoryRequest to ShoppingHistoryObject
        ShoppingHistory shoppingHistory = shoppingHistoryRequest.ToShoppingHistoryObj();
        //Save the changes and store it on a shoppingHistorySaved
        ShoppingHistory shoppingHistorySaved = shoppingHistoryRepository.save(shoppingHistory);
        //Convert the shoppingHistory Saved into ShoppingHistoryResponse
        ShoppingHistoryResponse shoppingHistoryResponse = ShoppingHistoryResponse.ToShoppingHistoryResponse(shoppingHistorySaved);
        return CompletableFuture.completedFuture(shoppingHistoryResponse);
    }

    @Override
    public CompletableFuture<ShoppingHistoryResponse> EditArticle(ShoppingHistoryUpdate shoppingHistoryUpdate) {
        //Find the shoppingHistoryId and retrieve the object on the shoppingHistory variable
        ShoppingHistory shoppingHistory = shoppingHistoryRepository.findById(shoppingHistoryUpdate.getHistoryId()).orElseThrow(() -> new BadRequestException("shoppingHistoryId was not found on the database"));
        //Make the changes and
        shoppingHistory.setPaymentProviderId(shoppingHistory.getPaymentProviderId());
        shoppingHistory.setQuantity(shoppingHistoryUpdate.getQuantity());
        shoppingHistory.setProduct(shoppingHistoryUpdate.getProductId());
        shoppingHistory.setStatus(shoppingHistoryUpdate.getStatus());
        shoppingHistory.setCustomer(shoppingHistory.getCustomer());
        shoppingHistory.setPurchaseUUID(shoppingHistoryUpdate.getPurchaseUUID());
        shoppingHistory.setDateTime(shoppingHistoryUpdate.getDateTime());
        //save it into the database
        ShoppingHistory shoppingHistorySaved =  shoppingHistoryRepository.save(shoppingHistory);
        //Convert shoppingHistorySaved into ShoppingHistoryResponse type
        ShoppingHistoryResponse shoppingHistoryResponse = ShoppingHistoryResponse.ToShoppingHistoryResponse(shoppingHistorySaved);
        return CompletableFuture.completedFuture(shoppingHistoryResponse);
    }

    @Override
    public CompletableFuture<Boolean> DeleteArticle(int historyId) {
        try{
            shoppingHistoryRepository.deleteById(historyId);
            return CompletableFuture.completedFuture(true);

        } catch (Exception e) {
            return CompletableFuture.completedFuture(false);
        }
    }

    @Override
    public CompletableFuture<List<ShoppingHistoryResponse>> GetAllArticlesBought(UUID userId) {

        return shoppingHistoryRepository.findAllItemsBoughtByUserId(userId).thenApply(shoppingHistoryResponses -> shoppingHistoryResponses.stream().map(ShoppingHistoryResponse::ToShoppingHistoryResponse).collect(Collectors.toList()));
    }

    @Override
    public CompletableFuture<ShoppingHistoryResponse> GetArticleById(int historyId) {
        //historyId validation
        ShoppingHistory shoppingHistorySaved =  shoppingHistoryRepository.findById(historyId).orElseThrow(() -> new BadRequestException("historyId was not found on the database"));
        //Convert shoppingHistorySaved into ShoppingHistoryResponse type
        ShoppingHistoryResponse shoppingHistoryResponse = ShoppingHistoryResponse.ToShoppingHistoryResponse(shoppingHistorySaved);
        return CompletableFuture.completedFuture(shoppingHistoryResponse) ;
    }

    @Override
    public CompletableFuture<List<ShoppingHistoryResponse>> GetAllArticlesBoughtAdmin() {
        //Retrieve all the articles from the database
        List<ShoppingHistory> shoppingHistoryList = shoppingHistoryRepository.findAll();
        //Convert the list into ShoppingHistoryResponse type
        List<ShoppingHistoryResponse> shoppingHistoryResponseList = shoppingHistoryList.stream().map(ShoppingHistoryResponse::ToShoppingHistoryResponse).collect(Collectors.toList());
        return CompletableFuture.completedFuture(shoppingHistoryResponseList);
    }

    @Override
    public CompletableFuture<List<ShoppingHistoryResponse>> GetAllArticlesBoughtByStoreId(int storeId) {
        return null;
    }

    @Override
    public CompletableFuture<ByteArrayOutputStream> ShoppingHistoryExcel() {
        return null;
    }
}
