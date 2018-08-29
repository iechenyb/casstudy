package com.cyb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @RequestMapping(value={"/welcome","/"},method=RequestMethod.GET)
    public String welcome(){
        return "index";
    }
    //@PreAuthorize("hasRole('ROLE_ADMIN')") 
    @RequestMapping(value={"/callback","/"},method=RequestMethod.GET)
    @ResponseBody
    public String get(){
        return "123456789";
    }
    @RequestMapping(value={"/aaa","/"},method=RequestMethod.GET)
    @ResponseBody
    public String aaa(){
        return "aaa";
    }
    
    @RequestMapping(value={"/exit","/"},method=RequestMethod.GET)
    public String exit(HttpServletRequest req){
        return "aaa";
    }
}
