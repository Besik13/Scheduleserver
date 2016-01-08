package scheduleserver.controllers;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scheduleserver.InfoFromTable;
import scheduleserver.dbserver.ConnectionStart;

import javax.servlet.http.HttpServletRequest;

@RestController
public class Controller {

    private ResultSet rs;
    private String query;

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
}
