package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeRequestDTO;
import com.example.employeemanagement.dto.EmployeeResponseDTO;
import com.example.employeemanagement.dto.TaskDTO;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.model.Task;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeeResponseDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public Page<EmployeeResponseDTO> getAllEmployeesPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        return employeePage.map(this::convertToResponseDTO);
    }

    public Optional<EmployeeResponseDTO> getEmployeeById(Long id) {
        return employeeRepository.findById(id).map(this::convertToResponseDTO);
    }

    public EmployeeResponseDTO saveEmployee(EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = new Employee();
//        employee.setFullName(employeeRequestDTO.getFullName());
        employee.setName(employeeRequestDTO.getName());
        employee.setPosition(employeeRequestDTO.getPosition());
        employee.setSalary(employeeRequestDTO.getSalary());
        employee.setDepartment(employeeRequestDTO.getDepartment());

        // Convert TaskDTOs to Task entities
        List<Task> tasks = employeeRequestDTO.getTasks().stream().map(taskDTO -> {
            Task task = new Task();
            task.setDescription(taskDTO.getDescription());
            task.setDueDate(taskDTO.getDueDate());
            task.setEmployee(employee);
            return task;
        }).collect(Collectors.toList());
        employee.setTasks(tasks);

        Employee savedEmployee = employeeRepository.save(employee);
        return convertToResponseDTO(savedEmployee);
    }

    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO employeeRequestDTO) {
        return employeeRepository.findById(id).map(employee -> {
//            employee.setFullName(employeeRequestDTO.getFullName());
            employee.setName(employeeRequestDTO.getName());
            employee.setPosition(employeeRequestDTO.getPosition());
            employee.setSalary(employeeRequestDTO.getSalary());
            employee.setDepartment(employeeRequestDTO.getDepartment());

            // Convert TaskDTOs to Task entities
            List<Task> tasks = employeeRequestDTO.getTasks().stream().map(taskDTO -> {
                Task task = new Task();
                task.setDescription(taskDTO.getDescription());
                task.setDueDate(taskDTO.getDueDate());
                task.setEmployee(employee);
                return task;
            }).collect(Collectors.toList());
            employee.setTasks(tasks);

            Employee updatedEmployee = employeeRepository.save(employee);
            return convertToResponseDTO(updatedEmployee);
        }).orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    private EmployeeResponseDTO convertToResponseDTO(Employee employee) {
        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
        responseDTO.setId(employee.getId());
//        responseDTO.setFullName(employee.getFullName());
        responseDTO.setName(employee.getName());
        responseDTO.setPosition(employee.getPosition());
        responseDTO.setSalary(employee.getSalary());
        responseDTO.setDepartment(employee.getDepartment());
        responseDTO.setTasks(employee.getTasks() != null ?
                employee.getTasks().stream().map(this::convertToTaskDTO).collect(Collectors.toList()) : null);
        return responseDTO;
    }

    private TaskDTO convertToTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setDueDate(task.getDueDate());
        return taskDTO;
    }
}
