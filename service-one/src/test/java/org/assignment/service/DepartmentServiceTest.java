package org.assignment.service;

import io.smallrye.mutiny.Uni;
import org.assignment.dto.DepartmentReq;
import org.assignment.dto.DepartmentRes;
import org.assignment.entity.Department;
import org.assignment.mapper.EntityMapper;
import org.assignment.repository.DepartmentRepository;
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

class DepartmentServiceTest {

    @InjectMocks
    private DepartmentService departmentService;

    @Mock
    private DepartmentRepository departmentRepository;

    private EntityMapper mapper;
    private DepartmentReq departmentReq;
    private Department department;
    private DepartmentRes departmentRes;
    private String departmentId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        departmentId = new ObjectId().toString();
        departmentReq = new DepartmentReq();
        departmentReq.setName("Finance");

        department = new Department();
        department.setId(new ObjectId(departmentId));
        department.setName("Finance");

        departmentRes = new DepartmentRes();
        departmentRes.setId(departmentId);
        departmentRes.setName("Finance");

        mapper = EntityMapper.INSTANCE;
    }

    @Test
    void testCreateDepartment() {
        when(departmentRepository.persist(any(Department.class))).thenReturn(Uni.createFrom().item(department));

        Uni<DepartmentRes> result = departmentService.createDepartment(departmentReq);
        assertDoesNotThrow(() -> result.await().indefinitely());
        assertEquals(departmentRes, result.await().indefinitely());
        verify(departmentRepository).persist(any(Department.class));
    }

    @Test
    void testGetDepartmentById() {
        when(departmentRepository.findById(any(ObjectId.class))).thenReturn(Uni.createFrom().item(department));

        Uni<DepartmentRes> result = departmentService.getDepartmentById(departmentId);

        assertDoesNotThrow(() -> result.await().indefinitely());
        assertEquals(departmentRes, result.await().indefinitely());
        verify(departmentRepository).findById(any(ObjectId.class));
    }
    @Test
    void testGetAllDepartments() {
        when(departmentRepository.listAll()).thenReturn(Uni.createFrom().item(Collections.singletonList(department)));

        Uni<List<DepartmentRes>> result = departmentService.getAllDepartments();

        assertDoesNotThrow(() -> result.await().indefinitely());
        assertEquals(1, result.await().indefinitely().size());
        verify(departmentRepository).listAll();
    }

}
