package com.ecommerce.ea.services;

import com.ecommerce.ea.DTOs.request.SizeRequest;
import com.ecommerce.ea.DTOs.response.SizeResponse;
import com.ecommerce.ea.DTOs.update.SizeUpdate;
import com.ecommerce.ea.entities.Size;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.ISize;
import com.ecommerce.ea.repository.SizeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class SizeService implements ISize {

    private final SizeRepository sizeRepository;

    public SizeService(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    /// Add a Size Object to the database
    @Override
    public SizeResponse AddSize(SizeRequest sizeRequest) {
        //Convert the SizeRequest into SizeObject
        Size size = sizeRequest.ToSizeObject();
        //Save the object on the database and stored it on "sizeSaved" variable
        Size sizeSaved = sizeRepository.save(size);
        //Convert it into sizeResponse
        return SizeResponse.ToSizeResponseObj(sizeSaved);
    }

    /// Edit a SizeObject from the database base on a given object
    @Override
    public SizeResponse EditSize(SizeUpdate sizeUpdate) {
        //sizeId validation
        int sizeId = sizeUpdate.getSizeId();
        //return the object with the modifications
        Size size = sizeRepository.findById(sizeId)
                .orElseThrow(() -> new BadRequestException("sizeId was not found on the database"));
        //Make changes
        size.setSize(sizeUpdate.getSize());
        //Save the Changes
        Size sizeSaved = sizeRepository.save(size);
        //Transform the "sizeSaved" variable into SizeResponse type
       return SizeResponse.ToSizeResponseObj(sizeSaved);
    }

    /// Delete a SizeObject from the database base on a given sizeId
    @Override
    public Boolean DeleteSize(int sizeId) {
        //sizeId validation
        sizeRepository.findById(sizeId).orElseThrow(() -> new BadRequestException("sizeId was not found on the database"));
        //Delete size object
        sizeRepository.deleteById(sizeId);
        return true
    }

    /// Delete a SizeObject from the database base on a given sizeId
    @Override
    public List<SizeResponse> GetAllSizes() {
        //retrieve all the size objects from the database
        List<Size> sizeList =  sizeRepository.findAll();
        //Transform it into SizeResponseType
        return sizeList.stream().map(SizeResponse::ToSizeResponseObj).toList();
    }
}
