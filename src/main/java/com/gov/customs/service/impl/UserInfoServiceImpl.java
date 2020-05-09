package com.gov.customs.service.impl;

import com.gov.customs.model.Role;
import com.gov.customs.model.User;
import com.gov.customs.model.UserInfo;
import com.gov.customs.repository.UserInfoRepository;
import com.gov.customs.repository.UserRepository;
import com.gov.customs.service.UserInfoService;
import com.gov.customs.service.pojo.UserVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("userInfoService")
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

//    private final PasswordEncoder identityEncoder = new BCryptPasswordEncoder();

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserInfoRepository userInfoRepository;

    @Override
    public boolean createOrUpdate(UserInfo userInfo) {
        if (userInfo.getIdentity()!=null&&userInfo.getName()!=null&&userInfo.getPhone()!=null){
//            userInfo.setIdentity(identityEncoder.encode(userInfo.getIdentity()));
            userInfoRepository.saveAndFlush(userInfo);
            return true;
        }
        return false;
    }

    @Override
    public UserInfo findUserInfoById(Long id) {
        return userInfoRepository.findUserInfoById(id);
    }

    @Override
    public List<UserInfo> findUserInfoByName(String name) {
        return userInfoRepository.findUserInfoByName(name);
    }

    @Override
    public UserInfo findUserInfoByIdentity(String identity) {
        return userInfoRepository.findUserInfoByIdentity(identity);
    }

    @Override
    public UserInfo findUserInfoByPhone(String phone) {
        return userInfoRepository.findUserInfoByPhone(phone);
    }

    @Override
    public List<UserInfo> findUserInfoByMail(String mail) {
        return userInfoRepository.findUserInfoByMail(mail);
    }

    public Long getMaxRid( List<Role> roleList){
        Long maxRid = (long)0;
        for (int i=0;i<roleList.size();i++){
            Long id = roleList.get(i).getId();
            maxRid =  (id > maxRid) ? id : maxRid;
        }
        return maxRid;
    }

    public boolean judgeRole(String oprator, List<Role> roleList){
        User user = userRepository.findUserByUsername(oprator);
        if (user==null){
            return false;
        }

//        Long opratorMaxRid = getMaxRid(user.getRoles());
//        Long listMaxRid =getMaxRid(roleList);

        Long opratorMaxRid = user.getRoles().get(0).getId();
        Long listMaxRid = roleList.get(0).getId();
        if (opratorMaxRid <= listMaxRid){
            return true;
        }
        return false;
    }

    @Override
    public List<UserVO> getUserVOByUsername(String oprator, String username) {
        User user;
        UserInfo userInfo;
        UserVO userVO;
        List<UserVO> userVOList = new ArrayList<>();

        user = userRepository.findUserByUsername(username);
        if (user==null){
            return null;
        }
        userInfo = this.findUserInfoById(user.getId());
        if (userInfo==null){
            return null;
        }
        if (judgeRole(oprator,user.getRoles())){
            userVO =  new UserVO(user.getId(),user.getUsername(),userInfo.getName(),userInfo.getIdentity(),userInfo.getPhone(),
                    userInfo.getMail(),userInfo.getEnd_date(),user.getRegister_date(), Role.getRoleFlags(user.getRoles()));
            userVOList.add(userVO);
        }

        return userVOList;
    }

    @Override
    public List<UserVO> getUserVOByName(String oprator,String name) {
        User user;
        UserVO userVO;
        List<UserInfo> userInfoList;
        List<UserVO> userVOList = new ArrayList<>();

        userInfoList = this.findUserInfoByName(name);
        if (userInfoList==null){
            return null;
        }
        for (UserInfo u : userInfoList){
            user = userRepository.findUserById(u.getId());
            if (user==null){
                return null;
            }
            if (judgeRole(oprator,user.getRoles())){
                userVO =  new UserVO(user.getId(),user.getUsername(),u.getName(),u.getIdentity(),u.getPhone(),
                        u.getMail(),u.getEnd_date(),user.getRegister_date(),Role.getRoleFlags(user.getRoles()));
                userVOList.add(userVO);
            }
        }
        return userVOList;
    }

    @Override
    public List<UserVO> getUserVOByIdentity(String oprator,String identity) {
        User user;
        UserInfo userInfo;
        UserVO userVO;
        List<UserVO> userVOList = new ArrayList<>();

        userInfo = this.findUserInfoByIdentity(identity);
        if (userInfo==null){
            return null;
        }
        user = userRepository.findUserById(userInfo.getId());
        if (user==null){
            return null;
        }
        if (judgeRole(oprator,user.getRoles())){
            userVO =  new UserVO(user.getId(),user.getUsername(),userInfo.getName(),userInfo.getIdentity(),userInfo.getPhone(),
                    userInfo.getMail(),userInfo.getEnd_date(),user.getRegister_date(),Role.getRoleFlags(user.getRoles()));
            userVOList.add(userVO);
        }
        return userVOList;
    }

    @Override
    public List<UserVO> getUserVOByphone(String oprator,String phone) {
        User user;
        UserInfo userInfo;
        UserVO userVO;
        List<UserVO> userVOList = new ArrayList<>();

        userInfo = this.findUserInfoByPhone(phone);
        if (userInfo==null){
            return null;
        }
        user = userRepository.findUserById(userInfo.getId());
        if (user==null){
            return null;
        }
        if (judgeRole(oprator,user.getRoles())){
            userVO =  new UserVO(user.getId(),user.getUsername(),userInfo.getName(),userInfo.getIdentity(),userInfo.getPhone(),
                    userInfo.getMail(),userInfo.getEnd_date(),user.getRegister_date(),Role.getRoleFlags(user.getRoles()));
            userVOList.add(userVO);
        }
        return userVOList;
    }

    @Override
    public List<UserVO> getUserVOByMail(String oprator,String mail) {
        User user;
        UserVO userVO;
        List<UserInfo> userInfoList;
        List<UserVO> userVOList = new ArrayList<>();

        userInfoList = this.findUserInfoByMail(mail);
        if (userInfoList==null){
            return null;
        }
        for (UserInfo u : userInfoList){
            user = userRepository.findUserById(u.getId());
            if (user==null){
                return null;
            }
            if (judgeRole(oprator,user.getRoles())){
                userVO =  new UserVO(user.getId(),user.getUsername(),u.getName(),u.getIdentity(),u.getPhone(),
                        u.getMail(),u.getEnd_date(),user.getRegister_date(),Role.getRoleFlags(user.getRoles()));
                userVOList.add(userVO);
            }

        }
        return userVOList;
    }

    @Override
    public List<UserVO> getUserVOList() {
        List<User> userList = userRepository.findAll();
        if (userList==null){
            return null;
        }
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        if (userInfoList==null){
            return null;
        }
        List<UserVO> userVOList = new ArrayList<>();
        UserVO userVO;
        int i = 0;
        for (User u : userList){
            while (i < userInfoList.size()){
                userVO = new UserVO(u.getId(),
                        userList.get(i).getUsername(),userInfoList.get(i).getName(),userInfoList.get(i).getIdentity(),userInfoList.get(i).getPhone(),
                        userInfoList.get(i).getMail(),userInfoList.get(i).getEnd_date(),
                        u.getRegister_date(), Role.getRoleFlags(u.getRoles()));
                i++;
                userVOList.add(userVO);
                break;
            }
        }
        return userVOList;
    }

