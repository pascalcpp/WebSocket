package endpoint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @program: svc
 * @description:
 * @author: "xpcf"
 * @create: 2020-05-01 16:46
 **/
public class ChatRoom {
    public static ChatRoom i = new ChatRoom();

    private List<ChatEndpoint> clientList = new LinkedList<>();

    private List<ChatEndpoint> clientListCopy = new LinkedList<>();

    private List<String> msgList = new LinkedList<>();

    private Integer maxNumMsg = 30;
    public void addClient(ChatEndpoint client){
        synchronized (clientList){
            clientList.add(client);

            clientListCopy = new LinkedList<>(clientList);
        }
    }

    public void removeClient(ChatEndpoint client){
        synchronized (clientList){
            clientList.remove(client);
            clientListCopy = new ArrayList<>(clientList);
        }
    }

    public void sendAll(String msg){
        synchronized (msgList){
            msgList.add(msg);
            if(msgList.size()>maxNumMsg){
                msgList.remove(0);
            }
        }
        Iterator iterator = clientListCopy.iterator();

        while(iterator.hasNext()){
            ChatEndpoint client = (ChatEndpoint)iterator.next();
            client.sendMessage(msg);
        }
    }

    public List<String> getMsgList(){
        synchronized (msgList){
            return new LinkedList<>(msgList);
        }
    }

    public List<ChatUser> getUserList(){
        List<ChatUser> result = new ArrayList<>();

        Iterator iterator = clientListCopy.iterator();
        while(iterator.hasNext()){
            ChatEndpoint client = (ChatEndpoint) iterator.next();
            ChatUser chatUser = new ChatUser();
            chatUser.setUserId(client.userId);
            chatUser.setUsername(client.username);
            result.add(chatUser);
        }
        return result;
    }
}
