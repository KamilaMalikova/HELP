package com.example.helptest.service;

import com.example.helptest.model.Owner;
import com.example.helptest.repository.OwnerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {
    @Autowired
    private OwnerDao ownerDao;

    public Owner getOwner(){
        return ownerDao.findFirstByOrderById();
    }
}
