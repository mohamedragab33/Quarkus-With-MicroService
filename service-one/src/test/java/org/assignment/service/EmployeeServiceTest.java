package org.assignment.service;

import io.smallrye.mutiny.Uni;
import org.assignment.dto.EmployeeReq;
import org.assignment.dto.EmployeeRes;
import org.assignment.entity.Employee;
import org.assignment.mapper.EntityMapper;
import org.assignment.repository.EmployeeRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    private EntityMapper mapper;
    private EmployeeReq employeeReq;
    private Employee employee;
    private EmployeeRes employeeRes;
    private String employeeId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        employeeId = new ObjectId().toString();
        employeeReq = new EmployeeReq();
        employeeReq.setName("John Doe");
        employeeReq.setDepartmentId(new ObjectId().toString()); // Assuming departmentId is required

        employee = new Employee();
        employee.setId(new ObjectId(employeeId));
        employee.setName("John Doe");
        employee.setDepartmentId(new ObjectId(employeeReq.getDepartmentId())); // Set department ID

        employeeRes = new EmployeeRes();
        employeeRes.setId(employeeId);
        employeeRes.setName("John Doe");
        employeeRes.setDepartmentId(employeeReq.getDepartmentId()); // Set department ID in response

        mapper = EntityMapper.INSTANCE;
    }

    @Test
    void testCreateEmployee() {
        when(employeeRepository.persist(any(Employee.class))).thenReturn(Uni.createFrom().item(employee));

        Uni<EmployeeRes> result = employeeService.createEmployee(employeeReq);
        assertDoesNotThrow(() -> result.await().indefinitely());
        assertEquals(employeeRes, result.await().indefinitely());
        verify(employeeRepository).persist(any(Employee.class));
    }

    @Test
    void testGetEmployeeById() {
        when(employeeRepository.findById(any(ObjectId.class))).thenReturn(Uni.createFrom().item(employee));

        Uni<EmployeeRes> result = employeeService.getEmployeeById(employeeId);

        assertDoesNotThrow(() -> result.await().indefinitely());
        assertEquals(employeeRes, result.await().indefinitely());
        verify(employeeRepository).findById(any(ObjectId.class));
    }

    @Test
    void testGetAllEmployees() {
        when(employeeRepository.listAll()).thenReturn(Uni.createFrom().item(Collections.singletonList(employee)));

        Uni<List<EmployeeRes>> result = employeeService.getAllEmployees();

        assertDoesNotThrow(() -> result.await().indefinitely());
        assertEquals(1, result.await().indefinitely().size());
        verify(employeeRepository).listAll();
    }
}
