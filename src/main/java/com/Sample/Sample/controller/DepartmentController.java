package com.Sample.Sample.controller;
import com.Sample.Sample.object.Employee;
import com.Sample.Sample.service.DepartmentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/max-salary")
    public Employee withMaxSalary(@RequestParam int departmentId) {
        return departmentService.withMaxSalary(departmentId);
    }

    @GetMapping("/min-salary")
    public Employee withMinSalary(@RequestParam int departmentId) {
        return departmentService.withMinSalary(departmentId);
    }

    @GetMapping("/all")
    public Map<Integer, List<Employee>> employeesByDepartment(@RequestParam(required = false) Integer departmentId) {
        return departmentService.employeesByDepartment(departmentId);
    }

    @GetMapping(path = "/{id}/employees")
    public List<Employee> getEmployeesByDepId(@PathVariable int id) {
        return departmentService.getEmployeesByDepId(id);
    }

    @GetMapping(path = "/{id}/salary/sum")
    public double getSalarySumByDepId(@PathVariable int id) {
        return departmentService.getSalarySumByDepId(id);
    }

    @GetMapping(path = "/{id}/salary/max")
    public double getMaxSalaryByDepId(@PathVariable int id) {
        return departmentService.getMaxSalaryByDepId(id);
    }

    @GetMapping(path = "/{id}/salary/min")
    public double getMinSalaryByDepId(@PathVariable int id) {
        return departmentService.getMinSalaryByDepId(id);
    }
}
