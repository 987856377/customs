package com.gov.customs.service;

import com.gov.customs.model.Announce;
import com.gov.customs.service.pojo.AnnounceVO;

import java.util.List;

public interface AnnounceService {
    boolean createOrUpdate(Announce announce);
    boolean deleteOne(Long id);
    Announce findAnnounceById(Long id);
    List<AnnounceVO> findAnnouncesByUid(Long id);
    List<AnnounceVO> findAnnounceByTitleContains(String title);
    List<AnnounceVO> findAll();
}
