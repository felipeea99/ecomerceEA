package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.SizeRequest;
import com.ecommerce.ea.DTOs.response.SizeResponse;
import com.ecommerce.ea.DTOs.update.SizeUpdate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ISize {
    SizeResponse AddSize(SizeRequest sizeRequest);
    SizeResponse EditSize(SizeUpdate sizeUpdate);
    Boolean DeleteSize(int sizeId);
    List<SizeResponse> GetAllSizes();
}
