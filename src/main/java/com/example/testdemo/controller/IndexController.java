package com.example.testdemo.controller;

import com.example.testdemo.module.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Created by 楚天佳
 * @date 2019/2/7 14:09
 */
@Controller
public class IndexController {
    @RequestMapping("/")/*路径拦截*/
    @ResponseBody/*返回非模板字符串*/
    public String index(){
        return "Hello Sport";
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
}
