package com.example.wendaoneversion.controller;

import com.example.wendaoneversion.model.DateWrap;
import com.example.wendaoneversion.model.Question;
import com.example.wendaoneversion.model.User;
import com.example.wendaoneversion.model.ViewObject;
import com.example.wendaoneversion.service.impl.QuestionServiceImpl;
import com.example.wendaoneversion.service.impl.UserServiceImpl;
import com.example.wendaoneversion.util.WendaUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    QuestionServiceImpl questionService;
    @Autowired
    UserServiceImpl userService;
    /*@GetMapping(path = {"/","/index"})
    public String index(Model model){
        List<Map<String,Object>> vos = new ArrayList<>();
        List<Question> questionList = questionService.getLatestQuestion(0,0,10);
        //List<ViewObject> vos = new ArrayList<>();
        for(Question question:questionList){
            Map<String,Object> vo = new HashMap<>();
            vo.put("question",question);
            vo.put("user",userService.getUserbyId(question.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("vos",vos);
        return "index";
    }*/

    @GetMapping(path = {"/","index"})
    public String index(Model model, @RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "limit",defaultValue = "5") Integer limit){
        //存放问题和用户部分信息
        List<DateWrap> dateWraps = new ArrayList<>();
        //启用分页
        PageHelper.startPage(page,limit);
        List<Question> questionList = questionService.findAllQuestion(0);
        for (Question question:questionList){
            DateWrap dateWrap = new DateWrap();
            BeanUtils.copyProperties(question,dateWrap);
            User user =  userService.getUserbyId(question.getUserId());
            dateWrap.setName(user.getName());
            dateWrap.setHeadUrl(user.getHeadUrl());
            dateWraps.add(dateWrap);
        }
        PageInfo pageInfo = new PageInfo(questionList);
        pageInfo.setList(dateWraps);
        List<Integer> pageList = WendaUtils.getPageList(pageInfo.getPages(),pageInfo.getPageNum());
        model.addAttribute("result",pageInfo);
        model.addAttribute("pagelist",pageList);
        return "index";
    }
}
