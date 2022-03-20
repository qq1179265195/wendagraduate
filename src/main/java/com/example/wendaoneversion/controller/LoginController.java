package com.example.wendaoneversion.controller;


import com.example.wendaoneversion.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserServiceImpl userService;

    /**
     * 登录注册界面
     *
     */
    @RequestMapping(path = "/login",method = RequestMethod.GET)
    public String logindex(Model model,
                           @RequestParam(value = "next",required = false) String next){
        model.addAttribute("next",next);
        return "login";
    }

    /**
     * 注册
     */
    @PostMapping("/reg")
    public String reg(Model model,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      HttpServletResponse response
                      ){
        try {
            Map<String, String> map = userService.register(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                response.addCookie(cookie);
                return "redirect:/";
            }else {
                model.addAttribute("msg",map.get("msg"));
                model.addAttribute("username",username);
                model.addAttribute("password",password);
                return "login";
            }
        }catch (Exception e){
            logger.error("注册异常"+e.getMessage());
            return "login";
        }
    }

    /**
     * 登录
     */
    @PostMapping("/userlogin")
    public String Userlogin(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            @RequestParam(value = "remenberme",defaultValue = "false") boolean remenberme,
                            HttpServletResponse response,
                            Model model,
                            @RequestParam(value = "next",required = false) String next){

        try{
            Map<String,Object> map = userService.login(username,password);
            if(map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
                cookie.setPath("/");
                response.addCookie(cookie);
                if (!"".equals(next)) {
                    return "redirect:" + next;
                }else{
                    return "redirect:/";
                }
            }else {
                model.addAttribute("msg",map.get("msg"));
                model.addAttribute("username",username);
                model.addAttribute("password",password);
                if (!"".equals(next)) {
                   model.addAttribute("next",next);
                }
                return "login";
            }

        }catch (Exception e){
                logger.error("登录异常"+e.getMessage());
                return "login";
        }

    }
    @GetMapping("/logout")
    public String Logout(@CookieValue("ticket") String ticket){
            userService.logout(ticket);
            return "redirect:/";
    }

}
