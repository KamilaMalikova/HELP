package com.example.helptest.service;

import com.example.helptest.config.LocalPagination;
import com.example.helptest.exception.IllegalArgumentException;
import com.example.helptest.model.EatingPlace;
import com.example.helptest.model.TablesDTO;
import com.example.helptest.model.User;
import com.example.helptest.repository.EatingPlaceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class EatingPlaceService {
    @Autowired
    private EatingPlaceDao eatingPlaceDao;
    private LocalPagination localPagination;
//    @Autowired
//    private UserDAO userService;

    public Page<EatingPlace> filter(int page, Map<String, String> tableParams) {
        try {
            String waiter_username = tableParams.get("username");

            if (!(waiter_username.isEmpty() || waiter_username.isBlank()))
                return eatingPlaceDao.findAllByReservedAndWaiterUsername(LocalPagination.getPageableWithTotal(page, "id", 20), tableParams.get("reserved").equals("1"), tableParams.get("username"));
            else
                return eatingPlaceDao.findAllByReserved(LocalPagination.getPageableWithTotal(page, "id", 20), tableParams.get("reserved").equals("1"));
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getLocalizedMessage());
        }

    }

    public Page<EatingPlace> getAllActiveTables(int page) {
//        page -= 1;
        int total = 5;
//        Pageable pageable = PageRequest.of(page, total, Sort.by("id"));
        Page<EatingPlace> tablesPage = eatingPlaceDao.findAllByActiveIsTrue(LocalPagination.getPageableWithTotal(page, "id", total));
        //tablesDao.findAllByActiveIsTrue(pageable);
        return tablesPage;
    }

    public List<EatingPlace> getEatingTables() {
        List<EatingPlace> eatingTables = eatingPlaceDao.findAll();
        return eatingTables;
    }

    public TablesDTO getTableById(int table_id) {
        EatingPlace eatingPlace = eatingPlaceDao.findById(table_id).get();
        if (eatingPlace == null) throw new NoSuchElementException();
        return new TablesDTO(eatingPlace);
    }

    public TablesDTO addNewTable(int count) {
        TablesDTO tablesDTO = null;
        for (int i = 0; i < count; i++) {
            //EatingPlace table = new EatingPlace(0, userService.findUserByUsername("null").get());
            EatingPlace table = new EatingPlace(eatingPlaceDao.countEatingPlaces().get(0) + 1);
            table = eatingPlaceDao.save(table);
            tablesDTO = new TablesDTO(table);
        }
        return tablesDTO;
    }

    public boolean deleteTable(int count) {

        for (int i = 0; i < count; i++) {
            EatingPlace eatingPlace = eatingPlaceDao.findDistinctTopByOrderByIdDesc();
            if (eatingPlace.isReserved()) throw new IllegalStateException("Table is reserved");
            if (eatingPlace != null) {
                eatingPlaceDao.delete(eatingPlace);
            } else throw new NoSuchElementException();
        }
        return true;
    }

    public TablesDTO setReserved(int table_id, User waiter) {
        EatingPlace eatingPlace = eatingPlaceDao.findEatingPlaceById(table_id).get();
        if (eatingPlace != null) {
            if (eatingPlace.isReserved()) throw new IllegalArgumentException("Table is already reserved!");

            eatingPlace.setReserved(true);
            eatingPlace.setWaiterUsername(waiter.getUsername());
            eatingPlace.setWaiterName(waiter.getName() + " " + waiter.getLastname());
            return new TablesDTO(eatingPlaceDao.save(eatingPlace));
        } else throw new NoSuchElementException();
    }


    public TablesDTO setNotReserved(int table_id) {
        EatingPlace eatingPlace = eatingPlaceDao.findEatingPlaceById(table_id).get();
        if (eatingPlace != null) {
            eatingPlace.setReserved(false);
            eatingPlace.setWaiterUsername("null");
            eatingPlace.setWaiterName("free");
            return new TablesDTO(eatingPlaceDao.save(eatingPlace));

        } else throw new NoSuchElementException();
    }
}
