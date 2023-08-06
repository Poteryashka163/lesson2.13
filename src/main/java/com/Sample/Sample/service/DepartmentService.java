package com.Sample.Sample.service;


import com.Sample.Sample.exception.EmployeeNotFoundException;
import com.Sample.Sample.object.Employee;
import org.springframework.stereotype.Service;

import java.util.Comparator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class DepartmentService {

    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Employee withMaxSalary(int departmentId) {
        return streamByDepartment(departmentId)
                .max(Comparator.comparing(Employee::getSalary))
                .orElseThrow(() -> new EmployeeNotFoundException("Сотрудник не найден"));
    }

    public Employee withMinSalary(int departmentId) {
        return streamByDepartment(departmentId)
                .min(Comparator.comparing(Employee::getSalary))
                .orElseThrow(() -> new EmployeeNotFoundException("Сотрудник не найден"));
    }

    public Map<Integer, List<Employee>> employeesByDepartment(Integer departmentId) {
        return streamByDepartment(departmentId)
                .collect(Collectors.groupingBy(Employee::getDepartmentId, Collectors.toList()));
    }

    private Stream<Employee> streamByDepartment(Integer departmentId) {
        List<Employee> employees = employeeService.getAll();
        return employees.stream()
                .filter(e -> departmentId == null || e.getDepartmentId().equals(departmentId));
    }

    public List<Employee> getEmployeesByDepId(int id) {
        return employeeService.getAll().stream()
                .filter(employee -> employee.getDepartmentId() == id)
                .collect(Collectors.toList());
    }

    public double getSalarySumByDepId(int id) {
        return employeeService.getAll().stream()
                .filter(employee -> employee.getDepartmentId() == id)
                .mapToDouble(Employee::getSalary)
                .sum();
    }

    public double getMaxSalaryByDepId(int id) {
        return employeeService.getAll().stream()
                .filter(employee -> employee.getDepartmentId() == id)
                .mapToDouble(Employee::getSalary)
                .max()
                .getAsDouble();
    }

    public double getMinSalaryByDepId(int id) {
        return employeeService.getAll().stream()
                .filter(employee -> employee.getDepartmentId() == id)
                .mapToDouble(Employee::getSalary)
                .min()
                .getAsDouble();
    }
}
