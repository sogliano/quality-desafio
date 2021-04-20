package com.mercadolibre.qualitydesafio.utils;

import com.mercadolibre.qualitydesafio.dto.FlightDTO;
import com.mercadolibre.qualitydesafio.dto.HotelDTO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    private DatabaseUtils dbUtils = new DatabaseUtils();

    // HotelDTO List load.
    public List<HotelDTO> loadDatabaseHotelsTest(String type) {
        List<HotelDTO> hotels = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        BufferedReader br;
        String line;
        String separator = ",";
        String[] data;
        String csvFile = "src/main/resources/oneHotel.csv";
        switch(type){
            case "oneHotel": csvFile = "src/test/resources/oneHotel.csv";
            break;
            case "filteredHotels": csvFile = "src/test/resources/filteredHotels.csv";
            break;
            case "allHotels": csvFile = "src/test/resources/allHotels.csv";
        }

        try{
            br = new BufferedReader(new FileReader(csvFile));
            while((line = br.readLine()) != null){
                data = line.split(separator);
                // Para ponerlo en formato d/m/y -> formatter.format(localDate)
                if(!data[0].equals("Codigo Hotel")){
                    HotelDTO hotel = new HotelDTO(data[0], data[1], data[2], data[3], Integer.valueOf(data[4].replace("$", "").replace(".", "")), LocalDate.parse(data[5], formatter), LocalDate.parse(data[6], formatter), data[7]);
                    hotels.add(hotel);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return hotels;
    }

    public List<FlightDTO> loadDatabaseFlightsTest(String type) {
        List<FlightDTO> flights = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        BufferedReader br;
        String line;
        String separator = ",";
        String[] data;
        String csvFile = "src/test/resources/oneFlight.csv";
        switch(type){
            case "oneFlight": csvFile = "src/test/resources/oneFlight.csv";
                break;
            case "filteredFlights": csvFile = "src/test/resources/filteredFlights.csv";
                break;
            case "allFlights": csvFile = "src/test/resources/allFlights.csv";
        }

        try{
            br = new BufferedReader(new FileReader(csvFile));
            while((line = br.readLine()) != null){
                data = line.split(separator);
                if(!data[0].equals("Nro Vuelo")){
                    try{
                        FlightDTO flight = new FlightDTO(data[0],data[1],data[2],data[3],Integer.valueOf(data[4].replace("$", "").replace(".","")),LocalDate.parse((data[5]), formatter),LocalDate.parse(data[6], formatter));
                        flights.add(flight);
                    } catch (DateTimeParseException e) {
                        FlightDTO flight = new FlightDTO(data[0],data[1],data[2],data[3],Integer.valueOf(data[4].replace("$", "").replace(".","")),LocalDate.parse(dbUtils.fixDate(data[5]), formatter),LocalDate.parse(dbUtils.fixDate(data[6]), formatter));
                        flights.add(flight);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return flights;
    }
}
