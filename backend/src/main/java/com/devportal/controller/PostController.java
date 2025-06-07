package com.devportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devportal.service.LikeService;

@RestController
@RequestMapping("/posts")
public class PostController {
	
	@Autowired
	private LikeService service;
	
	@PostMapping(value ="/{postId}/like", produces = "application/text")
	public String likePost(@PathVariable Integer postId) {
		service.recordLike(postId);
		return "Liked successfully!!!";
	}

}
