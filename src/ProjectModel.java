import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ProjectModel {
    Scanner in = new Scanner(System.in);
    Connection conn=null;
    String url;
    ResultSet rs=null;
    ResultSet rs2=null;
    Statement stmt=null;
    PreparedStatement pstmt=null;

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

    //if grade = null, type in grade
    void UpdateGrade() throws SQLException {
        int g;
        System.out.println("Grade is null please insert actual grade: ");
        g=in.nextInt();
        // language=<SQL>
        String sql="UPDATE Grade SET Grade ="+ g+ " WHERE Grade IS NULL;";
        rs=stmt.executeQuery(sql);
    }


    //Gives all student names from database
    ArrayList<String> SQLQueryStudents() throws SQLException{
        ArrayList<String> students =new ArrayList<>();
        // language=<SQL>
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
        // language=<SQL>
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
        // language=<SQL>
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
        // language=<SQL>
        String sql = "Select ProfessorName from Professor";
        rs = stmt.executeQuery(sql);
        while (rs != null && rs.next()) {
            String name = rs.getString(1);
            System.out.println(name);
            prof.add(name);
        }
        return prof;
    }

    //This method gets the information about a student thanks to their name
    public ArrayList<StudentInfo> QueryforStudent(String name) throws SQLException{

        ArrayList<StudentInfo> studentInfo =new ArrayList<>();

        //This is the sql line that gets us the information of the student
        // language=<SQL>
        String sql = "SELECT StudentID, StudentAddress from Student"
                + "WHERE StudentName='"+ name + "';";

        pstmt=conn.prepareStatement(sql);
        rs=pstmt.executeQuery();

        while(rs!=null && rs.next()){
            String address =rs.getString(2);
            Integer id =rs.getInt(1);

            System.out.println(name + ": "+ address + " and id is "+ id); //we print out the information we got
            StudentInfo t = new StudentInfo(id, name, address);
            studentInfo.add(t);
        }
        return studentInfo;
    }

    //This method gets the name of a course thanks to the Student ID
    public ArrayList<String> QueryForCourseName(Integer StudentID) throws SQLException{

        ArrayList<String> courseName =new ArrayList<>();

        //This is the sql line that gets us the information of the student
        // language=<SQL>
        String sql = "SELECT CourseID from Grade"
                + "WHERE StudentID ='"+ StudentID + "';";

        pstmt=conn.prepareStatement(sql);
        rs=pstmt.executeQuery();

        while(rs!=null && rs.next()){
            int id =rs.getInt(1); //we get the course ID
            // language=<SQL>
            String sql2 = "SELECT CourseName from Course"
                    + "WHERE CourseID ='"+ id + "';";

            pstmt=conn.prepareStatement(sql2);
            rs2=pstmt.executeQuery();
            String name = rs2.getString(1);

            courseName.add(name);
        }
        return courseName;
    }

}
