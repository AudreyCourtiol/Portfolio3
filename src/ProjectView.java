import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class ProjectView {
    GridPane startview;
    Label StartStationLbl;
    Label EndStationLbl;
    Label TimeLbl;
    Button ExitBtn;
    Button FindStudents;
    Button GetAverages;
    Button ModifyGrades;
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

    public ProjectView(){
        startview=new GridPane();
        CreateView();
    }

    //we create the window
    private void CreateView(){
        startview.setMinSize(300,200);
        startview.setPadding( new Insets(10,10,10,10));
        startview.setHgap(5);
        startview.setVgap(5);

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

    public Parent asParent(){
        return  startview;
    }
}
