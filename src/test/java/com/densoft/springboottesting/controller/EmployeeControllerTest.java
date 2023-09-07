package com.densoft.springboottesting.controller;

import com.densoft.springboottesting.model.Employee;
import com.densoft.springboottesting.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private ObjectMapper objectMapper;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .firstName("dennis")
                .lastName("githinji")
                .email("wakahiad@gmail.com")
                .build();
    }

    // junit test for create employee
    @DisplayName("junit test for  create employee")
    @Test
    void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        //given  - precondition or setup

        given(employeeService.saveEmployee(any(Employee.class)))
                .willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));
        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));


        //then - verify the output
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    // junit test for get all employees
    @DisplayName("junit test for get all employees ")
    @Test
    void givenEmployeesList_whenGetAllEmployees_thenReturnEmployees() throws Exception {
        //given  - precondition or setup
        Employee employeeTwo = Employee.builder()
                .firstName("jane")
                .lastName("doe")
                .email("jane@gmail.com")
                .build();

        List<Employee> employees = List.of(employee, employeeTwo);

        given(employeeService.getAllEmployees()).willAnswer(invocationOnMock -> employees);

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees"));
        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(employees.size())));
    }

    // junit test for get employee by id
    @DisplayName("junit test for get employee by id ")
    @Test
    void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee() throws Exception {
        //given  - precondition or setup
        long employeeId = 1L;
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));
        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    // junit test for get employee by id not found
    @DisplayName("junit test for get employee by id  not found")
    @Test
    void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeNotFound() throws Exception {
        //given  - precondition or setup
        long employeeId = 1L;
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));
        //then - verify the output
        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    // junit test for update employee
    @DisplayName("junit test for update employee")
    @Test
    void givenUpdatedEmployee_whenUpdateEmployee_thenReturnEmployeeObject() throws Exception {
        //given  - precondition or setup
        long employeeId = 1L;
        Employee updatedEmployee = Employee.builder()
                .firstName("denno")
                .lastName("gg")
                .email("denno@gmail.com")
                .build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));
        given(employeeService.updateEmployee(any(Employee.class))).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));
        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())));
    }

    // junit test for update employee not found
    @DisplayName("junit test for update employee not found")
    @Test
    void givenUpdatedEmployee_whenUpdateEmployee_thenReturnEmployeeObjectNotFound() throws Exception {
        //given  - precondition or setup
        long employeeId = 1L;
        Employee updatedEmployee = Employee.builder()
                .firstName("denno")
                .lastName("gg")
                .email("denno@gmail.com")
                .build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));
        //then - verify the output
        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    // junit test for delete employee
    @DisplayName("junit test for delete employee ")
    @Test
    void givenEmployeeId_whenDeleteEmployee_thenRemoveEmployee() throws Exception {
        //given  - precondition or setup
        long employeeId = 1L;
        willDoNothing().given(employeeService).deleteEmployee(employeeId);
        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employeeId));
        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Deleted successfully")));
    }

}