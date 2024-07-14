package org.example.jdbcjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.jdbcjavafx.db.DbException;
import org.example.jdbcjavafx.entities.Seller;
import org.example.jdbcjavafx.exceptions.ValidationException;
import org.example.jdbcjavafx.services.SellerService;
import org.example.jdbcjavafx.util.Alerts;
import org.example.jdbcjavafx.util.Constraints;
import org.example.jdbcjavafx.util.Utils;

import java.net.URL;
import java.util.*;

public class SellerFormController implements Initializable {


    private SellerService sellerService;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();


    private Seller seller;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private Label lblErroName;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;


    public void subscribeDataChengeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        if (seller == null) {
            throw new IllegalStateException("Seller is null");
        }
        if (sellerService == null) {
            throw new IllegalStateException("Seller service is null");
        }
        try {
            seller = getFormData();
            sellerService.saveOrUpdate(seller);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        } catch (DbException ex) {
            Alerts.showAlert("Error saving object", null, ex.getMessage(), Alert.AlertType.ERROR);
        } catch (ValidationException ex) {
            setErrorMessage(ex.getErrors());
        }
    }

    private void notifyDataChangeListeners() {
        dataChangeListeners.forEach(listener -> listener.onDataChanged());

    }

    private Seller getFormData() {
        Seller seller = new Seller();

        ValidationException exception = new ValidationException("Validation error");

        seller.setId(Utils.tryParseToInt(txtId.getText()));

        if (txtName.getText() == null) {
            exception.addError("name", "Field cant be empty");
        }

        if (!exception.getErrors().isEmpty()) {
            throw exception;
        }

        seller.setName(txtName.getText());

        return seller;
    }

    @FXML
    public void onBtCancellAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }


    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }


    public void updateFormData() {
        if (this.seller == null) {
            throw new IllegalStateException("Seller is null");
        }

        txtId.setText(String.valueOf(seller.getId()));
        txtName.setText(seller.getName());
    }

    private void setErrorMessage(Map<String, String> error) {
        Set<String> keys = error.keySet();
        if (keys.contains("name")) {
            lblErroName.setText(error.get("name"));
        }
    }

}
