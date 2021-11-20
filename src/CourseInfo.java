public class CourseInfo {
    Integer courseID =null;
    String courseName = null;
    //String courseAddress = null;


    CourseInfo(Integer id,String name){
        this.courseID = id;
        this.courseName = name;
        //this.courseAddress = address;
    }

    public Integer getStudentID(){return this.courseID; }
}
