package org.example.jdbcjavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.example.jdbcjavafx.db.DbException;
import org.example.jdbcjavafx.entities.Department;
import org.example.jdbcjavafx.entities.Seller;
import org.example.jdbcjavafx.exceptions.ValidationException;
import org.example.jdbcjavafx.services.DepartmentService;
import org.example.jdbcjavafx.services.SellerService;
import org.example.jdbcjavafx.util.Alerts;
import org.example.jdbcjavafx.util.Constraints;
import org.example.jdbcjavafx.util.Utils;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class SellerFormController implements Initializable {


    private DepartmentService departmentService;

    private SellerService sellerService;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();


    private Seller seller;


    @FXML
    private TextField txtEmail;

    @FXML
    private DatePicker dpBirthDate;

    @FXML
    private TextField txtBaseSalary;


    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private ComboBox<Department> comboBoxDepartment;


    @FXML
    private Label lblErroName;

    @FXML
    private Label lblErroEmail;

    @FXML
    private Label lblErroBirthDate;

    @FXML
    private Label lblErroBaseSalary;


    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    private ObservableList<Department> observableList;


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

        if (txtName.getText() == null || txtName.getText().trim().equals("")) {
            exception.addError("name", "Field cant be empty");
        }

        seller.setName(txtName.getText());

        if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
            exception.addError("email", "Field cant be empty");
        }

        seller.setEmail(txtEmail.getText());

        if (dpBirthDate.getValue() == null) {
            exception.addError("birthDate", "Field cant be empty");
        } else {
            Instant instant = Instant.from(dpBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()));
            seller.setBirthDate(Date.from(instant));
        }

        if (txtBaseSalary.getText() == null || txtBaseSalary.getText().trim().equals("")) {
            exception.addError("baseSalary", "Field cant be empty");
        }

        seller.setBaseSalary(Utils.tryParseToDouble(txtBaseSalary.getText()));

         seller.setDepartment(comboBoxDepartment.getValue());



        if (!exception.getErrors().isEmpty()) {
            throw exception;
        }


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
        Constraints.setTextFieldMaxLength(txtName, 70);
        Constraints.setTextFieldDouble(txtBaseSalary);
        Constraints.setTextFieldMaxLength(txtEmail, 60);
        Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
        initializeComboBoxDepartment();
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public void setServices(SellerService sellerService, DepartmentService departmentService) {
        this.sellerService = sellerService;
        this.departmentService = departmentService;
    }


    public void updateFormData() {
        if (this.seller == null) {
            throw new IllegalStateException("Seller is null");
        }

        txtId.setText(String.valueOf(seller.getId()));
        txtName.setText(seller.getName());
        txtEmail.setText(seller.getEmail());
        Locale.setDefault(Locale.US);
        txtBaseSalary.setText(String.format("%.2f", seller.getBaseSalary()));
        if (seller.getBirthDate() != null) {
            dpBirthDate.setValue(LocalDate.ofInstant(seller.getBirthDate().toInstant(), ZoneId.systemDefault()));
        }
        if (seller.getDepartment() == null) {
            comboBoxDepartment.getSelectionModel().selectFirst();
        } else {
            comboBoxDepartment.setValue(seller.getDepartment());
        }
    }

    public void loadAssociatedObjects() {
        if (departmentService == null) {
            throw new IllegalStateException("Department service is null");
        }

        List<Department> departments = departmentService.getAllDepartments();
        observableList = FXCollections.observableArrayList(departments);
        comboBoxDepartment.setItems(observableList);
    }


    private void setErrorMessage(Map<String, String> error) {
        Set<String> keys = error.keySet();

        lblErroName.setText(keys.contains("name") ? error.get("name") : "");
        lblErroEmail.setText(keys.contains("email") ? error.get("email") : "");
        lblErroName.setText(keys.contains("birthDate") ? error.get("birthDate") : "");
        lblErroName.setText(keys.contains("baseSalary") ? error.get("baseSalary") : "");


    }

    private void initializeComboBoxDepartment() {
        Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
            @Override
            protected void updateItem(Department item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };

        comboBoxDepartment.setCellFactory(factory);
        comboBoxDepartment.setButtonCell(factory.call(null));
    }


}
