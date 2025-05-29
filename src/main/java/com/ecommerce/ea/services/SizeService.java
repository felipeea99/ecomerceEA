package com.ecommerce.ea.services;

import com.ecommerce.ea.DTOs.request.SizeRequest;
import com.ecommerce.ea.DTOs.response.SizeResponse;
import com.ecommerce.ea.DTOs.update.SizeUpdate;
import com.ecommerce.ea.entities.Size;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.ISize;
import com.ecommerce.ea.repository.SizeRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class SizeService implements ISize {

    private final SizeRepository sizeRepository;

    public SizeService(SizeRepository sizeRepository){
        this.sizeRepository = sizeRepository;
    }

    @Override
    public CompletableFuture<SizeResponse> AddSize(SizeRequest sizeRequest) {
        //Convert the SizeRequest into SizeObject
        Size size = sizeRequest.ToSizeObject();
        // Stores it and save it on the database
        Size sizeSaved = sizeRepository.save(size);
        //Convert the sizeSaved into SizeResponse
        SizeResponse sizeResponse = SizeResponse.ToSizeResponseObj(sizeSaved);
        return CompletableFuture.completedFuture(sizeResponse);
    }

    @Override
    public CompletableFuture<SizeResponse> EditSize(SizeUpdate sizeUpdate) {
        //sizeId validation
        int sizeId = sizeUpdate.getSizeId();
        Size size = sizeRepository.findById(sizeId)
                .orElseThrow(() -> new BadRequestException("sizeId was not found on the database"));
        //Edit and save the change on the variable sizeSaved
        size.setSize(sizeUpdate.getSize());
        Size sizeSaved = sizeRepository.save(size);
        //Convert sizeSaved to SizeResponse type
        SizeResponse sizeResponse = SizeResponse.ToSizeResponseObj(sizeSaved);
        return CompletableFuture.completedFuture(sizeResponse);
    }

    @Override
    public CompletableFuture<Boolean> DeleteSize(int sizeId) {
        try{
            //sizeId validation
            Size size = sizeRepository.findById(sizeId)
                    .orElseThrow(() -> new BadRequestException("sizeId was not found on the database"));
            sizeRepository.deleteById(sizeId);
            return CompletableFuture.completedFuture(true);
        } catch (Exception e) {
            return CompletableFuture.completedFuture(false);
        }
    }

    @Override
    public CompletableFuture<List<SizeResponse>> GetAllSizes() {
        //retrieve all the size objects from the database
        List<Size> sizeList = sizeRepository.findAll();
        //convert the sizeList to SizeResponse Type
        List<SizeResponse> sizeResponseList = sizeList.stream().map(SizeResponse::ToSizeResponseObj).collect(Collectors.toList());
        return CompletableFuture.completedFuture(sizeResponseList);
    }
}
