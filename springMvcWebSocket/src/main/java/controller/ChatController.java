package controller;

import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @program: svc
 * @description:
 * @author: "xpcf"
 * @create: 2020-05-01 14:56
 **/
@Controller
public class ChatController {
    @PostMapping("/login")
    @ResponseBody
    public String login(String username , HttpSession httpSession) throws IOException {
        //send user enter
        httpSession.setAttribute("username",username);
        return username;
    }
    @GetMapping("/")
    public String chatRoom(){
        return "chat";
    }
}
