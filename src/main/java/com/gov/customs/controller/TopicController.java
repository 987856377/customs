package com.gov.customs.controller;

import com.gov.customs.model.News;
import com.gov.customs.model.Topic;
import com.gov.customs.model.User;
import com.gov.customs.service.TopicService;
import com.gov.customs.service.UserService;
import com.gov.customs.service.pojo.NewsVO;
import com.gov.customs.service.pojo.TopicVO;
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
public class TopicController {
    @Resource
    private UserService userService;

    @Resource
    private TopicService topicService;

    @Secured({"ROLE_USER","ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/topic/publish")
    public ResultJson topicPublish(@RequestParam("username") String username, @RequestParam("title") String title,
                               @RequestParam("content") String content){
        if (username!=null&&title!=null&&content!=null){
            User user = userService.findUserByUsername(username);
            if (user==null){
                return ResultJson.failure(ResultCode.NOT_FOUND);
            }
            Topic topic = new Topic(user.getId(),title, content, new Timestamp(System.currentTimeMillis()));
            if (topicService.createOrUpdate(topic)){
                return ResultJson.success();
            }
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/topic/deleteOne")
    public ResultJson topicDeleteOne(@RequestParam("id") Long id){
        if (id!=null){
            if (topicService.deleteOne(id)){
                return ResultJson.success();
            }
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/topic/deleteMul")
    public ResultJson topicDeleteMul(@RequestParam("ids") String ids){
        if (ids==null||ids == ""){
            return ResultJson.failure(ResultCode.NOT_FOUND);
        }
        String[] result = ids.replace("[","").replace("]","").split(",");
        for (String s : result){
            topicService.deleteOne(Long.parseLong(s));
        }
        return ResultJson.success();
    }

    @RequestMapping("/topic/find")
    public ResultJson<List<TopicVO>> topicFind(@RequestParam("index") String index, @RequestParam("value") String value){
        if (index!=null&&value!=null){
            if (index.equals("username")){
                User user = userService.findUserByUsername(value);
                if (user==null){
                    return null;
                }
                return ResultJson.success(topicService.findTopicsByUid(user.getId()));
            }else if (index.equals("title")){
                return ResultJson.success(topicService.findTopicByTitleContains(value));
            }
        }
        return null;
    }

    @RequestMapping("/topic/list")
    public ResultJson<List<TopicVO>> topicList(){
        return ResultJson.success(topicService.findAll());
    }
}
