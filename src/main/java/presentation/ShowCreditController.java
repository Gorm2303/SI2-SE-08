package presentation;

import domain.ICatalog;
import domain.Storable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ShowCreditController {
    @FXML
    private ListView<Storable> objectListview;
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

    @FXML
    public void initialize() {
        pageNumber = 1;
        catalog = ICatalog.getInstance();
        getNextList();

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
            Storable selectedProduction = objectListview.getSelectionModel().getSelectedItem();
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
        ObservableList<Storable> searchResultList;
        if (radioButtonProduction.isSelected()) {
            searchResultList = FXCollections.observableArrayList(
                    catalog.searchForProductions( searchString, pageNumber, pageSize));
        } else if (radioButtonContributor.isSelected()){
            searchResultList = FXCollections.observableArrayList(
                    catalog.searchForContributors(searchString, pageNumber, pageSize));
        } else {
            //Here goes organization stuff
            searchResultList = FXCollections.observableArrayList();
        }
        objectListview.setItems(searchResultList);
        previousButton.setDisable(pageNumber <= 1);
        nextButton.setDisable(searchResultList.size() < pageSize);
    }

    public static Scene getNewProduction() {
        return newProduction;
    }

    public void handleListViewMouseClick(MouseEvent mouseEvent) {
        if (radioButtonProduction.isSelected()) {
            Storable selectedProduction = objectListview.getSelectionModel().getSelectedItem();
            if (selectedProduction == null) {
                return;
            }
            displayArea.setText(selectedProduction.detailedString());
            editProductionButton.setDisable(false);
        } else if (radioButtonContributor.isSelected()) {
            Storable selectedContributor = objectListview.getSelectionModel().getSelectedItem();
            if (selectedContributor == null) {
                return;
            }
            displayArea.setText(selectedContributor.detailedString());
            editProductionButton.setDisable(true);
        }
    }

    public void handleRadioButtonMouseClick(MouseEvent mouseEvent) {
        getNextList();
        editProductionButton.setDisable(true);
    }

}