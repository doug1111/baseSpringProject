package com.template.config.advice;


import com.template.common.ResultDTO;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 返回数据如果没有IgnoreResponseAdvice注解，则统一封装成ResultDTO格式返回，如果需要特定格式返回 需要添加IgnoreResponseAdvice注解，可以在类或者方法上
 *
 * @author Doug Liu
 * @since 2022-06-10
 *
 */
@RestControllerAdvice("com.template")
public class ResultWapAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(
            MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        // 如果当前方法所在的类标识了 IgnoreResultWap 注解, 则不需要处理
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResultWap.class)) {
            return false;
        }
        // 如果当前方法标识了 IgnoreResultWap 注解, 则不需要处理
        return !methodParameter.getMethod().isAnnotationPresent(IgnoreResultWap.class);
        // 对响应进行处理, 执行 beforeBodyWrite 方法
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
            Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
            ServerHttpResponse serverHttpResponse) {
        // 定义最终的返回对象ResultDTO
        ResultDTO resultDTO = null;
        // 如果 o 是 null, response 不需要设置 data
        if (o == null) {
            return ResultDTO.success();
            // 如果 o 已经是 ResultDTO 类型, 强转即可
        } else if (o instanceof ResultDTO) {
            resultDTO = (ResultDTO<Object>) o;
            // 否则, 把响应对象作为 ResultDTO 的 data 部分
        } else {
            resultDTO = ResultDTO.success(o);
        }
        return resultDTO;
    }

}