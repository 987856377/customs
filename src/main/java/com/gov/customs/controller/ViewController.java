package com.gov.customs.controller;

import com.gov.customs.service.ClassifyService;
import com.gov.customs.service.UserInfoService;
import com.gov.customs.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
public class ViewController {

    @Resource
    private ClassifyService classifyService;

    @RequestMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/news")
    public String news() {
        return "news";
    }

    @RequestMapping("/announce")
    public String announce() {
        return "announce";
    }

    @RequestMapping("/online")
    public String online(Model model) {
        model.addAttribute("list", classifyService.findAllName());
        return "online";
    }

    @RequestMapping("/topic")
    public String topic() {
        return "topic";
    }

    @Secured({"ROLE_USER","ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/leave")
    public String leave() {
        return "leave_message";
    }


    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String postLogin() {
        // TODO Enable form login with Spring Security (trigger error for now)
        return "redirect:/login-error";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping("/user/index")
    public String userIndex() {
        return "user/index";
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/user/userFind")
    public String findUser(){
        return "/user/userFind";
    }

    @Secured({"ROLE_DBA"})
    @RequestMapping("/dba/userList")
    public String getUserVOList(){
        return "/user/userList";
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/user/resetPassword")
    public String resetPassword(){
        return "/user/resetPassword";
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/announce/announcePublish")
    public String announcePublish(){
        return "/announce/announcePublish";
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/announce/announceFind")
    public String announceFind(){
        return "/announce/announceFind";
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/announce/announceList")
    public String announceList(){
        return "/announce/announceList";
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/news/newsPublish")
    public String newsPublish(){
        return "/news/newsPublish";
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/news/newsFind")
    public String newsFind(){
        return "/news/newsFind";
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/news/newsList")
    public String newsList(){
        return "/news/newsList";
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/topic/topicFind")
    public String topicFind(){
        return "/topic/topicFind";
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/topic/topicList")
    public String topicList(){
        return "/topic/topicList";
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/reply/replyFind")
    public String replyFind(){
        return "/reply/replyFind";
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/junk/junkPublish")
    public String junkPublish(Model model){
        model.addAttribute("list", classifyService.findAllName());
        return "/junk/junkPublish";
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/junk/junkPublishClassify")
    public String junkPublishType(){
        return "/junk/junkPublishClassify";
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/junk/junkFind")
    public String junkFind(Model model){
        model.addAttribute("list", classifyService.findAllName());
        return "/junk/junkFind";
    }

    @Secured({"ROLE_ADMIN","ROLE_DBA"})
    @RequestMapping("/junk/junkList")
    public String junkList(Model model){
        model.addAttribute("list", classifyService.findAllName());
        return "/junk/junkList";
    }

}
