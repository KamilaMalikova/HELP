package com.example.helptest.service;

import com.example.helptest.repository.ApplicationUserRoleDAO;
import com.example.helptest.security.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    @Autowired
    private ApplicationUserRoleDAO userRoleDAO;

    public ApplicationUserRole getRoleByRolename(String role){
        return userRoleDAO.findApplicationUserRoleByRole(role);
    }
}
