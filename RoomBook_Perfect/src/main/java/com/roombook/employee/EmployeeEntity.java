package com.roombook.employee;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity @Table(name="employees")
public class EmployeeEntity {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @NotBlank @Column(nullable=false) private String name;
    @NotBlank @Email @Column(nullable=false) private String email;
    public EmployeeEntity(){}
    public EmployeeEntity(String name,String email){this.name=name;this.email=email;}
    public Long getId(){return id;} public String getName(){return name;} public String getEmail(){return email;}
    public void setId(Long id){this.id=id;} public void setName(String name){this.name=name;} public void setEmail(String email){this.email=email;}
}
