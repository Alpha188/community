package com.alpha.community.controller;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alpha.community.dto.AccessTokenDTO;
import com.alpha.community.dto.GithubUser;
import com.alpha.community.enums.CustomizeErrorCodeEnum;
import com.alpha.community.exception.CustomizeException;
import com.alpha.community.mapper.UserMapper;
import com.alpha.community.model.User;
import com.alpha.community.provider.GithubProvider;
import com.alpha.community.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class AuthorizeController {
	private static final int COOKIE_TOKEN_AGE = 60 * 60 * 24 * 2;
	@Autowired
	private GithubProvider githubProvider;
	@Autowired
	private UserService userService;
	
	@Value("${github.client.id}")
	private String githubClientId;
	@Value("${github.client.secret}")
	private String githubClientSecret;
	@Value("${github.redirect.uri}")
	private String githubRedirectUri;

	@GetMapping("/callback")
	public String calback(@RequestParam("code") String code, 
						  @RequestParam("state") String state,
						  HttpServletResponse resp) {
		
		AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
		accessTokenDTO.setCode(code);
		accessTokenDTO.setState(state);
		accessTokenDTO.setClient_id(githubClientId);
		accessTokenDTO.setClient_secret(githubClientSecret);
		accessTokenDTO.setRedirect_uri(githubRedirectUri);
		String accessToken = githubProvider.getAccessToken(accessTokenDTO);
		GithubUser githubUser = githubProvider.getUser(accessToken);
		if (githubUser != null && githubUser.getId() != null) {
			User user = new User();
			String token=UUID.randomUUID().toString();
			user.setAccountId(String.valueOf(githubUser.getId()));
			user.setName(githubUser.getName());
			user.setToken(token);
			user.setAvatarUrl(githubUser.getAvatarUrl());
			user.setBio(githubUser.getBio());
			userService.insertOrUpdateUser(user);
			
			Cookie cookie = new Cookie("token", token);
			cookie.setMaxAge(COOKIE_TOKEN_AGE);
			resp.addCookie(cookie);
			return "redirect:/";
		} else {
			log.error("用户登陆失败,{}",githubUser);
			throw new CustomizeException(CustomizeErrorCodeEnum.LOGIN_TIME_OUT);
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("user");
		Cookie cookie = new Cookie("token", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return "redirect:/";
	}
}


