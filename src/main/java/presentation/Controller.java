package presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;

public class Controller {
    public ListView productionListview;
    public TextArea displayArea;
    public TextField searchField;
    @FXML
    private Button btnShowCredits, editProductionButton, addProductionButton, newOrganization, newContributor, newOrganizationCancel, newOrganizationSave,
            searchButton, nextButton, previousButton, saveProduction, productionCancelChanges, deleteProduction;
    @FXML
    private Label birthdayLabel;
    @FXML
    private DatePicker birthdaySelect;

    public void onButtonClicked(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();

        if (button == addProductionButton || button == newOrganizationCancel) {
            try {
                Scene scene = new Scene(Main.loadFXML("newproduction"), 810, 500);
                Main.getPrimaryStage().setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (button == newOrganization || button == newContributor) {
            try {
                Scene scene = new Scene(Main.loadFXML("makecharacter"), 600, 500);
                Main.getPrimaryStage().setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (button == btnShowCredits || button == productionCancelChanges) {
            try {
                Scene scene = new Scene(Main.loadFXML("showcredit"), 600, 500);
                Main.getPrimaryStage().setScene(scene);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
