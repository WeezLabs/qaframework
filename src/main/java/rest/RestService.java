package rest;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * заполнение хедеров идентификационными данными, полученными после логина, для дальнейшего использования
 */
public class RestService extends AbstractRest implements Rest {

    protected String st;
    private AuthRest authRest;

    public RestService(Module module, String tgt) throws JsonProcessingException {
        super(module);
        if (tgt != null) setSt(tgt);
    }

    public void setSt(String st) {
        /*if ((st != null) && (!st.isEmpty())) {
            this.st = st;
            STANDARD_HEADERS.put("Authorization", "AccessToken "+st);
            Map<String,String> xTokenHeader = new HashMap<>();
            xTokenHeader.put("Authorization", "AccessToken "+st);

            getRequestSpecBuilder.addHeaders(xTokenHeader);
            getSpecification = getRequestSpecBuilder.build();

            postRequestSpecBuilder.addHeaders(xTokenHeader);
            postSpecification = postRequestSpecBuilder.build();

            postRequestUnContentSpecBuilder.addHeaders(xTokenHeader);
            postSpecificationUnContent = postRequestUnContentSpecBuilder.build();
        }*/
    }
    public String getSt() {
        return st;
    }
}

