package com.example.sebackend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.sebackend.entity.User;
import com.example.sebackend.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private Service service;

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public JSONObject register(@RequestBody User user) {
        return service.register(user);
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public JSONObject login(@RequestBody User user) {
        return service.login(user);
    }

}
