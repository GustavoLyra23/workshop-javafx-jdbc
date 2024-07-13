package org.example.jdbcjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import services.DepartmentService;
import util.Alerts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainviewController implements Initializable {

    @FXML
    private MenuItem menuItemSeller;

    @FXML
    private MenuItem menuItemDepartment;

    @FXML
    private MenuItem menuItemAbout;

    @FXML
    public void onMenuItemSellerAction(ActionEvent event) {
        System.out.println("onMenuItemSellerAction");

    }

    @FXML
    public void onMenuItemDepartmentAction(ActionEvent event) {
        loadView2("departmentlist.fxml");

    }

    private void loadView2(String absoluteName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox vbox = fxmlLoader.load();
            Scene mainScene = HelloApplication.getMainScene();
            VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            Node mainMenu = mainVbox.getChildren().get(0);
            mainVbox.getChildren().clear();
            mainVbox.getChildren().add(mainMenu);
            mainVbox.getChildren().addAll(vbox.getChildren());

            DepartmentListController departmentListController = fxmlLoader.getController();
            departmentListController.setService(new DepartmentService());
            departmentListController.updateTableView();


        } catch (IOException e) {
            Alerts.showAlert("IOException", "Error Loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onMenuItemAboutAction(ActionEvent event) {
        loadView("aboutview.fxml");

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private synchronized void loadView(String absoluteName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox vbox = fxmlLoader.load();
            Scene mainScene = HelloApplication.getMainScene();
            VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            Node mainMenu = mainVbox.getChildren().get(0);
            mainVbox.getChildren().clear();
            mainVbox.getChildren().add(mainMenu);
            mainVbox.getChildren().addAll(vbox.getChildren());


        } catch (IOException e) {
            Alerts.showAlert("IOException", "Error Loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
