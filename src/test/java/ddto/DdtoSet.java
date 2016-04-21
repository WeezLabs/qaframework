package ddto;

/*
*класс для формирования дата провайдера
* необходим для того чтобв не писать свой класс для каждого отдельного теста
* в переменной dto может быть любой класс и src/main/java/dto или при необходимости любой другой написанный специально для конкретного теста,
* если существующие классы не удовлетворяют необходимым условиям
*/
import java.math.BigDecimal;

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
        if(o instanceof String && ((String) o).trim().equals("null")) return null;
        if(o instanceof BigDecimal && o.equals(BigDecimal.ZERO)) return null;
        return o;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
