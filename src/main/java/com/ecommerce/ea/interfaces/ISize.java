package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.entities.Size;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ISize {
    CompletableFuture<Size> AddSize(Size size);
    CompletableFuture<Size> EditSize(Size size);
    CompletableFuture<Boolean> DeleteSize(int sizeId);
    CompletableFuture<List<Size>> GetAllSizes();
}
