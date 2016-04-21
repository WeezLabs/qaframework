package rest;
import java.util.HashMap;
import java.util.Map;

/**
 * используется при наличии нескольких точек входа
 * на данном этапе простаивает
 */
public enum Module {
     NONE("");


//    SAMPLE_REMOTE("sample-remote","/api/"),
//    SAMPLE("sample","/api/");
//    ACL_SERVICE("acl-service"),
//    CLIENT_SERVICE("client-service"),
//    COMPANY_SERVICE("company-service"),
//    DICTIONARY_SERVICE("dictionary-service"),
//    REPORT_SERVICE("report-service");

    private String moduleName;

    private static Map<String, Module> nameToModuleMapping;

    Module(String moduleName) {
        this.moduleName = moduleName;
    }

    private static void initNameMapping(){
        nameToModuleMapping = new HashMap<>();
        for(Module m : values()){
            nameToModuleMapping.put(m.moduleName, m);
        }
    }

    public static Module get(String moduleName){
        if(null == moduleName) return null;
        if(null == nameToModuleMapping){
            initNameMapping();
        }
        return nameToModuleMapping.get(moduleName);
    }

    public String getModuleName() {
        return moduleName;
    }
}
