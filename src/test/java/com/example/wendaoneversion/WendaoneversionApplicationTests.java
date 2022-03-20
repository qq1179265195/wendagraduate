package com.example.wendaoneversion;

import com.example.wendaoneversion.dao.QuestionDao;
import com.example.wendaoneversion.dao.UserDao;
import com.example.wendaoneversion.model.Question;
import com.example.wendaoneversion.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Random;

@SpringBootTest
class WendaoneversionApplicationTests {


    @Autowired
    UserDao userDAO;
    @Autowired
    QuestionDao questionDao;

    @org.junit.jupiter.api.Test
    public void contextLoads() {
      /*  Random random = new Random();*/

      /*  for (int i = 20; i < 21; ++i) {
             User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USER%d", i + 1));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);
           *//* user.setPassword("newpassword");
            userDAO.updatePassword(user);
            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * 5 * i);*//*
        }*/
    }
    @Test
    public void test(){
        Question question = new Question();
        question.setCommentCount(1);
        Date date = new Date();
        date.setTime(date.getTime()+1000*3600);
        question.setCreatedDate(date);
        question.setUserId(10);
        question.setTitle("title1");
        question.setContent("哈哈哈");
        questionDao.addQuestion(question);
        questionDao.selectLatestQuestion(0,0,10);
    }

}
