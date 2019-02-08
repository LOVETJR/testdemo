package com.example.testdemo.service;

import org.springframework.stereotype.Service;

/**
 * @author Created by 楚天佳
 * @date 2019/2/8 23:17
 */
@Service
public class WenDaService {
    public String grtMessage(int userId){
        return "hello message"+String.valueOf(userId);
    }
}
