package com.triconinfotech.WealthWise.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.triconinfotech.WealthWise.entity.Role;
import com.triconinfotech.WealthWise.exception.CustomException;
import com.triconinfotech.WealthWise.repository.RoleRepository;
import com.triconinfotech.WealthWise.service.RoleServiceImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class RoleServiceImplTest.
 */
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleServiceImpl;

    private Role role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        role = new Role();
        role.setId(1);
        role.setName("Admin");
    }

    @Test
    void testMergeRole() {
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        Role result = roleServiceImpl.mergeRole(role);

        assertNotNull(result);
        assertEquals(role.getId(), result.getId());
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void testGetAllRoles() {
        List<Role> roleList = Arrays.asList(role);
        when(roleRepository.findAllWithOffsetAndLimit(0, 10)).thenReturn(roleList);

        List<Role> result = roleServiceImpl.getAllRoles(1, 10);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(roleRepository, times(1)).findAllWithOffsetAndLimit(0, 10);
    }

    @Test
    void testGetRoleById() {
        when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role));

        Role result = roleServiceImpl.getRoleById(1);

        assertNotNull(result);
        assertEquals(role.getId(), result.getId());
        verify(roleRepository, times(1)).findById(1);
    }

    @Test
    void testGetRoleById_NotFound() {
        when(roleRepository.findById(anyInt())).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> roleServiceImpl.getRoleById(1));

        assertEquals("Role not found", exception.getMessage());
        verify(roleRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteRole() {
        when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role));

        roleServiceImpl.deleteRole(1);

        verify(roleRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteRole_NotFound() {
        when(roleRepository.findById(anyInt())).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> roleServiceImpl.deleteRole(1));

        assertEquals("Role not found", exception.getMessage());
        verify(roleRepository, times(1)).findById(1);
    }
}
