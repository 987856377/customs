package com.gov.customs.repository;

import com.gov.customs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
//    JPA
    User findUserById(Long id);
    User findUserByUsername(String username);

//    Navive Query
    @Modifying
    @Query(value = "update user,user_role set u_username = ?1, ur_r_id = ?2 where u_id = ?3 and ur_u_id = ?3", nativeQuery = true)
    void updateUserById(String username, long ur_r_id, long u_id);
}