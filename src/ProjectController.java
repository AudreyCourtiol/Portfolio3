import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

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


        //this.view.stations =getStations();
        //this.view.hours=getHours();
        //this.view.minutes=getMinutes();
        //this.view.FindTrains.setOnAction(e->HandlerPrintTrainRoutes(view.StartStationComB.getValue(),view.EndStationComB.getValue(),
            //    view.HoursComB.getValue(),view.MinutesComB.getValue(), view.textfield));

        //    this.model.SQLQueryStationNames();
        //view.configure();
    }

    void goBack(){
        this.view.primaryStage.setScene(this.view.primaryScene);
        this.view.primaryStage.show();
    }

    void findStudentsPage() throws SQLException { //we display the scene with the students' informations
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

        this.view.gridPaneForStudents.add(this.view.seeCourses, 1, 7); ///mettre à la bonne place
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

    void modifyGrades() throws SQLException {
        this.view.primaryStage.setScene(this.view.modifyGradesScene);
        this.view.primaryStage.show();

        //add the rules for this page
        Label rulesForModifying = new Label("Here you can select a student. If they have a grade that hasn't been entered yet, you can do it.");
        this.view.gridPaneforModifyGrades.add(rulesForModifying, 1, 1);

        //This allows us to choose an option on a list
        this.view.selectStudentsCB = new ComboBox<>();
        this.view.gridPaneforModifyGrades.add(this.view.selectStudentsCB,30,1);
        //We put in the list the data from the database
        this.view.selectStudentsCB.setItems(getStudents());
        this.view.selectStudentsCB.getSelectionModel().selectFirst();

        this.view.textfieldModifyGrades = new TextArea();
        this.view.textfieldModifyGrades.setMaxWidth(200);
        this.view.textfieldModifyGrades.setMaxHeight(100);
        this.view.gridPaneforModifyGrades.add(this.view.textfieldModifyGrades,1,20,2,2);


        this.view.gridPaneforModifyGrades.add(this.view.goBack,40,80);
    }

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

        Label rulesforCourses = new Label("Here you can select a course and see the average grade in said course.");
        this.view.gridPaneforAverages.add(rulesforCourses, 1, 6);
        //This allows us to choose an option on a list
        this.view.selectCourseCB = new ComboBox<>();
        this.view.gridPaneforAverages.add(this.view.selectCourseCB,1,7);
        //We put in the list the data from the database
        this.view.selectCourseCB.setItems(getCourses());
        this.view.selectCourseCB.getSelectionModel().selectFirst();

        //We display textfields where we will print out the name of the courses and the student's grades
        this.view.textfieldAverageOfCourse = new TextArea();
        this.view.textfieldAverageOfCourse.setMaxWidth(200);
        this.view.textfieldAverageOfCourse.setMaxHeight(100);
        this.view.textfieldAverageOfStudent = new TextArea();
        this.view.textfieldAverageOfStudent.setMaxWidth(200);
        this.view.textfieldAverageOfStudent.setMaxHeight(100);
        this.view.gridPaneforAverages.add(this.view.textfieldAverageOfCourse,1,8,2,2);
        this.view.gridPaneforAverages.add(this.view.textfieldAverageOfStudent,1,3,2,2);


        this.view.gridPaneforAverages.add(this.view.goBack,100,20);


        //Now we act
        printAverageOfStudent(this.view.selectStudentsCB.getValue());
        printAverageOfCourse(this.view.selectStudentsCB.getValue());
    }

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

    void printAverageOfCourse(String courseName){
        try{
            ArrayList<CourseInfo> course = model.QueryforCourse(courseName); //we get the course id
            System.out.println(course.size());

            Double courseAverage = model.QueryCourseAverage(course.get(1).courseID); //we get the average connected to this student id

            this.view.textfieldAverageOfCourse.appendText( "The average in " + courseName + " is " + courseAverage + "\n");

        }catch(SQLException e ){
            System.out.println(e.getMessage());
            System.out.println("error in controller: " + e.getMessage());
        }
    }

    public ObservableList<String> getStudents() throws SQLException {
        ArrayList<String> students = model.SQLQueryStudents();
        ObservableList<String> studentnames= FXCollections.observableArrayList(students);
        return studentnames;
    }

    public ObservableList<String> getCourses() throws SQLException {
        ArrayList<String> courses = model.SQLQueryCourseNames();
        ObservableList<String> coursenames= FXCollections.observableArrayList(courses);
        return coursenames;
    }

    public ObservableList<Double> getGrades() throws SQLException {
        ArrayList<Double> grades = model.SQLQueryGrades();
        ObservableList<Double> gradevalues= FXCollections.observableArrayList(grades);
        return gradevalues;
    }

/*
    public void HandlerPrintTrainRoutes(String From, String To, Integer Hour, Integer Minutes, TextArea txtArea)  {
        txtArea.clear();
        txtArea.appendText(" Train, From Station: Departure -> To station: arrival \n");
        double time = (double) Hour + ((double) Minutes / 100);
        try {
            ArrayList<TrainInfo> trips = model.QueryforDepartures(From, To, time);

            for (int i = 0; i < trips.size(); i++) {
                String deptime = String.format("%.2f", trips.get(i).departuretime);
                String arrtime = String.format("%.2f", trips.get(i).arrivaltime);
                System.out.println(i + ";" + trips.get(i).startstation + ": " + deptime + " -> " + trips.get(i).endstation + ": " + arrtime + "\n");
                txtArea.appendText(i + ";" + trips.get(i).startstation + ": " + deptime + " -> " + trips.get(i).endstation + ": " + arrtime + "\n");
            }
        }catch (SQLException e ){
            System.out.println(e.getMessage());
        }
    }

    public ObservableList<String> getStations() throws SQLException {
        ArrayList<String> stations= model.SQLQueryStationNames();
        ObservableList<String> stationnames= FXCollections.observableArrayList(stations);
        return stationnames;
    }

    public ObservableList<Integer>  getHours(){
        ArrayList<Integer> hours=new ArrayList<>();
        for(int i= 0;i<24;i++){
            hours.add(i);
        }
        return FXCollections.observableArrayList(hours);
    }


    public ObservableList<Integer> getMinutes(){
        ArrayList<Integer> minutes=new ArrayList<>();
        for(int i=0;i<60;i++) {
            minutes.add(i);
        }
        return FXCollections.observableArrayList(minutes);
    }*/
}
