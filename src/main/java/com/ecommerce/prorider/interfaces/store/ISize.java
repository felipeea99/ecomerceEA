package com.ecommerce.prorider.interfaces.store;

import com.ecommerce.prorider.DTOs.request.store.SizeRequest;
import com.ecommerce.prorider.DTOs.response.store.SizeResponse;
import com.ecommerce.prorider.DTOs.update.store.SizeUpdate;
import com.ecommerce.prorider.entities.store.Size;

import java.util.List;

public interface ISize {
    SizeResponse addSize(SizeRequest sizeRequest);
    SizeResponse editSize(SizeUpdate sizeUpdate);
    Boolean deleteSize(int sizeId);
    List<SizeResponse> findAllSizes();
    Size findSizeByIdBaseForm(int sizeId);
    SizeResponse ToSizeResponse(Size size);
    Size ToSizeObject(SizeResponse sizeResponse);

    }
