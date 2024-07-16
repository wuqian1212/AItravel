package com.travel.aitravel;

public class R<T> {
    private T data;
    private String errorMsg;
    private boolean success;
    public R() {
    }

    public R(T data, String errorMsg, boolean success) {
        this.data = data;
        this.errorMsg = errorMsg;
        this.success = success;
    }

    public static <T> R<T> success(T data) {
        return new R<>(data, null, true);
    }

    public static <T> R<T> error(String errorMsg) {
        return new R<>(null, errorMsg, false);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
