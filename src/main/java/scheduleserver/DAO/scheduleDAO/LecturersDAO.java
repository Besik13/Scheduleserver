package scheduleserver.DAO.scheduleDAO;

import scheduleserver.DAO.CRUD;
import scheduleserver.DAO.QueryBuilder;
import scheduleserver.bins.lecturer.Lecturer;
import scheduleserver.bins.lecturer.LecturerList;
import scheduleserver.connection.ConnectionStart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LecturersDAO {
    public static LecturerList getLecturers(String id, String name, String middleName, String surname) {

        String query = QueryBuilder.newBuilder().add("id", id).add("name", name)
                .add("middleName", middleName).add("surname", surname).build("lecturer", CRUD.READ);
        try (Connection conn = ConnectionStart.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            return resultSetAsLecturerList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            return null;
        }
    }

    public static boolean createLecturer(Lecturer lecturer) {
        if (lecturer != null) {
            String query = QueryBuilder.newBuilder().add("name", lecturer.getName()).
                    add("middleName", lecturer.getMiddleName()).add("surname", lecturer.getSurname())
                    .build("lecturer", CRUD.CREATE);

            try (Connection conn = ConnectionStart.getConnection();
                 PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                return preparedStatement.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean updateLecturer(int id, Lecturer lecturer) {
        if (lecturer != null) {
            String query = QueryBuilder.newBuilder().add("name", lecturer.getName()).
                    add("middleName", lecturer.getMiddleName()).add("surname", lecturer.getSurname())
                    .addCondition("id", id).build("lecturer", CRUD.UPDATE);
            try (Connection conn = ConnectionStart.getConnection();
                 PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                return preparedStatement.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean deleteLecturer(int id) {
        String query = QueryBuilder.newBuilder().add("id", id).build("lecturer", CRUD.DELETE);
        try (Connection conn = ConnectionStart.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Lecturer resultSetAsLecturer(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                Lecturer lecturer = new Lecturer();
                lecturer.setId(resultSet.getInt("id"));
                lecturer.setName(resultSet.getString("name"));
                lecturer.setMiddleName(resultSet.getString("middleName"));
                lecturer.setSurname(resultSet.getString("surname"));
                return lecturer;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static LecturerList resultSetAsLecturerList(ResultSet resultSet) {
        try {
            LecturerList lecturerList = new LecturerList();
            while (resultSet.next()) {
                lecturerList.getLecturerList().add(resultSetAsLecturer(resultSet));
            }
            return lecturerList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
