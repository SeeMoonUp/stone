package com.javalemon.stone;

import com.javalemon.stone.common.Result;
import com.javalemon.stone.model.dto.ArticleVideoDTO;
import com.javalemon.stone.model.dto.GroupTagDTO;
import com.javalemon.stone.model.dto.VideoDTO;
import com.javalemon.stone.service.ArticleService;
import com.javalemon.stone.service.VideoService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoneApplicationTests {

	@Resource
	VideoService videoService;

	@Resource
	ArticleService articleService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testVideoTag() {
		Result result = videoService.addVideo(VideoDTO.builder().qiniuKey("key").title("test").createTime(DateTime.now().toDate()).build());
		System.out.println(result);
	}

	@Test
	public void testListVideo() {
		Result result = videoService.listVideo();
		System.out.println(result);
	}
	@Test
	public void testArticle() {
		Result result = articleService.addArticleVideo(
				ArticleVideoDTO.builder()
						.content("123")
						.mdContent("234")
						.likeNum(1)
						.viewNum(1).build()
		);
		System.out.println(result);
	}

}
