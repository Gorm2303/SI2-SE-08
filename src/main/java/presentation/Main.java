package presentation;

import data.DBConnection;
import data.DataFacade;
import data.IDataFacade;
import domain.ICatalog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Main.primaryStage = primaryStage;
        Scene scene = new Scene(loadFXML("startscreen"), 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        ICatalog catalog = ICatalog.getInstance();
        catalog.setDBurl(args[0]);
        catalog.setDBPort(Integer.parseInt(args[1]));
        catalog.setDBName(args[2]);
        catalog.setDBUsername(args[3]);
        catalog.setDBPassword(args[4]);
        catalog.initializeDatalayer();

        IDataFacade dataFacade = new DataFacade();

        launch(args);
    }
}
