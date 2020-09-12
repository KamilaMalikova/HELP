package com.example.helptest.service;

import com.example.helptest.model.Tip;
import com.example.helptest.repository.TipDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipService {
    @Autowired
    private TipDao tipDao;
    public Tip getTip(){
        return tipDao.findFirstByOrderById();
    }
}
