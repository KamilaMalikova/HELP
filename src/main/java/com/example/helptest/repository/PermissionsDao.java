package com.example.helptest.repository;

import com.example.helptest.security.Permissions;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PermissionsDao extends CrudRepository<Permissions, Integer> {
    List<Permissions> findById(int id);
}
