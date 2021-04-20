package com.mercadolibre.qualitydesafio.utils;

import com.mercadolibre.qualitydesafio.dto.*;
import com.mercadolibre.qualitydesafio.exceptions.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class Validator {
    // Payload validation.
    public Boolean validatePayload(PayloadDTO payload) throws Exception {
        if(payload.getUserName() == null || (payload.getFlightReservation() == null && payload.getBooking() == null)){
            throw new InvalidPayloadException("Invalid payload. Please make sure you have: 'username' and 'booking'/'flightReservation' parameters.");
        } else {
            validateEmail("userName", payload.getUserName());
            if(payload.getBooking() != null && payload.getFlightReservation() == null) {
                BookingDTO booking = payload.getBooking();
                validateBooking(booking);
                if(Integer.parseInt(payload.getBooking().getPeopleAmount()) != payload.getBooking().getPeople().size()){
                    throw new InvalidPeopleException();
                }
            }
            if(payload.getFlightReservation() != null && payload.getBooking() == null){
                ReservationDTO reservation = payload.getFlightReservation();
                validateReservation(reservation);
                if(Integer.parseInt(payload.getFlightReservation().getSeats()) != payload.getFlightReservation().getPeople().size()){
                    throw new InvalidPeopleException();
                }
            }
        }
        return true;
    }

    // All booking's parameters validation.
    public Boolean validateBooking(BookingDTO booking) throws Exception {
        validateDates(booking.getDateFrom(), booking.getDateTo());
        validateStringParams("destination", booking.getDestination());
        validateStringParams("hotelCode", booking.getHotelCode());
        validateStringParams("peopleAmount", booking.getPeopleAmount());
        validateStringParams("roomType", booking.getRoomType());
        validateRoomType(booking.getRoomType(), booking.getPeopleAmount());
        validatePeople(booking.getPeople());
        validatePayment(booking.getPaymentMethod());
        return true;
    }

    public void validateReservation(ReservationDTO reservation) throws Exception {
        validateDates(reservation.getDateFrom(), reservation.getDateTo());
        validateStringParams("destination", reservation.getDestination());
        validateStringParams("origin", reservation.getOrigin());
        validateStringParams("seats", reservation.getSeats());
        validateStringParams("seatType", reservation.getSeatType());
        validatePeople(reservation.getPeople());
        validatePayment(reservation.getPaymentMethod());
    }

    // Roomtype validation.
    public boolean validateRoomType(String roomType, String peopleAmount){
        if(roomType.equalsIgnoreCase("Single") && !peopleAmount.equalsIgnoreCase("1")) return false;
        if(roomType.equalsIgnoreCase("Double") && !peopleAmount.equalsIgnoreCase("2")) return false;
        if(roomType.equalsIgnoreCase("Triple") && !peopleAmount.equalsIgnoreCase("3")) return false;
        if(roomType.equalsIgnoreCase("Multiple") && Integer.parseInt(peopleAmount) < 4) return false;
        return true;
    }

    // Emails validation.
    public Boolean validateEmail(String type, String email) throws Exception {
        if(email.matches("^\\S+@\\S+$")){
            return true;
        } else {
            throw new InvalidEmailException(type);
        }
    }

    // Date validation.
    private Boolean validateDates(String dateFrom, String dateTo) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate auxFrom;
        LocalDate auxTo;
        try{
            auxFrom = LocalDate.parse(dateFrom,formatter);
        } catch (Exception e) {
            throw new InvalidParamValueException("dateFrom");
        }

        try{
            auxTo = LocalDate.parse(dateTo,formatter);
        } catch (Exception e) {
            throw new InvalidParamValueException("dateTo");
        }
        // Checking 'dateFrom' is not after 'dateTo'
        if(auxFrom.isAfter(auxTo)) throw new InvalidDateException();
        return true;
    }

    // String validations depending on type.
    public Boolean validateStringParams(String param, String data) throws Exception{
        if(data == null) {
            throw new InvalidParamValueException(param);
        } else {
            switch(param){
                case "name": if(data.length() < 3) { throw new InvalidPeopleNameException(); }
                    break;
                case "origin": if(data.length() < 3) { throw new InvalidDestinationException(); }
                    break;
                case "destination": if(data.length() < 3) { throw new InvalidDestinationException(); }
                    break;
                case "roomType": if(!data.equalsIgnoreCase("Single") && !data.equalsIgnoreCase("Double") && !data.equalsIgnoreCase("Triple") && !data.equalsIgnoreCase("Múltiple")){
                    throw new InvalidRoomTypeException();
                }
                    break;
                case "hotelCode": if(data.length() < 7 || data.length() > 8){
                    throw new InvalidCodeException("'hotelCode' parameter is not valid. It should have 7 or 8 length.");
                }
                    break;
                case "peopleAmount": if (!isNumeric(data)){
                    throw new InvalidPeopleAmountException();
                }
                    break;
                case "cardType": if(!data.equalsIgnoreCase("CREDIT") && !data.equalsIgnoreCase("DEBIT")){
                    throw new InvalidCardTypeException();
                }
                    break;
                case "seats": if(!isNumeric(data)){
                    throw new InvalidSeatQuantityException();
                }
                    break;
                case "seatType": if(!data.equalsIgnoreCase("ECONOMY") && !data.equalsIgnoreCase("BUSINESS")){
                    throw new InvalidSeatTypeException();
                }
                    break;
            }
        }
        return true;
    }

    // People validation. Name longer than 3 chars and Mail containing '@' and '.sth'.
    private void validatePeople(List<PeopleDTO> people) throws Exception{
        for(PeopleDTO p : people){
            validateStringParams("name", p.getName());
            validateEmail("mail", p.getMail());
        }
    }

    // Payment validation.
    // - if card is 'debit' -> only 1 due allowed.
    // - if card is 'credit' -> only 1, 3, 6, 9, or 12 due allowed.
    private void validatePayment(PaymentMethodDTO paymentMethod) throws Exception{
        validateStringParams("cardType", paymentMethod.getType());
        if(paymentMethod.getType().equals("DEBIT") && paymentMethod.getDues() != 1){
            throw new InvalidDuesException("'1'", "debit");
        }
        if(paymentMethod.getType().equals("CREDIT")){
            if(paymentMethod.getDues() != 1 && paymentMethod.getDues() != 3 && paymentMethod.getDues() != 6 && paymentMethod.getDues() != 9 && paymentMethod.getDues() != 12){
                throw new InvalidDuesException("'1', '3', '6', '9', or '12'", "credit");
            }
        }
    }

    // Check if string is numeric.
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    // Keys validating method.
    public Boolean validateKeys(Map<String, String> params, String type) throws Exception {
        // Validating that keys are the expected.
        if(type.equals("hotelSearch")){
            if(!params.containsKey("dateFrom") || !params.containsKey("dateTo") || !params.containsKey("destination")){
                throw new InvalidParamKeyException();
            }
        }
        if(type.equals("hotelBooking")){
            if(!params.containsKey("userName") || !params.containsKey("booking")){
                throw new InvalidParamKeyException();
            }
        }

        if(type.equals("flightSearch")){
            if(!params.containsKey("origin") || !params.containsKey("destination") || !params.containsKey("dateFrom") || !params.containsKey("dateTo")){
                throw new InvalidParamKeyException();
            }
        }
        return true;
    }

    // Values validating method.
    public Boolean validateSearchValues(Map<String, String> params) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate from = LocalDate.now();
        LocalDate to = LocalDate.now();

        // Validating parameters depending of it's key.
        for(Map.Entry<String,String> entry: params.entrySet()){
            // Validating dates.
            if(entry.getKey().equalsIgnoreCase("dateTo") || entry.getKey().equalsIgnoreCase("dateFrom")){
                try{
                    LocalDate aux = LocalDate.parse(entry.getValue(),formatter);
                }catch(Exception e){
                    throw new InvalidParamValueException(entry.getKey());
                }

                if(entry.getKey().equalsIgnoreCase("dateFrom")){
                    from = LocalDate.parse(entry.getValue(),formatter);
                }
                if(entry.getKey().equalsIgnoreCase("dateTo")){
                    to = LocalDate.parse(entry.getValue(),formatter);
                }
            }
            // Validating destination and origin.
            if(entry.getKey().equalsIgnoreCase("destination") || entry.getKey().equalsIgnoreCase("origin")){
                if(entry.getValue().length() < 3) {
                    throw new InvalidParamValueException(entry.getKey());
                }
            }
        }
        // Required validation.
        if(from.isAfter(to)) throw new InvalidDateException();
        return true;
    }

    // Check if hotel exists in list
    // HotelCode validation. Check if exists in the database.
    public Boolean hotelExists(String hotelCode, List<HotelDTO> hotels) throws Exception {
        for(HotelDTO hotel : hotels){
            if(hotel.getCode().equalsIgnoreCase(hotelCode)) return true;
        }
        throw new InvalidCodeException("Hotel with code '" + hotelCode + "' does not exist.");
    }

    public Boolean flightExists(String flightCode, List<FlightDTO> flights) throws Exception {
        System.out.println("Buscando: " + flightCode);
        for(FlightDTO flight : flights){
            if(flight.getFlightNumber().equalsIgnoreCase(flightCode)) return true;
        }
        throw new InvalidCodeException("Flight with code '" + flightCode + "' does not exist.");
    }


    // Return hotel by hotelCode.
    public HotelDTO findHotelByCode(String hotelCode, List<HotelDTO> hotels) throws Exception {
        for(HotelDTO hotel : hotels){
            if(hotel.getCode().equalsIgnoreCase(hotelCode)) return hotel;
        }
        throw new InvalidCodeException("Hotel with code '" + hotelCode + "' does not exist.");
    }

    // Return flight by flightCode.
    public FlightDTO findFlightByCode(String flightCode, List<FlightDTO> flights) throws Exception {
        for(FlightDTO flight : flights){
            if(flight.getFlightNumber().equalsIgnoreCase(flightCode)) return flight;
        }
        throw new InvalidCodeException("Flight with code '" + flightCode + "' does not exist.");
    }

    // Destination validation. Check if exists in the database.
    public Boolean destinationExists(String destination, List<String> destinationsAvailable) throws Exception{
        for(String dest : destinationsAvailable){
            if(dest.equalsIgnoreCase(destination)) return true;
        }
        throw new InvalidDestinationException();
    }

    public boolean originExists(String origin, List<String> originsAvailable) throws Exception {
        for(String orig : originsAvailable){
            if(orig.equalsIgnoreCase(origin)) return true;
        }
        throw new InvalidOriginException();
    }
}
