package com.roombook.room;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "rooms")
public class RoomEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Column(nullable = false) private String name;
    @Positive @Column(nullable = false) private int capacity;
    @NotBlank @Column(nullable = false) private String amenities;

    public RoomEntity() {}
    public RoomEntity(String name, int capacity, String amenities) { this.name=name; this.capacity=capacity; this.amenities=amenities; }
    public Long getId(){return id;} public String getName(){return name;} public int getCapacity(){return capacity;} public String getAmenities(){return amenities;}
    public void setId(Long id){this.id=id;} public void setName(String name){this.name=name;} public void setCapacity(int capacity){this.capacity=capacity;} public void setAmenities(String amenities){this.amenities=amenities;}
}
