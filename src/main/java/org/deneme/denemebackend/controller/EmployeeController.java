package org.deneme.denemebackend.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.deneme.denemebackend.exception.ResourceNotFoundException;
import org.deneme.denemebackend.model.Employee;
import org.deneme.denemebackend.repository.EmployeeRepository;

//crossorigin domain seysi benim portum mu olmalı?
//++ belirli bir endpointe gelen dışarıdan isteklere izin verecek.  frontend --> backende erişecek.
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    //tüm çalışanları getirmek için (get kısmı)
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    //çalışanın restapisini oluşturma araştır
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        return ResponseEntity.ok(employee);
    }
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        employee.setDepartment(employeeDetails.getDepartment());

        Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
