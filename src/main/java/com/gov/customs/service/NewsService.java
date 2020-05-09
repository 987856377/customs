package com.gov.customs.service;

import com.gov.customs.model.News;
import com.gov.customs.service.pojo.NewsVO;

import java.util.List;

public interface NewsService {
    boolean createOrUpdate(News news);
    boolean deleteOne(Long id);
    News findNewsById(Long id);
    List<NewsVO> findNewsByUid(Long id);
    List<NewsVO> findNewsByTitleContains(String title);
    List<NewsVO> findAll();
}
