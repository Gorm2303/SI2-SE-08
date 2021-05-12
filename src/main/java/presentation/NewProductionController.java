package presentation;

import domain.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class NewProductionController {

    @FXML
    private DatePicker productionDate;
    @FXML
    private VBox organizationVBox, roleVBox;
    @FXML
    private HBox outerHBox;
    @FXML
    private Button newOrganization, newContributor, newOrganizationCancel, newOrganizationSave, saveProduction, productionCancelChanges, deleteProduction,
            addOrganization, addRole, searchButtonOrganization, searchButtonContributor;
    @FXML
    private TextField productionName, productionLength, productionCategory, productionProducer,
            searchFieldOrganization, searchFieldContributor;

    private ArrayList<TextField> contributingOrganizations;
    private HashMap<TextField, ArrayList<TextField>> roleContributors;

    private static NewProductionController latestProductionController;

    public NewProductionController() {
        this.productionName = new TextField();
        this.productionLength = new TextField();
        this.productionCategory = new TextField();
        this.productionProducer = new TextField();
        //this.contributingOrganization = new ChoiceBox(FXCollections.observableArrayList());
        //this.contributorToRole = new ChoiceBox(FXCollections.observableArrayList());
        this.productionDate = new DatePicker();
        this.organizationVBox = new VBox();
        this.roleVBox = new VBox();
        initialize();
        latestProductionController = this;
    }

    public void initialize() {
        roleContributors = new HashMap<>();
        contributingOrganizations = new ArrayList<>();
    }

    public void onButtonClicked(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();

        // Buttons for browsing the program
        if (button == newOrganization) {
            makeNewOrganization(false);

        } else if (button == newContributor) {
            makeNewOrganization(true);

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
        if (button == addRole) {
            searchingSetup();
            addRole();
            searchButtonContributor.setDisable(false);
            searchFieldContributor.setDisable(false);
            searchButtonOrganization.setDisable(true);
            searchFieldOrganization.setDisable(true);

        } else if (button == addOrganization) {
            searchingSetup();
            addOrganization();
            searchButtonOrganization.setDisable(false);
            searchFieldOrganization.setDisable(false);
            searchButtonContributor.setDisable(true);
            searchFieldContributor.setDisable(true);

        } else if (button == searchButtonOrganization || button == searchButtonContributor) {


        }
    }

    public ChoiceBox<Organization> addOrganization() {
        // Make this a beautiful method
        HBox hBox = new HBox();

        ChoiceBox<Organization> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().add(new Organization("This Organization", 69));
        choiceBox.getItems().add(new Organization("This Organization 222", 222));
        choiceBox.setPrefWidth(150);
        choiceBox.setFocusTraversable(false);
        choiceBox.setOnAction((this::onContextMenuRequested));
        choiceBox.setStyle("-fx-border-color: red");

        Button button1 = new Button();
        button1.setFocusTraversable(false);
        button1.setText("Fjern");
        button1.setOnAction((event) -> {
            if (getLastChild(organizationVBox) == hBox) {
                handleDisableOfAddButtons(outerHBox, false, true);
            }
            organizationVBox.getChildren().remove(hBox);
        });

        hBox.getChildren().addAll(choiceBox, button1);
        organizationVBox.getChildren().add(hBox);
        return choiceBox;
    }

    private Node getLastChild(Pane pane) {
        Node returnNode = null;
        for (Node n : pane.getChildren()) {
            returnNode = n;
        }
        return returnNode;
    }

    public TextField addRole() {
        HBox hBox = new HBox();
        hBox.setSpacing(10);

        TextField textFieldRole = new TextField();
        textFieldRole.setFocusTraversable(false);
        textFieldRole.setPromptText("Rolle");
        roleContributors.put(textFieldRole, new ArrayList<>());

        VBox newContributorVBox = new VBox();
        newContributorVBox.setSpacing(5);

        ChoiceBox<Contributor> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().add(new Contributor("This Contributor", 69, new Date()));
        choiceBox.getItems().add(new Contributor("This Contributor 222", 222, new Date()));
        choiceBox.setPrefWidth(150);
        choiceBox.setFocusTraversable(false);
        choiceBox.setOnAction((this::onContextMenuRequested));
        choiceBox.setStyle("-fx-border-color: red");


        Button removeButton = new Button();
        removeButton.setFocusTraversable(false);
        removeButton.setText("Fjern");
        removeButton.setOnAction((event) -> {
            if (getLastChild(roleVBox) == hBox) {
                handleDisableOfAddButtons(outerHBox, false, true);
            }
            roleVBox.getChildren().remove(hBox);
            removeCombination(textFieldRole);
        });

        Button addContributorButton = new Button();
        addContributorButton.setDisable(true);
        addContributorButton.setFocusTraversable(false);
        addContributorButton.setText("Tilføj endnu en medvirkende");
        addContributorButton.setOnAction((event -> {
            handleDisableOfAddButtons(outerHBox, true, true);
            addContributor(newContributorVBox, textFieldRole);
        }));

        newContributorVBox.getChildren().add(choiceBox);
        hBox.getChildren().addAll(textFieldRole, removeButton, newContributorVBox, addContributorButton);
        roleVBox.getChildren().add(hBox);
        return textFieldRole;
    }

    public void makeNewOrganization(boolean contributorInstead) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        VBox vBox = new VBox();

        if (contributorInstead) {
            makeNewContributor(vBox);
        } else {
            Label label = new Label("Organisationens navn");
            vBox.getChildren().add(label);
        }

        TextField textField = new TextField();
        Button cancel = new Button("Annuller");
        Button save = new Button("Gem");

        cancel.setOnAction(actionEvent1 -> {
            stage.close();
        });
        save.setOnAction(actionEvent1 -> {
            Organization org = new Organization();
            org.setName(textField.getText());
            System.out.println(org.store());
            System.out.println(Organization.get(org.getId()));
            System.out.println(Organization.get(org.getId()));
            System.out.println(Organization.get(55));
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
    }

    private void makeNewContributor(VBox vBox) {
        Label label = new Label("Medvirkendes navn");
        Label date = new Label("Fødselsdag");
        DatePicker datePicker = new DatePicker();
        vBox.getChildren().addAll(label, date, datePicker);
    }

    private void addContributor(VBox vBox, TextField key) {
        HBox hBox = new HBox();

        ChoiceBox<Contributor> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList());
        choiceBox.getItems().add(new Contributor("This Contributor", 69, new Date()));
        choiceBox.getItems().add(new Contributor("This Contributor 222", 222, new Date()));
        choiceBox.setPrefWidth(150);
        choiceBox.setFocusTraversable(false);
        choiceBox.setOnAction((this::onContextMenuRequested));
        choiceBox.setStyle("-fx-border-color: red");

        Button removeButton = new Button();
        removeButton.setFocusTraversable(false);
        removeButton.setText("Fjern");
        removeButton.setOnAction((event) -> {
            if (getLastChild(vBox) == hBox) {
                handleDisableOfAddButtons(outerHBox, false, true);
            }
            vBox.getChildren().remove(hBox);
        });

        hBox.getChildren().addAll(choiceBox, removeButton);
        vBox.getChildren().add(hBox);
    }

    public void onContextMenuRequested(ActionEvent actionEvent) {
        ChoiceBox<Object> choiceBox = (ChoiceBox<Object>) actionEvent.getSource();
        handleDisableOfAddButtons(outerHBox, false, false);

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
            //deleteProduction()
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

    public void deleteProduction(Production production) {
        ICatalog.getInstance().removeProduction(production);
    }

    public void saveProduction() {
        String name = productionName.getText(), date = productionDate.getEditor().getText(), length = productionLength.getText(),
                category = productionCategory.getText(), producer = productionProducer.getText();

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

        ICatalog.getInstance().addProduction(production);
    }

    public void loadProduction(Production production) {
        // The production information
        productionName.setText(production.getName());
        productionDate.getEditor().setText(production.getReleaseDate().toString());
        productionLength.setText(String.valueOf(production.getLength()));
        productionCategory.setText(production.getProgramCategory());
        productionProducer.setText(production.getProducer().getName());

        // The contributing organizations
        ArrayList<Organization> organizations = production.getOrgContributors();
        for (Organization organization : organizations) {
            ChoiceBox<Organization> choiceBox = addOrganization();
            choiceBox.setValue(organization);
        }

        // The credits for all roles and contributors
        ArrayList<Credit> credits = production.getCredits();

        for (Credit credit : credits) {
            TextField role = addRole();
            ArrayList<TextField> textFields = roleContributors.get(role);

            role.setText(credit.getRole());
            for (Contributor contributor : credit.getContributors()) {
                addContributor((VBox) textFields.get(0).getParent(), role);

            }
        }

        //ShowCreditController.getCatalog().addProduction(production);
    }

    private void searchInDB() {

    }

    private void searchingSetup() {
        //addOrganization.setDisable(!addOrganization.isDisabled());
        //addRole.setDisable(!addRole.isDisabled());
        handleDisableOfAddButtons(outerHBox, true, true);

    }

    // Recursive method for getting all children of the nodes, and doing something if the leafNodes are Buttons
    private void handleDisableOfAddButtons(Node node, boolean disableButtons, boolean disableChoiceBoxes) {
        if (node instanceof Pane) {
            for (Node n : ((Pane) node).getChildren()) {
                handleDisableOfAddButtons(n, disableButtons, disableChoiceBoxes);
            }
        } else if (node instanceof ScrollPane) {
            for (Node n : ((ScrollPane) node).getChildrenUnmodifiable()) {
                handleDisableOfAddButtons(n, disableButtons, disableChoiceBoxes);
            }
        } else if (node instanceof Button) {
            String[] buttonTextArray = ((Button) node).getText().split(" ");
            for (String s : buttonTextArray) {
                if (s.equalsIgnoreCase("tilføj")) {
                    node.setDisable(disableButtons);

                }
            }
        } else if (disableChoiceBoxes && node instanceof ChoiceBox) {
            node.setDisable(true);
            node.setStyle("-fx-border-width: 0");
        }
    }

    // Old code for getting textFields
    private void combineRoleContributors(TextField key, TextField value) {
        roleContributors.get(key).add(value);
    }

    private void combineRoleContributors(TextField key, ArrayList<TextField> values) {
        roleContributors.get(key).addAll(values);
    }

    private void removeCombination(TextField key) {
        roleContributors.remove(key);
    }

    public static NewProductionController getLatestProductionController() {
        return latestProductionController;
    }


}
