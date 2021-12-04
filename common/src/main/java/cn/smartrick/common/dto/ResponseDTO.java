package cn.smartrick.common.dto;

import cn.smartrick.common.constant.Resp;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Date: 2021/12/4
 * @Author: SmartRick
 * @Description: TODO
 */
@Data
@AllArgsConstructor
public class ResponseDTO<T> {
    private String msg;
    private Integer code;
    private T data;

    private ResponseDTO() {
    }

    public ResponseDTO(Resp resp) {
        this.code = resp.getCode();
        this.msg = resp.getMsg();
    }

    public ResponseDTO(Resp resp, T data) {
        this.code = resp.getCode();
        this.msg = resp.getMsg();
        this.data = data;
    }

    public static ResponseDTO succ() {
        return new ResponseDTO(Resp.SUCC);
    }

    public static <T> ResponseDTO<T> succ(T data) {
        return new ResponseDTO(Resp.SUCC, data);
    }

    public static <T> ResponseDTO<T> succ(String msg) {
        return new ResponseDTO(msg, 200, null);
    }

    public static <T> ResponseDTO<T> succ(String msg, T data) {
        return new ResponseDTO(msg, 200, data);
    }

    public static ResponseDTO fail() {
        return new ResponseDTO(Resp.FAIL);
    }

    public static ResponseDTO warp(Resp resp) {
        return new ResponseDTO(resp);
    }
}
