package com.gov.customs.controller;

import com.gov.customs.model.Classify;
import com.gov.customs.model.Junk;
import com.gov.customs.service.ClassifyService;
import com.gov.customs.service.JunkService;
import com.gov.customs.service.pojo.JunkVO;
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
public class JunkController {
    @Resource
    private ClassifyService classifyService;

    @Resource
    private JunkService junkService;

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/classify/publish")
    public ResultJson classifyPublish(@RequestParam("name") String name){
        if (name!=null){
            Classify classify = new Classify(name);
            Classify isExist = classifyService.findClassifyByName(name);
            if (isExist!=null){
                return ResultJson.failure(ResultCode.CONFLICT);
            }
            if (classifyService.createOrUpdate(classify)){
                return ResultJson.success();
            }
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/junk/publish")
    public ResultJson junkPublish(@RequestParam("name") String name, @RequestParam("classify") String classify,
                              @RequestParam("type") String type, @RequestParam("describe") String describe){
        if (name!=null&&classify!=null&&type!=null&&describe!=null){
            Classify classify1 = classifyService.findClassifyByName(classify);
            if (classify1==null){
                return ResultJson.failure(ResultCode.NOT_FOUND);
            }
            Junk junk = new Junk(classify1.getId(), name, type, describe, new Timestamp(System.currentTimeMillis()));
            if (junkService.createOrUpdate(junk)){
                return ResultJson.success();
            }
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/junk/update")
    public ResultJson junkUpdate(@RequestParam("id") Long id, @RequestParam("name") String name, @RequestParam("classify") String classify,
                             @RequestParam("type") String type, @RequestParam("describe") String describe){
        if (id!=null&&name!=null&&classify!=null&&type!=null&&describe!=null){
            Classify classify1 = classifyService.findClassifyByName(classify);
            if (classify1==null){
                return ResultJson.failure(ResultCode.NOT_FOUND);
            }
            Junk junk = junkService.findJunkById(id);
            junk.setName(name);
            junk.setCid(classify1.getId());
            junk.setType(type);
            junk.setDescribe(describe);
            if (junkService.createOrUpdate(junk)){
                return ResultJson.success();
            }
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/junk/deleteOne")
    public ResultJson junkDeleteOne(@RequestParam("id") Long id){
        if (id!=null){
            if (junkService.deleteOne(id)){
                return ResultJson.success();
            }
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/junk/deleteMul")
    public ResultJson junkDeleteMul(@RequestParam("ids") String ids){
        if (ids==null||ids == ""){
            return ResultJson.failure(ResultCode.NOT_FOUND);
        }
        String[] result = ids.replace("[","").replace("]","").split(",");
        for (String s : result){
            junkService.deleteOne(Long.parseLong(s));
        }
        return ResultJson.success();
    }

    @RequestMapping("/junk/find")
    public ResultJson<List<JunkVO>> junkFind(@RequestParam("index") String index, @RequestParam("value") String value){
        if (index!=null&&value!=null){
            if (index.equals("name")){
                return ResultJson.success(junkService.findJunksByNameContains(value));
            }else if (index.equals("classify")) {
                Classify classify = classifyService.findClassifyByName(value);
                if (classify==null){
                    return ResultJson.failure(ResultCode.NOT_FOUND);
                }
                return ResultJson.success(junkService.findJunksByCid(classify.getId()));
            }else if (index.equals("type")) {
                return ResultJson.success(junkService.findJunksByTypeContains(value));
            }
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/junk/list")
    public ResultJson<List<JunkVO>> junkList(){
        return ResultJson.success(junkService.findAll());
    }
}
