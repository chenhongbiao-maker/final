package com.atguigu.springboot.bean;

import java.util.HashMap;
import java.util.Map;

public class ImportExcel {
    private int sum;
    private int success;
    private int fail;
    private Map<String, Object> messages = new HashMap<>();

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    public Map<String, Object> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, Object> messages) {
        this.messages = messages;
    }
}
