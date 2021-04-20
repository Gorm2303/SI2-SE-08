package presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ShowCreditController {
    public ListView productionListview;
    public TextArea displayArea;
    public TextField searchField;
    @FXML
    private Button editProductionButton, addProductionButton, searchButton, nextButton, previousButton;
    private static Scene newProduction;


    public void onButtonClicked(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();

        // Buttons for browsing the program
        if (button == addProductionButton) {
            try {
                newProduction = new Scene(Main.loadFXML("newproduction"), 800, 600);
                Main.getPrimaryStage().setScene(newProduction);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Scene getNewProduction() {
        return newProduction;
    }
}