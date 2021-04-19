package com.mercadolibre.qualitydesafio.repositories;

import com.mercadolibre.qualitydesafio.dto.*;
import com.mercadolibre.qualitydesafio.exceptions.ReservedHotelException;
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
public class HotelRepositoryImpl implements HotelRepository {

    private DatabaseUtils dbUtils = new DatabaseUtils();
    private Calculator calculator = new Calculator();
    private Validator validator = new Validator();
    private List<HotelDTO> hotelDatabaseModify = dbUtils.loadHotelsDatabase();

    @Override
    public List<HotelDTO> getHotels(Map<String, String> params) throws Exception {
        List<HotelDTO> aux = new ArrayList<>();

        if(params.size() == 0) {
            for(HotelDTO h : this.hotelDatabaseModify){
                if(h.getReserved().equals("NO")){ aux.add(h); }
            }
            return aux;
        } else {
            ArrayList<String> destinations = new ArrayList<>();
            for(HotelDTO h : this.hotelDatabaseModify){
                destinations.add(h.getCity());
            }
            if(validator.destinationExists(params.get("destination"), destinations)){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                LocalDate from = LocalDate.parse(params.get("dateFrom"),formatter);
                LocalDate to = LocalDate.parse(params.get("dateTo"),formatter);
                for(HotelDTO hotel : this.hotelDatabaseModify){
                    if(hotel.getCity().equals(params.get("destination"))){
                        if((from.isAfter(hotel.getDateFrom()) || from.isEqual(hotel.getDateFrom())) && (to.isBefore(hotel.getDateTo()) || to.isEqual(hotel.getDateTo()))){
                            aux.add(hotel);
                        }
                    }
                }
            }
        }
        return aux;
    }

    @Override
    public ResponseDTO makeBooking(PayloadDTO payload) throws Exception {
        isHotelAvailable(payload.getBooking().getHotelCode());
        ResponseDTO response = new ResponseDTO();
        response.setUserName(payload.getUserName());
        response.setAmount(calculator.calculateHotelAmount(payload.getBooking(), this.hotelDatabaseModify));
        response.setInterest(calculator.calculateInterest(payload.getBooking().getPaymentMethod().getDues()));
        response.setTotal(calculator.calculateTotal(response.getAmount(), response.getInterest()));
        response.setBooking(payload.getBooking());
        response.setStatusCode(new StatusDTO(200, "The process has been completed successfully."));
        modifyDatabase(payload);
        return response;
    }

    private void isHotelAvailable(String hotelCode) throws Exception {
        validator.validateStringParams("hotelCode", hotelCode);
        if (validator.findHotelByCode(hotelCode, this.hotelDatabaseModify).getReserved().equalsIgnoreCase("SI")){
            throw new ReservedHotelException(hotelCode);
        }
    }

    // Update hotel reservation status.
    private void modifyDatabase(PayloadDTO payloadDTO) throws Exception{
        HotelDTO aux = validator.findHotelByCode(payloadDTO.getBooking().getHotelCode(), this.hotelDatabaseModify);
        for(HotelDTO hotel : this.hotelDatabaseModify){
            if(hotel.getCode().equalsIgnoreCase(aux.getCode())){
                hotel.setReserved("SI");
            }
        }
        dbUtils.writeDatabase(this.hotelDatabaseModify, null, "Hotels");
    }
}
