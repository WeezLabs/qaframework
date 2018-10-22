package util;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ResourceBundle;

/**
 * Created by vkulakov on 5/29/14.
 */
public class JdbcTemplateUtils {
    private static ResourceBundle rbDB= ResourceBundle.getBundle("database");
    private String SQL_URL;
    private String SQL_USER;
    private String SQL_PASSWORD;
    protected JdbcTemplate jdbcTemplatePg;

    protected JdbcTemplateUtils() {
        SQL_URL = rbDB.getString("db.url");
        SQL_USER = rbDB.getString("db.username");
        SQL_PASSWORD = rbDB.getString("db.password");
        jdbcTemplatePg = jdbcTemplatePg();
    }

    private  BasicDataSource dataSourcePg() {
        BasicDataSource dbcp = new BasicDataSource();
        dbcp.setDriverClassName("org.postgresql.Driver");
        dbcp.setUrl(SQL_URL);
        dbcp.setUsername(SQL_USER);
        dbcp.setPassword(SQL_PASSWORD);
        return dbcp;
    }

    private JdbcTemplate jdbcTemplatePg() {
        return new JdbcTemplate(dataSourcePg());
    }
}
