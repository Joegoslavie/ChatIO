package base.repositories;
import base.factories.MessageFactory;
import models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Message> getMessageByChatId(int chatId){
        return jdbcTemplate.query("sp_GetMessagesByChat ?", new Object[] {chatId}, new MessageFactory());
    }

    public List<Message> getMessagesByGroupId(int groupId){
        return jdbcTemplate.query("sp_GetMessagesByGroup ?", new Object[] {groupId}, new MessageFactory());
    }

    public Message storeMessage(int senderId, int destId, String body, boolean isGroup){
        if(isGroup)
            return this.store(senderId, 0, destId, body);
        else
            return this.store(senderId, destId, 0, body);
    }

    private Message store(int senderId, int chatId, int groupId, String body){
        return (Message)jdbcTemplate.queryForObject("sp_SendMessage ?, ?, ?, ?", new Object[] {senderId, chatId, groupId, body}, new MessageFactory());
    }
}
