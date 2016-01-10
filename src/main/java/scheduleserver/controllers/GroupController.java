package scheduleserver.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scheduleserver.DAO.DAOConstants;
import scheduleserver.DAO.scheduleDAO.GroupsDAO;
import scheduleserver.bins.wsresponse.WSResponse;
import scheduleserver.bins.group.Group;
import scheduleserver.bins.group.GroupList;
import scheduleserver.manager.SessionManager;

import javax.servlet.http.HttpServletRequest;

@RestController
public class GroupController {

    @RequestMapping(value = "/group")
    public ResponseEntity<GroupList> getGroups(HttpServletRequest httpServletRequest,
                                               @RequestParam(value = "id", defaultValue = DAOConstants.ALL_VALUES_IDENTIFIER) String id,
                                               @RequestParam(value = "specialityId", defaultValue = DAOConstants.ALL_VALUES_IDENTIFIER) String specialityId) {
        //if (SessionManager.isLogedIn(httpServletRequest)) {
            return new ResponseEntity<GroupList>(GroupsDAO.getGroups(id, specialityId), HttpStatus.OK);
        //}
        //return new ResponseEntity<GroupList>(HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/group", method = RequestMethod.POST)
    public ResponseEntity<WSResponse> addGroup(HttpServletRequest httpServletRequest, @RequestBody Group group) {

        if (SessionManager.isLogedIn(httpServletRequest)) {
            if (group != null) {
                boolean isCreated = GroupsDAO.createGroup(group);
                HttpStatus status = isCreated ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;
                String text = isCreated ? "CREATED" : "UNABLE TO CREATE NEW GROUP";
                return new ResponseEntity<WSResponse>(new WSResponse(status, text), status);

            }
        }
        return new ResponseEntity<WSResponse>(HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/group", method = RequestMethod.DELETE)
    public ResponseEntity<WSResponse> deleteGroup(HttpServletRequest httpServletRequest,
                                                  @RequestParam(value = "id", defaultValue = "-1") int id) {

        if (SessionManager.isLogedIn(httpServletRequest)) {
            boolean isDeleted = GroupsDAO.deleteGroup(id);
            HttpStatus status = isDeleted ? HttpStatus.ACCEPTED : HttpStatus.INTERNAL_SERVER_ERROR;
            String text = isDeleted ? "DELETED" : "UNABLE TO DELETE GROUP WITH ID=" + id;
            return new ResponseEntity<WSResponse>(new WSResponse(status, text), status);


        }
        return new ResponseEntity<WSResponse>(HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/group", method = RequestMethod.PUT)
    public ResponseEntity<WSResponse> updateGroup(HttpServletRequest httpServletRequest,
                                                  @RequestParam(value = "id", defaultValue = "-1") int id,
                                                  @RequestBody Group group) {

        if (SessionManager.isLogedIn(httpServletRequest)) {
            if (group != null) {
                boolean isUpdated = GroupsDAO.updateGroup(id, group);
                HttpStatus status = isUpdated ? HttpStatus.ACCEPTED : HttpStatus.INTERNAL_SERVER_ERROR;
                String text = isUpdated ? "UPDATED" : "UNABLE TO UPDATE GROUP WITH ID=" + id;
                return new ResponseEntity<WSResponse>(new WSResponse(status, text), status);

            }
        }
        return new ResponseEntity<WSResponse>(HttpStatus.UNAUTHORIZED);

    }
}
