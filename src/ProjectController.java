import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectController {
    ProjectView view;
    ProjectModel model;

    public ProjectController(ProjectView v, ProjectModel m) throws SQLException {
        this.view=v;
        this.model=m;

        // we get the data from the database
        this.model.connectData();
        this.model.createStatement();

        //we set the actions for our buttons
        this.view.ExitBtn.setOnAction(e-> Platform.exit()); //exit button
        this.view.GetAverages.setOnAction(e -> {
            try {
                getAverages();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        this.view.FindStudents.setOnAction(e -> {
            try {
                findStudentsPage();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }); //clear the page and offer new options
        this.view.ModifyGrades.setOnAction(e -> {
            try {
                modifyGrades();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        this.view.goBack.setOnAction(e -> goBack());

        //this.model.closeCourseDataConnection();
        this.model.createStatement();

        //Put students' data in view
        this.view.students = getStudents();
        this.view.courses = getCourses();
        this.view.grades = getGrades();
    }

    void goBack(){
        this.view.primaryStage.setScene(this.view.primaryScene);
        this.view.primaryStage.show();
    }

    void findStudentsPage() throws SQLException { //we display the scene with the students' information
        this.view.primaryStage.setScene(this.view.studentScene);
        this.view.primaryStage.show();

        //add the rules for this page
        Label rulesForStudents = new Label("Here you can select a student to get their grades or the courses they attend: ");
        this.view.gridPaneForStudents.add(rulesForStudents, 1, 1);


        //This allows us to choose an option on a list
        this.view.selectStudentsCB = new ComboBox<>();
        this.view.gridPaneForStudents.add(this.view.selectStudentsCB,30,1);

        //We put in the list the data from the database
        this.view.selectStudentsCB.setItems(getStudents());
        this.view.selectStudentsCB.getSelectionModel().selectFirst();

        //initialize the buttons for this page
        this.view.seeCourses = new Button("See courses");
        this.view.seeGrades = new Button("See grades");
        this.view.seeCourses.setOnAction(e -> printCoursesTakenByStudent(this.view.selectStudentsCB.getValue()));
        this.view.seeGrades.setOnAction(e -> printGradesOfChosenStudent(this.view.selectStudentsCB.getValue()));

        this.view.gridPaneForStudents.add(this.view.seeCourses, 1, 7);
        this.view.gridPaneForStudents.add(this.view.seeGrades, 1, 60);

        //We display textfields where we will print out the name of the courses and the student's grades
        this.view.textfieldCourses = new TextArea();
        this.view.textfieldCourses.setMaxWidth(200);
        this.view.textfieldCourses.setMaxHeight(100);
        this.view.textfieldGrades = new TextArea();
        this.view.textfieldGrades.setMaxWidth(200);
        this.view.textfieldGrades.setMaxHeight(100);
        this.view.gridPaneForStudents.add(this.view.textfieldCourses,1,20,2,2);
        this.view.gridPaneForStudents.add(this.view.textfieldGrades,1,70,2,2);
        this.view.gridPaneForStudents.add(this.view.goBack,40,80);
    }

    //This method prints out the courses taken by the student chosen
    void printCoursesTakenByStudent(String studentName) {
        try {
            ArrayList<StudentInfo> student = model.QueryforStudent(studentName); //we get the info of the chosen student
            ArrayList<Integer> coursesID = model.QueryForCourseID(student.get(0).studentID); //we get the courses taken by that student
            System.out.println("course id is " + coursesID.get(0));
            System.out.println("course id is " + coursesID.get(1));

            //We get all the courses names to all the course IDs found
            ArrayList<String> coursesNames = model.QueryForCourseName(coursesID);

            System.out.println(coursesNames.size() + " = " + coursesID.size());

            //We print the info of coursesNames in textfield
            for (int i = 0; i < coursesNames.size(); i++) {
                    this.view.textfieldCourses.appendText(i + ":" + coursesNames.get(i) + "\n");
            }
        }catch(SQLException e ){
                System.out.println(e.getMessage());
                System.out.println("error in controller: " + e.getMessage());
        }
    }

    //This method prints out the grades taken by the student chosen
    void printGradesOfChosenStudent(String studentName){
        try {
            ArrayList<StudentInfo> student = model.QueryforStudent(studentName); //we get the info of the chosen student

            ArrayList<Double> Grades = model.QueryForGrades(student.get(0).studentID);

            //We print the info of coursesNames in textfield
            for (int i = 0; i < Grades.size(); i++) {
                this.view.textfieldGrades.appendText(i + ":" + Grades.get(i) + "\n");
            }

        }catch(SQLException e ){
            System.out.println(e.getMessage());
            System.out.println("error in controller: " + e.getMessage());
        }
    }

    //This method gives the professor the chance to modify the grades of students that have yet to receive a mark
    void modifyGrades() throws SQLException {
        this.view.primaryStage.setScene(this.view.modifyGradesScene);
        this.view.primaryStage.show();

        //add the rules for this page
        Label rulesForModifying = new Label("Here you can select a student. If they have a grade that hasn't been entered yet, you can do it.");
        this.view.gridPaneforModifyGrades.add(rulesForModifying, 1, 1);

        //This allows us to choose an option on a list
        this.view.selectStudentsCB = new ComboBox<>();
        this.view.gridPaneforModifyGrades.add(this.view.selectStudentsCB,1,2);
        //We put in the list the data from the database
        this.view.selectStudentsCB.setItems(getStudents());
        this.view.selectStudentsCB.getSelectionModel().selectFirst();

        //Step 1: we check if the grade can be modified
        this.view.enterGrade = new Button("Enter grade");
        this.view.gridPaneforModifyGrades.add(this.view.enterGrade, 1, 3, 1 ,1);
        //We call the method that will tell us whether we can modify the grade or not
        this.view.enterGrade.setOnAction(e -> canGradeBeModified(this.view.selectStudentsCB.getValue()));

        //Step 2: if it can be modified, the ok button can be used
        this.view.ok = new Button("Ok");
        this.view.gridPaneforModifyGrades.add(this.view.ok, 2, 6, 1 ,1);

        this.view.textfieldModifyGrades = new TextArea();
        this.view.textfieldModifyGrades.setMaxWidth(400);
        this.view.textfieldModifyGrades.setMaxHeight(100);
        this.view.gridPaneforModifyGrades.add(this.view.textfieldModifyGrades,1,4,1,1);

        //the textfield in which only the new grade will be typed
        this.view.textfieldEnterGrade = new TextArea();
        this.view.textfieldEnterGrade.setMaxWidth(200);
        this.view.textfieldEnterGrade.setMaxHeight(100);
        this.view.gridPaneforModifyGrades.add(this.view.textfieldEnterGrade,2,5,1,1);


        this.view.gridPaneforModifyGrades.add(this.view.goBack,1,6);
    }

    //This method checks if indeed the grade is null and as such modifiable
    void canGradeBeModified(String studentName){
        try {
            ArrayList<StudentInfo> student = model.QueryforStudent(studentName); //we get the info of the chosen student
            //We get all the grades of the student
            ArrayList<Double> Grades = model.QueryForGrades(student.get(0).studentID);
            ArrayList<Integer> coursesID = model.QueryForCourseID(student.get(0).studentID);
            ArrayList<String> courseNames = model.QueryForCourseName(coursesID);
            boolean ismodify = false;
           //We go through the grades
            for (int i = 0; i < Grades.size(); i++) {
                //If the grade is null
                if(Grades.get(i) == 0){
                    ismodify = true;
                    this.view.textfieldModifyGrades.appendText("You can modify the grade for " + courseNames.get(i) + "\n");
                    this.view.textfieldModifyGrades.appendText("Please insert the grade you want to add in the other textfield.\n");
                    //this.view.ok.setOnAction(e -> System.out.println("Grade: " + this.view.textfieldEnterGrade.getText()));
                    this.view.ok.setOnAction(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            try {
                                getInputGrade(student.get(0).studentID); //We go get the grade entered by the user
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
            if(!ismodify){
                this.view.textfieldModifyGrades.appendText("there is no grade to fill out for this student\n");
            }
        }catch(SQLException e ){
            System.out.println(e.getMessage());
            System.out.println("error in controller: " + e.getMessage());
        }
    }

    //This method prints out the new grade
    void getInputGrade(Integer studentID) throws SQLException {
        //We get the input and translate it to a double
        System.out.println("Grade: " + this.view.textfieldEnterGrade.getText());
        String input = this.view.textfieldEnterGrade.getText();
        double g = Double.parseDouble(input);
        model.UpdateGrade(g, studentID);
        //We display the grade entered again to confirm it was taken into account
        this.view.textfieldModifyGrades.appendText("New grade is " + g + "\n");
    }

    //This method designs the gridPain of the average for the students and the courses
    void getAverages() throws SQLException {
        this.view.primaryStage.setScene(this.view.averagesScene);
        this.view.primaryStage.show();
        //Get the overall average of a student
        Label rulesStudentAverage = new Label("Here you can select a student to get their overall average grade of all the courses they attend: ");
        this.view.gridPaneforAverages.add(rulesStudentAverage, 1, 1);
        //This allows us to choose an option on a list
        this.view.selectStudentsCB = new ComboBox<>();
        this.view.gridPaneforAverages.add(this.view.selectStudentsCB,1,2);
        //We put in the list the data from the database
        this.view.selectStudentsCB.setItems(getStudents());
        this.view.selectStudentsCB.getSelectionModel().selectFirst();
        this.view.updateStudent = new Button("update");
        this.view.gridPaneforAverages.add(this.view.updateStudent,2,2);
        this.view.updateStudent.setOnAction(e -> printAverageOfStudent(this.view.selectStudentsCB.getValue()));
        Label rulesforCourses = new Label("Here you can select a course and see the average grade in said course.");
        this.view.gridPaneforAverages.add(rulesforCourses, 1, 6);
        //This allows us to choose an option on a list
        this.view.selectCourseCB = new ComboBox<>();
        this.view.gridPaneforAverages.add(this.view.selectCourseCB,1,7);
        //We put in the list the data from the database
        this.view.selectCourseCB.setItems(getCourses());
        this.view.selectCourseCB.getSelectionModel().selectFirst();
        this.view.updateCourse = new Button("update");
        this.view.gridPaneforAverages.add(this.view.updateCourse,2,7);
        this.view.updateCourse.setOnAction(e -> printAverageOfCourse(this.view.selectCourseCB.getValue()));
        //We display textfields where we will print out the name of the courses and the student's grades
        this.view.textfieldAverageOfCourse = new TextArea();
        this.view.textfieldAverageOfCourse.setMaxWidth(400);
        this.view.textfieldAverageOfCourse.setMaxHeight(100);
        this.view.textfieldAverageOfStudent = new TextArea();
        this.view.textfieldAverageOfStudent.setMaxWidth(400);
        this.view.textfieldAverageOfStudent.setMaxHeight(100);
        this.view.gridPaneforAverages.add(this.view.textfieldAverageOfCourse,1,8,2,2);
        this.view.gridPaneforAverages.add(this.view.textfieldAverageOfStudent,1,3,2,2);
        this.view.gridPaneforAverages.add(this.view.goBack,100,20);
    }

    //This method prints out the average of a students grades
    void printAverageOfStudent(String studentName){
        try{
            ArrayList<StudentInfo> student = model.QueryforStudent(studentName); //we get the student id
            Double studentAverage = model.QueryStudentAverage(student.get(0).studentID); //we get the average connected to this student id
            this.view.textfieldAverageOfStudent.appendText( "The average of " + studentName + " is " + studentAverage + "\n");
        }catch(SQLException e ){
            System.out.println(e.getMessage());
            System.out.println("error in controller: " + e.getMessage());
        }
    }

    //This method prints out the average of a course grades
    void printAverageOfCourse(String courseName){
        try{
            ArrayList<CourseInfo> course = model.QueryforCourse(courseName); //we get the course id
            System.out.println("course size is " + course.size());

            Double courseAverage = model.QueryCourseAverage(course.get(0).courseID); //we get the average connected to this student id

            this.view.textfieldAverageOfCourse.appendText( "The average in " + courseName + " is " + courseAverage + "\n");

        }catch(SQLException e ){
            System.out.println(e.getMessage());
            System.out.println("error in controller: " + e.getMessage());
        }
    }

    public ObservableList<String> getStudents() throws SQLException {
        ArrayList<String> students = model.SQLQueryStudents();
        return FXCollections.observableArrayList(students);
    }

    public ObservableList<String> getCourses() throws SQLException {
        ArrayList<String> courses = model.SQLQueryCourseNames();
        return FXCollections.observableArrayList(courses);
    }

    public ObservableList<Double> getGrades() throws SQLException {
        ArrayList<Double> grades = model.SQLQueryGrades();
        return FXCollections.observableArrayList(grades);
    }
}