package scheduleserver.DAO.scheduleDAO;

import scheduleserver.DAO.CRUD;
import scheduleserver.DAO.QueryBuilder;
import scheduleserver.bins.subject.Subject;
import scheduleserver.bins.subject.SubjectList;
import scheduleserver.connection.ConnectionStart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectsDAO {

    public static SubjectList getSubjects(String id, String number, String name,
                                          String lecturerId, String room, String day, String groupId) {

        String query = QueryBuilder.newBuilder().add("id", id).add("number", number)
                .add("name", name).add("lecturerId", lecturerId).add("room", room)
                .add("day", day).add("groupId", groupId).build("subjects", CRUD.READ);
        try (Connection conn = ConnectionStart.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            return resultSetAsSubjectList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            return null;
        }
    }

    public static boolean createSubject(Subject subject) {
        if (subject != null) {
            String query = QueryBuilder.newBuilder().add("id", subject.getId()).add("number", subject.getNumber())
                    .add("name", subject.getName()).add("lecturerId", subject.getLecturerId()).add("room", subject.getRoom())
                    .add("day", subject.getDay()).add("groupId", subject.getGroupId()).build("subjects", CRUD.CREATE);

            try (Connection conn = ConnectionStart.getConnection();
                 PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                return preparedStatement.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean updateSubject(int id, Subject subject) {
        if (subject != null) {
            String query = QueryBuilder.newBuilder().add("id", subject.getId()).add("number", subject.getNumber())
                    .add("name", subject.getName()).add("lecturerId", subject.getLecturerId()).add("room", subject.getRoom())
                    .add("day", subject.getDay()).add("groupId", subject.getGroupId()).addCondition("id", id).build("subjects", CRUD.UPDATE);
            try (Connection conn = ConnectionStart.getConnection();
                 PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                return preparedStatement.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean deleteSubject(int id) {
        String query = QueryBuilder.newBuilder().add("id", id).build("subjects", CRUD.DELETE);
        try (Connection conn = ConnectionStart.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Subject resultSetAsSubject(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                Subject subject = new Subject();
                subject.setId(resultSet.getInt("id"));
                subject.setNumber(resultSet.getInt("number"));
                subject.setName(resultSet.getString("name"));
                subject.setLecturerId(resultSet.getInt("lecturerId"));
                subject.setRoom(resultSet.getString("room"));
                subject.setDay(resultSet.getInt("day"));
                subject.setGroupId(resultSet.getInt("groupId"));
                return subject;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static SubjectList resultSetAsSubjectList(ResultSet resultSet) {
        try {
            SubjectList subjectList = new SubjectList();
            while (resultSet.next()) {
                subjectList.getSubjectList().add(resultSetAsSubject(resultSet));
            }
            return subjectList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
