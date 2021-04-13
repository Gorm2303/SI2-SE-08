package presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    private Button winButton;

    public void onButtonClicked(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        String s = button.getText();
        button.setText(s + "!");
        System.out.println("You win!");
    }
}
