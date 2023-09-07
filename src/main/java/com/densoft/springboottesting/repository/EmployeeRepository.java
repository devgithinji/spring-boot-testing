package com.densoft.springboottesting.repository;

import com.densoft.springboottesting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    @Query("SELECT e FROM Employee e WHERE e.firstName = ?1 AND e.lastName = ?2")
    Employee findByBothNames(String firstName, String lastName);


    @Query("SELECT e FROM Employee e WHERE e.firstName = :firstName AND e.lastName = :secondName")
    Employee findByBothNamesNamedParams(@Param("firstName") String firstName, @Param("secondName") String lastName);

    @Query(value = "SELECT * FROM employees e WHERE e.first_name = ?1 AND e.last_name = ?2", nativeQuery = true)
    Employee findByBothNamesNativeQuery(String firstName, String lastName);

    @Query(value = "SELECT * FROM employees e WHERE e.first_name = :firstName  AND e.last_name = :secondName", nativeQuery = true)
    Employee findByBothNamesNativeQueryNamedParams(@Param("firstName") String firstName, @Param("secondName") String lastName);


}
