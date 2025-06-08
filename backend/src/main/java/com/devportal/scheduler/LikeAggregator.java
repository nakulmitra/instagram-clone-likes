package com.devportal.scheduler;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.devportal.repository.PostRepository;

@Component
@EnableScheduling
public class LikeAggregator {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private PostRepository repo;

	@Scheduled(fixedRate = 30000)
	public void aggregateLike() {
		Set<String> keys = redisTemplate.keys("post:like:*");
		if (null == keys) {
			return;
		}

		for (String key : keys) {
			Long postId = Long.valueOf(key.split(":")[2]);
			String likeCountStr = redisTemplate.opsForValue().get(key);
			Long count = likeCountStr != null ? Long.valueOf(likeCountStr) : 0;

			if (count > 0) {
				repo.findById(postId).ifPresent(post -> {
					post.setLikeCount(post.getLikeCount() + count);
					repo.save(post);
					redisTemplate.delete(key);
				});
			}
		}
	}

}
