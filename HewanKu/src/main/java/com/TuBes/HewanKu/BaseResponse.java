package com.TuBes.HewanKu;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class BaseResponse {
    private Map<String, Object> base(int code, String status, Object message, Object data, Object errors) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("code", code);
        response.put("status", status);
        response.put("message", message);
        response.put("data", data);
        response.put("errors", errors);
        return response;
    }

    public Map<String, Object> OK(Object message, Object data, Object error) {
        return base(200, "OK", message, data, error);
    }

    public Map<String, Object> CREATED(Object message, Object data, Object error) {
        return base(201, "CREATED", message, data, error);
    }

    public Map<String, Object> UNAUTHORIZED(Object message, Object data, Object error) {
        return base(401, "UNAUTHORIZED", message, data, error);
    }

    public Map<String, Object> FORBIDDEN(Object message, Object data, Object error) {
        return base(403, "FORBIDDEN", message, data, error);
    }
}
