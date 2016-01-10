package scheduleserver.DAO.scheduleDAO;

import scheduleserver.DAO.CRUD;
import scheduleserver.DAO.QueryBuilder;
import scheduleserver.bins.specialty.Specialty;
import scheduleserver.bins.specialty.SpecialtyList;
import scheduleserver.connection.ConnectionStart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpecialtysDAO {

    public static SpecialtyList getSpecialtys(String id, String departmentId) {

        String query = QueryBuilder.newBuilder().add("id", id).add("departmentId", departmentId).build("specialtys", CRUD.READ);
        try (Connection conn = ConnectionStart.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            return resultSetAsSpecialtyList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            return null;
        }
    }

    public static boolean createSpecialty(Specialty specialty) {
        if (specialty != null) {
            String query = QueryBuilder.newBuilder().add("name", specialty.getName())
                    .add("departmentId", specialty.getDepartmentId()).build("specialtys", CRUD.CREATE);
            try (Connection conn = ConnectionStart.getConnection();
                 PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                return preparedStatement.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean updateSpecialty(int id, Specialty specialty) {
        if (specialty != null) {
            String query = QueryBuilder.newBuilder().add("name", specialty.getName())
                    .add("departmentId", specialty.getDepartmentId()).addCondition("id", id).build("specialtys", CRUD.UPDATE);
            try (Connection conn = ConnectionStart.getConnection();
                 PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                return preparedStatement.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean deleteSpecialty(int id) {
        String query = QueryBuilder.newBuilder().add("id", id).build("specialtys", CRUD.DELETE);
        try (Connection conn = ConnectionStart.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Specialty resultSetAsSpecialty(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                Specialty specialty = new Specialty();
                specialty.setId(resultSet.getInt("id"));
                specialty.setName(resultSet.getString("name"));
                specialty.setDepartmentId(resultSet.getInt("departmentId"));
                return specialty;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static SpecialtyList resultSetAsSpecialtyList(ResultSet resultSet) {
        try {
            SpecialtyList specialtyList = new SpecialtyList();
            while (resultSet.next()) {
                specialtyList.getSpecialtyList().add(resultSetAsSpecialty(resultSet));
            }
            return specialtyList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
