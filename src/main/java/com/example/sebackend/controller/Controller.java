package com.example.sebackend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.sebackend.entity.User;
import com.example.sebackend.entity.WorkPiece;
import com.example.sebackend.service.Service;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private Service service;

    @ApiOperation(value = "注册", notes = "务必将所有信息填写", httpMethod = "POST")
    @ApiImplicitParam(dataType = "User", name = "user", value = "用户", required = true)
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public JSONObject register(@RequestBody User user) {
        return service.register(user);
    }

    @ApiOperation(value = "登录", notes = "务必将所有信息填写", httpMethod = "POST")
    @ApiImplicitParam(dataType = "User", name = "user", value = "用户", required = true)
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public JSONObject login(@RequestBody User user) {
        return service.login(user);
    }

    @ApiOperation(value = "（后台）增加用户", notes = "务必将所有信息填写", httpMethod = "POST")
    @ApiImplicitParam(dataType = "User", name = "user", value = "用户", required = true)
    @RequestMapping(value = "/user/append", method = RequestMethod.POST)
    public JSONObject appendUser(@RequestBody User user) { return service.appendUser(user); }

    @ApiOperation(value = "获取用户列表", notes = "无", httpMethod = "GET")
    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public JSONObject getUserList() { return service.getUserList(); }

    @ApiOperation(value = "增加零件", notes = "务必将所有信息填写", httpMethod = "POST")
    @ApiImplicitParam(dataType = "WorkPiece", name = "workpiece", value = "零件", required = true)
    @RequestMapping(value = "/workpiece/append", method = RequestMethod.POST)
    public JSONObject appendWorkpiece(@RequestBody WorkPiece workPiece) { return service.appendItem(workPiece); }

    @ApiOperation(value = "查找零件", notes = "务必将所有信息填写", httpMethod = "GET")
    @ApiImplicitParam(dataType = "int", name = "id", value = "零件ID", required = true)
    @RequestMapping(value = "/workpiece/check", method = RequestMethod.GET)
    public JSONObject check(@RequestParam int id) { return service.checkItem(id); }

    @ApiOperation(value = "获取零件列表", notes = "无", httpMethod = "GET")
    @RequestMapping(value = "/workpiece/list", method = RequestMethod.GET)
    public JSONObject getProductList() { return service.getItemList(); }


    // 原生js的jsonp请求尝试
    @ApiOperation(value = "无", notes = "原生jsonp尝试", httpMethod = "GET")
    @RequestMapping(value = "/user/test", method = RequestMethod.GET)
    public String test(String callback) {
        JSONObject response = service.getUserList();
        String json = JSON.toJSONString(response);
        String result = callback + "(" + json + ")";
        return result;
    }
}
