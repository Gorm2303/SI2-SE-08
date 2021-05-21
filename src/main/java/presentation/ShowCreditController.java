package presentation;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;

public class ShowCreditController {
    @FXML
    private ListView<Object> productionListview;
    @FXML
    private TextArea displayArea;
    @FXML
    private TextField searchField;
    @FXML
    private Button editProductionButton, addProductionButton, searchButton, nextButton, previousButton;
    @FXML
    private RadioButton radioButtonProduction, radioButtonContributor;
    @FXML
    private ToggleGroup searchRadio;
    private static Scene newProduction;

    private int pageNumber;
    private String searchString = "";
    private final int pageSize = 12;
    private static ICatalog catalog;
    private ObservableList<Object> abstractList;
    private ObservableList<Production> productionObservableList;
    private ObservableList<Contributor> contributorObservableList;
    private boolean isProduction = true;

    @FXML
    public void initialize() {
        pageNumber = 1;
        catalog = ICatalog.getInstance();

        productionObservableList = FXCollections.observableArrayList(
                catalog.searchForProductions("", pageNumber, pageSize));
        abstractList = FXCollections.observableArrayList(productionObservableList);
        productionListview.setItems(abstractList);
        nextButton.setDisable(productionObservableList.size() < pageSize);


    }


    public void onButtonClicked(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();

        // Buttons for browsing the program
        if (button == addProductionButton) {
            try {
                newProduction = new Scene(Main.loadFXML("newproduction"), 800, 600);
                Main.getPrimaryStage().setScene(newProduction);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (button == editProductionButton) {
            Production selectedProduction = (Production) productionListview.getSelectionModel().getSelectedItem();
            if (selectedProduction == null) {
                return;
            }

            try {
                newProduction = new Scene(Main.loadFXML("newproduction"), 800, 600);
                Main.getPrimaryStage().setScene(newProduction);
                NewProductionController.getLatestProductionController().loadProduction(selectedProduction);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (button == nextButton) {
            pageNumber++;
            // Get next objects
            getNextList();

        } else if (button == previousButton) {
            pageNumber--;
            getNextList();

        } else if (button == searchButton) {
            System.out.println(searchField.getText());
            searchString = searchField.getText();
            pageNumber = 1;
            getNextList();

        }
    }

    private void getNextList() {
        if (radioButtonProduction.isSelected()) {
            abstractList = FXCollections.observableArrayList(
                    catalog.searchInDB(true, searchString, pageNumber, pageSize));
        } else {
            abstractList = FXCollections.observableArrayList(
                    catalog.searchInDB(false, searchString, pageNumber, pageSize));
        }
        productionListview.setItems(abstractList);
        previousButton.setDisable(pageNumber <= 1);
        nextButton.setDisable(abstractList.size() < pageSize);
    }

    public static Scene getNewProduction() {
        return newProduction;
    }

    public void handleMouseClick(MouseEvent mouseEvent) {
        if (radioButtonProduction.isSelected()) {
            Production selectedProduction = (Production) productionListview.getSelectionModel().getSelectedItem();
            if (selectedProduction == null) {
                return;
            }
            displayArea.setText(selectedProduction.detailedString());
        } else {
            Contributor selectedContributor = (Contributor) productionListview.getSelectionModel().getSelectedItem();
            if (selectedContributor == null) {
                return;
            }
            displayArea.setText(selectedContributor.detailedString());
        }

    }

    public void onMouseClicked(MouseEvent mouseEvent) {
        getNextList();
    }

    //TEMPORARY METHOD
    public void dummyProductions() {
        for (int i = 0; i < 50; i++) {
            ArrayList<Organization> organizationList = new ArrayList<>();
            ArrayList<Contributor> contributorList1 = new ArrayList<>();
            ArrayList<Contributor> contributorList2 = new ArrayList<>();
            ArrayList<Contributor> contributorList3 = new ArrayList<>();
            ArrayList<Credit> creditList = new ArrayList<>();

            //Contributing organizations
            Organization org1 = new Organization("Medvirkende organisation "+ i + ".1");
            Organization org2 = new Organization("Medvirkende organisation "+ i + ".2");
            Organization org3 = new Organization("Medvirkende organisation "+ i + ".3");
            org1.store();
            org2.store();
            org3.store();
            organizationList.add(org1);
            organizationList.add(org2);
            organizationList.add(org3);

            //Producer
            Organization producer = new Organization("Producer " + i);
            producer.store();

            //Contributors "09.04.1996"
            Contributor con1;
            Contributor con2;
            Contributor con3;
            Contributor con4;
            Contributor con5;

            int birthYear = 1950 + i;

            con1 = new Contributor("Medvirkende " + i + ".1", "01.01." + birthYear);
            con2 = new Contributor("Medvirkende " + i + ".2", "01.01." + birthYear);
            con3 = new Contributor("Medvirkende " + i + ".3", "01.01." + birthYear);
            con4 = new Contributor("Medvirkende " + i + ".4", "01.01." + birthYear);
            con5 = new Contributor("Medvirkende " + i + ".5", "01.01." + birthYear);

            con1.store();
            con2.store();
            con3.store();
            con4.store();
            con5.store();

            contributorList1.add(con1);
            contributorList2.add(con2);
            contributorList2.add(con3);
            contributorList3.add(con4);
            contributorList3.add(con5);

            //Credits
            creditList.add(new Credit("Rolle " + i + ".1", contributorList1));
            creditList.add(new Credit("Rolle " + i + ".2", contributorList2));
            creditList.add(new Credit("Rolle " + i + ".3", contributorList3));

            int releaseDate = 1970 + i;

            Production production = new Production("Film " + i, producer,
                    "01.01." + releaseDate, i * 5, organizationList, creditList);
            production.store();
        }
    }

    public void updateDummies() {
        ArrayList<Organization> orgList = new ArrayList<>();
        Organization tv2 = new Organization("TV2");
        tv2.store();
        Organization producer = new Organization("Real Company");
        producer.store();

        orgList.add(tv2);

        ArrayList<Contributor> contList = new ArrayList<>();
        Contributor bob = new Contributor("Mette Jensen", "09.04.1996");
        Contributor niels = new Contributor("Niels Sørensen", "11.12.1990");
        Contributor bolette = new Contributor("Bolette Kristiansen", "01.09.1967");
        contList.add(bob);
        contList.add(niels);
        contList.add(bolette);
        bob.store();
        niels.store();
        bolette.store();

        ArrayList<Credit> credList = new ArrayList<>();
        credList.add(new Credit("Lyd", contList));

        Production testProduction = new Production("Some Film1", producer,
                "09.09.2007",  20, orgList, credList);
        testProduction.setId(4);
        testProduction.store();
    }

}