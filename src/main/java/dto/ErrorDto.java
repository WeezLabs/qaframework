package dto;

import java.util.Map;

public class ErrorDto {
    private Integer code;
    private String message;
    private String record_type;
    private Map record_attributes;
    private Map error_data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecord_type() {
        return record_type;
    }

    public void setRecord_type(String record_type) {
        this.record_type = record_type;
    }

    public Map getRecord_attributes() {
        return record_attributes;
    }

    public void setRecord_attributes(Map record_attributes) {
        this.record_attributes = record_attributes;
    }

    public Map getError_data() {
        return error_data;
    }

    public void setError_data(Map error_data) {
        this.error_data = error_data;
    }
}
