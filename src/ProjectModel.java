import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ProjectModel {
    Scanner in = new Scanner(System.in);
    Connection conn = null;
    String url;
    ResultSet rs = null;
    ResultSet rs2 = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;

    public ProjectModel(String url) {
        this.url = url;
    }

    public void connectData() throws SQLException {
        conn = DriverManager.getConnection(url);
    }

    public void closeCourseDataConnection() throws SQLException {
        conn.close();
    }

    public void createStatement() throws SQLException {
        this.stmt = conn.createStatement();
    }

    //**** QUERY ****//

    //Gives all student names from database
    public ArrayList<String> SQLQueryStudents() throws SQLException {
        ArrayList<String> students = new ArrayList<>();
        // language=<SQL>
        String sql = "Select StudentName from Student";
        rs = stmt.executeQuery(sql);
        while (rs != null && rs.next()) {
            String name = rs.getString(1);
            //System.out.println(name);
            students.add(name);
        }
        return students;
    }

    //Gives all courses names
    public ArrayList<String> SQLQueryCourseNames() throws SQLException {
        ArrayList<String> course = new ArrayList<>();
        // language=<SQL>
        String sql = "Select CourseName from Course";
        rs = stmt.executeQuery(sql);
        while (rs != null && rs.next()) {
            String name = rs.getString(1);
            //System.out.println(name);
            course.add(name);
        }
        return course;
    }

    //Gives all grades
    public ArrayList<Double> SQLQueryGrades() throws SQLException {
        ArrayList<Double> grades = new ArrayList<>();
        // language=<SQL>
        String sql = "Select Grade from Grade";
        rs = stmt.executeQuery(sql);
        while (rs != null && rs.next()) {
            Double value = rs.getDouble(1);
            //System.out.println(value);
            grades.add(value);
        }
        return grades;
    }

    //Gives all professors names
    public ArrayList<String> SQLQueryProfessorName() throws SQLException {
        ArrayList<String> prof = new ArrayList<>();
        // language=<SQL>
        String sql = "Select ProfessorName from Professor";
        rs = stmt.executeQuery(sql);
        while (rs != null && rs.next()) {
            String name = rs.getString(1);
            //System.out.println(name);
            prof.add(name);
        }
        return prof;
    }

    //This method gets the information about a student thanks to their name
    public ArrayList<StudentInfo> QueryforStudent(String name) throws SQLException {

        ArrayList<StudentInfo> studentInfo = new ArrayList<>();

        //This is the sql line that gets us the information of the student
        // language=<SQL>
        String sql = "SELECT StudentID, StudentAddress from Student WHERE StudentName='" + name + "';";

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        while (rs != null && rs.next()) {
            String address = rs.getString(2);
            Integer id = rs.getInt(1);

            //System.out.println(name + ": " + address + " and id is " + id); //we print out the information we got
            StudentInfo t = new StudentInfo(id, name, address);
            studentInfo.add(t);
        }
        return studentInfo;
    }

    //This method gets the information about a student thanks to their name
    public ArrayList<CourseInfo> QueryforCourse(String name) throws SQLException {

        ArrayList<CourseInfo> courseInfo = new ArrayList<>();

        //This is the sql line that gets us the information of the student
        // language=<SQL>
        String sql = "SELECT CourseID from Course WHERE CourseName ='" + name + "';";

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        if(!rs.next()){
            System.out.println("ya pas de next");
        }

        while (rs != null && rs.next()) {
            Integer id = rs.getInt(1);
            System.out.println("course id " + id);

            CourseInfo t = new CourseInfo(id, name);
            courseInfo.add(t);
        }
        return courseInfo;
    }

    //Query where we get course ID from the student ID
    public ArrayList<Integer> QueryForCourseID(Integer StudentID) throws SQLException {

        ArrayList<Integer> courseID = new ArrayList<>();

        //This is the sql line that gets us the information of the student
        // language=<SQL>
        String sql = "SELECT CourseID from Grade WHERE StudentID ='" + StudentID + "';";

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        while (rs != null && rs.next()) {
            int id = rs.getInt(1); //we get the course ID

            System.out.println("course id is " + id);
            courseID.add(id);
        }
        return courseID;
    }

    //This method gets the name of a course thanks to the course ID
    public ArrayList<String> QueryForCourseName(ArrayList<Integer> courseID) throws SQLException {

        ArrayList<String> courseName = new ArrayList<>();

        // language=<SQL>
        ArrayList<String> sql = new ArrayList<>();

        //For every course ID we have, we go and get the course name associated
        for (int i = 0; i < courseID.size(); i++) {
            sql.add("SELECT CourseName from Course WHERE CourseID ='" + courseID.get(i) + "';");
        }

        //For every sql query, we get the course name in our result
        for (int i = 0; i < sql.size(); i++) {
            pstmt = conn.prepareStatement(sql.get(i));
            rs = pstmt.executeQuery();

            while (rs != null && rs.next()) {

                String name = rs.getString(1); //we get the course ID

                courseName.add(name);
            }
        }

        return courseName;
    }

    //Query where we get grade from the student ID
    public ArrayList<Double> QueryForGrades(Integer StudentID) throws SQLException {

        ArrayList<Double> grades = new ArrayList<>();

        //This is the sql line that gets us the information of the student
        // language=<SQL>
        String sql = "SELECT Grade from Grade WHERE StudentID ='" + StudentID + "';";

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        while (rs != null && rs.next()) {
            double grade = rs.getDouble(1); //we get the grade
            System.out.println("grade gotten is " + grade);
            grades.add(grade);
        }
        return grades;
    }

    //Query where if grade is 0 than you can type in a grade
    public void UpdateGrade(double g, int StudentID) throws SQLException {

        System.out.println("Please insert the grade you want to add: ");

        // language=<SQL>
        String sql = "UPDATE Grade SET Grade =" + g + " WHERE Grade IS NULL AND StudentID =" + StudentID + ";";

        pstmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery(sql);
/*
        while (rs != null && rs.next()) {
            g = rs.getDouble(1);
        }*/
    }

    //Query where we get the average grade of a student
    public Double QueryStudentAverage(int StudentID) throws SQLException {
        double average = 0;
        // language=<SQL>
        String sql = "SELECT AVG(Grade) FROM Grade where StudentID=" + StudentID + ";";
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        while (rs != null && rs.next()) {
            average = rs.getDouble(1);
            ;
        }
        return average;
    }

    //Query where we get the average grade in a course
    public Double QueryCourseAverage(int CourseID) throws SQLException {
        double average = 0;
        // language=<SQL>
        String sql = "SELECT AVG(Grade) FROM Grade where CourseID=" + CourseID + ";";
        pstmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery(sql);
        while (rs != null && rs.next()) {
            average = rs.getDouble(1);
            ;
        }
        return average;
    }
}
