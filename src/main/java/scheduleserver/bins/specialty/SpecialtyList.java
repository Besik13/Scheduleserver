package scheduleserver.bins.specialty;

import java.util.ArrayList;
import java.util.List;

public class SpecialtyList {

    List<Specialty> specialtyList = new ArrayList<>();

    public SpecialtyList() {

    }

    public List<Specialty> getSpecialtyList() {
        return specialtyList;
    }

    public int size() {
        return specialtyList.size();
    }
}
