package com.deltamate.demo.service;

import com.deltamate.demo.model.StyleTable;

import java.util.List;

public interface StyleTableService {
    public List<StyleTable> findAll();
    List<StyleTable> findStyleTableByNoExFtyDate(StyleTable styleTable);
}
