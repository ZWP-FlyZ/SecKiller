package com.zwp.web.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;

@Controller
public class HelloLoginController {

    private final static Logger logger = LoggerFactory.getLogger(HelloLoginController.class);


    @PostMapping("/my_login")
    @ResponseBody
    public String login(HttpServletRequest request,String username,String password){
        HttpSession sess = request.getSession();
        logger.info("username:{},password:{} account",username,password);
        logger.info(sess.getId()+","+sess.getMaxInactiveInterval());
        String ret = String.format("username:%s time:%s account",username, Instant.now());
        ret += " "+sess.getCreationTime();
        return ret;
    }

    @GetMapping("/hello")
    @ResponseBody
    public String say(HttpServletRequest request){
        HttpSession sess = request.getSession();
        logger.info(sess.getId()+","+sess.getMaxInactiveInterval());
        return "hello";
    }



}
