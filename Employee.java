package com.example.hrmanagement;

public class Employee {

    private int employeeId;
    private String name;
    private String position;
    private String department;
    private String email;
    private String phone;
    private String salary;

    // Default constructor
    public Employee(int employeeId, String name, String position, String department, String email, String phone, Double salary) {
    }

    // Full constructor
    public Employee(int employeeId, String name, String position, String department, String email, String phone, String salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
    }

    // Getters and Setters
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
