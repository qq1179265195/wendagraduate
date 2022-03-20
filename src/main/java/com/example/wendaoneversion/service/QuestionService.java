package com.example.wendaoneversion.service;

import com.example.wendaoneversion.model.Question;

import java.util.List;

public interface QuestionService {
    public List<Question> getLatestQuestion(int userId,int offset,int limit);

    public int addQuestion(Question question);

    public List<Question> findAllQuestion(int userId);

    public Question findQuestion(int questionid);

}
