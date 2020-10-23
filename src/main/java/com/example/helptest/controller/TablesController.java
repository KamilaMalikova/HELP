package com.example.helptest.controller;

import com.example.helptest.exception.NotFoundException;
import com.example.helptest.model.EatingPlace;
import com.example.helptest.model.TablesDTO;
import com.example.helptest.model.User;
import com.example.helptest.model.UserDTO;
import com.example.helptest.service.EatingPlaceService;
import com.example.helptest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get page of tables.",
                description = "Reserved status can be 0, 1. Username: username of the waiter.")
    @GetMapping("/{pageId}")
    @PreAuthorize("hasAuthority('table:read')")
    public @ResponseBody
    Page<EatingPlace> getAllTables(@PathVariable("pageId") int page,
                                   @RequestParam(value = "reserved", required = false, defaultValue = "2") String reserved,
                                   @RequestParam(value = "username", required = false, defaultValue = "null") String username) { //reserved = 1|0; waiter = username
        if (username.equals("null") && reserved.equals("2")) return tablesService.getAllActiveTables(page);
        else {
            Map<String, String> bodyParams = new HashMap<>();
            bodyParams.put("reserved", reserved);
            bodyParams.put("username", username);
            return tablesService.filter(page, bodyParams);

        }
    }

    @Operation(summary = "Get table information by tableId.")
    @GetMapping("/table/{tableId}")
    @PreAuthorize("hasAuthority('table:read')")
    public @ResponseBody
    EatingPlace getTable(@PathVariable("tableId") int table_id) {
        return tablesService.getTableById(table_id);
    }

    @Operation(summary = "Add tables.", description = "Request body requires \"count:\"requiredQty of tables: {\"count:\"\"10\"}")
    @PostMapping
    @PreAuthorize("hasAuthority('table:add')")
    public @ResponseBody
    TablesDTO addNewTable(@RequestBody Map<String, String> bodyParam) {
        int count = Integer.parseInt(bodyParam.get("count"));
        return tablesService.addNewTable(count);
    }

    @Operation(summary = "Update table information.", description = "request body: {" +
            "\"reserved:\"\"1\","+
            "\"username:\"\"waiter\","+
            "}")
    @PostMapping("/table/{tableId}")
    @PreAuthorize("hasAuthority('table:write')")
    public @ResponseBody
    EatingPlace updateTable(@PathVariable("tableId") int tableId,
                          @RequestBody Map<String, String> bodyParams) {
        try {
            boolean reserved = bodyParams.get("reserved").equals("1");
            if (reserved) {
                String waiter_username = bodyParams.get("username");
                User waiter = userService.getUser(waiter_username);
                return tablesService.setReserved(tableId, waiter);
            } else {
                return tablesService.setNotReserved(tableId);
            }
        }catch (Exception ex){
            throw new NotFoundException(ex.getMessage());
        }
    }

    @Operation(summary = "Delete range of tables.", description = "Request body requires count: number of tables to delete: {\"count:\"\"10\"}")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('table:write')")
    public @ResponseBody
    long deleteTable(@RequestBody Map<String, String> bodyParam) {
        int count = Integer.parseInt(bodyParam.get("count"));
        return tablesService.deleteTable(count);
    }

    public @ResponseBody long getNumberOfTables(){
        return tablesService.countTables();
    }
}
