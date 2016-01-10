package scheduleserver.bins.department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentList {

    List<Department> departmentList = new ArrayList<>();

    public DepartmentList() {

    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public int size() {
        return departmentList.size();
    }

}
