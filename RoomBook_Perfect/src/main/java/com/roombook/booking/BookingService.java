package com.roombook.booking;

import com.roombook.common.exception.BusinessRuleException;
import com.roombook.common.exception.ResourceNotFoundException;
import com.roombook.employee.EmployeeEntity;
import com.roombook.employee.EmployeeRepository;
import com.roombook.room.RoomEntity;
import com.roombook.room.RoomRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final EmployeeRepository employeeRepository;

    public BookingService(BookingRepository bookingRepository,RoomRepository roomRepository,EmployeeRepository employeeRepository){
        this.bookingRepository=bookingRepository;this.roomRepository=roomRepository;this.employeeRepository=employeeRepository;
    }

    public BookingEntity createBooking(BookingDTO dto){
        validateTimes(dto.startTime(),dto.endTime());
        RoomEntity room=findRoom(dto.roomId());
        EmployeeEntity employee=findEmployee(dto.organizerId());
        ensureNoConflict(room.getId(),dto.startTime(),dto.endTime(),null);
        BookingEntity b=new BookingEntity();
        b.setRoom(room);b.setOrganizer(employee);b.setStartTime(dto.startTime());b.setEndTime(dto.endTime());
        b.setStatus(BookingStatus.CONFIRMED);b.setCheckInTime(null);
        return bookingRepository.save(b);
    }

    public List<BookingEntity> getAllBookings(){return bookingRepository.findAll();}
    public BookingEntity getBookingById(Long id){return bookingRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Booking not found with id: "+id));}
    public List<BookingEntity> getBookingsByRoom(Long roomId){return bookingRepository.findByRoomId(roomId);}
    public List<BookingEntity> getBookingsByOrganizer(Long employeeId){return bookingRepository.findByOrganizerId(employeeId);}

    public BookingEntity updateBooking(Long id,BookingDTO dto){
        BookingEntity b=getBookingById(id);
        if(b.getStatus()!=BookingStatus.CONFIRMED){throw new BusinessRuleException("Only CONFIRMED bookings can be updated.");}
        validateTimes(dto.startTime(),dto.endTime());
        RoomEntity room=findRoom(dto.roomId());EmployeeEntity employee=findEmployee(dto.organizerId());
        ensureNoConflict(room.getId(),dto.startTime(),dto.endTime(),id);
        b.setRoom(room);b.setOrganizer(employee);b.setStartTime(dto.startTime());b.setEndTime(dto.endTime());
        return bookingRepository.save(b);
    }

    public BookingEntity cancelBooking(Long id){
        BookingEntity b=getBookingById(id);
        if(b.getStatus()!=BookingStatus.CONFIRMED){throw new BusinessRuleException("Only CONFIRMED bookings can be cancelled.");}
        b.setStatus(BookingStatus.CANCELLED);return bookingRepository.save(b);
    }

    public BookingEntity checkIn(Long id){
        BookingEntity b=getBookingById(id);
        if(b.getStatus()==BookingStatus.CANCELLED||b.getStatus()==BookingStatus.RELEASED){
            throw new BusinessRuleException("Check-in is not allowed for a cancelled or released booking.");
        }
        b.setCheckInTime(LocalDateTime.now());b.setStatus(BookingStatus.CONFIRMED);return bookingRepository.save(b);
    }

    @Scheduled(fixedRate=60000)
    public void releaseNoShowBookings(){
        LocalDateTime now=LocalDateTime.now();
        for(BookingEntity b:bookingRepository.findByStatusAndCheckInTimeIsNull(BookingStatus.CONFIRMED)){
            LocalDateTime releaseAt=b.getStartTime().plusMinutes(10);
            if(!now.isBefore(releaseAt)){
                b.setStatus(BookingStatus.RELEASED);bookingRepository.save(b);
            }
        }
    }

    public void deleteBooking(Long id){bookingRepository.delete(getBookingById(id));}

    private RoomEntity findRoom(Long id){return roomRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Room not found with id: "+id));}
    private EmployeeEntity findEmployee(Long id){return employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not found with id: "+id));}
    private void validateTimes(LocalDateTime start,LocalDateTime end){if(start==null||end==null||!end.isAfter(start))throw new BusinessRuleException("Booking end time must be after start time.");}
    private void ensureNoConflict(Long roomId,LocalDateTime start,LocalDateTime end,Long excludedId){
        boolean conflict=bookingRepository.findOverlappingConfirmedBookings(roomId,start,end).stream().anyMatch(b->excludedId==null||!b.getId().equals(excludedId));
        if(conflict)throw new BusinessRuleException("Room is already booked for the selected time range.");
    }
}
