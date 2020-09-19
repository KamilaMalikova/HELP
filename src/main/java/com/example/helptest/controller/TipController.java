package com.example.helptest.controller;

import com.example.helptest.model.Tip;
import com.example.helptest.service.TipService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.FlashMap;

import javax.management.StandardEmitterMBean;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tip")
public class TipController {

    @Autowired
    private TipService tipService;

    @Operation(summary = "Update tip percentage.", description = "Request body: {\"tip\":\"0.2\"}.")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER')")
    @PostMapping
    public @ResponseBody Tip updateTip( @RequestBody HashMap<String, String> tip){
        return tipService.updateTip(Double.parseDouble(tip.get("tip")));
    }
    @Operation(summary = "Get tip percentage.")
    @GetMapping
    public @ResponseBody Tip getTip(){
        return tipService.getTip();
    }
}
