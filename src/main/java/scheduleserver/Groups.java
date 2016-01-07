package scheduleserver;

import java.util.Map;

public class Groups {
    private Map<Integer,String> groups;

    Groups(Map<Integer,String> groups)
    {
            this.groups = groups;
    }

    public Map<Integer,String> getGroups()
    {
        return groups;
    }
}
