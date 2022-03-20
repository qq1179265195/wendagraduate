package com.example.wendaoneversion.controller;

import com.example.wendaoneversion.model.HostHolder;
import com.example.wendaoneversion.model.Question;
import com.example.wendaoneversion.service.impl.QuestionServiceImpl;
import com.example.wendaoneversion.service.impl.SensitiveServiceImpl;
import com.example.wendaoneversion.util.WendaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

@Controller
public class PublishController {

        private static final Logger logger = LoggerFactory.getLogger(PublishController.class);

        @Autowired
        QuestionServiceImpl questionService;
        @Autowired
        HostHolder hostHolder;
        @Autowired
        SensitiveServiceImpl sensitiveService;
        @GetMapping("/Publish")
        public String PublishIndex(Model model){

            return "publish";
        }
        @PostMapping("/doPublish")
        public String doPublish(@RequestParam("title") String title,
                                @RequestParam("descritption") String content,
                                @RequestParam(value = "tag",required = false) String Tag,
                                Model model){
                /**
                 * 必填校验
                 */
                if (WendaUtils.isEmpty(title)||WendaUtils.isEmpty(content)) {
                        if (WendaUtils.isEmpty(title)) {
                                model.addAttribute("title", "请填写标题");
                        } else {
                                model.addAttribute("intitle", title);
                        }
                        if (WendaUtils.isEmpty(content)) {
                                model.addAttribute("content", "请填写问题描述");
                        } else {
                                model.addAttribute("incontent", content);
                        }
                        return "publish";
                }

                try{
                        Question question = new Question();
                        question.setTitle(HtmlUtils.htmlEscape(title));
                        question.setContent(HtmlUtils.htmlEscape(content));
                        //敏感词过滤
                        question.setTitle(sensitiveService.filter(question.getTitle()));
                        question.setContent(sensitiveService.filter(question.getContent()));
                        question.setCreatedDate(new Date());
                        question.setCommentCount(0);
                        if (hostHolder.getUser()!=null){
                                question.setUserId(hostHolder.getUser().getId());
                        }else{
                                question.setUserId(WendaUtils.ANONYMOUS_USER);
                        }
                        int result=questionService.addQuestion(question);
                        if (result>0){
                                return "redirect:/";
                        }
                }catch (Exception e){
                    logger.error("增加题目异常"+e.getMessage());
                    return "publish";
                }
                return "redirect:/";
        }

}
