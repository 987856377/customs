package com.gov.customs.service;

import com.gov.customs.model.Junk;
import com.gov.customs.service.pojo.JunkVO;

import java.util.List;

public interface JunkService {
    boolean createOrUpdate(Junk junk);
    boolean deleteOne(Long id);
    Junk findJunkById(Long id);
    List<JunkVO> findJunksByCid(Long id);
    List<JunkVO> findJunksByNameContains(String name);
    List<JunkVO> findJunksByTypeContains(String type);
    List<JunkVO> findAll();
}
