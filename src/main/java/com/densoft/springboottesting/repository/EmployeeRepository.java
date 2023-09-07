package com.densoft.springboottesting.repository;

import com.densoft.springboottesting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
