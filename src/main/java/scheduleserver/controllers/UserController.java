package scheduleserver.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scheduleserver.DAO.DAOConstants;
import scheduleserver.DAO.scheduleDAO.UsersDAO;
import scheduleserver.bins.user.User;
import scheduleserver.bins.user.UserList;
import scheduleserver.bins.wsresponse.WSResponse;
import scheduleserver.manager.SessionManager;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @RequestMapping(value = "/user")
    public ResponseEntity<UserList> getUsers(HttpServletRequest httpServletRequest,
                                             @RequestParam(value = "id", defaultValue = DAOConstants.ALL_VALUES_IDENTIFIER) String id,
                                             @RequestParam(value = "email", defaultValue = DAOConstants.ALL_VALUES_IDENTIFIER) String email,
                                             @RequestParam(value = "password", defaultValue = DAOConstants.ALL_VALUES_IDENTIFIER) String password,
                                             @RequestParam(value = "name", defaultValue = DAOConstants.ALL_VALUES_IDENTIFIER) String name,
                                             @RequestParam(value = "surname", defaultValue = DAOConstants.ALL_VALUES_IDENTIFIER) String surname) {
        //if (SessionManager.isLogedIn(httpServletRequest)) {
            return new ResponseEntity<UserList>(UsersDAO.getUsers(id,email,password,name,surname), HttpStatus.OK);
        //}
        //return new ResponseEntity<UserList>(HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<WSResponse> addUser(HttpServletRequest httpServletRequest, @RequestBody User user) {

        //if (SessionManager.isLogedIn(httpServletRequest)) {
            if (user != null) {
                boolean isCreated = UsersDAO.createUser(user);
                HttpStatus status = isCreated ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;
                String text = isCreated ? "CREATED" : "UNABLE TO CREATE NEW USER";
                return new ResponseEntity<WSResponse>(new WSResponse(status, text), status);

            }
        //}
        return new ResponseEntity<WSResponse>(HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public ResponseEntity<WSResponse> deleteUser(HttpServletRequest httpServletRequest,
                                                  @RequestParam(value = "id", defaultValue = "-1") int id) {

        if (SessionManager.isLogedIn(httpServletRequest)) {
            boolean isDeleted = UsersDAO.deleteUser(id);
            HttpStatus status = isDeleted ? HttpStatus.ACCEPTED : HttpStatus.INTERNAL_SERVER_ERROR;
            String text = isDeleted ? "DELETED" : "UNABLE TO DELETE USER WITH ID=" + id;
            return new ResponseEntity<WSResponse>(new WSResponse(status, text), status);


        }
        return new ResponseEntity<WSResponse>(HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public ResponseEntity<WSResponse> updateUser(HttpServletRequest httpServletRequest,
                                                  @RequestParam(value = "id", defaultValue = "-1") int id,
                                                  @RequestBody User user) {

        if (SessionManager.isLogedIn(httpServletRequest)) {
            if (user != null) {
                boolean isUpdated = UsersDAO.updateUser(id,user);
                HttpStatus status = isUpdated ? HttpStatus.ACCEPTED : HttpStatus.INTERNAL_SERVER_ERROR;
                String text = isUpdated ? "UPDATED" : "UNABLE TO UPDATE USER WITH ID=" + id;
                return new ResponseEntity<WSResponse>(new WSResponse(status, text), status);

            }
        }
        return new ResponseEntity<WSResponse>(HttpStatus.UNAUTHORIZED);

    }
}
