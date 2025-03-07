package org.example.jdbcjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.jdbcjavafx.db.DbException;
import org.example.jdbcjavafx.entities.Department;
import org.example.jdbcjavafx.exceptions.ValidationException;
import org.example.jdbcjavafx.services.DepartmentService;
import org.example.jdbcjavafx.util.Alerts;
import org.example.jdbcjavafx.util.Constraints;
import org.example.jdbcjavafx.util.Utils;

import java.net.URL;
import java.util.*;

public class DepartmentFormController implements Initializable {


    private DepartmentService departmentService;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();


    private Department department;

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
        if (department == null) {
            throw new IllegalStateException("Department is null");
        }
        if (departmentService == null) {
            throw new IllegalStateException("Department service is null");
        }
        try {
            department = getFormData();
            departmentService.saveOrUpdate(department);
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

    private Department getFormData() {
        Department department = new Department();

        ValidationException exception = new ValidationException("Validation error");

        department.setId(Utils.tryParseToInt(txtId.getText()));

        if (txtName.getText() == null) {
            exception.addError("name", "Field cant be empty");
        }

        if (!exception.getErrors().isEmpty()) {
            throw exception;
        }

        department.setName(txtName.getText());

        return department;
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

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    public void updateFormData() {
        if (this.department == null) {
            throw new IllegalStateException("Department is null");
        }

        txtId.setText(String.valueOf(department.getId()));
        txtName.setText(department.getName());
    }

    private void setErrorMessage(Map<String, String> error) {
        Set<String> keys = error.keySet();
        if (keys.contains("name")) {
            lblErroName.setText(error.get("name"));
        }
    }

}
