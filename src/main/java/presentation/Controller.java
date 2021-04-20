package presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Controller {
    public ListView productionListview;
    public TextArea displayArea;
    public TextField searchField;
    @FXML
    private Button btnShowCredits, editProductionButton, addProductionButton, searchButton, nextButton, previousButton;


    public void onButtonClicked(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();

        // Buttons for browsing the program
        if (button == addProductionButton) {
            try {
                Scene scene = new Scene(Main.loadFXML("newproduction"), 810, 500);
                Main.getPrimaryStage().setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (button == btnShowCredits) {
            try {
                Scene scene = new Scene(Main.loadFXML("showcredit"), 600, 500);
                Main.getPrimaryStage().setScene(scene);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}