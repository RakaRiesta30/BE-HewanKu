package com.TuBes.HewanKu;

import java.util.HashMap;
import java.util.Map;

public class BaseResponse {
    public Map<String, Object> OK(){
        Map<String, Object> response = new HashMap<>();
        response.put("code", "200");
        response.put("status", "OK");
        response.put("message", null);
        response.put("data", null);
        response.put("errors", null);
        return response;
    }
}
