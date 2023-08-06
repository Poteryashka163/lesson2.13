package com.Sample.Sample.service;
import org.apache.commons.lang3.StringUtils;

import com.Sample.Sample.exception.EmployeeAlreadyAddedException;
import com.Sample.Sample.exception.EmployeeNotFoundException;
import com.Sample.Sample.exception.EmployeeStorageIsFullException;
import com.Sample.Sample.exception.InvalidNameException;
import com.Sample.Sample.object.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final List<Employee> employees = new ArrayList<>();
    private final int MAX_SIZE = 10;

    public Employee add(String firstName, String lastName)  {

        checkUpperCase(firstName, lastName);
        checkAlpha(firstName, lastName);

        if (employees.size() >= MAX_SIZE) {
            throw new EmployeeStorageIsFullException("Массив сотрудников переполнен");
        }

        Employee newEmployee = new Employee(firstName, lastName);

        if (employees.contains(newEmployee)) {
            throw new EmployeeAlreadyAddedException("Сотрудник " + newEmployee + " уже существует");
        }

        employees.add(newEmployee);
        return newEmployee;
    }

    private void checkUpperCase(String firstName, String lastName) {
        String capitalizeFirstName = StringUtils.capitalize(firstName);
        String capitalizeLastName = StringUtils.capitalize(lastName);

        if (!(firstName.equals(capitalizeFirstName))) {
            throw new InvalidNameException("Имя начинается не с заглавной буквы");
        }

        if (!(lastName.equals(capitalizeLastName))) {
            throw new InvalidNameException("Фамилия начинается не с заглавной буквы");
        }
    }

    private void checkAlpha(String firstName, String lastName)  {
        if (!StringUtils.isAlpha(firstName)) {
            throw new InvalidNameException("Имя содержит запрещенные символы");
        }

        if (!StringUtils.isAlpha(lastName)) {
            throw new InvalidNameException("Фамилия содержит запрещенные символы");
        }
    }

    public Employee find(String firstName, String lastName) {
        Employee employeeForFind = new Employee(firstName, lastName);

        if (!employees.contains(employeeForFind)) {
            throw new EmployeeNotFoundException("Такого сотрудника нет");
        }

        return employees.get(employees.indexOf(employeeForFind));
    }

    public Employee remove(String firstName, String lastName) {
        Employee employeeForRemove = new Employee(firstName, lastName);

        if (!employees.contains(employeeForRemove)) {
            throw new EmployeeNotFoundException("Такого сотрудника нет");
        }

        employees.remove(employeeForRemove);
        return employeeForRemove;
    }

    public List<Employee> getAll() {
        return employees;
    }
}
