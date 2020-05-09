package com.gov.customs.service.impl;

import com.gov.customs.model.Classify;
import com.gov.customs.repository.ClassifyRepository;
import com.gov.customs.service.ClassifyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("classifyService")
@Transactional
public class ClassifyServiceImpl implements ClassifyService {
    @Resource
    private ClassifyRepository classifyRepository;

    @Override
    public boolean createOrUpdate(Classify classify) {
        if (classify!=null){
            classifyRepository.saveAndFlush(classify);
            return true;
        }
        return false;
    }

    @Override
    public Classify findClassifyById(Long id) {
        return classifyRepository.findClassifyById(id);
    }

    @Override
    public Classify findClassifyByName(String name) {
        return classifyRepository.findClassifyByName(name);
    }

    @Override
    public List<String> findAllName() {
        List<String> nameList = new ArrayList<>();
        List<Classify> classifyList = classifyRepository.findAll();
        if (classifyList==null){
            return null;
        }
        for (Classify classify : classifyList){
            nameList.add(classify.getName());
        }
        return nameList;
    }
}
