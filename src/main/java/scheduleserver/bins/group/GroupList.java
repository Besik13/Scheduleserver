package scheduleserver.bins.group;

import java.util.ArrayList;
import java.util.List;

public class GroupList {

    List<Group> groupList = new ArrayList<>();

    public GroupList() {

    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public int size() {
        return groupList.size();
    }


}
