package endpoint;

import javax.servlet.http.HttpSession;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

/**
 * @program: svc
 * @description:
 * @author: "xpcf"
 * @create: 2020-05-01 16:01
 **/
public class ChatEndpointConfig extends Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec
            , HandshakeRequest request
            , HandshakeResponse response)
    {
        // 把 HttpSession 放到 ServerEndpointConfig 中
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        if(httpSession != null){
            sec.getUserProperties().put("httpSession", httpSession);
        }

    }
}
