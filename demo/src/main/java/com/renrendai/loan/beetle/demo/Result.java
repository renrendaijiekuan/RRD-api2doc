package com.renrendai.loan.beetle.demo;


import com.renrendai.loan.beetle.commons.api2doc.annotations.ApiComment;

/**
 * 对外返回值统一封转，由此转为json
 * Result
 *
 * @param <T>
 * @author Wang Zhao
 * @date 2015年11月23日 下午4:39:20
 */
public class Result<T> {

    /**
     * 对外返回的对象
     */
    @ApiComment("对外返回的对象")
    private T data;

    /**
     * 返回状态码
     */
    @ApiComment("返回状态码")
    private int code = 0;

    /**
     * 返回消息
     */
    @ApiComment("返回消息")
    private String msg = "";

    public Result() {
        super();
    }


    public Result(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }


    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    /**
     * 服务器unix utc时间戳秒值
     *
     * @return
     */
    public long getTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "data=" + data + " & code=" + code + " & msg=" + msg;
    }
}
