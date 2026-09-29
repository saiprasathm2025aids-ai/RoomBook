package com.roombook.booking;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService service;
    public BookingController(BookingService service){this.service=service;}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public BookingEntity create(@Valid @RequestBody BookingDTO dto){return service.createBooking(dto);}
    @GetMapping public List<BookingEntity> all(){return service.getAllBookings();}
    @GetMapping("/{id}") public BookingEntity one(@PathVariable Long id){return service.getBookingById(id);}
    @GetMapping("/room/{roomId}") public List<BookingEntity> byRoom(@PathVariable Long roomId){return service.getBookingsByRoom(roomId);}
    @GetMapping("/organizer/{employeeId}") public List<BookingEntity> byOrganizer(@PathVariable Long employeeId){return service.getBookingsByOrganizer(employeeId);}
    @PutMapping("/{id}") public BookingEntity update(@PathVariable Long id,@Valid @RequestBody BookingDTO dto){return service.updateBooking(id,dto);}
    @PutMapping("/{id}/cancel") public BookingEntity cancel(@PathVariable Long id){return service.cancelBooking(id);}
    @PutMapping("/{id}/check-in") public BookingEntity checkIn(@PathVariable Long id){return service.checkIn(id);}
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){service.deleteBooking(id);}
}
