package endpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.thymeleaf.model.IOpenElementTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @program: svc
 * @description:
 * @author: "xpcf"
 * @create: 2020-05-01 15:40
 **/
/*
* 与前端对比
* socket = new WebSocket("ws://"+location.host+"/web/websocket/chat");
* */
@ServerEndpoint(value = "/websocket/chat",configurator = ChatEndpointConfig.class)
@Component
public class ChatEndpoint {

    /**
     * @author:xpcf;
     * @description: a websocket , not httpSession
     */
        Session session;

        String username;

        String userId;

        public ChatEndpoint(){

        }


        @OnOpen
        public void onOpen(Session session, EndpointConfig endpointConfig){
            this.session = session;
            ChatRoom.i.addClient(this);
            HttpSessionEvent event;
            HttpSession httpSession = (HttpSession)
                    endpointConfig.getUserProperties().get("httpSession");

            if(httpSession != null){
                String temp =(String) httpSession.getAttribute("username");
                if(temp != null){
                    this.username = temp;
                }
                this.userId = httpSession.getId();
            }

            List<String> msgList = ChatRoom.i.getMsgList();

            for(String msg : msgList){
                this.sendMessage(msg);
            }

            List<ChatUser>userList = ChatRoom.i.getUserList();

            for(ChatUser chatUser : userList){
                this.sendMessage(chatUser.getUsername());
            }

            String enterMsg = "welcome"+username;
            ChatRoom.i.sendAll(enterMsg);
        }

        @OnClose
        public void close(){
            ChatRoom.i.removeClient(this);

            String leaveMsg = "see you"+username;

            ChatRoom.i.sendAll(leaveMsg);
        }

        @OnError
        public void error(Session session,Throwable error){
            ChatRoom.i.removeClient(this);
            String errorMsg = username + "disconnect";
            error.printStackTrace();
            ChatRoom.i.sendAll(errorMsg);
        }
        @OnMessage
        public void message(String message,Session session){
            String msg = message;
            ChatRoom.i.sendAll(msg);
        }

        public void sendMessage(String msg){
            try{
                session.getBasicRemote().sendText(msg);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        private String getCurrentTime(){
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'");
            return simpleDateFormat.format(new Date());
        }

}
