import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        Label rulesForStudents = new Label("Here you can select a student to get their grades or the courses they attend.");
        this.view.gridPaneForStudents.add(rulesForStudents, 1, 1);

        //initialize the buttons for this page
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
