package com.mercadolibre.qualitydesafio.repositories;

import com.mercadolibre.qualitydesafio.dto.*;
import com.mercadolibre.qualitydesafio.utils.Calculator;
import com.mercadolibre.qualitydesafio.utils.DatabaseUtils;
import com.mercadolibre.qualitydesafio.utils.Validator;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Repository
public class FlightRepositoryImpl implements FlightRepository {

    private DatabaseUtils dbUtils = new DatabaseUtils();
    private Calculator calculator = new Calculator();
    private Validator validator = new Validator();
    private List<FlightDTO> flightDatabaseModify = dbUtils.loadFlightsDatabase();

    @Override
    public List<FlightDTO> getFlights(Map<String, String> params) throws Exception {
        List<FlightDTO> aux = new ArrayList<>();
        if(params.size() == 0) {
            return this.flightDatabaseModify;
        } else {
            ArrayList<String> destinations = new ArrayList<>();
            ArrayList<String> origins = new ArrayList<>();
            for(FlightDTO f : this.flightDatabaseModify){
                destinations.add(f.getDestination());
                origins.add(f.getOrigin());
            }
            if(validator.destinationExists(params.get("destination"), destinations) && validator.originExists(params.get("origin"), origins)){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                LocalDate from = LocalDate.parse(params.get("dateFrom"),formatter);
                LocalDate to = LocalDate.parse(params.get("dateTo"),formatter);
                for(FlightDTO flight : this.flightDatabaseModify){
                    if(flight.getDestination().equalsIgnoreCase(params.get("destination")) && flight.getOrigin().equalsIgnoreCase(params.get("origin"))){
                        if((from.isAfter(flight.getDateFrom()) || from.isEqual(flight.getDateFrom())) && (to.isBefore(flight.getDateTo()) || to.isEqual(flight.getDateTo()))){
                            aux.add(flight);
                        }
                    }
                }
            }
        }
        return aux;
    }

    @Override
    public ResponseDTO makeReservation(PayloadDTO payload) throws Exception {
        ResponseDTO response = new ResponseDTO();
        response.setUserName(payload.getUserName());
        response.setAmount(calculator.calculateFlightAmount(payload.getFlightReservation(), this.flightDatabaseModify));
        response.setInterest(calculator.calculateInterest(payload.getFlightReservation().getPaymentMethod().getDues()));
        response.setTotal(calculator.calculateTotal(response.getAmount(), response.getInterest()));
        response.setFlightReservation(payload.getFlightReservation());
        response.setStatusCode(new StatusDTO(200, "The process has been completed successfully."));
        modifyDatabase(payload);
        return response;
    }

    // Update hotel reservation status.
    private void modifyDatabase(PayloadDTO payloadDTO) throws Exception{
        FlightDTO aux = validator.findFlightByCode(payloadDTO.getFlightReservation().getFlightNumber(), this.flightDatabaseModify);
        deleteFlight(aux);
        dbUtils.writeDatabase(null, this.flightDatabaseModify, "Flights");
    }

    private void deleteFlight(FlightDTO flight){
        this.flightDatabaseModify.remove(flight);
    }
}
