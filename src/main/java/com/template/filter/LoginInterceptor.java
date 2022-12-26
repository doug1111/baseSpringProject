package com.template.filter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.template.util.RedisUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @author Doug Liu
 * @since 2022-06-10
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final RedisUtil redisUtil;

    public LoginInterceptor(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
        /*String token = request.getHeader("token");
        String uri = request.getRequestURI();
        if (StringUtils.isNotBlank(token)) {
            // 校验用户是否已经补全信息
            Map<Object, Object> map = redisUtil.hmget(CustomConstants.User.TOKEN_KEY + token);
            // 校验未登录状态
            if (!map.isEmpty()) {
                LoginDTO loginDTO = BeanUtil.mapToBean(map, LoginDTO.class, true, null);
                List<String> apiList = loginDTO.getApiList();
                if (apiList.contains(uri)) {
                    ContextUtil.setContext(loginDTO);
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