package scheduleserver;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;
import scheduleserver.dbserver.ConnectionStart;

@RestController
public class Controller {

    private ResultSet rs;
    private String query;

    @RequestMapping("/login")
    public Login login(@RequestParam(value = "email") String email,
                       @RequestParam(value = "password") String password) {
        query = "select * from users WHERE `email`= ? and `password`= ?";
        try (Connection conn = ConnectionStart.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query))
        {
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            rs = preparedStatement.executeQuery();
            int size = 0;
            if (rs != null) {
                rs.beforeFirst();
                rs.last();
                size = rs.getRow();
                if (size != 0) {
                    return new Login(true);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Login(false);
    }

    @RequestMapping("/groups")
    public Groups groups() {
        Map<Integer, String> groups = new HashMap<Integer, String>();
        query = "select  * from groups";
        try (Connection conn = ConnectionStart.getConnection();
             Statement stmt = conn.createStatement())
        {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                groups.put(Integer.valueOf(rs.getString("id")), rs.getString("group"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Groups(groups);
    }

    @RequestMapping(value = "/add_user", method = RequestMethod.POST)
    @ResponseBody
    public NewUser addUser(@RequestBody NewUser user) {

        try(Connection conn = ConnectionStart.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            query = "INSERT INTO `users`" +
                    "(`id`, `email`, `password`, `name`, `surename`)" +
                    "VALUES (NULL, ? , ? , ? , ?)";
            preparedStatement.setString(1,user.getEmail());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getName());
            preparedStatement.setString(4,user.getSurname());
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
