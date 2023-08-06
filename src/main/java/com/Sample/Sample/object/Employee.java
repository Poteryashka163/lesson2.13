package com.Sample.Sample.object;

public class Employee {

    private final String firstName;
    private final String lastName;
    private final Integer departmentId;
    private final double salary;

    public Employee(String firstName, String lastName, Integer departmentId, double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentId = departmentId;
        this.salary = salary;
    }

    //конструктор для нормальной сборки, не используется в данном дз
    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentId = 0;
        this.salary = 0;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public double getSalary() {
        return salary;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        if (departmentId != employee.departmentId) return false;
        if (Double.compare(employee.salary, salary) != 0) return false;
        if (getFirstName() != null ? !getFirstName().equals(employee.getFirstName()) : employee.getFirstName() != null)
            return false;
        return getLastName() != null ? getLastName().equals(employee.getLastName()) : employee.getLastName() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getFirstName() != null ? getFirstName().hashCode() : 0;
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + departmentId;
        temp = Double.doubleToLongBits(salary);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", departmentId=" + departmentId +
                ", salary=" + salary +
                '}';
    }
}
