package com.ecommerce.ea.services;

import com.ecommerce.ea.DTOs.request.ShoppingHistoryRequest;
import com.ecommerce.ea.DTOs.response.ShoppingHistoryResponse;
import com.ecommerce.ea.DTOs.update.ShoppingHistoryUpdate;
import com.ecommerce.ea.interfaces.IShoppingHistory;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ShoppingHistoryService implements IShoppingHistory {
    @Override
    public CompletableFuture<ShoppingHistoryResponse> AddArticleToHistory(ShoppingHistoryRequest shoppingHistoryRequest) {
        return null;
    }

    @Override
    public CompletableFuture<ShoppingHistoryResponse> EditArticle(ShoppingHistoryUpdate shoppingHistoryUpdate) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> DeleteArticle(int historyId) {
        return null;
    }

    @Override
    public CompletableFuture<List<ShoppingHistoryResponse>> GetAllArticlesBought(UUID userId) {
        return null;
    }

    @Override
    public CompletableFuture<ShoppingHistoryResponse> GetArticleById(int historyId) {
        return null;
    }

    @Override
    public CompletableFuture<List<ShoppingHistoryResponse>> GetAllArticlesBoughtAdmin() {
        return null;
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
