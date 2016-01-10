package scheduleserver.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scheduleserver.DAO.DAOConstants;
import scheduleserver.DAO.scheduleDAO.SpecialtysDAO;
import scheduleserver.bins.wsresponse.WSResponse;
import scheduleserver.bins.specialty.Specialty;
import scheduleserver.bins.specialty.SpecialtyList;
import scheduleserver.manager.SessionManager;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SpecialtyController {

    @RequestMapping(value = "/specialty")
    public ResponseEntity<SpecialtyList> getSpecialtys(HttpServletRequest httpServletRequest,
                                                       @RequestParam(value = "id", defaultValue = DAOConstants.ALL_VALUES_IDENTIFIER) String id,
                                                       @RequestParam(value = "departmentId", defaultValue = DAOConstants.ALL_VALUES_IDENTIFIER) String departmentId) {
        //if (SessionManager.isLogedIn(httpServletRequest)) {
            return new ResponseEntity<SpecialtyList>(SpecialtysDAO.getSpecialtys(id, departmentId), HttpStatus.OK);
        //}
        //return new ResponseEntity<SpecialtyList>(HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/specialty", method = RequestMethod.POST)
    public ResponseEntity<WSResponse> addSpecialty(HttpServletRequest httpServletRequest,
                                                   @RequestBody Specialty specialty) {

        if (SessionManager.isLogedIn(httpServletRequest)) {
            if (specialty != null) {
                boolean isCreated = SpecialtysDAO.createSpecialty(specialty);
                HttpStatus status = isCreated ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;
                String text = isCreated ? "CREATED" : "UNABLE TO CREATE NEW SPECIALTY";
                return new ResponseEntity<WSResponse>(new WSResponse(status, text), status);

            }
        }
        return new ResponseEntity<WSResponse>(HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/specialty", method = RequestMethod.DELETE)
    public ResponseEntity<WSResponse> deleteSpecialty(HttpServletRequest httpServletRequest,
                                                      @RequestParam(value = "id", defaultValue = "-1") int id) {

        if (SessionManager.isLogedIn(httpServletRequest)) {
            boolean isDeleted = SpecialtysDAO.deleteSpecialty(id);
            HttpStatus status = isDeleted ? HttpStatus.ACCEPTED : HttpStatus.INTERNAL_SERVER_ERROR;
            String text = isDeleted ? "DELETED" : "UNABLE TO DELETE SPECIALTY WITH ID=" + id;
            return new ResponseEntity<WSResponse>(new WSResponse(status, text), status);


        }
        return new ResponseEntity<WSResponse>(HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/specialty", method = RequestMethod.PUT)
    public ResponseEntity<WSResponse> updateSpecialty(HttpServletRequest httpServletRequest,
                                                      @RequestParam(value = "id", defaultValue = "-1") int id,
                                                      @RequestBody Specialty specialty) {

        if (SessionManager.isLogedIn(httpServletRequest)) {
            if (specialty != null) {
                boolean isUpdated = SpecialtysDAO.updateSpecialty(id, specialty);
                HttpStatus status = isUpdated ? HttpStatus.ACCEPTED : HttpStatus.INTERNAL_SERVER_ERROR;
                String text = isUpdated ? "UPDATED" : "UNABLE TO UPDATE SPECIALTY WITH ID=" + id;
                return new ResponseEntity<WSResponse>(new WSResponse(status, text), status);

            }
        }
        return new ResponseEntity<WSResponse>(HttpStatus.UNAUTHORIZED);

    }
}
