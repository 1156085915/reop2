package com.wfl.springbootshiro.controller;


import com.wfl.springbootshiro.serivce.UserSerivce;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class testController {

    @Autowired
    private UserSerivce userSerivce;


    @RequestMapping("/findByUser")
    @ResponseBody
    public Object findByUser(String name){
        return userSerivce.findByUser(name);
    }


    @RequestMapping("/tologin")
    public String tologin(String name,String password,Model model){

        /**
         * 使用shiro进行登录操作编写
         */
        Subject subject= SecurityUtils.getSubject();

        //封装用户登录数据
        UsernamePasswordToken token =new UsernamePasswordToken(name,password);

        //执行登录方法
        try {
            subject.login(token);//会来到自定义Realm中进行认证逻辑
            return "redirect:/test"; //登录成功返回的页面
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户名不存在");
            return "login"; //登录失败返回登录页面并封装错误信息在页面显示
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "login";
        }

    }

    @RequestMapping("/add")
    public String add(){
        return "/user/add";  //这边返回的是页面名称
    }

    @RequestMapping("/login")
    public String login(){
        return "/login";  //这边返回的是页面名称
    }

    @RequestMapping("/update")
    public String update(){
        return "/user/update";  //这边返回的是页面名称
    }

    @RequestMapping("/test")
    public String test(Model model){
        model.addAttribute("name","hello world");
        return "test";  //这边返回的是页面名称
    }

    @RequestMapping("/noAuth")
    public String noAuth(){
        return "/noAuth";  //这边返回的是页面名称
    }

    @RequestMapping("/index")
    public String index(){
        return "/index";  //这边返回的是页面名称
    }

}
