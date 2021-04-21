package presentation;

import domain.Contributor;
import domain.Credit;
import domain.Organization;
import domain.Production;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class NewProductionController {
    @FXML
    private Label birthdayLabel, organizationNameLabel;
    @FXML
    private DatePicker birthdaySelect, productionDate;
    @FXML
    private VBox organizationVBox, roleVBox, contributorVBox;
    @FXML
    private Button newOrganization, newContributor, newOrganizationCancel, newOrganizationSave, saveProduction, productionCancelChanges, deleteProduction, addOrganization,
            addContributor, addRole;
    @FXML
    private TextField productionName, productionLength, productionCategory, productionProducer, organizationName, roleName, contributorToRole;

    private ArrayList<TextField> contributingOrganizations;
    private HashMap<TextField, ArrayList<TextField>> roleContributors;

    public void initialize() {
        roleContributors = new HashMap<>();
        roleContributors.put(roleName, new ArrayList<>());
        contributingOrganizations = new ArrayList<>();
    }

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
            if (button == saveProduction) {
                saveProduction();
            }
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
            handleAddContributor(actionEvent, contributorVBox, roleName);

        } else if (button == addRole) {
            HBox hBox = new HBox();
            hBox.setSpacing(10);

            TextField textFieldRole = new TextField();
            textFieldRole.setPromptText("Rolle");
            roleContributors.put(textFieldRole, new ArrayList<>());

            VBox newContributorVBox = new VBox();
            newContributorVBox.setSpacing(5);

            TextField textFieldContributor = new TextField();
            textFieldContributor.setPromptText("Eksisterende medvirkende");
            combineRoleContributors(textFieldRole, textFieldContributor);

            Button removeButton = new Button();
            removeButton.setText("Fjern");
            removeButton.setOnAction((event) -> {
                roleVBox.getChildren().remove(hBox);
                removeCombination(textFieldRole);
            });

            Button addContributorButton = new Button();
            addContributorButton.setText("Tilføj endnu en medvirkende");
            addContributorButton.setOnAction((event -> {
                handleAddContributor(event, newContributorVBox, textFieldRole);
            }));

            newContributorVBox.getChildren().add(textFieldContributor);
            hBox.getChildren().addAll(textFieldRole, removeButton, newContributorVBox, addContributorButton);
            roleVBox.getChildren().add(hBox);

        } else if (button == addOrganization) {
            HBox hBox = new HBox();
            TextField textField = new TextField();
            textField.setPromptText("Organisation");
            contributingOrganizations.add(textField);

            Button button1 = new Button();
            button1.setText("Fjern");
            button1.setOnAction((event) -> {
                organizationVBox.getChildren().remove(hBox);
                contributingOrganizations.remove(textField);
            });

            hBox.getChildren().addAll(textField, button1);
            organizationVBox.getChildren().add(hBox);
        }
    }

    private void handleAddContributor(ActionEvent actionEvent, VBox vBox, TextField key) {
        HBox hBox = new HBox();

        TextField textFieldContributor = new TextField();
        textFieldContributor.setPromptText("Eksisterende medvirkende");

        combineRoleContributors(key, textFieldContributor);

        Button removeButton = new Button();
        removeButton.setText("Fjern");
        removeButton.setOnAction((event) -> {
            vBox.getChildren().remove(hBox);
            roleContributors.get(key).remove(textFieldContributor);
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

    public void saveProduction() {
        String name = productionName.getText(), date = productionDate.getEditor().getText(), length = productionLength.getText(),
                category = productionCategory.getText(), producer = productionProducer.getText();

        combineRoleContributors(roleName, contributorToRole);

        Production production = new Production();

        production.setName(name);
        production.setProgramCategory(category);
        if (!length.isEmpty()) {
            production.setLength(Integer.parseInt(length));
        }

        // Translating DatePicker's String to Date object
        if (!date.isEmpty()) {
            String[] strings = date.split("\\.");

            int[] ints = new int[strings.length];
            int i = 0;
            for (String s : strings) {
                ints[i] = Integer.parseInt(s);
                i++;
            }
            production.setReleaseDate(new Date(ints[2] - 1900, ints[1] - 1, ints[0]));
        }
        // The producer
        Organization organization = new Organization();
        organization.setName(producer);
        production.setProducer(organization);

        // The contributing organizations
        ArrayList<Organization> organizations = new ArrayList<>();
        contributingOrganizations.add(0, organizationName);
        for (TextField textField : contributingOrganizations) {
            String org = textField.getText();
            Organization organiz = new Organization();
            organiz.setName(org);
            organizations.add(organiz);
        }

        production.setOrgContributors(organizations);

        // The credits for all roles and contributors
        ArrayList<Credit> credits = new ArrayList<>();
        for (TextField textField : roleContributors.keySet()) {
            String role = textField.getText();
            Credit credit = new Credit();
            credit.setRole(role);

            ArrayList<Contributor> contributors = new ArrayList<>();
            for (TextField tf : roleContributors.get(textField)) {
                String cont = tf.getText();
                Contributor contributor = new Contributor();
                contributor.setName(cont);
                contributors.add(contributor);
            }

            credit.setContributors(contributors);
            credits.add(credit);
        }

        production.setCredits(credits);

        ShowCreditController.getCatalog().addProduction(production);
    }

    private void combineRoleContributors(TextField key, TextField value) {
        roleContributors.get(key).add(value);
    }

    private void combineRoleContributors(TextField key, ArrayList<TextField> values) {
        roleContributors.get(key).addAll(values);
    }

    private void removeCombination(TextField key) {
        roleContributors.remove(key);
    }


}
