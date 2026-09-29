package com.roombook.employee;

import com.roombook.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;
    public EmployeeService(EmployeeRepository repository){this.repository=repository;}
    public EmployeeEntity createEmployee(EmployeeDTO dto){return repository.save(new EmployeeEntity(dto.name(),dto.email()));}
    public List<EmployeeEntity> getAllEmployees(){return repository.findAll();}
    public EmployeeEntity getEmployeeById(Long id){return repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not found with id: "+id));}
    public EmployeeEntity updateEmployee(Long id,EmployeeDTO dto){EmployeeEntity e=getEmployeeById(id);e.setName(dto.name());e.setEmail(dto.email());return repository.save(e);}
    public void deleteEmployee(Long id){repository.delete(getEmployeeById(id));}
}
