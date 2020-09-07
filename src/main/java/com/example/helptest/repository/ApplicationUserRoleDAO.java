package com.example.helptest.repository;

import com.example.helptest.security.ApplicationUserRole;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationUserRoleDAO extends CrudRepository<ApplicationUserRole, Integer> {
    ApplicationUserRole findApplicationUserRoleByRole(String role);
}
