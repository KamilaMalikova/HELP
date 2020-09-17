package com.example.helptest.controller;

import com.example.helptest.model.Tip;
import com.example.helptest.service.TipService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public @ResponseBody Tip updateTip( @RequestBody HashMap<String, String> tip){
        return tipService.updateTip(Double.parseDouble(tip.get("tip")));
    }

    @GetMapping
    public @ResponseBody Tip getTip(){
        return tipService.getTip();
    }
}
