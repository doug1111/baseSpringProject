package com.template.common;


import java.io.Serializable;

import com.template.util.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 通用返回数据封装
 *
 * @author Doug Liu
 * @since 2022-06-10
 *
 */
@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private T data;

    private Integer code;

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