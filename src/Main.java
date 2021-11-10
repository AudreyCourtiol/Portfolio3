import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;


public class Main extends Application {
            public void start(Stage primaryStage)  {
                String url = "jdbc:sqlite:C:\\Java programs\\Portfolio3.db";
                ProjectView view=new ProjectView();
                ProjectModel model=new ProjectModel(url);
                ProjectController control=null;

                try {
                    control = new ProjectController(view, model);
                }catch (SQLException e){
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
                primaryStage.setTitle("Students Course Registration");
                primaryStage.setScene(new Scene(view.asParent(),600,475));
                primaryStage.show();


            }

            public static void main(String[] args){
                launch(args);  }
}
