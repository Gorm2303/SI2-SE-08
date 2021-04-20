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
    public Button previousButton;
    public Button nextButton;
    public TextArea displayArea;
    public Button editProductionButton;
    public Button addProductionButton;
    public Button searchButton;
    public TextField searchField;
    @FXML
    private Button winButton, btnSeKrediteringer;

    public void onButtonClicked(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();

        if (button == btnSeKrediteringer) {
            try {
                Scene scene = new Scene(Main.loadFXML("showcredit"), 800, 800);
                Main.getPrimaryStage().setScene(scene);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
