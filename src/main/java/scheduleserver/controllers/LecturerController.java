package scheduleserver.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scheduleserver.DAO.DAOConstants;
import scheduleserver.DAO.scheduleDAO.LecturersDAO;
import scheduleserver.bins.wsresponse.WSResponse;
import scheduleserver.bins.lecturer.Lecturer;
import scheduleserver.bins.lecturer.LecturerList;
import scheduleserver.manager.SessionManager;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LecturerController {

    @RequestMapping(value = "/lecturer")
    public ResponseEntity<LecturerList> getLecturers(HttpServletRequest httpServletRequest,
                                                       @RequestParam(value = "id",defaultValue = DAOConstants.ALL_VALUES_IDENTIFIER) String id,
                                                       @RequestParam(value = "name",defaultValue = DAOConstants.ALL_VALUES_IDENTIFIER) String name,
                                                       @RequestParam(value = "middleName",defaultValue = DAOConstants.ALL_VALUES_IDENTIFIER) String middleName,
                                                       @RequestParam(value = "surname",defaultValue = DAOConstants.ALL_VALUES_IDENTIFIER) String surname) {

        if (SessionManager.isLogedIn(httpServletRequest)) {
            return new ResponseEntity<LecturerList>(LecturersDAO.getLecturers(id,name,middleName,surname), HttpStatus.OK);
        }
        return new ResponseEntity<LecturerList>(HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/lecturer", method = RequestMethod.POST)
    public ResponseEntity<WSResponse> addLecturer(HttpServletRequest httpServletRequest,
                                                  @RequestBody Lecturer lecturer) {

        if (SessionManager.isLogedIn(httpServletRequest)) {
            if (lecturer != null) {
                boolean isCreated = LecturersDAO.createLecturer(lecturer);
                HttpStatus status = isCreated ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;
                String text = isCreated ? "CREATED" : "UNABLE TO CREATE NEW LECTURER";
                return new ResponseEntity<WSResponse>(new WSResponse(status, text), status);

            }
        }
        return new ResponseEntity<WSResponse>(HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/lecturer", method = RequestMethod.DELETE)
    public ResponseEntity<WSResponse> deleteLecturer(HttpServletRequest httpServletRequest,
                                                       @RequestParam(value = "id", defaultValue = "-1") int id) {

        if (SessionManager.isLogedIn(httpServletRequest)) {
            boolean isDeleted = LecturersDAO.deleteLecturer(id);
            HttpStatus status = isDeleted ? HttpStatus.ACCEPTED : HttpStatus.INTERNAL_SERVER_ERROR;
            String text = isDeleted ? "DELETED" : "UNABLE TO DELETE LECTURER WITH ID=" + id;
            return new ResponseEntity<WSResponse>(new WSResponse(status, text), status);


        }
        return new ResponseEntity<WSResponse>(HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/lecturer", method = RequestMethod.PUT)
    public ResponseEntity<WSResponse> updateLecturer(HttpServletRequest httpServletRequest,
                                                  @RequestParam(value = "id", defaultValue = "-1") int id,
                                                  @RequestBody Lecturer lecturer) {

        if (SessionManager.isLogedIn(httpServletRequest)) {
            if (lecturer != null) {
                boolean isUpdated = LecturersDAO.updateLecturer(id, lecturer);
                HttpStatus status = isUpdated ? HttpStatus.ACCEPTED : HttpStatus.INTERNAL_SERVER_ERROR;
                String text = isUpdated ? "UPDATED" : "UNABLE TO UPDATE LECTURER WITH ID=" + id;
                return new ResponseEntity<WSResponse>(new WSResponse(status, text), status);

            }
        }
        return new ResponseEntity<WSResponse>(HttpStatus.UNAUTHORIZED);

    }
}
