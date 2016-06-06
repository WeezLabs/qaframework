package ddto;


import java.math.BigDecimal;

/**
 * Class for creating data provider (ddto).
 * Inside dto variable you can use any class from src/main/java/dto or write another class specific to your test.
 */
public class DdtoSet<T> extends AbstractDdtoSet {
    private T dto;
    private int statusCode;
    private String moduleName;

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

    public Object valueOf(Object o){
        if (o instanceof String && ((String) o).trim().equals("null")) {
            return null;
        }

        if (o instanceof BigDecimal && o.equals(BigDecimal.ZERO) ){
            return null;
        }

        return o;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
