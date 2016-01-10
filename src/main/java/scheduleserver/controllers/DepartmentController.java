package scheduleserver.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scheduleserver.DAO.scheduleDAO.DepartmentsDAO;
import scheduleserver.bins.wsresponse.WSResponse;
import scheduleserver.bins.department.Department;
import scheduleserver.bins.department.DepartmentList;
import scheduleserver.manager.SessionManager;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DepartmentController {
    @RequestMapping(value = "/department")
    public ResponseEntity<DepartmentList> getDepartments(HttpServletRequest httpServletRequest) {

        //if (SessionManager.isLogedIn(httpServletRequest)) {
            return new ResponseEntity<DepartmentList>(DepartmentsDAO.getDepartments(), HttpStatus.OK);
        //}
        //return new ResponseEntity<DepartmentList>(HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/department", method = RequestMethod.POST)
    public ResponseEntity<WSResponse> addDepartment(HttpServletRequest httpServletRequest, @RequestBody Department department) {

        if (SessionManager.isLogedIn(httpServletRequest)) {
            if (department != null) {
                boolean isCreated = DepartmentsDAO.createDepartment(department);
                HttpStatus status = isCreated ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;
                String text = isCreated ? "CREATED" : "UNABLE TO CREATE NEW DEPARTMENT";
                return new ResponseEntity<WSResponse>(new WSResponse(status, text), status);

            }
        }
        return new ResponseEntity<WSResponse>(HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/department", method = RequestMethod.DELETE)
    public ResponseEntity<WSResponse> deleteDepartment(HttpServletRequest httpServletRequest,
                                                  @RequestParam(value = "id", defaultValue = "-1") int id) {

        if (SessionManager.isLogedIn(httpServletRequest)) {
            boolean isDeleted = DepartmentsDAO.deleteDepartment(id);
            HttpStatus status = isDeleted ? HttpStatus.ACCEPTED : HttpStatus.INTERNAL_SERVER_ERROR;
            String text = isDeleted ? "DELETED" : "UNABLE TO DELETE DEPARTMENT WITH ID=" + id;
            return new ResponseEntity<WSResponse>(new WSResponse(status, text), status);


        }
        return new ResponseEntity<WSResponse>(HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/department", method = RequestMethod.PUT)
    public ResponseEntity<WSResponse> updateGroup(HttpServletRequest httpServletRequest,
                                                  @RequestParam(value = "id", defaultValue = "-1") int id,
                                                  @RequestBody Department department) {

        if (SessionManager.isLogedIn(httpServletRequest)) {
            if (department != null) {
                boolean isUpdated = DepartmentsDAO.updateDepartment(id, department);
                HttpStatus status = isUpdated ? HttpStatus.ACCEPTED : HttpStatus.INTERNAL_SERVER_ERROR;
                String text = isUpdated ? "UPDATED" : "UNABLE TO UPDATE DEPARTMENT WITH ID=" + id;
                return new ResponseEntity<WSResponse>(new WSResponse(status, text), status);

            }
        }
        return new ResponseEntity<WSResponse>(HttpStatus.UNAUTHORIZED);

    }
}
