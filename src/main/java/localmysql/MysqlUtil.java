package localmysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author litao34
 * @ClassName MysqlUtil
 * @Description TODO
 * @CreateDate 2022/5/6-11:21 AM
 **/
public class MysqlUtil {
    private static final String PATH_URL = "jdbc:mysql://localhost:3306/test_db";
    private static final String USER = "root";
    private static final String PASSWORD = "123";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(PATH_URL,USER,PASSWORD);
        if (Objects.nonNull(connection)){
            return connection;
        }
        return null;
    }

}
