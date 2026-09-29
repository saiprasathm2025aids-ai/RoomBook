package com.roombook.room;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService service;
    public RoomController(RoomService service){this.service=service;}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public RoomEntity create(@Valid @RequestBody RoomDTO dto){return service.createRoom(dto);}
    @GetMapping public List<RoomEntity> all(){return service.getAllRooms();}
    @GetMapping("/{id}") public RoomEntity one(@PathVariable Long id){return service.getRoomById(id);}
    @PutMapping("/{id}") public RoomEntity update(@PathVariable Long id,@Valid @RequestBody RoomDTO dto){return service.updateRoom(id,dto);}
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){service.deleteRoom(id);}
}
