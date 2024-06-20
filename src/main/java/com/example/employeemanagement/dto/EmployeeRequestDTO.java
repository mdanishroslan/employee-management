package com.example.employeemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import java.util.List;

public class EmployeeRequestDTO {

    @NotBlank(message = "The full name is required")
    @Size(min = 2, max = 100, message = "The length of full name must be between 2 and 100 characters.")
    private String name;

    @NotBlank(message = "Position is mandatory")
    @Size(min = 2, max = 100, message = "The position must be between 2 and 100 characters.")
    private String position;

    @Positive(message = "Salary must be greater than zero")
    @Min(value = 500, message = "Salary must be at least 500")
    @Max(value = 100000, message = "Salary must be at most 100,000")
    private double salary;

    @NotBlank(message = "Department is mandatory")
    private String department;

    private List<TaskDTO> tasks;

    // Getters and setters...
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }
}
