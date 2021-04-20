package com.mercadolibre.qualitydesafio.controllers;

import com.mercadolibre.qualitydesafio.dto.PayloadDTO;
import com.mercadolibre.qualitydesafio.dto.StatusDTO;
import com.mercadolibre.qualitydesafio.exceptions.*;
import com.mercadolibre.qualitydesafio.services.HotelService;
import com.mercadolibre.qualitydesafio.services.HotelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HotelController {

    private HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService){
        this.hotelService = hotelService;
    }


    @GetMapping("/hotels")
    public ResponseEntity getHotels(@RequestParam Map<String, String> params) throws Exception {
        return new ResponseEntity(hotelService.getHotels(params), HttpStatus.OK);
    }

    @PostMapping("/hotels/booking")
    public ResponseEntity makeBooking(@RequestBody PayloadDTO payload) throws Exception {
        return new ResponseEntity(hotelService.makeBooking(payload), HttpStatus.OK);
    }

    @ExceptionHandler(value={InvalidCardTypeException.class})
    public ResponseEntity<StatusDTO> invalidCardTypeException(InvalidCardTypeException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value={InvalidDateException.class})
    public ResponseEntity<StatusDTO> invalidDateException(InvalidDateException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value={InvalidDestinationException.class})
    public ResponseEntity<StatusDTO> invalidDestinationException(InvalidDestinationException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value={InvalidDuesException.class})
    public ResponseEntity<StatusDTO> invalidDuesException(InvalidDuesException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value={InvalidEmailException.class})
    public ResponseEntity<StatusDTO> invalidEmailException(InvalidEmailException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value={InvalidCodeException.class})
    public ResponseEntity<StatusDTO> invalidHotelCodeException(InvalidCodeException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value={InvalidParamKeyException.class})
    public ResponseEntity<StatusDTO> invalidParamKeyException(InvalidParamKeyException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value={InvalidParamQuantityException.class})
    public ResponseEntity<StatusDTO> invalidParamQuantityException(InvalidParamQuantityException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value={InvalidParamValueException.class})
    public ResponseEntity<StatusDTO> invalidParamValueException(InvalidParamValueException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value={InvalidPeopleAmountException.class})
    public ResponseEntity<StatusDTO> invalidPeopleAmountException(InvalidPeopleAmountException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value={InvalidPeopleException.class})
    public ResponseEntity<StatusDTO> invalidPeopleException(InvalidPeopleException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value={InvalidPeopleNameException.class})
    public ResponseEntity<StatusDTO> invalidPeopleNameException(InvalidPeopleNameException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value={InvalidRoomTypeException.class})
    public ResponseEntity<StatusDTO> InvalidRoomTypeException(InvalidRoomTypeException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value={ReservedHotelException.class})
    public ResponseEntity<StatusDTO> reservedHotelException(ReservedHotelException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value={InvalidPayloadException.class})
    public ResponseEntity<StatusDTO> invalidPayloadException(InvalidPayloadException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }
}
