package com.gov.customs.controller;

import com.gov.customs.model.Reply;
import com.gov.customs.model.Topic;
import com.gov.customs.model.User;
import com.gov.customs.repository.ReplyRepository;
import com.gov.customs.service.ReplyService;
import com.gov.customs.service.TopicService;
import com.gov.customs.service.UserService;
import com.gov.customs.service.pojo.ReplyVO;
import com.gov.customs.service.pojo.TopicVO;
import com.gov.customs.utils.ResultCode;
import com.gov.customs.utils.ResultJson;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ReplyController {
    @Resource
    private UserService userService;

    @Resource
    private TopicService topicService;

    @Resource
    private ReplyService replyService;

    @Secured({"ROLE_USER","ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/reply/publish")
    public ResultJson replyPublish(@RequestParam("username") String username, @RequestParam("id") Long id, @RequestParam("content") String content){
        if (username!=null&&id!=null&&content!=null){
            User user = userService.findUserByUsername(username);
            if (user==null){
                return ResultJson.failure(ResultCode.NOT_FOUND);
            }
            Reply reply = new Reply(user.getId(),id, content, new Timestamp(System.currentTimeMillis()));
            if (replyService.createOrUpdate(reply)){
                return ResultJson.success();
            }
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/reply/deleteOne")
    public ResultJson replyDeleteOne(@RequestParam("id") Long id){
        if (id!=null){
            if (replyService.deleteOne(id)){
                return ResultJson.success();
            }
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/reply/deleteMul")
    public ResultJson replyDeleteMul(@RequestParam("ids") String ids){
        if (ids==null||ids == ""){
            return ResultJson.failure(ResultCode.NOT_FOUND);
        }
        String[] result = ids.replace("[","").replace("]","").split(",");
        for (String s : result){
            replyService.deleteOne(Long.parseLong(s));
        }
        return ResultJson.success();
    }

    @RequestMapping("/reply/find")
    public ResultJson<List<ReplyVO>> replyFind(@RequestParam("index") String index, @RequestParam("value") String value){
        if (index!=null&&value!=null){
            if (index.equals("username")){
                User user = userService.findUserByUsername(value);
                if (user==null){
                    return ResultJson.failure(ResultCode.NOT_FOUND);
                }
                return ResultJson.success(replyService.findReplysByUid(user.getId()));
            }else if (index.equals("title")){
                List<ReplyVO> replyVOList = new ArrayList<>();
                List<TopicVO> topicVOList = topicService.findTopicByTitleContains(value);
                if (topicVOList==null){
                    return null;
                }
                for (TopicVO topicVO : topicVOList){
                    replyVOList.addAll(replyService.findReplysByTid(topicVO.getId()));
                }
                return ResultJson.success(replyVOList);
            }
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @RequestMapping("/reply/list")
    public ResultJson<List<ReplyVO>> replyList(@RequestParam("id") Long id){
        if (id!=null){
            return ResultJson.success(replyService.findReplysByTid(id));
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }
}
