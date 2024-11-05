package com.example.hrmanagement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class employeeControllerTest {

    @Test
    void calculateSalary() {
        employeeController x =new employeeController();
        assertEquals(x.calculateSalary(4000.00),48000);
    }
}