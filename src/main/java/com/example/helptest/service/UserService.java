package com.example.helptest.service;

import com.example.helptest.auth.ApplicationUser;
import com.example.helptest.exception.DuplicateException;
import com.example.helptest.model.User;
import com.example.helptest.model.UserDTO;
import com.example.helptest.repository.UserDAO;
import com.example.helptest.security.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public List<UserDTO> filter(){
        List<User> users =  userDAO.findAll();
        List<UserDTO> usersDTO = users.stream()
                .map(user -> new UserDTO(user))
                .collect(Collectors.toList());
        return usersDTO;
    }

    public Page<UserDTO> filter(Pageable pageable){
        Page<User> users =  userDAO.findAll(pageable);
        System.out.println(users.getContent());
        List<User> userList = users.getContent();
        System.out.println(userList);
        List<UserDTO> usersDTO = userList.stream()
                .map(user -> new UserDTO(user))
                .collect(Collectors.toList());
        Page<UserDTO> userDTOPage = users.map(user -> new UserDTO(user));
        return userDTOPage;
    }
    public UserDTO getUserByUsername(String username){
        return new UserDTO(userDAO.findUserByUsername(username).get());
    }

    public User getUser(String username){
        User user = userDAO.findUserByUsername(username).get();
        if (user == null) throw new NoSuchElementException("User not found");
        return user;
    }

    public User saveUser(User user){
        try {
            user.setDeleted(false);
            user.setActive(0);
            User saved_user = userDAO.save(user);
            return saved_user;
        }catch (Exception ex){
            throw new DuplicateException(ex.getMessage());
        }
    }

    public void deleteUser(String username){
        Optional<User> userOptional = userDAO.findUserByUsername(username);
        User user;
        if (!userOptional.isEmpty()){
            user = userOptional.get();
        }else throw new NoSuchElementException();
        userDAO.delete(user);
    }

    public UserDTO setUserDeleted(String username){
        Optional<User> userOptional = userDAO.findUserByUsername(username);
        User user;
        if (!userOptional.isEmpty()){
            user = userOptional.get();
            user.setDeleted(true);
            UserDTO userDTO = new UserDTO(userDAO.save(user));
            return userDTO;
        }else throw new NoSuchElementException();
    }

    public UserDTO updateUser(String username, String lastname, String name, ApplicationUserRole role, String password, boolean deleted) {
        Optional<User> userOptional = userDAO.findUserByUsername(username);
        User user;
        if (!userOptional.isEmpty()){
            user = userOptional.get();
            if (notNull(lastname)) user.setLastname(lastname);
            if (notNull(name)) user.setName(name);
            if (notNull(password)) user.setPassword(password);
            if (role != null) user.setRole(role);
            if (deleted != user.isDeleted()) user.setDeleted(deleted);
//            user.setDeleted(true);
            UserDTO userDTO = new UserDTO(userDAO.save(user));
            return userDTO;
        }else throw new NoSuchElementException();
    }

    private boolean notNull(String value){
        if (value.isBlank() || value.isEmpty()) return false;
        else return true;
    }
}
