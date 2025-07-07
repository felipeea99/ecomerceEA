package com.ecommerce.prorider.services.store;

import com.ecommerce.prorider.DTOs.request.store.SizeRequest;
import com.ecommerce.prorider.DTOs.response.store.SizeResponse;
import com.ecommerce.prorider.DTOs.update.store.SizeUpdate;
import com.ecommerce.prorider.entities.store.Size;
import com.ecommerce.prorider.exceptions.BadRequestException;
import com.ecommerce.prorider.interfaces.store.ISize;
import com.ecommerce.prorider.repository.store.SizeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeService implements ISize {

    private final SizeRepository sizeRepository;

    public SizeService(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    /// Add a Size Object to the database
    @Override
    public SizeResponse addSize(SizeRequest sizeRequest) {
        //Convert the SizeRequest into SizeObject
        Size size = sizeRequest.ToSizeObject();
        //Save the object on the database and stored it on "sizeSaved" variable
        Size sizeSaved = sizeRepository.save(size);
        //Convert it into sizeResponse
        return this.ToSizeResponse(sizeSaved);
    }

    /// Edit a SizeObject from the database base on a given object
    @Override
    public SizeResponse editSize(SizeUpdate sizeUpdate) {
        //sizeId validation
        int sizeId = sizeUpdate.getSizeId();
        //return the object with the modifications
        Size size = this.findSizeByIdBaseForm(sizeId);
        //Make changes
        size.setSize(sizeUpdate.getSize());
        //Save the Changes
        Size sizeSaved = sizeRepository.save(size);
        //Transform the "sizeSaved" variable into SizeResponse type
       return this.ToSizeResponse(sizeSaved);
    }

    /// Delete a SizeObject from the database base on a given sizeId
    @Override
    public Boolean deleteSize(int sizeId) {
        //sizeId validation
        this.findSizeByIdBaseForm(sizeId);
        //Delete size object
        sizeRepository.deleteById(sizeId);
        return true;
    }

    /// Delete a SizeObject from the database base on a given sizeId
    @Override
    public List<SizeResponse> findAllSizes() {
        //retrieve all the size objects from the database
        List<Size> sizeList =  sizeRepository.findAll();
        //Transform it into SizeResponseType
        return sizeList.stream().map(this::ToSizeResponse).toList();
    }

    @Override
    public Size findSizeByIdBaseForm(int sizeId) {
        return sizeRepository.findById(sizeId).orElseThrow(() -> new BadRequestException("sizeId was not found on the database"));
    }

    @Override
    public SizeResponse ToSizeResponse(Size size) {
        SizeResponse sizeResponse = new SizeResponse();
        sizeResponse.setSize(size.getSize());
        sizeResponse.setSizeId(size.getSizeId());
        return sizeResponse;
    }

    @Override
    public Size ToSizeObject(SizeResponse sizeResponse) {
        Size size = new Size();
        size.setSize(sizeResponse.getSize());
        size.setSizeId(sizeResponse.getSizeId());
        return size;
    }
}
