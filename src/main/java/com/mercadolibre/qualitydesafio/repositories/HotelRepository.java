package com.mercadolibre.qualitydesafio.repositories;

import com.mercadolibre.qualitydesafio.dto.HotelDTO;
import com.mercadolibre.qualitydesafio.dto.PayloadDTO;
import com.mercadolibre.qualitydesafio.dto.ResponseDTO;

import java.util.List;
import java.util.Map;

public interface HotelRepository {
    List<HotelDTO> getHotels(Map<String,String> params) throws Exception;
    ResponseDTO makeBooking(PayloadDTO payload) throws Exception;
}
