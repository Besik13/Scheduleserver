package scheduleserver.manager;

import scheduleserver.connection.ConnectionStart;
import scheduleserver.bins.credentials.Credentials;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionManager {
    private static final String currentUser="currentUser";
    public static boolean isLogedIn(HttpServletRequest httpServletRequest)
    {
        if(httpServletRequest.getSession().getAttribute(currentUser)==null)
        {
            return false;
        }
        Credentials credentials= (Credentials) httpServletRequest.getSession().getAttribute(currentUser);
        String query = "select * from users WHERE `email`= ? and `password`= ?";
        try (Connection conn = ConnectionStart.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query))
        {
            preparedStatement.setString(1,credentials.getEmail());
            preparedStatement.setString(2,credentials.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            int size = 0;
            if (rs != null) {
                rs.beforeFirst();
                rs.last();
                size = rs.getRow();
                if (size != 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logOut(httpServletRequest);
            return false;
        }
        logOut(httpServletRequest);
        return false;
    }

    public static void logIn(Credentials credentials, HttpServletRequest httpServletRequest)
    {
        httpServletRequest.getSession().setAttribute(currentUser,credentials);
    }
    public static void logOut(HttpServletRequest httpServletRequest)
    {
        httpServletRequest.getSession().invalidate();
    }
}
