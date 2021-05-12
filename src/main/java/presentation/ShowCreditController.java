package presentation;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ShowCreditController {
    @FXML
    private ListView<Production> productionListview;
    @FXML
    private TextArea displayArea;
    @FXML
    private TextField searchField;
    @FXML
    private Button editProductionButton, addProductionButton, searchButton, nextButton, previousButton;
    private static Scene newProduction;

    private int pageNumber;
    private static ICatalog catalog;
    private ObservableList<Production> productionObservableList;

    @FXML
    public void initialize() {
        pageNumber = 1;
        catalog = ICatalog.getInstance();
        //dummyProductions();
        productionObservableList = FXCollections.observableArrayList(catalog.getNext10Productions(pageNumber));
        productionListview.setItems(productionObservableList);
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
            Production selectedProduction = productionListview.getSelectionModel().getSelectedItem();
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
            previousButton.setDisable(false);
            pageNumber++;
            System.out.println(pageNumber);
            productionObservableList = FXCollections.observableArrayList(catalog.getNext10Productions(pageNumber));
            productionListview.setItems(productionObservableList);
        } else if (button == previousButton) {
            if (pageNumber <= 2) {
                previousButton.setDisable(true);
            }
            pageNumber--;
            System.out.println(pageNumber);
            productionObservableList = FXCollections.observableArrayList(catalog.getNext10Productions(pageNumber));
            productionListview.setItems(productionObservableList);
        }
    }

    public static Scene getNewProduction() {
        return newProduction;
    }

    //TEMPORARY METHOD
    public void dummyProductions() {
        ArrayList<Organization> orgList = new ArrayList<>();
        Organization tv2 = new Organization();
        tv2.setName("TV2");
        tv2.store();
        Organization fakeCompany = new Organization();
        fakeCompany.setName("Fake Company");
        fakeCompany.store();
        Organization producer = new Organization();
        producer.setName("Producing Company");
        producer.store();

        orgList.add(tv2);
        orgList.add(fakeCompany);

        ArrayList<Contributor> contList = new ArrayList<>();
        Contributor bob = new Contributor("Bob Jensen", "09.04.1996");
        Contributor niels = new Contributor("Niels Sørensen", "11.12.1990");
        Contributor bolette = new Contributor("Bolette Kristiansen", "01.09.1967");
        contList.add(bob);
        contList.add(niels);
        contList.add(bolette);
        bob.store();
        niels.store();
        bolette.store();

        ArrayList<Credit> credList = new ArrayList<>();
        credList.add(new Credit("Kamera", contList));

        Production testProduction = new Production("Some Film", producer, "09.09.2009", "some genre", 120, orgList, credList);

        testProduction.store();
    }

    public void handleMouseClick(MouseEvent mouseEvent) {
        Production selectedProduction = productionListview.getSelectionModel().getSelectedItem();
        if (selectedProduction == null) {
            return;
        }
        displayArea.setText(selectedProduction.detailedString());
    }
}