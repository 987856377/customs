package com.gov.customs.controller;

import com.gov.customs.model.Announce;
import com.gov.customs.model.User;
import com.gov.customs.service.AnnounceService;
import com.gov.customs.service.UserService;
import com.gov.customs.service.pojo.AnnounceVO;
import com.gov.customs.utils.ResultCode;
import com.gov.customs.utils.ResultJson;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@RestController
public class AnnounceController {
    @Resource
    private UserService userService;

    @Resource
    private AnnounceService announceService;

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/announce/publish")
    public ResultJson announcePublish(@RequestParam("username") String username, @RequestParam("title") String title,
                                      @RequestParam("content") String content){
        if (username!=null&&title!=null&&content!=null){
            User user = userService.findUserByUsername(username);
            if (user==null){
                return ResultJson.failure(ResultCode.NOT_FOUND);
            }
            Announce announce = new Announce(user.getId(),title, content, new Timestamp(System.currentTimeMillis()));
            if (announceService.createOrUpdate(announce)){
                return ResultJson.success();
            }
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/announce/update")
    public ResultJson  announceUpdate(@RequestParam("id") Long id, @RequestParam("title")String title, @RequestParam("content") String content){
        if (id!=null&&title!=null&&content!=null){
            Announce announce = announceService.findAnnounceById(id);
            if (announce==null){
                return ResultJson.failure(ResultCode.NOT_FOUND);
            }
            announce.setTitle(title);
            announce.setContent(content);
            if (announceService.createOrUpdate(announce)){
                return ResultJson.success();
            }
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/announce/deleteOne")
    public ResultJson announceDeleteOne(@RequestParam("id") Long id){
        if (id!=null){
            if (announceService.deleteOne(id)){
                return ResultJson.success();
            }
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/announce/deleteMul")
    public ResultJson announceDeleteMul(@RequestParam("ids") String ids){
        if (ids==null||ids == ""){
            return ResultJson.failure(ResultCode.BAD_REQUEST);
        }
        String[] result = ids.replace("[","").replace("]","").split(",");
        for (String s : result){
            announceService.deleteOne(Long.parseLong(s));
        }
        return ResultJson.success();
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/announce/find")
    public ResultJson<List<AnnounceVO>> announceFind(@RequestParam("index") String index, @RequestParam("value") String value){
        if (index!=null&&value!=null){
            if (index.equals("username")){
                User user = userService.findUserByUsername(value);
                if (user==null){
                    return ResultJson.failure(ResultCode.NOT_FOUND);
                }
                return ResultJson.success(announceService.findAnnouncesByUid(user.getId()));
            }else if (index.equals("title")) {
                return ResultJson.success(announceService.findAnnounceByTitleContains(value));
            }
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @RequestMapping("/announce/list")
    public ResultJson<List<AnnounceVO>> announceList(){
        return ResultJson.success(announceService.findAll());
    }
}
