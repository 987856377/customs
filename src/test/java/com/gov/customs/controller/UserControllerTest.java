package com.gov.customs.controller;


import com.gov.customs.service.UserInfoService;
import org.junit.Test;

import javax.annotation.Resource;
import java.sql.Timestamp;


public class UserControllerTest {
//
    @Resource
    private UserInfoService userInfoService;
//    @Resource
//    private UserInfoRepository userRepository;
//
//    @Test
//    public void findAllUser() {
//        System.out.println("1");
//        List<UserInfo> userList = userRepository.findAll();
//        System.out.println("2");
//        for (UserInfo u: userList){
//            System.out.println(u.toString());
//        }
//    }
//
//    @Test
//    @Transactional
//    public void getUserList(){
//       List<UserVO> list = userInfoService.getAllUserVO();
//       for (UserVO u: list){
//           System.out.println(u.toString());
//       }
//    }
//    @Resource
//    private UserInfoRepository userInfoRepository;
//
//    @Test
//    public void update(){
//        userInfoRepository.updateUserInfoById("龚振星","410928199702151652","13783939562","987856377@qq.com",53);
//    }

    @Test
    public void getTime(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp);
    }
}