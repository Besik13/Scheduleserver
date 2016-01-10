package scheduleserver.DAO.scheduleDAO;

import scheduleserver.DAO.CRUD;
import scheduleserver.DAO.QueryBuilder;
import scheduleserver.bins.department.Department;
import scheduleserver.bins.department.DepartmentList;
import scheduleserver.connection.ConnectionStart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentsDAO {

    public static DepartmentList getDepartments() {

        String query = QueryBuilder.newBuilder().build("departments", CRUD.READ);
        try (Connection conn = ConnectionStart.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            return resultSetAsDepartmentList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            return null;
        }
    }

    public static boolean createDepartment(Department department) {
        if (department != null) {
            String query = QueryBuilder.newBuilder().add("name", department.getName()).build("departments", CRUD.CREATE);

            try (Connection conn = ConnectionStart.getConnection();
                 PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                return preparedStatement.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean updateDepartment(int id, Department department) {
        if (department != null) {
            String query = QueryBuilder.newBuilder().add("name", department.getName())
                    .addCondition("id", id).build("departments", CRUD.UPDATE);
            try (Connection conn = ConnectionStart.getConnection();
                 PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                return preparedStatement.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean deleteDepartment(int id) {
        String query = QueryBuilder.newBuilder().add("id", id).build("departments", CRUD.DELETE);
        try (Connection conn = ConnectionStart.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Department resultSetAsGroup(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                Department department = new Department();
                department.setId(resultSet.getInt("id"));
                department.setName(resultSet.getString("name"));
                return department;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static DepartmentList resultSetAsDepartmentList(ResultSet resultSet) {
        try {
            DepartmentList departmentList = new DepartmentList();
            while (resultSet.next()) {
                departmentList.getDepartmentList().add(resultSetAsGroup(resultSet));
            }
            return departmentList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
