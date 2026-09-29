package com.roombook.booking;

import com.roombook.employee.EmployeeEntity;
import com.roombook.room.RoomEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="bookings")
public class BookingEntity {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) @JoinColumn(name="room_id",nullable=false) private RoomEntity room;
    @ManyToOne(optional=false) @JoinColumn(name="organizer_id",nullable=false) private EmployeeEntity organizer;
    @Column(nullable=false) private LocalDateTime startTime;
    @Column(nullable=false) private LocalDateTime endTime;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private BookingStatus status;
    private LocalDateTime checkInTime;
    public BookingEntity(){}
    public Long getId(){return id;} public RoomEntity getRoom(){return room;} public EmployeeEntity getOrganizer(){return organizer;}
    public LocalDateTime getStartTime(){return startTime;} public LocalDateTime getEndTime(){return endTime;} public BookingStatus getStatus(){return status;} public LocalDateTime getCheckInTime(){return checkInTime;}
    public void setId(Long id){this.id=id;} public void setRoom(RoomEntity room){this.room=room;} public void setOrganizer(EmployeeEntity organizer){this.organizer=organizer;}
    public void setStartTime(LocalDateTime v){this.startTime=v;} public void setEndTime(LocalDateTime v){this.endTime=v;} public void setStatus(BookingStatus v){this.status=v;} public void setCheckInTime(LocalDateTime v){this.checkInTime=v;}
}
