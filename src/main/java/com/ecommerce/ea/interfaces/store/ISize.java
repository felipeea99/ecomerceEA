package com.ecommerce.ea.interfaces.store;

import com.ecommerce.ea.DTOs.request.store.SizeRequest;
import com.ecommerce.ea.DTOs.response.store.SizeResponse;
import com.ecommerce.ea.DTOs.update.SizeUpdate;
import com.ecommerce.ea.entities.store.Size;

import java.util.List;

public interface ISize {
    SizeResponse addSize(SizeRequest sizeRequest);
    SizeResponse editSize(SizeUpdate sizeUpdate);
    Boolean deleteSize(int sizeId);
    List<SizeResponse> findAllSizes();
    Size findSizeByIdBaseForm(int sizeId);
    SizeResponse ToSizeResponse(Size size);
}
