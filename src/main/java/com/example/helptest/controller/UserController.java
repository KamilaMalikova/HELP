package com.example.helptest.controller;

import com.example.helptest.model.User;
import com.example.helptest.model.UserDTO;
import com.example.helptest.security.ApplicationUserRole;
import com.example.helptest.service.UserRoleService;
import com.example.helptest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    @GetMapping(value = "/{pageId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER')")
    public @ResponseBody
    Page<UserDTO> getAllUsers(@PathVariable("pageId") int pageId) {
        pageId -= 1;
        int total = 5;
        Pageable pageable = PageRequest.of(pageId, total, Sort.by("id"));
        Page<UserDTO> usersPaged = userService.filter(pageable);

        //return userService.filter();
        return usersPaged;
    }

    @GetMapping(path = "user/{username}")
    @PreAuthorize("hasAuthority('user:read')")
    public @ResponseBody
    UserDTO getUser(@PathVariable("username") String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    public @ResponseBody
    UserDTO addUser(@RequestBody Map<String, String> userMap
//                @RequestParam("creator") String creator,
//                 @RequestParam("lastname") String lastname,
//                 @RequestParam("name") String name,
//                 @RequestParam("username") String username,
//                 @RequestParam("password") String password,
//                 @RequestParam("role") String rolename
    ) {
        String rolename = userMap.get("role");
        String username = userMap.get("username");
        String password = userMap.get("password");
        String name = userMap.get("name");
        String lastname = userMap.get("lastname");
        String creator = userMap.get("creator");
        ApplicationUserRole role = userRoleService.getRoleByRolename(rolename);
        User user = new User(username, password, name, lastname, 0, role, creator, "", "", false);
        return new UserDTO(userService.saveUser(user));
    }

    @PostMapping(path = "/{username}")
    @PreAuthorize("hasAuthority('user:write')")
    public @ResponseBody
    UserDTO setUserUpdate(@PathVariable("username") String username,
                          @RequestParam(value = "password", required = false, defaultValue = "") String password,
                          @RequestParam(value = "name", required = false, defaultValue = "") String name,
                          @RequestParam(value = "lastname", required = false, defaultValue = "") String lastname,
                          @RequestParam(value = "role", required = false, defaultValue = "") String rolename,
                          @RequestParam(value = "deleted", required = false, defaultValue = "0") boolean deleted
    ) {
        ApplicationUserRole role;
        if (!(rolename.isEmpty() || rolename.isBlank())) {
            role = userRoleService.getRoleByRolename(rolename);
            return userService.updateUser(username, lastname, name, role, password, deleted);
        } else return userService.updateUser(username, lastname, name, null, password, deleted);
    }


//    public User updateUser(@RequestParam("oldUsername") String oldUsername){
//
//    }
}
