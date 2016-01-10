package scheduleserver.bins.user;

import java.util.ArrayList;
import java.util.List;

public class UserList {

    List<User> userList = new ArrayList<>();

    public UserList() {

    }

    public List<User> getUserList() {
        return userList;
    }

    public int size() {
        return userList.size();
    }
}
