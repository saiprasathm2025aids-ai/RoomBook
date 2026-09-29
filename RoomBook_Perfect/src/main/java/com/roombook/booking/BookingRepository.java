package com.roombook.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity,Long> {
    @Query("SELECT b FROM BookingEntity b WHERE b.room.id=:roomId AND b.status=com.roombook.booking.BookingStatus.CONFIRMED AND b.startTime < :endTime AND b.endTime > :startTime")
    List<BookingEntity> findOverlappingConfirmedBookings(@Param("roomId") Long roomId,@Param("startTime") LocalDateTime startTime,@Param("endTime") LocalDateTime endTime);
    List<BookingEntity> findByRoomId(Long roomId);
    List<BookingEntity> findByOrganizerId(Long organizerId);
    List<BookingEntity> findByStatusAndCheckInTimeIsNull(BookingStatus status);
}
