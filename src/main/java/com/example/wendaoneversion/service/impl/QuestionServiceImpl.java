package com.example.wendaoneversion.service.impl;

import com.example.wendaoneversion.dao.QuestionDao;
import com.example.wendaoneversion.model.Question;
import com.example.wendaoneversion.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionDao questionDao;
    @Override
    public List<Question> getLatestQuestion(int userId,int offset,int limit) {
        List<Question> list = questionDao.selectLatestQuestion(userId, offset, limit);
        return list;
    }

    @Override
    public int addQuestion(Question question) {

        return questionDao.addQuestion(question);
    }

    @Override
    public List<Question> findAllQuestion(int userId) {
        List<Question> list = questionDao.findAllQuestion(userId);
        return list;
    }

    @Override
    public Question findQuestion(int questionid) {
        return questionDao.findQuestion(questionid);
    }
}
