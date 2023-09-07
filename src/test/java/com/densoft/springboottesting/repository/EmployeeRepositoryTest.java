package com.densoft.springboottesting.repository;

import com.densoft.springboottesting.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .firstName("Dennis")
                .lastName("Githinji")
                .email("wakahiad@gmail.com")
                .build();
    }

    //junit test for save employee function
    @DisplayName("Test save employee operation")
    @Test
    void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        //given -precondition or setup
        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.save(employee);
        //then verify the output
        assertNotNull(savedEmployee);
        assertTrue(savedEmployee.getId() > 0);

    }

    // junit test for get all employees
    @DisplayName("junit test for get all employees ")
    @Test
    void givenEmployeesList_whenFindAll_thenReturnEmployeesList() {
        //given  - precondition or setup
        Employee employeeTwo = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@gmail.com")
                .build();
        List<Employee> newEmployees = List.of(employee, employeeTwo);
        employeeRepository.saveAll(newEmployees);
        //when - action or the behaviour that we are going to test
        List<Employee> employees = employeeRepository.findAll();
        //then - verify the output
        assertNotNull(employees);
        assertEquals(newEmployees.size(), employees.size());
    }

    // junit test for get employee by id
    @DisplayName("junit test for get employee by Id ")
    @Test
    void givenEmployeeId_whenFindById_thenReturnEmployee() {
        //given  - precondition or setup
        Employee savedEmployee = employeeRepository.save(employee);
        //when - action or the behaviour that we are going to test
        Employee fetchedEmployee = employeeRepository.findById(savedEmployee.getId()).get();
        //then - verify the output
        assertNotNull(fetchedEmployee);
        assertEquals(savedEmployee.getId(), fetchedEmployee.getId());
    }

    // junit test for
    @DisplayName("junit test for find employee by email")
    @Test
    void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {
        //given  - precondition or setup
        Employee savedEmployee = employeeRepository.save(employee);
        //when - action or the behaviour that we are going to test
        Employee fetchedEmployee = employeeRepository.findByEmail(savedEmployee.getEmail()).get();
        //then - verify the output
        assertNotNull(fetchedEmployee);
        assertEquals(savedEmployee.getEmail(), fetchedEmployee.getEmail());
    }

    // junit test for update operations
    @DisplayName("junit test for update operations ")
    @Test
    void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        //given  - precondition or setup
        Employee savedEmployee = employeeRepository.save(employee);
        //when - action or the behaviour that we are going to test
        Employee fetchedEmployee = employeeRepository.findById(savedEmployee.getId()).get();
        fetchedEmployee.setFirstName("harun");
        fetchedEmployee.setEmail("harun@gmail.com");
        Employee updatedEmployee = employeeRepository.save(fetchedEmployee);
        //then - verify the output
        assertEquals("harun", updatedEmployee.getFirstName());
        assertEquals("harun@gmail.com", updatedEmployee.getEmail());
    }

    // junit test for delete employee
    @DisplayName("junit test for delete employee")
    @Test
    void givenEmployeeObject_whenDeleteEmployee_thenDeleteEmployee() {
        //given  - precondition or setup
        Employee savedEmployee = employeeRepository.save(employee);
        //when - action or the behaviour that we are going to test
        employeeRepository.delete(savedEmployee);
        //then - verify the output
        assertTrue(employeeRepository.findAll().isEmpty());
        assertTrue(employeeRepository.findById(savedEmployee.getId()).isEmpty());
    }

    // junit test for custom query using jpql with indexed params
    @DisplayName("junit test for custom query with JPQL using indexed parameters ")
    @Test
    void givenFirstNameAndLastName_whenFindByBothNames_thenReturnEmployeeObject() {
        //given  - precondition or setup
        Employee savedEmployee = employeeRepository.save(employee);
        //when - action or the behaviour that we are going to test
        Employee fetchedEmployee = employeeRepository.findByBothNames(savedEmployee.getFirstName(), savedEmployee.getLastName());
        //then - verify the output
        assertNotNull(fetchedEmployee);
    }


    // junit test for custom query using jpql with named params
    @DisplayName("junit test for custom query with JPQL using named parameters ")
    @Test
    void givenFirstNameAndLastName_whenFindByBothNamesNamedParameters_thenReturnEmployeeObject() {
        //given  - precondition or setup
        Employee savedEmployee = employeeRepository.save(employee);
        //when - action or the behaviour that we are going to test
        Employee fetchedEmployee = employeeRepository.findByBothNamesNamedParams(savedEmployee.getFirstName(), savedEmployee.getLastName());
        //then - verify the output
        assertNotNull(fetchedEmployee);
    }


    // junit test for custom query with Native SQL using indexed params
    @DisplayName("junit test for custom query with Native SQL using indexed parameters ")
    @Test
    void givenFirstNameAndLastName_whenFindByBothNamesNativeQueryIndexedParams_thenReturnEmployeeObject() {
        //given  - precondition or setup
        Employee savedEmployee = employeeRepository.save(employee);
        //when - action or the behaviour that we are going to test
        Employee fetchedEmployee = employeeRepository.findByBothNamesNativeQuery(savedEmployee.getFirstName(), savedEmployee.getLastName());
        //then - verify the output
        assertNotNull(fetchedEmployee);
    }


    // junit test for custom query with Native SQL using named params
    @DisplayName("junit test for custom query with Native SQL using named parameters ")
    @Test
    void givenFirstNameAndLastName_whenFindByBothNamesNativeQueryNamedParams_thenReturnEmployeeObject() {
        //given  - precondition or setup
        Employee savedEmployee = employeeRepository.save(employee);
        //when - action or the behaviour that we are going to test
        Employee fetchedEmployee = employeeRepository.findByBothNamesNativeQueryNamedParams(savedEmployee.getFirstName(), savedEmployee.getLastName());
        //then - verify the output
        assertNotNull(fetchedEmployee);
    }
}