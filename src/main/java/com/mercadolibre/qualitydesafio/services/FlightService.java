package com.mercadolibre.qualitydesafio.services;

import com.mercadolibre.qualitydesafio.dto.FlightDTO;
import com.mercadolibre.qualitydesafio.dto.PayloadDTO;
import com.mercadolibre.qualitydesafio.dto.ResponseDTO;

import java.util.List;
import java.util.Map;

public interface FlightService {
    List<FlightDTO> getFlights(Map<String, String> params) throws Exception;

    ResponseDTO makeReservation(PayloadDTO payload) throws Exception;
}
