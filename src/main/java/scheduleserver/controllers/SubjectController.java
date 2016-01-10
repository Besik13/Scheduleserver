package scheduleserver.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scheduleserver.DAO.DAOConstants;
import scheduleserver.DAO.scheduleDAO.SubjectsDAO;
import scheduleserver.bins.wsresponse.WSResponse;
import scheduleserver.bins.subject.Subject;
import scheduleserver.bins.subject.SubjectList;
import scheduleserver.manager.SessionManager;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SubjectController {

    @RequestMapping(value = "/subject")
    public ResponseEntity<SubjectList> getSubject(HttpServletRequest httpServletRequest,
                                                  @RequestParam(value = "id", defaultValue = DAOConstants.ALL_VALUES_IDENTIFIER) String id,
                                                  @RequestParam(value = "number", defaultValue = DAOConstants.ALL_VALUES_IDENTIFIER) String number,
                                                  @RequestParam(value = "name", defaultValue = DAOConstants.ALL_VALUES_IDENTIFIER) String name,
                                                  @RequestParam(value = "lecturerId", defaultValue = DAOConstants.ALL_VALUES_IDENTIFIER) String lecturerId,
                                                  @RequestParam(value = "room", defaultValue = DAOConstants.ALL_VALUES_IDENTIFIER) String room,
                                                  @RequestParam(value = "day", defaultValue = DAOConstants.ALL_VALUES_IDENTIFIER) String day,
                                                  @RequestParam(value = "groupId", defaultValue = DAOConstants.ALL_VALUES_IDENTIFIER) String groupId) {
        //if (SessionManager.isLogedIn(httpServletRequest)) {
            return new ResponseEntity<SubjectList>(SubjectsDAO.getSubjects(id,number,name,lecturerId,room,day,groupId), HttpStatus.OK);
        //}
        //return new ResponseEntity<SubjectList>(HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/subject", method = RequestMethod.POST)
    public ResponseEntity<WSResponse> addSubject(HttpServletRequest httpServletRequest, @RequestBody Subject subject) {

        if (SessionManager.isLogedIn(httpServletRequest)) {
            if (subject != null) {
                boolean isCreated = SubjectsDAO.createSubject(subject);
                HttpStatus status = isCreated ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;
                String text = isCreated ? "CREATED" : "UNABLE TO CREATE NEW SUBJECT";
                return new ResponseEntity<WSResponse>(new WSResponse(status, text), status);

            }
        }
        return new ResponseEntity<WSResponse>(HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/subject", method = RequestMethod.DELETE)
    public ResponseEntity<WSResponse> deleteGroup(HttpServletRequest httpServletRequest,
                                                  @RequestParam(value = "id", defaultValue = "-1") int id) {

        if (SessionManager.isLogedIn(httpServletRequest)) {
            boolean isDeleted = SubjectsDAO.deleteSubject(id);
            HttpStatus status = isDeleted ? HttpStatus.ACCEPTED : HttpStatus.INTERNAL_SERVER_ERROR;
            String text = isDeleted ? "DELETED" : "UNABLE TO DELETE SUBJECT WITH ID=" + id;
            return new ResponseEntity<WSResponse>(new WSResponse(status, text), status);


        }
        return new ResponseEntity<WSResponse>(HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/subject", method = RequestMethod.PUT)
    public ResponseEntity<WSResponse> updateGroup(HttpServletRequest httpServletRequest,
                                                  @RequestParam(value = "id", defaultValue = "-1") int id,
                                                  @RequestBody Subject subject) {

        if (SessionManager.isLogedIn(httpServletRequest)) {
            if (subject != null) {
                boolean isUpdated = SubjectsDAO.updateSubject(id, subject);
                HttpStatus status = isUpdated ? HttpStatus.ACCEPTED : HttpStatus.INTERNAL_SERVER_ERROR;
                String text = isUpdated ? "UPDATED" : "UNABLE TO UPDATE SUBJECT WITH ID=" + id;
                return new ResponseEntity<WSResponse>(new WSResponse(status, text), status);

            }
        }
        return new ResponseEntity<WSResponse>(HttpStatus.UNAUTHORIZED);

    }
}
