package com.example.helptest.service;

import com.example.helptest.exception.NotFoundException;
import com.example.helptest.model.Tip;
import com.example.helptest.repository.TipDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipService {
    @Autowired
    private TipDao tipDao;
    public Tip getTip(){
        try {
            return tipDao.findFirstByOrderById();
        }catch (Exception ex){
            throw new NotFoundException(ex.getMessage());
        }

    }

    public Tip updateTip(double percent){
        try {
            Tip tip = tipDao.findFirstByOrderById();
            tip.setTip(percent);
            return tipDao.save(tip);
        }catch (Exception ex){
            throw new NotFoundException(ex.getMessage());
        }
    }
}
