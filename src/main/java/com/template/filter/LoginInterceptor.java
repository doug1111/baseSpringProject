package com.template.filter;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.template.app.dto.LoginDto;
import com.template.app.entity.enums.CustomConstants;
import com.template.common.ResultDTO;
import com.template.util.ContextUtil;
import com.template.util.RedisUtil;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器
 *
 * @author Doug Liu
 * @since 2022-06-10
 *
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Resource
	private RedisUtil redisUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
        /*String token = request.getHeader("token");
		String uri = request.getRequestURI();
		if (!Strings.isNullOrEmpty(token)) {
			// 校验用户是否已经补全信息
			Map<Object, Object> map = redisUtil.hmget(CustomConstants.User.TOKEN_KEY + token);
			// 校验未登录状态
			if (!map.isEmpty()) {
				LoginDto loginDto = BeanUtil.mapToBean(map, LoginDto.class, true, null);
				List<String> apiList = loginDto.getApiList();
				if (apiList.contains(uri)) {
					ContextUtil.setContext(loginDto);
					return true;
				}
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		PrintWriter out = response.getWriter();
		out.println(JSONObject.toJSONString(ResultDTO.fail(401)));
		out.flush();
		out.close();
		return false;*/
	}
}