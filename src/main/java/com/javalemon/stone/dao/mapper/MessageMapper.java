package com.javalemon.stone.dao.mapper;

import com.javalemon.guide.model.dto.MessageDTO;

import java.util.List;

/**
 *
 */
public interface MessageMapper {
    int addMessage(MessageDTO messageDTO);
    List<MessageDTO> listMessageByReceive(int userId);
}
