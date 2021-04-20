package com.mercadolibre.qualitydesafio.utils;

import com.mercadolibre.qualitydesafio.dto.FlightDTO;
import com.mercadolibre.qualitydesafio.dto.HotelDTO;
import com.mercadolibre.qualitydesafio.repositories.FlightRepository;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class DatabaseUtils {
    // .csv updating method.
    public void writeDatabase(List<HotelDTO> hotels, List<FlightDTO> flights, String type) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String csvFile = "src/main/resources/dbHotels.csv";
        String collect = "";
        FileWriter writer = new FileWriter(csvFile);
        if(type.equals("Hotels")){
            collect = "Codigo Hotel,Nombre,Lugar/Ciudad,Tipo de Habitación,Precio por noche,Disponible Desde,Disponible hasta,Reservado\n";
            writer = new FileWriter(csvFile);
            for(HotelDTO hotel: hotels) {
                collect += hotel.getCode() + "," + hotel.getName() + "," + hotel.getCity() + "," + hotel.getRoomType() + "," + "$" + hotel.getPrice() + "," + hotel.getDateFrom().format(formatter) + "," + hotel.getDateTo().format(formatter) + "," + hotel.getReserved()+"\n";
            }
        }
        if(type.equals("Flights")){
            csvFile = "src/main/resources/dbFlights.csv";
            collect = "Nro Vuelo,Origen,Destino,Tipo Asiento,Precio por persona,Fecha ida,Fecha Vuelta\n";
            writer = new FileWriter(csvFile);
            for(FlightDTO flight: flights) {
                collect += flight.getFlightNumber() + "," + flight.getOrigin() + "," + flight.getDestination() + "," + flight.getSeatType() + ",$" + flight.getPrice() + "," + flight.getDateFrom().format(formatter) + "," + flight.getDateTo().format(formatter)+"\n";
            }
        }
        writer.write(collect);
        writer.close();
    }

    // HotelDTO List load.
    public List<HotelDTO> loadHotelsDatabase() {
        List<HotelDTO> hotels = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        BufferedReader br;
        String line;
        String separator = ",";
        String[] data;
        String csvFile = "src/main/resources/dbHotels.csv";

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
        //writeDatabase(hotels, null, "Hotels");
        return hotels;
    }

    public String fixDate(String date){
        String[] dateArr = date.split("/");
        String fixedDate = "";
        int slashCount = 0;
        for(int i = 0; i < dateArr.length; i ++){
            if(dateArr[i].length() == 2 || dateArr[i].length() == 4){
                fixedDate += dateArr[i];
            }
            if(dateArr[i].length() == 1){
                fixedDate += "0" + dateArr[i];
            }
            if(slashCount < 2){
                fixedDate += "/";
                slashCount++;
            }
        }
        return fixedDate;
    }

    public List<FlightDTO> loadFlightsDatabase() {
        List<FlightDTO> flights = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        BufferedReader br;
        String line;
        String separator = ",";
        String[] data;
        String csvFile = "src/main/resources/dbFlights.csv";

        try{
            br = new BufferedReader(new FileReader(csvFile));
            while((line = br.readLine()) != null){
                data = line.split(separator);
                if(!data[0].equals("Nro Vuelo")){
                    try{
                        FlightDTO flight = new FlightDTO(data[0],data[1],data[2],data[3],Integer.valueOf(data[4].replace("$", "").replace(".","")),LocalDate.parse((data[5]), formatter),LocalDate.parse(data[6], formatter));
                        flights.add(flight);
                    } catch (DateTimeParseException e) {
                        FlightDTO flight = new FlightDTO(data[0],data[1],data[2],data[3],Integer.valueOf(data[4].replace("$", "").replace(".","")),LocalDate.parse(fixDate(data[5]), formatter),LocalDate.parse(fixDate(data[6]), formatter));
                        flights.add(flight);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        //writeDatabase(null, flights, "Flights");
        return flights;
    }
}
