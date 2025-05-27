package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.entities.ShoppingHistory;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IShoppingHistory {
    CompletableFuture<ShoppingHistory> AddArticleToHistory(ShoppingHistory shoppingHistory);
    CompletableFuture<ShoppingHistory> EditArticle(ShoppingHistory shoppingHistory);
    CompletableFuture<Boolean> DeleteArticle(int historyId);
    CompletableFuture<List<ShoppingHistory>> GetAllArticlesBought(UUID userId);
    CompletableFuture<ShoppingHistory> GetArticleById(int articleId);
    CompletableFuture<List<ShoppingHistory>> GetAllArticlesBoughtAdmin();
    CompletableFuture<ByteArrayOutputStream> ShoppingHistoryExcel();
}
