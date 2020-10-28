package com.example.helptest.controller;

import com.example.helptest.config.LocalPagination;
import com.example.helptest.model.User;
import com.example.helptest.model.UserDTO;
import com.example.helptest.security.ApplicationUserRole;
import com.example.helptest.service.UserRoleService;
import com.example.helptest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get page of users.")
    @GetMapping(value = "/{pageId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER')")
    public @ResponseBody
    Page<UserDTO> getAllUsers(@PathVariable("pageId") int pageId,
                              @RequestParam(value = "query", required = false, defaultValue = "") String query,
                              @RequestParam(value = "role", required = false) String rolename) {


        Pageable pageable = LocalPagination.getDefaultPageable(pageId);
        Page<UserDTO> usersPaged;
        if (query.equals("") && rolename == null) usersPaged= userService.filter(pageable);
        else {
            if (rolename == null){
                usersPaged = userService.filter(pageable, query);
            }else
            {
                ApplicationUserRole role = userRoleService.getRoleByRolename(rolename);
                usersPaged = userService.filter(pageable, query, role);
            }
        }
        //return userService.filter();
        return usersPaged;
    }

    @Operation(summary = "Get list of users.")
    @GetMapping()
    public @ResponseBody
    List<UserDTO> getListUsers() {
        return userService.filter();
    }


    @Operation(summary = "Get user information by username.")
    @GetMapping("/user/{username}")
//    @PreAuthorize("hasAuthority('user:read')")
    public @ResponseBody
    UserDTO getUser(@PathVariable("username") String username) {
        return userService.getUserByUsername(username);
    }

    @Operation(summary = "Add new user.", description = "Request body: {" +
            "\"role\":\"WAITER\"," +
            "\"username\":\"username\"," +
            "\"password\":\"password\"," +
            "\"name\":\"Name\"," +
            "\"lastname\":\"Lastname\"," +
            "\"creator\":\"creator_username\"," +
            "}")
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

    @Operation(summary = "Update user information.")
    @PostMapping(path = "/{username}")
    @PreAuthorize("hasAuthority('user:write')")
    public @ResponseBody
    UserDTO updateUser(@PathVariable("username") String username,
                       @RequestBody   Map<String, String> userMap)
    {
         String rolename = userMap.get("role");
         String password = userMap.get("password");
         String name = userMap.get("name");
         String lastname = userMap.get("lastname");
         boolean deleted = userMap.get("deleted").equals("1");
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
