package com.Sample.Sample.service;
import com.Sample.Sample.exception.EmployeeAlreadyAddedException;
import com.Sample.Sample.exception.EmployeeNotFoundException;
import com.Sample.Sample.exception.InvalidNameException;
import com.Sample.Sample.object.Employee;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmployeeServiceTest {

    private static final String FIRST_NAME_1 = "Ivan";
    private static final String LAST_NAME_1 = "Ivanov";

    private final EmployeeService employeeService = new EmployeeService();

    @Test
    void add_nameNotInUpperCase() {
        //Подготовка входных данных
        String wrongFirsName = "ivan";
        String lastName = "Ivanov";

        //Подготовка ожидаемого результата
        String expectedMessage = "Имя начинается не с заглавной буквы";

        //Начало теста
        Exception exception = assertThrows(InvalidNameException.class, () -> {
            employeeService.add(wrongFirsName, lastName);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void add_success()  {
        //Подготовка входных данных
        String firstName = "Ivan";
        String lastName = "Ivanov";

        //Подготовка ожидаемого результата
        Employee expectedEmployee = new Employee(firstName, lastName);

        //Начало теста
        Employee addedEmployee = employeeService.add(firstName, lastName);
        assertEquals(expectedEmployee, addedEmployee);
    }

    @Test
    void add_employeeAlreadyExist()  {
        //Подготовка входных данных
        String firstName1 = "Ivan";
        String lastName1 = "Ivanov";

        //Подготовка ожидаемого результата
        Employee expectedEmployee1 = new Employee(firstName1, lastName1);
        String expectedErrorMessage = "Сотрудник " + expectedEmployee1 + " уже существует";

        //Начало теста
        Employee addedEmployee = employeeService.add(firstName1, lastName1);
        assertEquals(expectedEmployee1, addedEmployee);

        Exception exception = assertThrows(EmployeeAlreadyAddedException.class, () -> {
            employeeService.add(firstName1, lastName1);
        });

        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void find_success()  {
        //Подготовка входных данных
        fullEmployees();
        String firstName = FIRST_NAME_1;
        String lastName = LAST_NAME_1;

        //Подготовка ожидаемого результата
        Employee expectedEmployee = new Employee(firstName, lastName);

        //Начало теста
        Employee actualEmployee = employeeService.find(firstName, lastName);
        assertEquals(expectedEmployee, actualEmployee);

        cleanEmployees();
    }


    @Test
    void find_withEmployeeNotFoundException()  {
        //Подготовка входных данных
        fullEmployees();
        String firstName = "employeeFirstName";
        String lastName = "employeeLastName";

        //Подготовка ожидаемого результата
        Employee expectedEmployee = new Employee(firstName, lastName);
        String expectedErrorMessage = "Такого сотрудника нет";

        //Начало теста
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.find(firstName, lastName);
        });

        assertEquals(expectedErrorMessage, exception.getMessage());

        cleanEmployees();
    }

    @Test
    void remove_success()  {
        //Подготовка входных данных
        fullEmployees();
        String firstName1 = "Ivan";
        String lastName1 = "Ivanov";

        //Подготовка ожидаемого результата
        Employee expectedEmployee1 = new Employee(firstName1, lastName1);


        //Начало теста
        Employee actualEmployee = employeeService.remove(firstName1, lastName1);
        assertEquals(expectedEmployee1, actualEmployee);

        cleanEmployees();

    }

    private void fullEmployees()  {
        employeeService.add(FIRST_NAME_1, LAST_NAME_1);
        employeeService.add("Ivann", "Ivanovv");
    }

    private void cleanEmployees() {
        employeeService.remove(FIRST_NAME_1, LAST_NAME_1);
        employeeService.remove("Ivann", "Ivanovv");
    }
}
