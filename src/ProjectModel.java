import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ProjectModel {
    Connection conn=null;
    String url=null;
    ResultSet rs=null;
    Statement stmt=null;

    public ProjectModel(String url){
        this.url=url;
    }

    void connectData()throws SQLException {
        conn= DriverManager.getConnection(url);
    }
    void closeCourseDataConnection()throws SQLException{
        conn.close();
    }
    void createStatement()throws SQLException{
        this.stmt=conn.createStatement();
    }

    //**** QUERY ****//


    //Gives all student names from database
    ArrayList<String> SQLQueryStudents() throws SQLException{
        ArrayList<String> students =new ArrayList<>();
        String sql = "Select StudentName from Student";
        rs = stmt.executeQuery(sql);
        while (rs != null && rs.next()) {
            String name = rs.getString(1);
            System.out.println(name);
            students.add(name);
        }
        return students;
    }

    //Gives all courses names
    ArrayList<String> SQLQueryCourseNames() throws SQLException{
        ArrayList<String> course=new ArrayList<>();
        String sql = "Select CourseName from Course";
        rs = stmt.executeQuery(sql);
        while (rs != null && rs.next()) {
            String name = rs.getString(1);
            System.out.println(name);
            course.add(name);
        }
        return course;
    }

    //Gives all grades
    ArrayList<Double> SQLQueryGrades() throws SQLException{
        ArrayList<Double> grades=new ArrayList<>();
        String sql = "Select Grade from Grade";
        rs = stmt.executeQuery(sql);
        while (rs != null && rs.next()) {
            Double value = rs.getDouble(1);
            System.out.println(value);
            grades.add(value);
        }
        return grades;
    }

    //Gives all professors names
    ArrayList<String> SQLQueryProfessorName() throws SQLException{
        ArrayList<String> prof=new ArrayList<>();
        String sql = "Select ProfessorName from Professor";
        rs = stmt.executeQuery(sql);
        while (rs != null && rs.next()) {
            String name = rs.getString(1);
            System.out.println(name);
            prof.add(name);
        }
        return prof;
    }

}

class StudentInfo{
    Integer studentID =null;
    String studentName = null;
    String studentAddress = null;


    StudentInfo(Integer id,String name, String address){
        this.studentID = id;
        this.studentName = name;
        this.studentAddress = address;
    }
}
