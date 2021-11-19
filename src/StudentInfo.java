public class StudentInfo {
    Integer studentID =null;
    String studentName = null;
    String studentAddress = null;


    StudentInfo(Integer id,String name, String address){
        this.studentID = id;
        this.studentName = name;
        this.studentAddress = address;
    }

    public Integer getStudentID(){return this.studentID; }
}
