import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ProjectView {
    //a gridPane is the thing on which you display your labels, buttons, text fields ect
    Stage primaryStage;

    //Scene number 1
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
    Button selectAStudent; //menu défilant à coder

    Button goBack; //use on all last three scenes to go back to the first one

    GridPane gridPanefor3;
    Scene averagesScene;


    Scene modifyGradesScene;
    GridPane gridPanefor4;

    /*
    ComboBox<String> StartStationComB;
    ComboBox<String> EndStationComB;
    ComboBox<Integer> HoursComB;
    ComboBox<Integer> MinutesComB;*/
    TextArea textfield;

    /*
    ObservableList<String> stations;
    ObservableList<Integer> hours;
    ObservableList<Integer> minutes;*/

    ObservableList<String> students;

    public ProjectView(Stage primaryStage){
        //initialization of the window
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Students Course Registration");

        startview=new GridPane();
        gridPaneForStudents = new GridPane();
        gridPanefor3 = new GridPane();
        gridPanefor4 = new GridPane();
        CreateView();
    }

    //we create the window
    private void CreateView(){
        //we initialize all scenes
        this.primaryScene = new Scene(this.asParentForPrimaryScene(),600,475);
        this.studentScene = new Scene(this.asParentForStudentScene(),600,475);
        this.modifyGradesScene = new Scene(this.asParentForModifyGrades(),600,475);
        this.averagesScene =  new Scene(this.asParentForAverages(),600,475);

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

        //display the buttons
        startview.add(FindStudents,15,5);
        startview.add(GetAverages,20,5);
        startview.add(ModifyGrades,25,5);
        startview.add(ExitBtn,40,20);

        /*
        StartStationComB=new ComboBox<>();
        startview.add(StartStationComB,2,1);

        EndStationComB = new ComboBox<>();
        startview.add(EndStationComB,2,2);

        HoursComB=new ComboBox<>();
        startview.add(HoursComB,3,3);

        MinutesComB= new ComboBox<>();
        startview.add(MinutesComB,5,3);*/

        /*
        //To have a text field
        textfield=new TextArea();
        startview.add(textfield,1,7,15,10);*/




    }

    /*
    public void configure(){
        StartStationComB.setItems(stations);
        StartStationComB.getSelectionModel().selectFirst();

        EndStationComB.setItems(stations);
        EndStationComB.getSelectionModel().selectFirst();

        HoursComB.setItems(hours);
        HoursComB.getSelectionModel().selectFirst();

        MinutesComB.setItems(minutes);
        MinutesComB.getSelectionModel().selectFirst();

    }*/

    public Parent asParentForPrimaryScene(){
        return  startview;
    }

    public Parent asParentForStudentScene(){
        return gridPaneForStudents;
    }

    public Parent asParentForModifyGrades(){
        return  gridPanefor3;
    }
    public Parent asParentForAverages(){
        return  gridPanefor4;
    }
}
