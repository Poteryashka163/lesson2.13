package com.Sample.Sample.service;

import com.Sample.Sample.exception.EmployeeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.Sample.Sample.object.Employee;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.stream.Collectors;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DepartmentService.class})
class DepartmentServiceTest {


    @Autowired
    private DepartmentService departmentService;

    @MockBean
    private EmployeeService employeeService;
    List<Employee> employeeList;

    @BeforeEach
    public void init() {
        employeeList = new ArrayList<>();
        employeeList.add(new Employee("Иван", "Иванов", 1, 1000));
        employeeList.add(new Employee("Петр", "Петрович", 2, 1500));
        employeeList.add(new Employee("Алексй", "Иванов", 1, 900));
        employeeList.add(new Employee("Максим", "Петров", 3, 800));
        employeeList.add(new Employee("Альбер", "Шилов", 2, 1200));
        employeeList.add(new Employee("Руслан", "Семенов", 3, 1200));
        when(employeeService.getAll()).thenReturn(employeeList);

    }

    @Test
    void getMaxSalary_Success() {
        int departmentId = 2;

        Employee expectedEmployee = employeeList.get(1);

        Employee actualEmployee = departmentService.withMaxSalary(departmentId);

        assertEquals(expectedEmployee, actualEmployee);
        Mockito.verify(employeeService).getAll();


    }

    @Test
    void getMaxSalary_UnSuccess() {
        int departmentId = 4;
        EmployeeNotFoundException thrown = assertThrows(EmployeeNotFoundException.class, () -> departmentService.withMaxSalary(departmentId));
        assertEquals("Сотрудник не найден", thrown.getMessage());


    }

    @Test
    void getMinSalary_Success() {
        int departmentId = 2;

        Employee expectedEmployee = employeeList.get(4);

        Employee actualEmployee = departmentService.withMinSalary(departmentId);

        assertEquals(expectedEmployee, actualEmployee);
        Mockito.verify(employeeService).getAll();


    }

    @Test
    void getMinSalary_UnSuccess() {
        int departmentId = 4;
        EmployeeNotFoundException thrown = assertThrows(EmployeeNotFoundException.class, () -> departmentService.withMinSalary(departmentId));
        assertEquals("Сотрудник не найден", thrown.getMessage());
    }

    @Test
    void getAllSalaryByDepartment_Success() {
        int departmentId = 2;
        double expectedSumSalary = 2700;

        double actualSumSalary = departmentService.getSalarySumByDepId(departmentId);

        assertEquals(expectedSumSalary, actualSumSalary);
        Mockito.verify(employeeService).getAll();


    }

    @Test
    void getMaxSalaryByDepartment_Success() {
        int departmentId = 2;

        double expectedSalary = 1500;

        double actualSalary = departmentService.getMaxSalaryByDepId(departmentId);

        assertEquals(expectedSalary, actualSalary);
        Mockito.verify(employeeService).getAll();

    }

    @Test
    void getMinSalaryByDepartment_Success() {
        int departmentId = 1;

        double expectedSalary = 900;

        double actualSalary = departmentService.getMinSalaryByDepId(departmentId);

        assertEquals(expectedSalary, actualSalary);
        Mockito.verify(employeeService).getAll();

    }


    @Test
    void employeesByDepartment() {
        int departmentId1 = 1;

        String firstName1 = "Ivan";
        String lastName1 = "Ivanov";
        double salary1 = 88;

        Employee employee1 = new Employee(firstName1, lastName1, departmentId1, salary1);

        String firstName2 = "Petr";
        String lastName2 = "Petrov";
        double salary2 = 999;

        Employee employee2 = new Employee(firstName2, lastName2, departmentId1, salary2);

        String firstName3 = "Petr";
        String lastName3 = "Petrov";
        double salary3 = Double.MAX_VALUE;

        int departmentId30 = 30;
        Employee employee3 = new Employee(firstName3, lastName3, departmentId30, salary3);

        //Подготовка ожидаемого результата
        when(employeeService.getAll()).thenReturn(Arrays.asList(employee1, employee2, employee3));
        Map<Integer, List<Employee>> expectedEmployeesByDepId = new HashMap<>();
        expectedEmployeesByDepId.put(departmentId1, Arrays.asList(employee1, employee2));
        expectedEmployeesByDepId.put(departmentId30, Arrays.asList(employee3));

        //Начало теста
        Map<Integer, List<Employee>> actualEmployeesByDepId = departmentService.employeesByDepartment(null);
        assertEquals(expectedEmployeesByDepId, actualEmployeesByDepId);
        verify(employeeService).getAll();
    }




    @Test
    void getEmployeesByDepId() {
        int departmentId = 3;

        List<Employee> expectedListOfEmployee = employeeList.stream()
                .filter(employee -> employee.getDepartmentId() == departmentId)
                .collect(Collectors.toList());

        List<Employee> actualListOfEmployee = departmentService.getEmployeesByDepId(departmentId);

        assertEquals(expectedListOfEmployee, actualListOfEmployee);
        Mockito.verify(employeeService).getAll();


    }
}