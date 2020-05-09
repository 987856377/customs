package com.gov.customs.controller;

import com.gov.customs.model.Announce;
import com.gov.customs.model.News;
import com.gov.customs.model.User;
import com.gov.customs.service.NewsService;
import com.gov.customs.service.UserService;
import com.gov.customs.service.pojo.AnnounceVO;
import com.gov.customs.service.pojo.NewsVO;
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
public class NewsController {
    @Resource
    private UserService userService;

    @Resource
    private NewsService newsService;

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/news/publish")
    public ResultJson announcePublish(@RequestParam("username") String username, @RequestParam("title") String title,
                                  @RequestParam("content") String content){
        if (username!=null&&title!=null&&content!=null){
            User user = userService.findUserByUsername(username);
            if (user==null){
                return ResultJson.failure(ResultCode.NOT_FOUND);
            }
            News news = new News(user.getId(),title, content, new Timestamp(System.currentTimeMillis()));
            if (newsService.createOrUpdate(news)){
                return ResultJson.success();
            }
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/news/update")
    public ResultJson  announceUpdate(@RequestParam("id") Long id, @RequestParam("title")String title, @RequestParam("content") String content){
        if (id!=null&&title!=null&&content!=null){
            News news = newsService.findNewsById(id);
            if (news==null){
                return ResultJson.failure(ResultCode.NOT_FOUND);
            }
            news.setTitle(title);
            news.setContent(content);
            if (newsService.createOrUpdate(news)){
                return ResultJson.success();
            }
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/news/deleteOne")
    public ResultJson announceDeleteOne(@RequestParam("id") Long id){
        if (id!=null){
            if (newsService.deleteOne(id)){
                return ResultJson.success();
            }
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/news/deleteMul")
    public ResultJson announceDeleteMul(@RequestParam("ids") String ids){
        if (ids==null||ids == ""){
            return ResultJson.failure(ResultCode.NOT_FOUND);
        }
        String[] result = ids.replace("[","").replace("]","").split(",");
        for (String s : result){
            newsService.deleteOne(Long.parseLong(s));
        }
        return ResultJson.success();
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/news/find")
    public ResultJson<List<NewsVO>> announceFind(@RequestParam("index") String index, @RequestParam("value") String value){
        if (index!=null&&value!=null){
            if (index.equals("username")){
                User user = userService.findUserByUsername(value);
                if (user==null){
                    return null;
                }
                return ResultJson.success(newsService.findNewsByUid(user.getId()));
            }else if (index.equals("title")) {
                return ResultJson.success(newsService.findNewsByTitleContains(value));
            }
        }
        return null;
    }

    @RequestMapping("/news/list")
    public ResultJson<List<NewsVO>> announceList(){
        return ResultJson.success(newsService.findAll());
    }
}
