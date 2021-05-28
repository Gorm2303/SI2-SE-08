package presentation;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewProductionController {

    @FXML
    private DatePicker productionDate;
    @FXML
    private VBox organizationVBox, roleVBox;
    @FXML
    private HBox outerHBox;
    @FXML
    private Button newOrganization, newContributor, newOrganizationCancel, newOrganizationSave, saveProduction, productionCancelChanges, deleteProduction,
            addOrganization, addRole, searchButtonOrganization, searchButtonContributor, searchButtonProducer;
    @FXML
    private TextField productionName, productionLength, searchFieldOrganization, searchFieldContributor, searchFieldProducer;
    @FXML
    private ChoiceBox<Organization> currentOrganization, productionProducer;
    private ChoiceBox<Contributor> currentContributor;

    private ArrayList<ChoiceBox> contributingOrganizations;
    private LinkedHashMap<TextField, ArrayList<ChoiceBox>> roleContributors;

    private static NewProductionController latestProductionController;

    private Production currentProduction;

    public NewProductionController() {
        initialize();
        latestProductionController = this;
    }

    public void initialize() {
        roleContributors = new LinkedHashMap<>();
        contributingOrganizations = new ArrayList<>();
        handleDisableOfAddButtons(outerHBox, true, false); //disable add buttons until producer is selected
    }

    public void onButtonClicked(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        // Fixing when you click the choicebox menu it will show no results and be buggy when you search for something.
        hideChoiceBoxMenu();

        // Checking which one of all the buttons in new production was clicked
        if (button == newOrganization) {
            makeNewOrganization(false);

        } else if (button == newContributor) {
            makeNewOrganization(true);

        } else if (button == saveProduction) {
            checkFieldsAndSave();

        } else if (button == productionCancelChanges) {
            try {
                Scene scene = new Scene(Main.loadFXML("showcredit"));
                Main.getPrimaryStage().setScene(scene);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (button == deleteProduction) {
            productionDeletion();

        } else if (button == addRole) {
            resetSearchArea(true);
            addRole();

        } else if (button == addOrganization) {
            resetSearchArea(false);
            addOrganization();

        } else if (button == searchButtonOrganization) {
            searchInDB(searchFieldOrganization, currentOrganization);

        } else if (button == searchButtonContributor) {
            searchInDB(searchFieldContributor, currentContributor);

        } else if (button == searchButtonProducer) {
            searchInDB(searchFieldProducer, productionProducer);
        }
    }

    private void searchInDB(TextField searchField, ChoiceBox currentStorable) {
        String searchString = searchField.getText();
        if (searchString.isBlank()) {
            return;
        }
        // List with search results
        ObservableList<Storable> observableList = FXCollections.observableArrayList();
        if (searchField == searchFieldContributor) {
            observableList.addAll(Catalog.getInstance().searchForContributors(searchString, 1, 10));
        } else {
            observableList.addAll(Catalog.getInstance().searchForOrganizations(searchString, 1, 10));
        }
        currentStorable.setItems(observableList);

        // If no search results were found
        if (currentStorable.getItems().isEmpty()) {
            handleDisableOfAddButtons(outerHBox, true, false);
            return;
        }
        System.out.println(currentStorable.getItems());
        currentStorable.show();

    }

    private void checkFieldsAndSave() {
        // Checks the production attributes if they are the right format
        if (productionName.getText().isBlank() || (productionDate.getValue() == null && !isRegularDate(productionDate.getEditor().getText()))
                || (productionLength.getText().isBlank() && isRegularLength(productionLength.getText()))
                || (productionProducer.getValue() == null)) {
            fieldMissingWindow();
        } else {
            try {
                // Save production and set another scene
                saveProduction();
                Scene scene = new Scene(Main.loadFXML("showcredit"));
                Main.getPrimaryStage().setScene(scene);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                return;
            }
        }
    }

    // Add javafx for another organization
    public ChoiceBox<Organization> addOrganization() {
        HBox hBox = new HBox();

        ChoiceBox<Organization> choiceBox = new ChoiceBox<>();
        contributingOrganizations.add(choiceBox);
        currentOrganization = choiceBox;
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

    // Add javafx for another role and contributor
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
        roleContributors.get(textFieldRole).add(choiceBox);
        currentContributor = choiceBox;
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
            resetSearchArea(true);
            addContributor(newContributorVBox, textFieldRole);
        }));

        newContributorVBox.getChildren().add(choiceBox);
        hBox.getChildren().addAll(textFieldRole, removeButton, newContributorVBox, addContributorButton);
        roleVBox.getChildren().add(hBox);
        return textFieldRole;
    }

    // Javafx for saving an organization in the database
    private void makeNewOrganization(boolean contributorInstead) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        VBox vBox = new VBox();

        TextField textField = new TextField();
        Button cancel = new Button("Annuller");
        Button save = new Button("Gem");

        cancel.setOnAction(actionEvent1 -> {
            stage.close();
        });

        // If this boolean is true it is a contributor which is gonna be saved in the database
        if (contributorInstead) {
            makeNewContributor(vBox, save, textField, stage);
        } else {
            // It is a organization which is saved in the database
            Label label = new Label("Organisationens navn");
            vBox.getChildren().add(label);
            save.setOnAction(actionEvent1 -> {
                // Store organization in database
                Organization org = new Organization();
                org.setName(textField.getText());
                org.store();
                stage.close();
            });
        }

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

    // Javafx for saving an contributor in the database
    private void makeNewContributor(VBox vBox, Button save, TextField textField, Stage stage) {
        Label label = new Label("Medvirkendes navn");
        Label date = new Label("Fødselsdag");
        DatePicker datePicker = new DatePicker();

        save.setOnAction(actionEvent1 -> {
            if (isRegularDate(datePicker.getEditor().getText())) {
                // Store contributor in database
                Contributor contributor = new Contributor();
                contributor.setName(textField.getText());
                contributor.setBirthDate(datePicker.getEditor().getText());
                contributor.store();
                stage.close();
            }
        });

        vBox.getChildren().addAll(label, date, datePicker);
    }

    // Add javafx for another contributor to a specific role
    private ChoiceBox<Contributor> addContributor(VBox vBox, TextField key) {
        HBox hBox = new HBox();

        ChoiceBox<Contributor> choiceBox = new ChoiceBox<>();
        roleContributors.get(key).add(choiceBox);
        currentContributor = choiceBox;
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
        return choiceBox;
    }

    public void onContextMenuRequested(ActionEvent actionEvent) {
        handleDisableOfAddButtons(outerHBox, false, false);
    }

    // Ask the user if he is sure to delete a production
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
            ICatalog.getInstance().removeProduction(currentProduction.getId());
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

    // Saving the production and all the fields in the database
    public void saveProduction() throws IllegalArgumentException {
        String name = productionName.getText(), date = productionDate.getEditor().getText(), length = productionLength.getText();
        Organization producer = productionProducer.getValue();

        if (currentProduction == null) {
            currentProduction = new Production();
        }
        currentProduction.setName(name);
        currentProduction.setLength(Integer.parseInt(length));
        currentProduction.setReleaseDate(date);

        // The producer
        currentProduction.setProducer(producer);

        // The contributing organizations
        ArrayList<Organization> organizations = new ArrayList<>();
        for (ChoiceBox choiceBox : contributingOrganizations) {
            Organization org = (Organization) choiceBox.getValue();
            organizations.add(org);
        }

        currentProduction.setOrgContributors(organizations);

        // The credits for all roles and contributors
        ArrayList<Credit> credits = new ArrayList<>();
        for (TextField textField : roleContributors.keySet()) {
            String role = textField.getText();
            // if role is blank stop the process and try again later
            if (role.isBlank()) {
                fieldMissingWindow();
                throw new IllegalArgumentException();
            }
            Credit credit = new Credit();
            credit.setRole(role);
            // Contributors for each role
            ArrayList<Contributor> contributors = new ArrayList<>();
            for (ChoiceBox cb : roleContributors.get(textField)) {
                Contributor cont = (Contributor) cb.getValue();
                contributors.add(cont);
            }

            credit.setContributors(contributors);
            credits.add(credit);
        }

        currentProduction.setCredits(credits);

        int productionID = currentProduction.store(); //stores production in DB and stores Credits

    }

    // Load a production which already exists in the database
    public void loadProduction(Storable production) {
        currentProduction = (Production) production;

        // The production information
        productionName.setText(currentProduction.getName());
        productionDate.getEditor().setText(currentProduction.getReleaseDate());
        productionLength.setText(String.valueOf(currentProduction.getLength()));
        productionProducer.getItems().add(currentProduction.getProducer());
        productionProducer.setValue(currentProduction.getProducer());
        productionProducer.setStyle("-fx-border-width: 0");

        // Filling the choiceBoxes with the contributing organizations
        ArrayList<Organization> organizations = currentProduction.getOrgContributors();
        for (Organization organization : organizations) {
            ChoiceBox<Organization> choiceBox = addOrganization();
            choiceBox.getItems().add(organization);
            choiceBox.setValue(organization);
            choiceBox.setDisable(true);
            choiceBox.setStyle("-fx-border-width: 0");
        }

        // Filling the choiceBoxes with the credits for all roles and contributors
        ArrayList<Credit> credits = currentProduction.getCredits();

        for (Credit credit : credits) {
            TextField role = addRole();
            role.setText(credit.getRole());
            ArrayList<ChoiceBox> choiceBoxes = roleContributors.get(role);

            ArrayList<Contributor> contributors = new ArrayList<>(credit.getContributors());
            ChoiceBox<Contributor> firstChoiceBox = currentContributor;
            Contributor firstContributor = contributors.remove(0);
            firstChoiceBox.getItems().add(firstContributor);
            firstChoiceBox.setValue(firstContributor);
            firstChoiceBox.setDisable(true);
            firstChoiceBox.setStyle("-fx-border-width: 0");

            // Filling each roles choiceBoxes with its contributors
            for (Contributor contributor : contributors) {
                ChoiceBox<Contributor> choiceBox = addContributor((VBox) choiceBoxes.get(0).getParent(), role);
                choiceBox.getItems().add(contributor);
                choiceBox.setValue(contributor);
                choiceBox.setDisable(true);
                choiceBox.setStyle("-fx-border-width: 0");
            }
        }
        searchButtonContributor.setDisable(true);
        searchFieldContributor.setDisable(true);

    }

    // Recursive method for getting all children of the nodes, and doing something
    // if the leafNodes are Buttons, Panes, ScrollPanes or ChoiceBoxes
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
        } else if (disableChoiceBoxes && node instanceof ChoiceBox && node != productionProducer) {
            node.setDisable(true);
            node.setStyle("-fx-border-width: 0");
        }
    }

    // If a field in the production is missing give a warning
    private void fieldMissingWindow() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Tomt felt");

        VBox vBox = new VBox();
        Label label = new Label("Ikke alle nødvendige felter er udfyldt!");
        HBox hBox = new HBox();
        Button okButton = new Button("Okay");

        okButton.setOnAction((actionEvent -> {
            stage.close();
        }));

        vBox.setStyle("-fx-background-color: orange; " + "-fx-border-color: black; " + "-fx-padding: 5");
        vBox.setSpacing(10);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        hBox.getChildren().addAll(okButton);
        vBox.getChildren().addAll(label, hBox);

        stage.setScene(new Scene(vBox));
        stage.show();
    }

    // Regular expression for checking the format of the production Date.
    private boolean isRegularDate(String date) {
        Pattern pattern = Pattern.compile("^(\\d{2}\\.){2}\\d{4}$");
        Matcher matcher = pattern.matcher(date);
        return matcher.find();
    }

    // Checking the format of the production length.
    private boolean isRegularLength(String length) {
        try {
            int i = Integer.parseInt(length);
        } catch (NumberFormatException e) {
            System.out.println("Produktionslængden er ikke et tal");
            return true;
        }
        return false;
    }


    private void removeCombination(TextField key) {
        roleContributors.remove(key);
    }

    public static NewProductionController getLatestProductionController() {
        return latestProductionController;
    }

    // Hide the choiceBox menu if it is deselected
    public void onMouseClicked(MouseEvent mouseEvent) {
        hideChoiceBoxMenu();
    }

    // Hide all choiceBox menus
    private void hideChoiceBoxMenu() {
        if (currentContributor != null) {
            currentContributor.hide();
        }
        if (currentOrganization != null) {
            currentOrganization.hide();
        }
        if (productionProducer != null) {
            productionProducer.hide();
        }
    }

    // Delete old searches and disables some of the search buttons and fields
    private void resetSearchArea(boolean searchForContributor) {
        handleDisableOfAddButtons(outerHBox, true, true);
        searchFieldContributor.setText("");
        searchFieldOrganization.setText("");
        searchButtonContributor.setDisable(!searchForContributor);
        searchFieldContributor.setDisable(!searchForContributor);
        searchButtonOrganization.setDisable(searchForContributor);
        searchFieldOrganization.setDisable(searchForContributor);
    }
}
