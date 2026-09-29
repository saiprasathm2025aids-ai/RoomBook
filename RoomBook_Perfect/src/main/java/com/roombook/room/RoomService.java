package com.roombook.room;

import com.roombook.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoomService {
    private final RoomRepository repository;
    public RoomService(RoomRepository repository){this.repository=repository;}
    public RoomEntity createRoom(RoomDTO dto){return repository.save(new RoomEntity(dto.name(),dto.capacity(),dto.amenities()));}
    public List<RoomEntity> getAllRooms(){return repository.findAll();}
    public RoomEntity getRoomById(Long id){return repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Room not found with id: "+id));}
    public RoomEntity updateRoom(Long id,RoomDTO dto){RoomEntity r=getRoomById(id);r.setName(dto.name());r.setCapacity(dto.capacity());r.setAmenities(dto.amenities());return repository.save(r);}
    public void deleteRoom(Long id){repository.delete(getRoomById(id));}
}
