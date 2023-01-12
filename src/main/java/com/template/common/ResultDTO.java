package com.template.common;


import com.template.util.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通用返回数据封装
 *
 * @author Doug Liu
 * @since 2022-06-10
 */
@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 返回代码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    public static <T> ResultDTO<T> fail(Integer code, String message) {
        ResultDTO<T> r = new ResultDTO<>();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    public static <T> ResultDTO<T> fail(Integer code) {
        ResultDTO<T> r = new ResultDTO<>();
        r.setCode(code);
        r.setMessage(MessageUtil.get(String.valueOf(code)));
        return r;
    }

    public static <T> ResultDTO<T> success(T data, Integer code, String message) {
        ResultDTO<T> r = new ResultDTO<>();
        r.setCode(code);
        r.setMessage(message);
        r.setData(data);
        return r;
    }

    public static <T> ResultDTO<T> success() {
        ResultDTO<T> r = new ResultDTO<>();
        r.setCode(0);
        r.setData(null);
        r.setMessage("success");
        return r;
    }

    public static <T> ResultDTO<T> success(T data) {
        ResultDTO<T> r = new ResultDTO<>();
        r.setCode(0);
        r.setData(data);
        r.setMessage("success");
        return r;
    }
}