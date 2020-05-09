package com.gov.customs.service;

import com.gov.customs.model.UserInfo;
import com.gov.customs.service.pojo.UserVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface UserInfoService {
    // Jpa 方法
    boolean createOrUpdate(UserInfo userInfo);
    UserInfo findUserInfoById(Long id);
    List<UserInfo> findUserInfoByName(String name);
    UserInfo findUserInfoByIdentity(String identity);
    UserInfo findUserInfoByPhone(String phone);
    List<UserInfo> findUserInfoByMail(String mail);

    // 需求方法
    List<UserVO> getUserVOByUsername(String oprator, String username);
    List<UserVO> getUserVOByName(String oprator, String name);
    List<UserVO> getUserVOByIdentity(String oprator, String identity);
    List<UserVO> getUserVOByphone(String oprator, String phone);
    List<UserVO> getUserVOByMail(String oprator, String mail);
    List<UserVO> getUserVOList();
//    List<UserVO> getAllUserVO(PageRequest pageRequest);

    boolean updateUserInfoById(String name, String identity, String phone, String mail, long u_id);

}
