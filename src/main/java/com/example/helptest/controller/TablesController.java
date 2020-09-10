package com.example.helptest.controller;

import com.example.helptest.model.EatingPlace;
import com.example.helptest.model.TablesDTO;
import com.example.helptest.model.User;
import com.example.helptest.model.UserDTO;
import com.example.helptest.service.EatingPlaceService;
import com.example.helptest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/tables")
public class TablesController {

    @Autowired
    private EatingPlaceService tablesService;
    @Autowired
    private UserService userService;

    @GetMapping("/{pageId}")
    @PreAuthorize("hasAuthority('table:read')")
    public @ResponseBody
    Page<EatingPlace> getAllTables(@PathVariable("pageId") int page,
                                   @RequestParam(value = "reserved", required = false) String reserved, @RequestParam(value = "username", required = false) String username){ //reserved = 1|0; waiter = username
        if (username == null && reserved == null)   return tablesService.getAllActiveTables(page);
        else{
            Map<String, String> bodyParams = new HashMap<>();
            bodyParams.put("reserved", reserved);
            bodyParams.put("username", username);
            return tablesService.filter(page, bodyParams);

        }
    }

    @GetMapping("/table/{tableId}")
    @PreAuthorize("hasAuthority('table:read')")
    public @ResponseBody
    TablesDTO getTable(@PathVariable("tableId") int table_id){
        return tablesService.getTableById(table_id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('table:add')")
    public @ResponseBody
    TablesDTO addNewTable(@RequestBody Map<String, String> bodyParam){
        int count = Integer.parseInt(bodyParam.get("count"));
        return tablesService.addNewTable(count);
    }

    @PostMapping("/table/{tableId}")
    @PreAuthorize("hasAuthority('table:write')")
    public @ResponseBody
    TablesDTO updateTable(@PathVariable("tableId") int tableId,
                          @RequestBody Map<String, String> bodyParams){
        boolean reserved = bodyParams.get("reserved").equals("1");
        if (reserved){
            String waiter_username = bodyParams.get("username");
            User waiter = userService.getUser(waiter_username);
            return tablesService.setReserved(tableId, waiter);
        }else {
            return tablesService.setNotReserved(tableId);
        }
    }
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('table:write')")
    public @ResponseBody
    boolean deleteTable(@RequestBody  Map<String, String> bodyParam){
        int count = Integer.parseInt(bodyParam.get("count"));
        return tablesService.deleteTable(count);
    }
}
