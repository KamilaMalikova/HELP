package com.example.helptest.config;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public class LocalPagination {
    public static Pageable getPageable(int page, String sortBy) {
        //page -= 1;
        int total = 20;
        Pageable pageable = PageRequest.of(page, total, Sort.by(sortBy));
        return pageable;
    }

    public static Pageable getDefaultPageable(int page) {
        //page -= 1;
        int total = 10;
        Pageable pageable = PageRequest.of(page, total, Sort.by("id"));
        return pageable;
    }

    public static Pageable getPageableWithTotal(int page, String sortBy, int total) {
        //page -= 1;
        Pageable pageable = PageRequest.of(page, total, Sort.by(sortBy));
        return pageable;
    }
}
