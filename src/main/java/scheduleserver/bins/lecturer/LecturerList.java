package scheduleserver.bins.lecturer;

import java.util.ArrayList;
import java.util.List;

public class LecturerList {

    List<Lecturer> lecturerList = new ArrayList<>();

    public LecturerList() {

    }

    public List<Lecturer> getLecturerList() {
        return lecturerList;
    }

    public int size() {
        return lecturerList.size();
    }
}
