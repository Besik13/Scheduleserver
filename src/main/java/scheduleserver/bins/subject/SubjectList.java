package scheduleserver.bins.subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectList {

    List<Subject> subjectList = new ArrayList<>();

    public SubjectList() {

    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public int size() {
        return subjectList.size();
    }
}
