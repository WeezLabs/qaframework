package dao;


import org.springframework.jdbc.datasource.DataSourceUtils;
import util.JdbcTemplateUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Put your DB calls here
 */
public class DBDao extends JdbcTemplateUtils {

    public void closeConnection() throws SQLException {
        Connection con = DataSourceUtils.getConnection(jdbcTemplatePg.getDataSource());
        con.close();
    }

    // This is just code example. Remove it later
    public Integer setUserRole(Integer id, Integer role) {
        String sql = "UPDATE users SET role = " + role + " WHERE id = " + id;
        return jdbcTemplatePg.update(sql);
    }
}
