package com.gov.customs.service.impl;

import com.gov.customs.model.Classify;
import com.gov.customs.model.Junk;
import com.gov.customs.repository.ClassifyRepository;
import com.gov.customs.repository.JunkRepository;
import com.gov.customs.service.JunkService;
import com.gov.customs.service.pojo.JunkVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("junkService")
@Transactional
public class JunkServiceImpl implements JunkService {
    @Resource
    private JunkRepository junkRepository;

    @Resource
    private ClassifyRepository classifyRepository;

    @Override
    public boolean createOrUpdate(Junk junk) {
        if (junk!=null){
            junkRepository.saveAndFlush(junk);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteOne(Long id) {
        if (id!=null){
            junkRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Junk findJunkById(Long id) {
        return junkRepository.findJunkById(id);
    }

    @Override
    public List<JunkVO> findJunksByCid(Long id) {
        List<JunkVO> junkVOList = new ArrayList<>();
        List<Junk> junkList = junkRepository.findJunksByCid(id);
        if (junkList==null){
            return null;
        }
        for (Junk junk : junkList){
            Classify classify = classifyRepository.findClassifyById(junk.getCid());
            junkVOList.add(new JunkVO(junk.getId(), junk.getName(), classify.getName(), junk.getType(), junk.getDescribe(), junk.getDate()));
        }
        return junkVOList;
    }

    @Override
    public List<JunkVO> findJunksByNameContains(String name) {
        if (name!=null){
            List<JunkVO> junkVOList = new ArrayList<>();
            List<Junk> junkList = junkRepository.findJunksByNameContains(name);
            if (junkList==null){
                return null;
            }
            for (Junk junk : junkList){
                Classify classify = classifyRepository.findClassifyById(junk.getCid());
                junkVOList.add(new JunkVO(junk.getId(), junk.getName(), classify.getName(), junk.getType(), junk.getDescribe(), junk.getDate()));
            }
            return junkVOList;
        }
        return null;
    }

    @Override
    public List<JunkVO> findJunksByTypeContains(String type) {
        if (type!=null){
            List<JunkVO> junkVOList = new ArrayList<>();
            List<Junk> junkList = junkRepository.findJunksByTypeContains(type);
            if (junkList==null){
                return null;
            }
            for (Junk junk : junkList){
                Classify classify = classifyRepository.findClassifyById(junk.getCid());
                junkVOList.add(new JunkVO(junk.getId(), junk.getName(), classify.getName(), junk.getType(), junk.getDescribe(), junk.getDate()));
            }
            return junkVOList;
        }
        return null;
    }

    @Override
    public List<JunkVO> findAll() {
        List<JunkVO> junkVOList = new ArrayList<>();
        List<Junk> junkList = junkRepository.findAll();
        if (junkList==null){
            return null;
        }
        for (Junk junk : junkList){
            Classify classify = classifyRepository.findClassifyById(junk.getCid());
            junkVOList.add(new JunkVO(junk.getId(), junk.getName(), classify.getName(), junk.getType(), junk.getDescribe(), junk.getDate()));
        }
        return junkVOList;
    }

}
