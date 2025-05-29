package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.SizeRequest;
import com.ecommerce.ea.DTOs.response.SizeResponse;
import com.ecommerce.ea.DTOs.update.SizeUpdate;
import com.ecommerce.ea.entities.Size;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ISize {
    CompletableFuture<SizeResponse> AddSize(SizeRequest sizeRequest);
    CompletableFuture<SizeResponse> EditSize(SizeUpdate sizeUpdate);
    CompletableFuture<Boolean> DeleteSize(int sizeId);
    CompletableFuture<List<SizeResponse>> GetAllSizes();
}
