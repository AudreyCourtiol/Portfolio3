public class StudentInfo {
    Integer studentID;
    String studentName;
    String studentAddress;

    //This class helps us collect the students data
    StudentInfo(Integer id,String name, String address){
        this.studentID = id;
        this.studentName = name;
        this.studentAddress = address;
    }
}
