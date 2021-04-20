package presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class StartScreenController {
    @FXML
    private Button btnShowCredits;

    public void onButtonClicked(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();

        // Buttons for browsing the program
        if (button == btnShowCredits) {
            try {
                Scene scene = new Scene(Main.loadFXML("showcredit"), 600, 500);
                Main.getPrimaryStage().setScene(scene);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
