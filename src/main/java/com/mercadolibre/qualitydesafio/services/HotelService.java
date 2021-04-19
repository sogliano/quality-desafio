package com.mercadolibre.qualitydesafio.services;

import com.mercadolibre.qualitydesafio.dto.HotelDTO;
import com.mercadolibre.qualitydesafio.dto.PayloadDTO;
import com.mercadolibre.qualitydesafio.dto.ResponseDTO;

import java.util.List;
import java.util.Map;

public interface HotelService {
    List<HotelDTO> getHotels(Map<String, String> params) throws Exception;
    ResponseDTO makeBooking(PayloadDTO payloadDTO) throws Exception;
}
