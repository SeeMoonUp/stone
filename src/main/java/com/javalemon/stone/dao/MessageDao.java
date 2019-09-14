package com.javalemon.stone.dao;

import com.javalemon.stone.dao.mapper.MessageMapper;
import com.javalemon.stone.model.dto.MessageDTO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 消息
 */
@Repository
public class MessageDao {
    @Resource
    private MessageMapper messageMapper;

    public int addMessage(MessageDTO messageDTO) {
        return messageMapper.addMessage(messageDTO);
    }

    public List<MessageDTO> listMessageByReceive(int userId) {
        return messageMapper.listMessageByReceive(userId);
    }

}
