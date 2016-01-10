package scheduleserver.DAO.scheduleDAO;

import scheduleserver.DAO.CRUD;
import scheduleserver.DAO.QueryBuilder;
import scheduleserver.bins.group.Group;
import scheduleserver.bins.group.GroupList;
import scheduleserver.connection.ConnectionStart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupsDAO {

    public static GroupList getGroups(String id, String specialityId) {

        String query = QueryBuilder.newBuilder().add("id", id).add("specialityId", specialityId).build("groups", CRUD.READ);
        try (Connection conn = ConnectionStart.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            return resultSetAsGroupList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            return null;
        }
    }

    public static boolean createGroup(Group group) {
        if (group != null) {
            String query = QueryBuilder.newBuilder().add("name", group.getName()).
                    add("specialityId", group.getSpecialityId()).build("groups", CRUD.CREATE);

            try (Connection conn = ConnectionStart.getConnection();
                 PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                return preparedStatement.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean updateGroup(int id, Group group) {
        if (group != null) {
            String query = QueryBuilder.newBuilder().add("name", group.getName()).
                    add("specialityId", group.getSpecialityId()).addCondition("id", id).build("groups", CRUD.UPDATE);
            try (Connection conn = ConnectionStart.getConnection();
                 PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                return preparedStatement.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean deleteGroup(int id) {
        String query = QueryBuilder.newBuilder().add("id", id).build("groups", CRUD.DELETE);
        try (Connection conn = ConnectionStart.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Group resultSetAsGroup(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                Group group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
                group.setSpecialityId(resultSet.getInt("specialityId"));
                return group;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static GroupList resultSetAsGroupList(ResultSet resultSet) {
        try {
            GroupList groupList = new GroupList();
            while (resultSet.next()) {
                groupList.getGroupList().add(resultSetAsGroup(resultSet));
            }
            return groupList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
