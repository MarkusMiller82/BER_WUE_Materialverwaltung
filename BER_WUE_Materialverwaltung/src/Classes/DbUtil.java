package Classes;

import java.sql.*;


public class DbUtil {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DBConfig.getUrl()
//                "postgres",
  //              "Bereitschaft_Wue_2026"
);
    }
}

