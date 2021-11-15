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
        this.view.ExitBtn.setOnAction(e-> Platform.exit()); //exit button
        this.view.FindStudents.setOnAction(e -> findStudentsPage()); //clear the page and offer new options
        this.view.ModifyGrades.setOnAction(e -> modifyGrades());
        this.view.GetAverages.setOnAction(e -> getAverages());


        this.model.connectToTrainData();
        this.model.CreateStatement();
        //this.view.stations =getStations();
        //this.view.hours=getHours();
        //this.view.minutes=getMinutes();
        //this.view.FindTrains.setOnAction(e->HandlerPrintTrainRoutes(view.StartStationComB.getValue(),view.EndStationComB.getValue(),
            //    view.HoursComB.getValue(),view.MinutesComB.getValue(), view.textfield));

        //    this.model.SQLQueryStationNames();
        //view.configure();
    }

    void findStudentsPage(){ //we display the scene with the students' informations
        this.view.primaryStage.setScene(this.view.studentScene);
        this.view.primaryStage.show();

        //add the rules for this page
        Label rulesForStudents = new Label("Here you can select a student to get their grades or the courses they attend: ");
        this.view.gridPaneForStudents.add(rulesForStudents, 1, 1);
        //This allows us to choose an option on a list
        this.view.selectStudentsCB = new ComboBox<>();
        this.view.gridPaneForStudents.add(this.view.selectStudentsCB,30,1);

        //initialize the buttons for this page
        this.view.seeCourses = new Button("See courses");
        this.view.seeGrades = new Button("See grades");

        this.view.gridPaneForStudents.add(this.view.seeCourses, 1, 7); ///mettre à la bonne place
        this.view.gridPaneForStudents.add(this.view.seeGrades, 1, 60);

        //We display textfields where we will print out the name of the courses and the student's grades
        this.view.textfieldCourses = new TextArea();
        this.view.textfieldGrades = new TextArea();
        this.view.gridPaneForStudents.add(this.view.textfieldCourses,1,20,2,2);
        this.view.gridPaneForStudents.add(this.view.textfieldGrades,1,70,2,2);

        this.view.goBack = new Button("Back");
        this.view.gridPaneForStudents.add(this.view.goBack,40,80);

    }

    void modifyGrades(){



        this.view.goBack = new Button("Back");
        this.view.gridPaneforModifyGrades.add(this.view.goBack,40,80);
    }

    void getAverages(){
        this.view.goBack = new Button("Back");
        this.view.gridPaneforAverages.add(this.view.goBack,40,80);
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
