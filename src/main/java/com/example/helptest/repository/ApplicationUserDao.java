package com.example.helptest.repository;

import com.example.helptest.auth.ApplicationUser;
import com.example.helptest.model.User;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ApplicationUserDao {
    @Autowired
    UserDAO userDAO;

    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        Set<User> users = userDAO.findUserByUsername(username).stream()
                .collect(Collectors.toSet());
        Set<ApplicationUser> applicationUsers = new HashSet<>();
        users.forEach(user -> applicationUsers.add(user.getApplicationUser()));

        return applicationUsers.stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }


}
