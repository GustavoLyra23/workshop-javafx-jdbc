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
        if (keys.contains("name")) {
            lblErroName.setText(error.get("name"));
        }
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
