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
import java.util.function.Consumer;

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
        loadView("departmentlist.fxml", (DepartmentListController controller) -> {
            controller.setService(new DepartmentService());
            controller.updateTableView();
        });

    }


    @FXML
    public void onMenuItemAboutAction(ActionEvent event) {
        loadView("aboutview.fxml", x -> {
        });

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private synchronized <T> void loadView(String absoluteName, Consumer<T> action) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox vbox = fxmlLoader.load();
            Scene mainScene = HelloApplication.getMainScene();
            VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            Node mainMenu = mainVbox.getChildren().get(0);
            mainVbox.getChildren().clear();
            mainVbox.getChildren().add(mainMenu);
            mainVbox.getChildren().addAll(vbox.getChildren());

            T controller = fxmlLoader.getController();
            action.accept(controller);


        } catch (IOException e) {
            Alerts.showAlert("IOException", "Error Loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
