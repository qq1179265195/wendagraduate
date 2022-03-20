package com.example.wendaoneversion.controller;


import com.example.wendaoneversion.model.DateWrap;
import com.example.wendaoneversion.model.Question;
import com.example.wendaoneversion.model.User;
import com.example.wendaoneversion.service.impl.QuestionServiceImpl;
import com.example.wendaoneversion.service.impl.UserServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class QuestionController {

    @Autowired
    QuestionServiceImpl questionService;

    @Autowired
    UserServiceImpl userService;
    @RequestMapping(path = "/question/{questionid}",method = RequestMethod.GET)
    public String QuestionDetail(Model model,
                                 @PathVariable("questionid") int questionid){
        DateWrap dateWrap = new DateWrap();
        Question question = questionService.findQuestion(questionid);
        BeanUtils.copyProperties(question,dateWrap);
        User user = userService.getUserbyId(question.getUserId());
        dateWrap.setName(user.getName());
        dateWrap.setHeadUrl(user.getHeadUrl());
        model.addAttribute("dateWrap",dateWrap);
        return "detail";

    }

}
