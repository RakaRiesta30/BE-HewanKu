package com.TuBes.HewanKu.Pesanan;

import java.util.Map;

public class PesananDTO {
    private Map<String, String> form;
    private String status;

    public Map<String, String> getForm() {
        return form;
    }

    public void setForm(Map<String, String> form) {
        this.form = form;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
