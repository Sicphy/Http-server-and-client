package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.net.Socket;

public class Controller {
    @FXML
    private Button putButton;
    @FXML
    private Button deleteButton;
    private Socket socket;
    private PutMethod putMethod;
    private DeleteMethod deleteMethod;


    @FXML
    private void deleteAction(ActionEvent event) {
        deleteWindowOpen();
    }

    @FXML
    private void putAction(ActionEvent event) {
        putWindowOpen();
    }


    public void initialize() throws Exception {
        this.putMethod = new PutMethod();
        this.deleteMethod = new DeleteMethod();
    }

    @FXML
    private void deleteWindowOpen() {
        try {
            Stage primaryStage = (Stage)putButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/delete.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            DeleteController deleteController = loader.getController();
            deleteController.setDeleteMethod(this.deleteMethod);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Delete");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void putWindowOpen() {
        try {
            Stage primaryStage = (Stage)putButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/put.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            PutController putController = loader.getController();
            putController.setPutMethod(this.putMethod);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Put");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
