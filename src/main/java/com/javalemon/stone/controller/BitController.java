package com.javalemon.stone.controller;

import com.javalemon.guide.common.Result;
import com.javalemon.guide.model.vo.bit.WeekHotVO;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lemon
 * @date 2019-07-13
 * @desc
 */

@Controller
@Configuration
@RequestMapping("bit")
public class BitController extends BaseController{

    @PostMapping("home")
    @ResponseBody
    public Result home(HttpServletRequest request) {
        List<WeekHotVO> weekHotVOS = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            weekHotVOS.add(
                    WeekHotVO.builder()
                            .videoId("java-byte")
                            .videoImg("http://img.javalemon.com/20190825156674496824689.png")
                            .authorImg("http://img.javalemon.com/2019082515667451245009.png")
                            .title("第一个精选视频")
                            .desc("描述")
                            .videoDuration("05:16")
                            .author("bob")
                            .viewNum(100 + i)
                            .publishTime(i+"天前")
                            .build()
            );
        }
        return Result.success(weekHotVOS);

    }

}
