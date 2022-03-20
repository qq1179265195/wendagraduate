package com.example.wendaoneversion.service.impl;

import com.example.wendaoneversion.dao.LoginTicketDao;
import com.example.wendaoneversion.dao.UserDao;
import com.example.wendaoneversion.model.LoginTicket;
import com.example.wendaoneversion.model.User;
import com.example.wendaoneversion.service.UserService;
import com.example.wendaoneversion.util.WendaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.security.krb5.internal.Ticket;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    LoginTicketDao ticketDao;

    @Override
    public User getUserbyId(int id) {
        User user = userDao.selectByid(id);
        return user;
    }

    /**
     * 注册功能
     * @param username
     * @param password
     * @return
     */
    @Override
    public Map<String, String> register(String username, String password) {
        Map<String ,String> map = new HashMap<String,String>();
        if(StringUtils.isEmpty(username)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if (StringUtils.isEmpty(password)){
            map.put("msg","密码不能为空");
            return map;
        }
        User user = userDao.selectByname(username);
        if (user!=null){
            map.put("msg","用户名已存在");
            return map;
        }
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setHeadUrl(head);
        user.setPassword(WendaUtils.MD5(password+user.getSalt()));
        userDao.addUser(user);
        // 登陆
        String ticket = addLoginTicket(userDao.selectByname(username).getId());
        map.put("ticket", ticket);
        return map;

    }

    /**
     * 登录功能
     * @param
     * @return
     */
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isEmpty(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }

        if (!StringUtils.hasLength(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = userDao.selectByname(username);

        if (user == null) {
            map.put("msg", "用户名不存在");
            return map;
        }

        if (!WendaUtils.MD5(password+user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "密码不正确");
            return map;
        }

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        map.put("userId", user.getId());
        return map;
    }

    /**
     * 生成Ticket
     */
    public String addLoginTicket(int userId){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime()+3600*24*1000);
        loginTicket.setExpired(date);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        ticketDao.addTicket(loginTicket);
        return loginTicket.getTicket();
    }
    /**
     * 退出登录
     */
    public void logout(String ticket){
        ticketDao.updateStatus(ticket,1);
    }
}
