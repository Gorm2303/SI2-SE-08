package presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class NewProductionController {
    @FXML
    private Label birthdayLabel;
    @FXML
    private DatePicker birthdaySelect;
    @FXML
    private VBox organizationVBox, roleVBox, contributorVBox;
    @FXML
    private Button newOrganization, newContributor, newOrganizationCancel, newOrganizationSave, saveProduction, productionCancelChanges, deleteProduction, addOrganization,
            addContributor, addRole;


    public void onButtonClicked(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();

        // Buttons for browsing the program
        if (button == newOrganizationCancel) {
            Main.getPrimaryStage().setScene(ShowCreditController.getNewProduction());

        } else if (button == newOrganization || button == newContributor) {
            try {
                Scene scene = new Scene(Main.loadFXML("makecharacter"));
                Main.getPrimaryStage().setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (button == productionCancelChanges) {
            try {
                Scene scene = new Scene(Main.loadFXML("showcredit"));
                Main.getPrimaryStage().setScene(scene);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // new production buttons
        if (button == addContributor) {
            handleAddContributor(actionEvent, contributorVBox);

        } else if (button == addRole) {
            HBox hBox = new HBox();
            hBox.setSpacing(10);

            TextField textFieldRole = new TextField();
            textFieldRole.setPromptText("Rolle");

            VBox newContributorVBox = new VBox();
            newContributorVBox.setSpacing(5);

            TextField textFieldContributor = new TextField();
            textFieldContributor.setPromptText("Eksisterende medvirkende");

            Button removeButton = new Button();
            removeButton.setText("Fjern");
            removeButton.setOnAction((event) -> {
                roleVBox.getChildren().remove(hBox);
            });

            Button addContributorButton = new Button();
            addContributorButton.setText("Tilføj endnu en medvirkende");
            addContributorButton.setOnAction((event -> {
                handleAddContributor(event, newContributorVBox);
            }));

            newContributorVBox.getChildren().add(textFieldContributor);
            hBox.getChildren().addAll(textFieldRole, removeButton, newContributorVBox, addContributorButton);
            roleVBox.getChildren().add(hBox);

        } else if (button == addOrganization) {
            HBox hBox = new HBox();
            TextField textField = new TextField();
            textField.setPromptText("Organisation");

            Button button1 = new Button();
            button1.setText("Fjern");
            button1.setOnAction((event) -> {
                organizationVBox.getChildren().remove(hBox);
            });

            hBox.getChildren().addAll(textField, button1);
            organizationVBox.getChildren().add(hBox);
        }
    }


    private void handleAddContributor(ActionEvent actionEvent, VBox vBox) {
        HBox hBox = new HBox();

        TextField textFieldContributor = new TextField();
        textFieldContributor.setPromptText("Eksisterende medvirkende");

        Button removeButton = new Button();
        removeButton.setText("Fjern");
        removeButton.setOnAction((event) -> {
            vBox.getChildren().remove(hBox);
        });

        hBox.getChildren().addAll(textFieldContributor, removeButton);
        vBox.getChildren().add(hBox);
    }
}
