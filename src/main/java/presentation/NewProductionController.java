package presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class NewProductionController {
    @FXML
    private Label birthdayLabel, organizationNameLabel;
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
        if (button == newOrganization || button == newContributor) {
            Stage stage = new Stage();
            VBox vBox = new VBox();
            if (button == newOrganization) {
                Label label = new Label("Organisationens navn");
                vBox.getChildren().add(label);
            } else {
                Label label = new Label("Medvirkendes navn");
                Label date = new Label("Fødselsdag");
                DatePicker datePicker = new DatePicker();
                vBox.getChildren().addAll(label, date, datePicker);
            }

            TextField textField = new TextField();
            Button cancel = new Button("Annuller");
            Button save = new Button("Gem");

            cancel.setOnAction(actionEvent1 -> {
                stage.close();
            });
            save.setOnAction(actionEvent1 -> {
                stage.close();
            });

            vBox.setSpacing(10);
            vBox.setPadding(new Insets(10));
            vBox.getChildren().add(1, textField);

            HBox hBox = new HBox();
            hBox.setSpacing(40);
            hBox.getChildren().addAll(save, cancel);
            vBox.getChildren().add(hBox);

            Scene scene = new Scene(vBox);
            stage.setScene(scene);
            stage.show();

        } else if (button == productionCancelChanges || button == saveProduction) {
            try {
                Scene scene = new Scene(Main.loadFXML("showcredit"));
                Main.getPrimaryStage().setScene(scene);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (button == deleteProduction) {
            productionDeletion();
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

    private void productionDeletion() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Sletning af produktion");

        VBox vBox = new VBox();
        Label label = new Label("Er du sikker på du vil slette denne produktion?");
        HBox hBox = new HBox();
        Button yesButton = new Button("Ja");
        Button noButton = new Button("Nej");

        yesButton.setOnAction((actionEvent -> {
            try {
                Scene scene = new Scene(Main.loadFXML("showcredit"));
                Main.getPrimaryStage().setScene(scene);

            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.close();
        }));

        noButton.setOnAction(actionEvent -> {
            stage.close();
        });

        vBox.setStyle("-fx-background-color: orange; " + "-fx-border-color: black; " + "-fx-padding: 5");
        vBox.setSpacing(10);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        hBox.getChildren().addAll(yesButton, noButton);
        vBox.getChildren().addAll(label, hBox);

        stage.setScene(new Scene(vBox));
        stage.show();
    }

}
