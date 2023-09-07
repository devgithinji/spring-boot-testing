package com.densoft.springboottesting.service;

import com.densoft.springboottesting.exception.APIException;
import com.densoft.springboottesting.model.Employee;
import com.densoft.springboottesting.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(1L)
                .firstName("Dennis")
                .lastName("Githinji")
                .email("wakahiad@gmail.com")
                .build();
    }

    // junit test for save employee
    @DisplayName("junit test for save employee ")
    @Test
    void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        //given  - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        //when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeService.saveEmployee(employee);
        //then - verify the output
        assertNotNull(savedEmployee);
        assertEquals(employee.getEmail(), savedEmployee.getEmail());
    }

    // junit test for save employee using existing email
    @DisplayName("junit test for save employee using existing email ")
    @Test
    void givenEmployeeObject_whenSaveEmployee_thenThrowException() {
        //given  - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));
        //when - action or the behaviour that we are going to test
        assertThrows(APIException.class, () -> employeeService.saveEmployee(employee));
        //then - verify the output
        then(employeeRepository).should(never()).save(employee);
    }

    // junit test for get all employees
    @DisplayName("junit test for get all employees ")
    @Test
    void givenEmployeesList_whenFindAllEmployees_thenReturnAllEmployees() {
        //given  - precondition or setup
        Employee employeeTwo = Employee.builder()
                .id(2L)
                .firstName("John")
                .lastName("Doe")
                .email("john@gmail.com")
                .build();
        List<Employee> dbEmployees = List.of(employee, employeeTwo);
        when(employeeRepository.findAll()).thenReturn(dbEmployees);
        //when - action or the behaviour that we are going to test
        List<Employee> fetchedEmployees = employeeRepository.findAll();
        //then - verify the output
        assertFalse(fetchedEmployees.isEmpty());
        assertEquals(dbEmployees.size(), fetchedEmployees.size());
    }

    // junit test for get all employees
    @DisplayName("junit test for get all employees empty list")
    @Test
    void givenEmployeesEmptyList_whenFindAllEmployees_thenEmptyList() {
        //given  - precondition or setup
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());
        //when - action or the behaviour that we are going to test
        List<Employee> fetchedEmployees = employeeRepository.findAll();
        //then - verify the output
        assertTrue(fetchedEmployees.isEmpty());
        assertEquals(0, fetchedEmployees.size());
    }

    // junit test for getEmployeeId
    @DisplayName("junit test for get employeeById ")
    @Test
    void givenEmployeeId_whenGetEmployeeById_thenEmployeeObject() {
        //given  - precondition or setup
        given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));

        //when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

        //then - verify the output
        assertNotNull(employee);
    }

    // junit test for update employee
    @DisplayName("junit test for update employee")
    @Test
    void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        //given  - precondition or setup
        Employee updatedEmployee = Employee.builder()
                .id(1L)
                .firstName("paul")
                .lastName("duke")
                .build();
        given(employeeRepository.save(updatedEmployee)).willReturn(updatedEmployee);

        //when - action or the behaviour that we are going to test
        Employee returnedUpdatedEmployee = employeeService.updateEmployee(updatedEmployee);

        //then - verify the output
        assertNotNull(returnedUpdatedEmployee);
        assertEquals(updatedEmployee.getFirstName(), returnedUpdatedEmployee.getFirstName());
    }

    // junit test for delete employee
    @DisplayName("junit test for delete employee  ")
    @Test
    void givenEmployeeId_whenDeleteEmployee_thenRemoveEmployee() {
        //given  - precondition or setup
        long employeeId = 1L;
        willDoNothing().given(employeeRepository).deleteById(employeeId);
        //when - action or the behaviour that we are going to test
        employeeService.deleteEmployee(employeeId);
        //then - verify the output
        then(employeeRepository).should(atLeastOnce()).deleteById(employeeId);
    }
}