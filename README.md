# Desafío Quality



## Hotels API

__Resume__:
- List available hotels: GET (/api/v1/hotels).
- Make hotel booking: POST (/api/v1/articles/booking).

### List available hotels:

__GET (/api/v1/hotels)__

Se pueden recibir 0 o 3 parámetros.
Si ingresamos 0, se ven los hoteles disponibles. Si ingresamos los 3 parámetros se filtra según ellos.

No se permite otra cantidad de parámetros.
- Se valida:
    - que las keys de los parámetros sean los permitidos (hotelCode,name,city,roomType,price,dateFrom,dateTo,reserved).
    - se valida cada value con su respectiva regla según su key.

### Make hotel booking:
__POST (/api/v1/hotels/booking)__

Se debe enviar un Payload como el siguiente:

```json
{
    "userName" : "seba_gonzalez@unmail.com",
    "booking" : {
        "dateFrom" : "10/11/2021",
        "dateTo" : "20/11/2021",
        "destination" : "Buenos Aires",
        "hotelCode" : "CH-0002",
        "peopleAmount" : 2,
        "roomType" : "DOUBLE",
        "people" : [
            {
                "dni" : "12345678",
                "name" : "Pepito",
                "lastName" : "Gomez",
                "birthDate" : "10/11/1982",
                "mail" : "pepitogomez@gmail.com"
            },
             {
                "dni" : "13345678",
                "name" : "Fulanito",
                "lastName" : "Gomez",
                "birthDate" : "10/11/1983",
                "mail" : "fulanitogomez@gmail.com"
            }
        ],
        "paymentMethod" : {
            "type" : "CREDIT",
            "number" : "1234-1234-1234-1234",
            "dues" : 6
        }
    }
}
```



Se valida cada uno de los campos del Payload: userName, booking (todos los parámetros, incluyendo los PeopleDTO de la List<PeopleDTO>), y el paymentMethod.

Se hacen todas las validaciones especificadas en la letra:

- Si todo está OK:
    - se realiza el Booking, cambiando el reserved del hotel.
    - se devuelve un ResponseDTO.

## Flights API

__Resume__:
- List available flights: GET (/api/v1/flights).
- Make flight reservation: POST (/api/v1/articles/flight-reservation).

### List available flights:
__GET (/api/v1/flights)__

Se pueden recibir 0 o 4 parámetros.
Si ingresamos 0, se ven todos los vuelos. Si ingresamos los 4 parámetros se filtra según ellos.

No se permite otra cantidad de parámetros.
Se valida lo mismo que en HotelDTO, aplicado sobre FlightDTO.

### Make flight reservation
__POST (/api/v1/flights/make-reservation)__

Se debe enviar un Payload como el siguiente:

```json
{
    "userName" : "seba_gonzalez@unmail.com",
    "flightReservation" : {
        "dateFrom" : "10/11/2021",
        "dateTo" : "20/11/2021",
        "origin" : "Buenos Aires",
        "destination" : "Puerto Iguazú",
        "flightNumber" : "BAPI-1235",
        "seats" : 2,
        "seatType" : "ECONOMY",
        "people" : [
            {
                "dni" : "12345678",
                "name" : "Pepito",
                "lastName" : "Gomez",
                "birthDate" : "10/11/1982",
                "mail" : "arjonamiguel@gmail.com"
            },
             {
                "dni" : "13345678",
                "name" : "Fulanito",
                "lastName" : "Gomez",
                "birthDate" : "10/11/1983",
                "mail" : "arjonamiguel2@gmail.com"
            }
        ],
        "paymentMethod" : {
            "type" : "CREDIT",
            "number" : "1234-1234-1234-1234",
            "dues" : 6
        }
    }
}
```

Se valida cada uno de los campos del Payload: userName, flightReservation (todos los parámetros, incluyendo los PeopleDTO de la List<PeopleDTO>), y el paymentMethod.

Se hacen todas las validaciones especificadas en la letra.

- Si todo está OK:
    - se elimina el vuelo de la base de datos.
    - se devuelve un ResponseDTO.