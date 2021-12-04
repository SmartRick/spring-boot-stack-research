package cn.smartrick.common.constant;

public enum Resp {
    /**
     * 成功
     */
    SUCC("SUCCESS",200),
    /**
     * 失败
     */
    FAIL("FAILED",500);

    private String msg;
    private Integer code;

    Resp(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Resp{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }
}
