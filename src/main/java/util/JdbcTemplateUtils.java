package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ResourceBundle;

/**
 * Database connection.
 */
public class JdbcTemplateUtils {
    private static ResourceBundle rbDB = ResourceBundle.getBundle("database");
    private String SQL_URL;
    private String SQL_USER;
    private String SQL_PASSWORD;
    private JdbcTemplate jdbcTemplatePg;

    protected ObjectMapper mapper = new ObjectMapper();

    public JdbcTemplateUtils() {
        SQL_URL = rbDB.getString("db.url")
                .replaceAll("(.+?)(\\$\\{db-server})(.+)", "$1"+rbDB.getString("db.defaultServer")+"$3");
        SQL_USER = rbDB.getString("db.username");
        SQL_PASSWORD = rbDB.getString("db.password");
        jdbcTemplatePg  = jdbcTemplatePg();
    }

    public BasicDataSource dataSourcePg() {
        BasicDataSource dbcp = new BasicDataSource();
        dbcp.setDriverClassName("net.sourceforge.jtds.jdbc.Driver");
        dbcp.setUrl(SQL_URL);
        dbcp.setUsername(SQL_USER);
        dbcp.setPassword(SQL_PASSWORD);
        return dbcp;
    }

    private JdbcTemplate jdbcTemplatePg() {
        return new JdbcTemplate(dataSourcePg());
    }
}
