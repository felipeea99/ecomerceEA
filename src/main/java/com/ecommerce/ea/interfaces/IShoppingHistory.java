package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.ShoppingHistoryRequest;
import com.ecommerce.ea.DTOs.response.ShoppingHistoryResponse;
import com.ecommerce.ea.DTOs.update.ShoppingHistoryUpdate;
import com.ecommerce.ea.entities.ShoppingHistory;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

public interface IShoppingHistory {
    ShoppingHistoryResponse addArticleToHistory(ShoppingHistoryRequest shoppingHistoryRequest);
    ShoppingHistoryResponse editArticle(ShoppingHistoryUpdate shoppingHistoryUpdate);
    Boolean deleteArticle(int historyId);
    List<ShoppingHistoryResponse> getAllArticlesBought(UUID userId);
    ShoppingHistoryResponse getArticleById(int historyId);
    List<ShoppingHistoryResponse> getAllArticlesBoughtAdmin();
    List<ShoppingHistoryResponse> getAllArticlesBoughtByStoreId(UUID storeId);
    ByteArrayOutputStream shoppingHistoryExcel(UUID storeId);

    ShoppingHistoryResponse ToShoppingHistoryResponse(ShoppingHistory shoppingHistory);
    ShoppingHistory ToShoppingHistoryObj(ShoppingHistoryRequest shoppingHistoryRequest);
}
