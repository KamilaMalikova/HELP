package com.example.helptest.service;

import com.example.helptest.config.LocalPagination;
import com.example.helptest.exception.DuplicateException;
import com.example.helptest.exception.IllegalArgumentException;
import com.example.helptest.exception.NotFoundException;
import com.example.helptest.model.Units;
import com.example.helptest.repository.UnitsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UnitsService {

    @Autowired
    private UnitsDao unitsDao;

    public Units addNewUnit(String unit){
        try {
            Units units = new Units(unitsDao.count()+1, unit);
            return unitsDao.save(units);
        }catch (Exception ex){
            throw new DuplicateException(ex.getLocalizedMessage());
        }
    }

    public Page<Units> getUnits(int page){
        try {
            return unitsDao.findAll(LocalPagination.getDefaultPageable(page));
        }catch (Exception ex){
            throw new IllegalArgumentException(ex.getLocalizedMessage());
        }
    }

    public Units getUnit(int id){
        try {
            return unitsDao.findUnitsById(id).get();
        }catch (Exception ex){
            throw new NotFoundException(ex.getLocalizedMessage());
        }
    }

    public Units updateUnit(int id, String newUnitName){
        try {
            Units unit = unitsDao.findUnitsById(id).get();
            unit.setUnitName(newUnitName);
            return unitsDao.save(unit);
        }catch (Exception ex){
            throw new NotFoundException(ex.getLocalizedMessage());
        }
    }

    public Units deleteUnit(int id){
        try{
            Units unit = unitsDao.findUnitsById(id).get();
            unitsDao.delete(unit);
            return unit;
        }catch (Exception ex){
            throw new NotFoundException(ex.getLocalizedMessage());
        }
    }

    public List<Units> getUnitsList() {
        return unitsDao.findAllByOrderById();
    }
}
