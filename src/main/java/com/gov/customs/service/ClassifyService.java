package com.gov.customs.service;

import com.gov.customs.model.Classify;

import java.util.List;

public interface ClassifyService {
    boolean createOrUpdate(Classify classify);
    Classify findClassifyById(Long id);
    Classify findClassifyByName(String name);
    List<String> findAllName();
}
