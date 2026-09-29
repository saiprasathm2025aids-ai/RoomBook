package com.roombook.employee;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService service;
    public EmployeeController(EmployeeService service){this.service=service;}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public EmployeeEntity create(@Valid @RequestBody EmployeeDTO dto){return service.createEmployee(dto);}
    @GetMapping public List<EmployeeEntity> all(){return service.getAllEmployees();}
    @GetMapping("/{id}") public EmployeeEntity one(@PathVariable Long id){return service.getEmployeeById(id);}
    @PutMapping("/{id}") public EmployeeEntity update(@PathVariable Long id,@Valid @RequestBody EmployeeDTO dto){return service.updateEmployee(id,dto);}
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){service.deleteEmployee(id);}
}
