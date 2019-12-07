package com.example.sebackend.service;

import com.alibaba.fastjson.JSONObject;
import com.example.sebackend.entity.User;
import com.example.sebackend.entity.WorkPiece;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class Service implements ServiceModel {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int getUsersCount() {
        String sql = "SELECT COUNT(1) FROM USER";
        int response = jdbcTemplate.queryForObject(sql, Integer.class);
        return response;
    }

    @Override
    public List<User> getAll() {
        try {
            String sql = "SELECT * FROM USER";
            List<User> response = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
            return response;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User getUserByName(String name) {
        User response = new User();
        try {
            String sql = "SELECT * FROM USER WHERE name = ?";
            response = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), name);
        } catch (EmptyResultDataAccessException e) {
            response.setId(-1);
        }
        return response;
    }

    @Override
    public User getUserById(int id) {
        User response = new User();
        try {
            String sql = "SELECT * FROM USER WHERE id = ?";
            response = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
        } catch (EmptyResultDataAccessException e) {
            response.setId(-1);
        }
        return response;
    }

    @Override
    public User alterUserByName(String name, User newUser) {
        String sql = "UPDATE USER SET id=?, name=?, tel=?, pwd=? WHERE name = ?";
        return null;
    }

    @Override
    public User alterUserById(int id, User newUser) {
        return null;
    }

    @Override
    public boolean userExistsByID(int id) {
        User response = new User();
        try {
            String sql = "SELECT * FROM USER WHERE id = ?";
            response = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean userExistsByName(String name) {
        User response = new User();
        try {
            String sql = "SELECT * FROM USER WHERE name = ?";
            response = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), name);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public int addUser(User user) {
        try {
            String sql = "INSERT INTO USER(name, tel, pwd) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, user.getName(), user.getTel(), user.getPwd());
        } catch (DataAccessException e) {
            return -1;
        }
        String sql = "SELECT id FROM USER WHERE name = ?";
        int response = jdbcTemplate.queryForObject(sql, Integer.class, user.getName());
        return 0;
    }

    public JSONObject register(User user) {
        int id = addUser(user);
        int code = 1;
        String msg = "注册成功";
        JSONObject response = new JSONObject();
        if (id == -1) {
            code = 0;
            msg = "用户名已经存在";
        }
        response.put("id", id);
        response.put("code", code);
        response.put("msg", msg);
        return response;
    }

    public JSONObject login(User user) {
        String name = user.getName();
        String pwd = user.getPwd();
        User getUser = getUserByName(name);
        JSONObject response = new JSONObject();
        int code = 1;
        String msg = "登录成功";
        System.out.println(pwd);
        System.out.println(getUser.getPwd());
        if (!pwd.equals(getUser.getPwd())) {
            code = 0;
            msg = "用户名或密码错误";
        }
        response.put("code", code);
        response.put("msg", msg);
        return response;
    }

    public JSONObject appendUser(User user) {
        System.out.println(user.getName());
        System.out.println(user.getPwd());
        System.out.println(user.getTel());
        int result = addUser(user);
        JSONObject response = new JSONObject();
        if (result == -1) {
            response.put("code", 0);
            response.put("status", "已经存在该用户了");
        } else {
            response.put("code", 1);
            response.put("status", "添加成功");
        }
        return response;
    }

    public JSONObject getUserList() {
        List<User> data = getAll();
        JSONObject response = new JSONObject();
        if (data.equals(null)) {
            response.put("code", 0);
            response.put("status", "暂无数据");
        } else {
            response.put("data", data);
            response.put("code", 1);
            response.put("status", "成功");
        }
        return response;
    }

    @Override
    public WorkPiece checkWorkPiece(int id) {
        WorkPiece workPiece = new WorkPiece();
        try {
            String sql = "SELECT * FROM WORKPIECE WHERE id = ?";
            workPiece = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<WorkPiece>(WorkPiece.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return workPiece;
    }

    @Override
    public WorkPiece appendWorkPiece(WorkPiece workPiece) {
        try {
            String sql = "INSERT INTO WORKPIECE(id, type, model, stock) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, workPiece.getId(), workPiece.getType(), workPiece.getModel(), workPiece.getStock());
        } catch (DataAccessException e) {
            String sql = "UPDATE WORKPIECE SET stock = stock + ? WHERE type = ?";
            jdbcTemplate.update(sql, workPiece.getStock(), workPiece.getType());
            return null;
        }
        return workPiece;
    }

    public JSONObject checkItem(int id) {
        WorkPiece workPiece = checkWorkPiece(id);
        JSONObject response = new JSONObject();
        if (workPiece == null) {
            response.put("code", 0);
            response.put("status", "can not found this workpiece");
        } else {
            response.put("code", 1);
            response.put("status", "success");
            response.put("id", workPiece.getId());
            response.put("type", workPiece.getType());
            response.put("model", workPiece.getModel());
            response.put("stock", workPiece.getStock());
        }
        return response;
    }

    public JSONObject appendItem(WorkPiece workPiece) {
        WorkPiece tempWorkPiece = appendWorkPiece(workPiece);
        JSONObject response = new JSONObject();
        if (tempWorkPiece == null) {
            response.put("code", 0);
            response.put("status", "已为该零件添加库存");
        } else {
            response.put("code", 1);
            response.put("status", "添加成功");
        }
        return response;
    }

    public JSONObject getItemList () {
        JSONObject response = new JSONObject();
        try {
            String sql = "SELECT * FROM WORKPIECE";
            List<WorkPiece> productList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<WorkPiece>(WorkPiece.class));
            response.put("code", 1);
            response.put("status", "查找成功");
            response.put("data", productList);
        } catch (EmptyResultDataAccessException e) {
            response.put("code", 0);
            response.put("status", "产品列表为空");
            return response;
        }
        return response;
    }
}
