package com.mercadolibre.qualitydesafio.services;

import com.mercadolibre.qualitydesafio.dto.HotelDTO;
import com.mercadolibre.qualitydesafio.dto.PayloadDTO;
import com.mercadolibre.qualitydesafio.dto.ResponseDTO;
import com.mercadolibre.qualitydesafio.exceptions.*;
import com.mercadolibre.qualitydesafio.repositories.HotelRepository;
import com.mercadolibre.qualitydesafio.utils.Validator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private Validator validator;

    public HotelServiceImpl(HotelRepository hotelRepository){
        this.hotelRepository = hotelRepository;
        this.validator = new Validator();
    }

    @Override
    public List<HotelDTO> getHotels(Map<String, String> params) throws Exception {
        if(params.size() == 3){
            if(validator.validateKeys(params, "hotelSearch")){
                if(validator.validateSearchValues(params)){
                    return hotelRepository.getHotels(params);
                }
            }
        }
        if(params.size() == 0){
            return hotelRepository.getHotels(params);
        }
        throw new InvalidParamQuantityException();
    }

    @Override
    public ResponseDTO makeBooking(PayloadDTO payload) throws Exception {
        if(payload.getBooking() == null){
            throw new InvalidPayloadException("Invalid payload. Please make sure you are sending a Booking when you POST /api/v1/hotels/booking.");
        } else {
            validator.validatePayload(payload);
        }
        return hotelRepository.makeBooking(payload);
    }
}
