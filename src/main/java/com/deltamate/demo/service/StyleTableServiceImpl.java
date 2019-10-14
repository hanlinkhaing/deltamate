package com.deltamate.demo.service;

import com.deltamate.demo.model.Ex_Fty;
import com.deltamate.demo.model.StyleTable;
import com.deltamate.demo.repository.StyleTableRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class StyleTableServiceImpl implements StyleTableService {
    private StyleTableRepository styleTableRepository;

    public StyleTableServiceImpl(StyleTableRepository styleTableRepository) {
        this.styleTableRepository = styleTableRepository;
    }

    @Override
    public List<StyleTable> findAll() {
        return sortByInsertedDate(styleTableRepository.findAll());
    }

    private List<StyleTable> sortByInsertedDate(List<StyleTable> styleTables){
        Collections.sort(styleTables, StyleTable.InsertDateComparator);
        return styleTables;
    }

    @Override
    public List<StyleTable> findStyleTableByNoExFtyDate(StyleTable styleTable) {
        if (styleTable.getEx_ftys().size() > 0) {
            Ex_Fty ex_fty = styleTable.getEx_ftys().get(0);
            if(null == styleTable.getStyleCode() || styleTable.getStyleCode().equals(""))
                styleTable.setStyleCode("%");
            if(null == styleTable.getBuyerCode() || styleTable.getBuyerCode().equals(""))
                styleTable.setBuyerCode("%");
            if(null == ex_fty.getDestination() || ex_fty.getDestination().equals(""))
                ex_fty.setDestination("%");
            if(null == ex_fty.getPo() || ex_fty.getPo().equals(""))
                ex_fty.setPo("%");

            if (null == ex_fty.getFromDate() && null == ex_fty.getToDate()) {
                return sortByInsertedDate(styleTableRepository.findStyleTableByNoExFtyDate(styleTable.getBagType(),styleTable.getBuyer(),styleTable.getStyleCode(),
                        styleTable.getBuyerCode(), ex_fty.getDestination(), ex_fty.getPo()));
            } else if (null != ex_fty.getFromDate() && null == ex_fty.getToDate()) {
                return sortByInsertedDate(styleTableRepository.findStyleTableByFromDate(styleTable.getBagType(),styleTable.getBuyer(),styleTable.getStyleCode(),
                        styleTable.getBuyerCode(), ex_fty.getDestination(), ex_fty.getPo(), ex_fty.getFromDate()));
            } else if (null != ex_fty.getToDate() && null == ex_fty.getFromDate()) {
                return sortByInsertedDate(styleTableRepository.findStyleTableByToDate(styleTable.getBagType(),styleTable.getBuyer(),styleTable.getStyleCode(),
                        styleTable.getBuyerCode(), ex_fty.getDestination(), ex_fty.getPo(), ex_fty.getToDate()));
            } else {
                return sortByInsertedDate(styleTableRepository.findStyleTableByExFtyDates(styleTable.getBagType(),styleTable.getBuyer(),styleTable.getStyleCode(),
                        styleTable.getBuyerCode(), ex_fty.getDestination(), ex_fty.getPo(), ex_fty.getFromDate(), ex_fty.getToDate()));
            }
        } else {
            return findAll();
        }
    }
}
