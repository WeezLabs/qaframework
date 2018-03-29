package ddto;

import java.math.BigDecimal;

public class DdtoSet<T> extends AbstractDdtoSet {
    private T dto;
    private int statusCode;

    public T getDto() {
        return dto;
    }

    public void setDto(T dto) {
        this.dto = dto;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Object valueOf(Object object) {
        if (object instanceof String && ((String) object).trim().equals("null")) {
            return null;
        }
        if (object instanceof BigDecimal && object.equals(BigDecimal.ZERO)) {
            return null;
        }
        return object;
    }
}
