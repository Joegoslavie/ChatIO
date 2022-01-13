package base.repositories;

import base.factories.ChatFactory;
import models.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    public List<Chat> getChatsByUserId(int userId){
        List<Chat> chats = jdbcTemplate.query("sp_GetChatsByUserId ?", new Object[] {userId}, new ChatFactory());

        for(Chat chat : chats){
            chat.setMembers(userRepository.getUsersByChat(chat.getId()));
            chat.setMessages(messageRepository.getMessageByChatId(chat.getId()));
        }

        return chats;
    }

    public Chat create(int userA, int userB){
        Chat cht = (Chat)jdbcTemplate.queryForObject("sp_StartChat ?, ?", new Object[]{userA, userB}, new ChatFactory());
        cht.setMembers(userRepository.getUsersByChat(cht.getId()));
        cht.setMessages(messageRepository.getMessageByChatId(cht.getId()));

        return cht;
    }
}
