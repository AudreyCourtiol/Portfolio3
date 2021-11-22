import javafx.application.Application;
import javafx.stage.Stage;
import java.sql.SQLException;

public class Main extends Application {
    public void start(Stage primaryStage)  {
        //URL of the database
        String url = "jdbc:sqlite:C:\\Java programs\\Portfolio3.db";
        ProjectView view=new ProjectView(primaryStage);
        ProjectModel model=new ProjectModel(url);
        try {
            new ProjectController(view, model);
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args){
        launch(args);  }
}