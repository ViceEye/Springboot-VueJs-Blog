package top.venja.common.lang;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    private int code;
    private String msg;
    private Object data;

    public static Result success(String msg) {
        return success(CODE.GLOBAL_SUCCESS, msg, null);
    }

    public static Result success(Object data) {
       return success(CODE.GLOBAL_SUCCESS, "操作成功", data);
    }

    public static Result success(String msg, Object data) {
        return success(CODE.GLOBAL_SUCCESS, msg, data);
    }

    public static Result success(CODE code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code.getCode());
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result fail(String msg) {
        return fail(CODE.GLOBAL_FAIL, msg, null);
    }

    public static Result fail(Object data) {
        return fail(CODE.GLOBAL_FAIL, "操作异常", data);
    }

    public static Result fail(String msg, Object data) {
        return fail(CODE.GLOBAL_FAIL, msg, data);
    }

    public static Result fail(CODE code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code.getCode());
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    /**
     * HTTP respond code
     */
    public enum CODE {

        GLOBAL_SUCCESS(200),
        GLOBAL_FAIL(400),
        SHIRO_FAIL(401),
        LOGIN_TOKEN_FAIL(410);

        private final int code;

        CODE(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

}
