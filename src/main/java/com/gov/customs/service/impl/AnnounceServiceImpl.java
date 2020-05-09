package com.gov.customs.service.impl;

import com.gov.customs.model.Announce;
import com.gov.customs.model.User;
import com.gov.customs.repository.AnnounceRepository;
import com.gov.customs.repository.UserRepository;
import com.gov.customs.service.AnnounceService;
import com.gov.customs.service.pojo.AnnounceVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("announceService")
@Transactional
public class AnnounceServiceImpl implements AnnounceService {
    @Resource
    UserRepository userRepository;

    @Resource
    private AnnounceRepository announceRepository;

    @Override
    public boolean createOrUpdate(Announce announce) {
        if (announce!=null){
            announceRepository.saveAndFlush(announce);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteOne(Long id) {
        if (id!=null){
            announceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Announce findAnnounceById(Long id) {
        if (id!=null){
            return announceRepository.findAnnounceById(id);
        }
        return null;
    }

    @Override
    public List<AnnounceVO> findAnnouncesByUid(Long id) {
        if (id!=null){
            User user;
            List<AnnounceVO> announceVOList = new ArrayList<>();
            List<Announce> announceList = announceRepository.findAnnouncesByUid(id);
            if (announceList==null){
                return null;
            }
            for (Announce announce : announceList){
                user = userRepository.findUserById(announce.getUid());
                if (user==null){
                    return null;
                }
                announceVOList.add(new AnnounceVO(announce.getId(), user.getUsername(),announce.getTitle(), announce.getContent(), announce.getDate()));
            }
            return announceVOList;
        }
        return null;
    }

    @Override
    public List<AnnounceVO> findAnnounceByTitleContains(String title) {
        if (title!=null){
            User user;
            List<AnnounceVO> announceVOList = new ArrayList<>();
            List<Announce> announceList = announceRepository.findAnnounceByTitleContains(title);
            if (announceList==null){
                return null;
            }
            for (Announce announce : announceList){
                user = userRepository.findUserById(announce.getUid());
                if (user==null){
                    return null;
                }
                announceVOList.add(new AnnounceVO(announce.getId(), user.getUsername(),announce.getTitle(), announce.getContent(), announce.getDate()));
            }
            return announceVOList;
        }
        return null;
    }

    @Override
    public List<AnnounceVO> findAll() {
        User user;
        AnnounceVO announceVO;
        List<AnnounceVO> announceVOList = new ArrayList<>();
        List<Announce> announceList = announceRepository.findAll();
        if (announceList==null){
            return null;
        }
        for (Announce announce : announceList){
            user = userRepository.findUserById(announce.getUid());
            if (user==null){
                return null;
            }
            announceVOList.add(new AnnounceVO(announce.getId(),user.getUsername(),announce.getTitle(),announce.getContent(),announce.getDate()));
        }
        return announceVOList;
    }
}
