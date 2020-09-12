package com.example.helptest.repository;

import com.example.helptest.model.EatingPlace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EatingPlaceDao extends CrudRepository<EatingPlace, Integer>, PagingAndSortingRepository<EatingPlace, Integer> {

    //find all active tables (shows as pages)
    Page<EatingPlace> findAll(Pageable pageable);

    List<EatingPlace> findAll();

    //find all active tables (shows as pages)
    Page<EatingPlace> findAllByActiveIsTrue(Pageable pageable);

    //finds active table by table_is
    Optional<EatingPlace> findEatingPlaceById(int table_id);

    //Page<EatingPlace> findAllByActiveIsFalse(Pageable pageable);
    @Query(value = "select count(id) from eating_place", nativeQuery = true)
    List<Integer> countEatingPlaces();

    //filter eating place
    Page<EatingPlace> findAllByReservedAndWaiterUsernameLike(Pageable pageable, boolean reserved, String waiter_username);

    Page<EatingPlace> findAllByReservedAndWaiterUsernameLikeAndActiveIsTrue(Pageable pageable, boolean reserved, String waiter_username);

    Page<EatingPlace> findAllByReserved(Pageable id, boolean reserved);

    EatingPlace findDistinctTopByOrderById();

    Page<EatingPlace> findAllByReservedAndWaiterUsername(Pageable id, boolean reserved, String username);

    EatingPlace findDistinctTopByOrderByIdDesc();
}
