package scheduleserver;

import java.util.Map;

public class InfoFromTable {
    private Map<Integer,String> info;

    InfoFromTable(Map<Integer,String> info)
    {
        this.info = info;
    }

    public Map<Integer,String> getInfo()
    {
        return info;
    }
}
