package com.gov.customs.controller;

import com.gov.customs.model.Role;
import com.gov.customs.model.User;
import com.gov.customs.model.UserInfo;
import com.gov.customs.service.UserInfoService;
import com.gov.customs.service.UserService;
import com.gov.customs.service.pojo.UserVO;
import com.gov.customs.utils.ResultCode;
import com.gov.customs.utils.ResultJson;
import org.apache.juli.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private Logger logger = LogManager.getLogger(this.getClass());
    @Resource
    private UserService userService;

    @Resource
    private UserInfoService userInfoService;

    @RequestMapping(value = "/isUsernameRegisted",method = RequestMethod.POST)
    public Boolean isUsernameRegisted(@PathParam("username") String username){
        if (username!=null){
            if (userService.findUserByUsername(username)!=null){
                logger.info(username+" 用户名已被注册");
                // 用户名已被注册
                return true;
            }
        }
        return false;
    }

    @RequestMapping(value = "/isIdentityRegisted",method = RequestMethod.POST)
    public Boolean isIdentityRegisted(@PathParam("identity") String identity){
        if (identity!=null){
            if (userInfoService.findUserInfoByIdentity(identity)!=null){
                // 身份证已被注册
                logger.info(identity+" 身份证已被注册");
                return true;
            }
        }
        return false;
    }

    @RequestMapping(value = "/isPhoneRegisted", method = RequestMethod.POST)
    public Boolean isPhoneRegisted(@PathParam("phone") String phone){
        if (phone!=null){
            if (userInfoService.findUserInfoByPhone(phone)!=null){
                logger.info(phone+" 手机号已被注册");
                return true;
            }
        }
        return false;
    }

    @RequestMapping(value = "/signup",method = RequestMethod.POST)
    public ResultJson save(@RequestParam("username") String username, @RequestParam("password") String password,
                       @RequestParam("name") String name, @RequestParam("identity") String identity,
                       @RequestParam("phone") String phone, @RequestParam("mail") String mail,
                       @RequestParam("start_date") Date start_date, @RequestParam("end_date") Date end_date){
        if (username!=null&&password!=null&&name!=null&&identity!=null&&phone!=null&&mail!=null){
            List<Role> roles = new ArrayList<>();
            Role role = new Role((long)3,"USER","ROLE_USER");
            roles.add(role);
            User user = new User(username,password, roles);
            user.setRegister_date(new Timestamp(System.currentTimeMillis()));
            if(userService.create(user)){
                User user1 = userService.findUserByUsername(user.getUsername());
                if (user1==null){
                    logger.info(username+" 注册失败");
                    return ResultJson.failure(ResultCode.NOT_FOUND);
                }
                UserInfo userInfo = new UserInfo(user1.getId(),name,identity,phone, mail,start_date, end_date);
                if(userInfoService.createOrUpdate(userInfo)){
                    logger.info(username+" 注册成功");
                    return ResultJson.success();
                }else{
                    userService.deleteUser(user);
                }
            }
            logger.info(username+" 注册失败");
            return ResultJson.failure(ResultCode.OPERATE_ERROR);
        }
        logger.info(username+" 校检失败");
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping(value = "/user/find",method = RequestMethod.POST)
    public ResultJson<List<UserVO>> findUser(@RequestParam("oprator")String oprator,@RequestParam("index")String index,@RequestParam("value") String value){
        if (index!=null&&value!=null){
            if (index.equals("username")) {
                return ResultJson.success(userInfoService.getUserVOByUsername(oprator,value));
            } else if (index.equals("name")) {
                return ResultJson.success(userInfoService.getUserVOByName(oprator,value));
            } else if (index.equals("identity")) {
                return ResultJson.success(userInfoService.getUserVOByIdentity(oprator,value));
            }else if (index.equals("phone")) {
                return ResultJson.success(userInfoService.getUserVOByphone(oprator,value));
            }else if (index.equals("mail")) {
                return ResultJson.success(userInfoService.getUserVOByMail(oprator,value));
            }
        }
        return null;
    }

    @Secured({"ROLE_DBA"})
    @RequestMapping(value = "/dba/list", method = RequestMethod.GET)
    public ResultJson<List<UserVO>> getUserVOList(){
        return ResultJson.success(userInfoService.getUserVOList());
    }

//    @Secured({"ROLE_DBA"})
//    @RequestMapping(value = "/dba/all", method = RequestMethod.GET)
//    public ResultJson<List<UserVO>> getAllUserVO(@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber){
//        PageRequest pageRequest = PageRequest.of(pageSize,pageNumber);
//        return ResultJson.success(userInfoService.getAllUserVO(pageRequest));
//    }


//    @RequestMapping(value = "/user/update",method = RequestMethod.POST)
//    public ResultJson updateUser(@RequestParam("id") Long id, @RequestParam("username") String username, @RequestParam("realname") String realname,
//                              @RequestParam("identity") String identity, @RequestParam("phone") String phone,
//                              @RequestParam("mail") String mail, @RequestParam("roles") String roles){
//        if (id!=null&&username!=null&&realname!=null&&identity!=null&&phone!=null&&mail!=null&&roles!=null){
//            Role role;
//            User user = userService.findUserById(id);
//            UserInfo userInfo = userInfoService.findUserInfoById(id);
//
//            List<Role> roleList = new ArrayList<>();
//            if (roles == "ROLE_USER" || roles.equals("ROLE_USER")){
//                role = new Role((long)3,"USER","ROLE_USER");
//                roleList.add(role);
//            }else if (roles == "ROLE_ADMIN" || roles.equals("ROLE_ADMIN")){
//                role = new Role((long)2,"ADMIN","ROLE_USER");
//                roleList.add(role);
//            }else if (roles == "ROLE_DBA" || roles.equals("ROLE_DBA")){
//                role = new Role((long)1,"DBA","ROLE_USER");
//                roleList.add(role);
//            }
//            user.setUsername(username);
//            user.setRoles(roleList);
//            userInfo.setName(realname);
//            userInfo.setIdentity(identity);
//            userInfo.setPhone(phone);
//            userInfo.setMail(mail);
//
//            if (userService.update(user)){
//                if (userInfoService.createOrUpdate(userInfo)){
//                    return ResultJson.success();
//                }
//                return ResultJson.failure(ResultCode.OPERATE_ERROR);
//            }
//        }
//        return ResultJson.failure(ResultCode.BAD_REQUEST);
//    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping(value = "/user/update",method = RequestMethod.POST)
    public ResultJson updateUser(@RequestParam("id") Long id, @RequestParam("username") String username, @RequestParam("realname") String realname,
                             @RequestParam("identity") String identity, @RequestParam("phone") String phone,
                             @RequestParam("mail") String mail, @RequestParam("roles") String roles){
        if (id!=null&&username!=null&&realname!=null&&identity!=null&&phone!=null&&mail!=null&&roles!=null){
            Long ur_r_id = (long)3;
            User user = userService.findUserById(id);
            if (user==null){
                return ResultJson.failure(ResultCode.NOT_FOUND);
            }
            UserInfo userInfo = userInfoService.findUserInfoById(id);
            if (userInfo==null){
                return ResultJson.failure(ResultCode.NOT_FOUND);
            }

            if (roles == "ROLE_USER" || roles.equals("ROLE_USER")){
                ur_r_id = (long)3;
            }else if (roles == "ROLE_ADMIN" || roles.equals("ROLE_ADMIN")){
                ur_r_id = (long)2;
            }else if (roles == "ROLE_DBA" || roles.equals("ROLE_DBA")){
                ur_r_id = (long)1;
            }
            user.setUsername(username);
            userInfo.setName(realname);
            userInfo.setIdentity(identity);
            userInfo.setPhone(phone);
            userInfo.setMail(mail);

            if (userService.updateUserById(username,ur_r_id,id)){
                if (userInfoService.updateUserInfoById(realname,identity,phone,mail,id)){
                    logger.info(username+" 已更新");
                    return ResultJson.success();
                }
                logger.info(username+" 更新失败");
                return ResultJson.failure(ResultCode.OPERATE_ERROR);
            }
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping(value = "/user/deleteOne", method = RequestMethod.POST)
    public ResultJson deleteUser(@RequestParam("id") Long id){
        if (id!=null){
            userService.deleteUserById(id);
            logger.info("用户已删除");
            return ResultJson.success();
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping(value = "/user/deleteMul", method = RequestMethod.POST)
    public ResultJson deleteUsers(@RequestParam("ids") String ids){
        if (ids==null||ids == ""){
            return ResultJson.failure(ResultCode.NOT_FOUND);
        }
        String[] result = ids.replace("[","").replace("]","").split(",");
        for (String s : result){
            userService.deleteUserById(Long.parseLong(s));
        }
        logger.info("用户已删除");
        return ResultJson.success();
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping(value = "/user/reset", method = RequestMethod.POST)
    public ResultJson reset(@RequestParam("username") String username, @RequestParam("realname") String realname,
                                @RequestParam("identity") String identity, @RequestParam("password") String password){
        if (username!=null&&realname!=null&&identity!=null&&password!=null){
            User user = userService.findUserByUsername(username);
            if (user==null){
                logger.info("用户密码重置失败");
                return ResultJson.failure(ResultCode.NOT_FOUND);
            }
            UserInfo userInfo = userInfoService.findUserInfoById(user.getId());
            if (userInfo==null){
                logger.info("用户密码重置失败");
                return ResultJson.failure(ResultCode.NOT_FOUND);
            }
            if (userInfo.getName().equals(realname)&&userInfo.getIdentity().equals(identity)&&password!=null){
                if(userService.resetPassword(user,password)){
                    logger.info("用户密码已重置");
                    return ResultJson.success();
                }
            }
        }
        logger.info("用户密码重置失败");
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @RequestMapping(value = "/forget", method = RequestMethod.POST)
    public ResultJson forgetPassword(@RequestParam("username") String username, @RequestParam("realname") String realname,
                                 @RequestParam("identity") String identity, @RequestParam("phone") String phone,
                                 @RequestParam("password") String password){
        if (username!=null&&realname!=null&&identity!=null&&phone!=null&&password!=null){
            User user = userService.findUserByUsername(username);
            if (user==null){
                logger.info("用户密码修改失败");
                return ResultJson.failure(ResultCode.NOT_FOUND);
            }
            UserInfo userInfo = userInfoService.findUserInfoById(user.getId());
            if (userInfo==null){
                logger.info("用户密码修改失败");
                return ResultJson.failure(ResultCode.NOT_FOUND);
            }
            if (userInfo.getName().equals(realname)&&userInfo.getIdentity().equals(identity)&&userInfo.getPhone().equals(phone)&&password!=null){
                if(userService.resetPassword(user,password)){
                    logger.info("用户密码修改成功");
                    return ResultJson.success();
                }
            }
        }
        logger.info("用户密码修改失败");
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @Secured({"ROLE_USER","ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ResultJson resetPassword(@RequestParam("username") String username, @RequestParam("realname") String realname,
                            @RequestParam("identity") String identity, @RequestParam("phone") String phone,
                                @RequestParam("oldPwd") String oldPwd, @RequestParam("newPwd") String newPwd){
        if (username!=null&&realname!=null&&identity!=null&&phone!=null&&oldPwd!=null&&newPwd!=null){
            User user = userService.findUserByUsername(username);
            if (user==null){
                logger.info("用户密码重置失败");
                return ResultJson.failure(ResultCode.NOT_FOUND);
            }
            UserInfo userInfo = userInfoService.findUserInfoById(user.getId());
            if (userInfo==null){
                logger.info("用户密码重置失败");
                return ResultJson.failure(ResultCode.NOT_FOUND);
            }
            if (userInfo.getName().equals(realname)&&userInfo.getIdentity().equals(identity)&&userInfo.getPhone().equals(phone)&&oldPwd!=null&&newPwd!=null){
                if(userService.resetPassword(user,oldPwd,newPwd)){
                    logger.info("用户密码已重置");
                    return ResultJson.success();
                }
            }
        }
        logger.info("用户密码重置失败");
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @Secured({"ROLE_DBA"})
    @RequestMapping("/dba/hello")
    public ResultJson<String> hello(){
        return ResultJson.success("hello spring");
    }
}
