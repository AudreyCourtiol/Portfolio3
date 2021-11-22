import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.collections.ObservableList;

public class ProjectView {
    //a gridPane is the thing on which you display your labels, buttons, text fields ect
    Stage primaryStage;

    //Scene number 1 : menu
    GridPane startview;
    Scene primaryScene;
    Button ExitBtn;
    Button FindStudents;
    Button GetAverages;
    Button ModifyGrades;


    //Scene for students informations
    GridPane gridPaneForStudents;
    Scene studentScene;
    Button seeCourses;
    Button seeGrades;
    TextArea textfieldCourses;
    TextArea textfieldGrades;
    ComboBox<String> selectStudentsCB; //also used for averages

    Button goBack; //use on all last three scenes to go back to the first one

    //Scene for Averages
    GridPane gridPaneforAverages;
    Scene averagesScene;
    //average grade of a student
    //average grade on a selected course
    TextArea textfieldAverageOfCourse;
    TextArea textfieldAverageOfStudent;
    ComboBox<String> selectCourseCB;


    Scene modifyGradesScene;
    GridPane gridPaneforModifyGrades;
    TextArea textfieldModifyGrades;
    TextArea textfieldEnterGrade;
    Button enterGrade;
    Button ok;

    TextArea textfield;

    ObservableList<String> students;
    ObservableList<String> courses;
    ObservableList<String> professors;
    ObservableList<Double> grades;

    public ProjectView(Stage primaryStage){
        //initialization of the window
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Students Course Registration");

        startview=new GridPane();
        gridPaneForStudents = new GridPane();
        gridPaneforAverages = new GridPane();
        gridPaneforModifyGrades = new GridPane();
        CreateView();
    }

    //we create the window
    private void CreateView(){
        //we initialize all scenes
        this.primaryScene = new Scene(this.asParentForPrimaryScene(),600,500);
        this.studentScene = new Scene(this.asParentForStudentScene(),600,500);
        this.modifyGradesScene = new Scene(this.asParentForModifyGrades(),600,500);
        this.averagesScene =  new Scene(this.asParentForAverages(),600,500);

        //we put our primary scene on the stage when we start
        primaryStage.setScene(primaryScene);
        primaryStage.show();

        //we choose the parameters we want for our first display
        startview.setMinSize(300,200);
        startview.setPadding( new Insets(10,10,10,10));
        startview.setHgap(5);
        startview.setVgap(5);

        //we choose what we want to display on our first display

        //initialize the buttons
        ExitBtn=new Button("Exit");
        FindStudents=new Button("Find students");
        GetAverages = new Button("Get averages");
        ModifyGrades = new Button("Modify Grades");
        goBack = new Button("Back");

        //display the buttons
        startview.add(FindStudents,15,5);
        startview.add(GetAverages,20,5);
        startview.add(ModifyGrades,25,5);
        startview.add(ExitBtn,40,20);
    }

    public Parent asParentForPrimaryScene(){
        return  startview;
    }

    public Parent asParentForStudentScene(){
        return gridPaneForStudents;
    }

    public Parent asParentForModifyGrades(){
        return gridPaneforModifyGrades;
    }
    public Parent asParentForAverages(){
        return gridPaneforAverages;
    }
}
