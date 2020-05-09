package com.gov.customs.service.impl;

import com.gov.customs.model.News;
import com.gov.customs.model.User;
import com.gov.customs.repository.NewsRepository;
import com.gov.customs.repository.UserRepository;
import com.gov.customs.service.NewsService;
import com.gov.customs.service.pojo.NewsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("newsService")
@Transactional
public class NewsServiceImpl implements NewsService {
    @Resource
    UserRepository userRepository;

    @Resource
    private NewsRepository newsRepository;

    @Override
    public boolean createOrUpdate(News news) {
        if (news!=null){
            newsRepository.saveAndFlush(news);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteOne(Long id) {
        if (id!=null){
            newsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public News findNewsById(Long id) {
        if (id!=null){
            return newsRepository.findNewsById(id);
        }
        return null;
    }

    @Override
    public List<NewsVO> findNewsByUid(Long id) {
        if (id!=null){
            User user;
            List<NewsVO> newsVOList = new ArrayList<>();
            List<News> newsList = newsRepository.findNewsByUid(id);
            if (newsList==null){
                return null;
            }
            for (News news : newsList){
                user = userRepository.findUserById(news.getUid());
                if (user==null){
                    return null;
                }
                newsVOList.add(new NewsVO(news.getId(), user.getUsername(),news.getTitle(), news.getContent(), news.getDate()));
            }
            return newsVOList;
        }
        return null;
    }

    @Override
    public List<NewsVO> findNewsByTitleContains(String title) {
        if (title!=null){
            User user;
            List<NewsVO> newsVOList = new ArrayList<>();
            List<News> newsList = newsRepository.findNewsByTitleContains(title);
            if (newsList==null){
                return null;
            }
            for (News news : newsList){
                user = userRepository.findUserById(news.getUid());
                if (user==null){
                    return null;
                }
                newsVOList.add(new NewsVO(news.getId(), user.getUsername(),news.getTitle(), news.getContent(), news.getDate()));
            }
            return newsVOList;
        }
        return null;
    }

    @Override
    public List<NewsVO> findAll() {
        User user;
        NewsVO newsVO;
        List<NewsVO> newsVOList = new ArrayList<>();
        List<News> newsList = newsRepository.findAll();
        if (newsList==null){
            return null;
        }
        for (News news : newsList){
            user = userRepository.findUserById(news.getUid());
            if (user==null){
                return null;
            }
            newsVOList.add(new NewsVO(news.getId(),user.getUsername(),news.getTitle(),news.getContent(),news.getDate()));
        }
        return newsVOList;
    }
}
