package com.alpha.community.provider;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alpha.community.dto.AccessTokenDTO;
import com.alpha.community.dto.GithubUser;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
@Component
public class GithubProvider {
	private String string;
	/**
	 * 通过post请求访问https://github.com/login/oauth/access_token这个网址
	 * 它的参数封装为一个dto，会返回一个access_token
	 * @param accessTokenDTO
	 * @return
	 */
	public String getAccessToken(AccessTokenDTO accessTokenDTO) {
		final MediaType JSONType = MediaType.get("application/json; charset=utf-8");

		OkHttpClient client = new OkHttpClient();

		RequestBody body = RequestBody.create(JSONType, JSON.toJSONString(accessTokenDTO));
		Request request = new Request.Builder().url("https://github.com/login/oauth/access_token").post(body).build();
		try (Response response = client.newCall(request).execute()) {
			// 由于返回的字符串类似
			// access_token=f12fe7f03e88d097091079fdb1b6fd32c740adb1&scope=user&token_type=bearer
			// 需要对其剪辑
			String respStr= response.body().string();
			return respStr.split("=")[1].replaceAll("&scope", "");
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * 通过aceess_token来获取用户信息，get https://api.github.com/user
	 * 会返回用户信息的json数据
	 * @param accessToken
	 * @return
	 */
	public GithubUser getUser(String accessToken) {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url("https://api.github.com/user?access_token="+accessToken).build();
		try {
			Response response = client.newCall(request).execute();
			String result = response.body().string();
			return JSON.parseObject(result, GithubUser.class);
		} catch (IOException e) {
			return null;
		}

	}

}