//    @Override
//    public List<UserVO> getAllUserVO(PageRequest pageRequest) {
//        Page<User> userList = userRepository.findAll(pageRequest);
//        if (userList==null){
//            return null;
//        }
//        Page<UserInfo> userInfoList = userInfoRepository.findAll(pageRequest);
//        if (userInfoList==null){
//            return null;
//        }
//        List<UserVO> userVOList = new ArrayList<>();
//        User user;
//        UserInfo userInfo;
//        UserVO userVO;
//
//        for (Iterator<User> iter = userList.iterator(); iter.hasNext(); ) {
//            user = iter.next();
//            for (Iterator<UserInfo> it = userInfoList.iterator(); it.hasNext(); ) {
//                userInfo = it.next();
//                userVO = new UserVO(user.getId(),
//                        user.getUsername(),userInfo.getName(),userInfo.getIdentity(),userInfo.getPhone(),
//                        userInfo.getMail(),userInfo.getEnd_date(),
//                        user.getRegister_date(), Role.getRoleFlags(user.getRoles()));
//                userVOList.add(userVO);
//            }
//        }
//        return userVOList;
//    }

    @Override
    public boolean updateUserInfoById(String name, String identity, String phone, String mail, long u_id) {
        userInfoRepository.updateUserInfoById(name, identity, phone, mail, u_id);
        return true;
    }


}
