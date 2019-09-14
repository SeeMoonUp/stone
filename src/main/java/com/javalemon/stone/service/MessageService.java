package com.javalemon.stone.service;

import com.javalemon.stone.common.Result;
import com.javalemon.stone.dao.MessageDao;
import com.javalemon.stone.model.MessageVO;
import com.javalemon.stone.model.dto.MessageDTO;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author lemon
 * @date 2019-07-14
 * @desc
 */

@Service
public class MessageService {

    @Resource
    private MessageDao messageDao;

    public Result addMessage(MessageDTO messageDTO) {
        try {
            int res = messageDao.addMessage(messageDTO);
            if (res >= 0) {
                return Result.success();
            } else {
                return Result.error(Result.CodeEnum.DAO_ERROR);
            }
        } catch (Exception e) {
            return Result.error(Result.CodeEnum.SERVICE_ERROR);
        }
    }

    public Result<List<MessageVO>> listMessage(int userId) {
        try {
            List<MessageDTO> messageDTOS = messageDao.listMessageByReceive(userId);
            if (messageDTOS == null || messageDTOS.size() <= 0) {
                return Result.success(Collections.emptyList());
            }

            List<MessageVO> messageVOS = new ArrayList<>();
            for (MessageDTO messageDTO : messageDTOS) {
                DateTime createTime = new DateTime(messageDTO.getCreateTime());
                messageVOS.add(
                        MessageVO.builder().
                                content(messageDTO.getContent()).
                                date(createTime.toString("MM月dd日")).
                                sender(messageDTO.getSendUserName()).
                                build()
                );
            }

            return Result.success(messageVOS);
        } catch (Exception e) {
            return Result.error(Result.CodeEnum.SERVICE_ERROR);
        }
    }
}
