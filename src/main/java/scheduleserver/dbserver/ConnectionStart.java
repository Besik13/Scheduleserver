package scheduleserver.dbserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionStart {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(ConnectionSettings.URL,
                ConnectionSettings.USER,
                ConnectionSettings.PASSWORD);
    }
}
