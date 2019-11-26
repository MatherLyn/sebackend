package com.example.sebackend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.sebackend.entity.User;
import com.example.sebackend.entity.WorkPiece;
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


    @RequestMapping(value = "/workpiece/append", method = RequestMethod.POST)
    public JSONObject append(@RequestBody WorkPiece workPiece) { return service.appendItem(workPiece); }

    @RequestMapping(value = "/workpiece/check", method = RequestMethod.GET)
    public JSONObject check(@RequestParam int id) { return service.checkItem(id); }

    @RequestMapping(value = "/workpiece/list", method = RequestMethod.GET)
    public JSONObject getProductList() { return service.getItemList(); }
}
