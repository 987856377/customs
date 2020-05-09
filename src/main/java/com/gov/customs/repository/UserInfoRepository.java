package com.gov.customs.repository;

import com.gov.customs.model.UserInfo;
import com.gov.customs.service.pojo.UserVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findUserInfoById(Long id);
    List<UserInfo> findUserInfoByName(String name);
    UserInfo findUserInfoByIdentity(String identity);
    UserInfo findUserInfoByPhone(String phone);
    List<UserInfo> findUserInfoByMail(String mail);

    @Modifying
    @Query(value = "update user_info set user_info.ui_name = ?1, user_info.ui_identity = ?2, user_info.ui_phone = ?3, user_info.ui_mail = ?4 where user_info.ui_id = ?5", nativeQuery = true)
    void updateUserInfoById(String name, String identity, String phone, String mail, long u_id);

//    @Query(value = "select user.u_id,user.u_username,user_info.ui_name,user_info.ui_identity,user_info.ui_phone, user_info.ui_mail,user_info.ui_end_date,user.u_register_date from user_info inner join user on user.u_id=user_info.ui_id", nativeQuery = true)
//    @Query(value = "select user.u_id,user.u_username,user_info.ui_name,user_info.ui_identity,user_info.ui_phone, user_info.ui_mail,user_info.ui_end_date,user.u_register_date,role.r_name from ((user inner join user_info on user.u_id=user_info.ui_id) inner join user_role on user.u_id = user_role.ur_u_id) inner join role on role.r_id = user_role.ur_r_id",nativeQuery = true)
//    List<UserVO> getUserVOList();

//    @Modifying
//    @Query("update user set u_username = ?1 where u_id = ?2")
//    void updateUsernameById(String username, long id);
}