package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.ShoppingHistoryRequest;
import com.ecommerce.ea.DTOs.response.ShoppingHistoryResponse;
import com.ecommerce.ea.DTOs.update.ShoppingHistoryUpdate;
import com.ecommerce.ea.entities.ShoppingHistory;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IShoppingHistory {
    CompletableFuture<ShoppingHistoryResponse> AddArticleToHistory(ShoppingHistoryRequest shoppingHistoryRequest);
    CompletableFuture<ShoppingHistoryResponse> EditArticle(ShoppingHistoryUpdate shoppingHistoryUpdate);
    CompletableFuture<Boolean> DeleteArticle(int historyId);
    CompletableFuture<List<ShoppingHistoryResponse>> GetAllArticlesBought(UUID userId);
    CompletableFuture<ShoppingHistoryResponse> GetArticleById(int historyId);
    CompletableFuture<List<ShoppingHistoryResponse>> GetAllArticlesBoughtAdmin();
    CompletableFuture<List<ShoppingHistoryResponse>> GetAllArticlesBoughtByStoreId(int storeId);
    CompletableFuture<ByteArrayOutputStream> ShoppingHistoryExcel();
}
