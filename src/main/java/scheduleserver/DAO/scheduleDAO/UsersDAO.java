package scheduleserver.DAO.scheduleDAO;

import scheduleserver.DAO.CRUD;
import scheduleserver.DAO.QueryBuilder;
import scheduleserver.bins.user.User;
import scheduleserver.bins.user.UserList;
import scheduleserver.connection.ConnectionStart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDAO {

    public static UserList getUsers(String id, String email, String password,
                                    String name, String surname) {

        String query = QueryBuilder.newBuilder().add("id", id).add("email", email)
                .add("password", password).add("name", name).add("surname", surname)
                .build("users", CRUD.READ);
        try (Connection conn = ConnectionStart.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            return resultSetAsUserList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            return null;
        }
    }

    public static boolean createUser(User user) {
        if (user != null) {
            String query = QueryBuilder.newBuilder().add("email", user.getEmail())
                    .add("password", user.getPassword()).add("name", user.getName()).add("surname", user.getSurname())
                    .build("users", CRUD.CREATE);
            try (Connection conn = ConnectionStart.getConnection();
                 PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                return preparedStatement.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean updateUser(int id, User user) {
        if (user != null) {
            String query = QueryBuilder.newBuilder().add("email", user.getEmail())
                    .add("password", user.getPassword()).add("name", user.getName()).add("surname", user.getSurname())
                    .addCondition("id", id).build("users", CRUD.UPDATE);
            try (Connection conn = ConnectionStart.getConnection();
                 PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                return preparedStatement.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean deleteUser(int id) {
        String query = QueryBuilder.newBuilder().add("id", id).build("users", CRUD.DELETE);
        try (Connection conn = ConnectionStart.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static User resultSetAsUser(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                return user;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static UserList resultSetAsUserList(ResultSet resultSet) {
        try {
            UserList userList = new UserList();
            while (resultSet.next()) {
                userList.getUserList().add(resultSetAsUser(resultSet));
            }
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
