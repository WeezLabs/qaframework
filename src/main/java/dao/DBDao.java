package dao;

import util.JdbcTemplateUtils;


/**
 * класс используется для обращения к базе
 * необходимо заполнить своими функциями.
 * пример
 * public Long createNewUser(String userName, Integer roleId) throws SQLException {
 String sql = "INSERT INTO \"dbo\".\"User\" " +
 "( CompanyID, Email, Username, Lastname, Firstname, PasswordHash, PasswordSalt, RoleID, LastActivity, IsActivated, IsBlocked, IsDeleted)" +
 " VALUES " +
 "( ?, ?, ?, ?,?, '6C9ACE0931D3C6204A2658531681C207F8F89766B7DE0F388B9712A844726347', '0B31B34EF76CC47C417D95FC876D42C176F4FDDE430FF7728C22166FA33124F6', ?, null,1 ,0 ,0 );";
 String firstName = userName.substring(0, userName.indexOf("@"));
 String lastName = userName.substring(userName.indexOf("@") + 1, userName.indexOf("."));
 Long companyId = (isCompanyExists("API Test Main Company") > 0) ? getCompany("API Test Main Company") : createMainCompany();
 jdbcTemplatePg.update(sql, companyId, userName, firstName + "_" + lastName, lastName, firstName, roleId);
 String sql1 = "SELECT ID FROM \"dbo\".\"User\" " +
 "WHERE Email LIKE ? " +
 "";
 List<Map<String, Object>> mapList = jdbcTemplatePg.queryForList(sql1, new Object[]{"%" + userName + "%"});
 if (mapList.size() == 0) return null;
 else return (Long) mapList.get(0).get("id");
 }
 */
public class DBDao extends JdbcTemplateUtils {



}
