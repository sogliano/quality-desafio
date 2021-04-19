package com.mercadolibre.qualitydesafio.services;

import com.mercadolibre.qualitydesafio.dto.FlightDTO;
import com.mercadolibre.qualitydesafio.dto.PayloadDTO;
import com.mercadolibre.qualitydesafio.dto.ResponseDTO;
import com.mercadolibre.qualitydesafio.exceptions.InvalidParamQuantityException;
import com.mercadolibre.qualitydesafio.exceptions.InvalidPayloadException;
import com.mercadolibre.qualitydesafio.repositories.FlightRepository;
import com.mercadolibre.qualitydesafio.utils.Validator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private Validator validator;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
        this.validator = new Validator();
    }

    @Override
    public List<FlightDTO> getFlights(Map<String, String> params) throws Exception {
        if(params.size() == 4){
            if(validator.validateKeys(params, "flightSearch")){
                if(validator.validateSearchValues(params)){
                    return flightRepository.getFlights(params);
                }
            }
        }
        if(params.size() == 0){
            return flightRepository.getFlights(params);
        }
        throw new InvalidParamQuantityException();
    }

    @Override
    public ResponseDTO makeReservation(PayloadDTO payload) throws Exception {
        if(payload.getFlightReservation() == null){
            throw new InvalidPayloadException("Invalid payload. Please make sure you are sending a FlightReservation when you POST /api/v1/flights/flight-reservation.");
        } else {
            validator.validatePayload(payload);
        }
        return flightRepository.makeReservation(payload);
    }
}
