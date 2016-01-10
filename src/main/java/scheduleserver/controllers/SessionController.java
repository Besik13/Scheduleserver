package scheduleserver.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import scheduleserver.bins.wsresponse.WSResponse;
import scheduleserver.manager.SessionManager;
import scheduleserver.connection.ConnectionStart;
import scheduleserver.bins.credentials.Credentials;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
public class SessionController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<WSResponse> login(@RequestBody Credentials credentials,
                                            HttpServletRequest httpServletRequest) {
        String query = "select * from users WHERE `email`= ? and `password`= ?";
        try (Connection conn = ConnectionStart.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, credentials.getEmail());
            preparedStatement.setString(2, credentials.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                SessionManager.logIn(credentials, httpServletRequest);
                return new ResponseEntity<WSResponse>(new WSResponse(HttpStatus.OK, "OK"), HttpStatus.OK);
            }
            return new ResponseEntity<WSResponse>(new WSResponse(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED"), HttpStatus.UNAUTHORIZED);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<WSResponse>(new WSResponse(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED"), HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest httpServletRequest) {
        SessionManager.logOut(httpServletRequest);
    }

}
