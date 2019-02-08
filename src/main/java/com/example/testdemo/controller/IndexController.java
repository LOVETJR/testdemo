package com.example.testdemo.controller;

import com.example.testdemo.aspect.LogAspect;
import com.example.testdemo.module.User;
import com.example.testdemo.service.WenDaService;
import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author Created by 楚天佳
 * @date 2019/2/7 14:09
 */
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);


    @Autowired
    WenDaService wenDaService;/*依赖注入 享元模式*/

    @RequestMapping("/")/*路径拦截*/
    @ResponseBody/*返回非模板字符串*/
    public String index(HttpSession httpSession){
        logger.info("VISIT HOME");

        return wenDaService.grtMessage(1)+"Hello Sport "+httpSession.getAttribute("msg");
    }
    /*返回模板*/
    @RequestMapping(path = {"/vm","/test"},method = {RequestMethod.GET,RequestMethod.POST})
    public String TestIndex(Model model){
        /*参数传至模板*/
        model.addAttribute("value1","vv1");
        List<String>colors= Arrays.asList(new String[]{"red","blue","green"});
        model.addAttribute("colors",colors);

        Map<String,String >map=new HashMap <>();
        for (int i=0;i<4;i++){
            map.put(String.valueOf(i),String.valueOf(i*i));
        }
        model.addAttribute("map",map);

        model.addAttribute("user",new User("Lee"));
        return "index";
    }
    /*含参返回*/
    @RequestMapping(path = {"/profile/{groupid}/{userid}"})
    @ResponseBody
    public String profile(@PathVariable("userid") int userid,
                          @PathVariable("groupid") String groupid,
                          @RequestParam(value = "type", defaultValue="1") int type,
                          @RequestParam(value="key",defaultValue = "zz",required = false) String key){
        return String.format("Profile Page of %s / %d, t:%d  k:%s",groupid,userid,type,key);
    }

//    http
@RequestMapping(path = {"/request"},method = {RequestMethod.GET,RequestMethod.POST})
@ResponseBody
public String request(Model model, HttpServletRequest request,
                        HttpServletResponse response,
                        HttpSession httpSession,
                        @CookieValue("JSESSIONID")String sessionid){
        StringBuilder sb=new StringBuilder();
        sb.append("COOKVALUE:"+sessionid+"<br>");
    Enumeration<String>headerNames=request.getHeaderNames();
    while (headerNames.hasMoreElements())
    {
        String name=headerNames.nextElement();
        sb.append(name+":"+request.getHeader(name)+"<br>");
    }
    if (request.getCookies()!=null){
        for (Cookie cookie:request.getCookies()){
            sb.append("Cookie:"+cookie.getName()+"value:"+cookie.getValue());
        }
    }
        sb.append(request.getMethod()+"<br>");
        sb.append(request.getQueryString()+"<br>");
        sb.append(request.getPathInfo()+"<br>");
        sb.append(request.getRequestURL()+"<br>");

        response.addHeader("soprtid","hello");
        response.addCookie(new Cookie("username","Sport"));


        return sb.toString();
                        }
/*重定向*/
    @RequestMapping(path = {"/redirect/{code}"},method = {RequestMethod.GET,RequestMethod.POST})
    public RedirectView redirect(@PathVariable("code") int code,
                           HttpSession httpSession){
        httpSession.setAttribute("msg","jump from redirect");
//        return "redirect:/";
        RedirectView red=new RedirectView("/");
        if(code==301){/* 302临时性跳转   301永久性跳转*/
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }

    @RequestMapping(path = {"/admin"},method = {RequestMethod.GET})
    @ResponseBody
    public String admin(@RequestParam("key") String key){
        if("admin".equals(key)){
            return "hello admin";
        }
            throw new IllegalArgumentException("参数不对");

    }
    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e){
        return "error: "+e.getMessage();
    }
}
