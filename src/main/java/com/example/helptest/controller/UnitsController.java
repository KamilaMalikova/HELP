package com.example.helptest.controller;

import com.example.helptest.exception.IllegalArgumentException;
import com.example.helptest.model.Units;
import com.example.helptest.service.UnitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/units")
public class UnitsController {
    @Autowired
    public UnitsService unitsService;


    @GetMapping("/{pageId}")
    @PreAuthorize("hasAuthority('unit:read')")
    public @ResponseBody
    Page<Units> getUnits(@PathVariable("pageId") int page){
        return unitsService.getUnits(page);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('unit:read')")
    public @ResponseBody
    List<Units> getUnitsList(){
        return unitsService.getUnitsList();
    }


    @GetMapping("/unit/{unitId}")
    @PreAuthorize("hasAuthority('unit:read')")
    public @ResponseBody
    Units getUnit(@PathVariable("unitId") int unitId){
        return unitsService.getUnit(unitId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('unit:add')")
    public @ResponseBody
    Units addUnit(@RequestBody Map<String, String> bodyParams){
        try {
            return unitsService.addNewUnit(bodyParams.get("unit"));
        }catch (Exception ex){
            throw new IllegalArgumentException(ex.getLocalizedMessage());
        }

    }

    @PostMapping("/unit/{unitId}")
    @PreAuthorize("hasAuthority('unit:write')")
    public @ResponseBody
    Units modifyUnit(@PathVariable("unitId") int unitId, @RequestBody(required = false) Map<String, String> bodyParams){

        if (bodyParams == null){
            return unitsService.deleteUnit(unitId);
        }else {
            String unitName = bodyParams.get("unit");
            return unitsService.updateUnit(unitId, unitName);
        }
    }

}
