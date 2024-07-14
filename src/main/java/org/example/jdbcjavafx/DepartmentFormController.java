package org.example.jdbcjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.jdbcjavafx.entities.Department;
import org.example.jdbcjavafx.util.Constraints;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentFormController implements Initializable {


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

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        System.out.println("onBtSaveAction");
    }

    @FXML
    public void onBtCancellAction(ActionEvent event) {
        System.out.println("onBtCancellAction");
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

    public void updateFormData() {
        if (this.department == null) {
            throw new IllegalStateException("Department is null");
        }

        txtId.setText(String.valueOf(department.getId()));
        txtName.setText(department.getName());
    }
}
