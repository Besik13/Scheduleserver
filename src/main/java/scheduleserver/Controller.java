package scheduleserver;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping("/info")
    public InfoFromTable info(@RequestParam(value = "what") String what,
                              @RequestParam(value = "from") String from,
                              @RequestParam(value = "where") String where,
                              @RequestParam(value = "eq",defaultValue = "1") String eq) {
        Map<Integer, String> info = new HashMap<Integer, String>();
        query = "select  `id` , `" +
                what +"` from " +
                from + " WHERE "+where+"="+eq;
        try (Connection conn = ConnectionStart.getConnection();
             Statement stmt = conn.createStatement())
        {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                info.put(Integer.valueOf(rs.getString("id")), rs.getString(what));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new InfoFromTable(info);
    }

    @RequestMapping("/test")
    public String test(@RequestParam(value = "what", defaultValue = "*") String what,
                     @RequestParam(value = "from") String from) {
        Map<Integer, String> info = new HashMap<Integer, String>();
        query = "select  " +
                what +" from " +
                from;
        try (Connection conn = ConnectionStart.getConnection();
             Statement stmt = conn.createStatement())
        {
            //preparedStatement.setString(1,what);
            //preparedStatement.setString(2,from);
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                info.put(Integer.valueOf(rs.getString("id")), rs.getString("groups"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }
}
