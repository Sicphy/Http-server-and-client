package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import javafx.event.ActionEvent;

import java.io.IOException;

public class DeleteController {
    @FXML
    private Button sendDeleteRequestButton;
    @FXML
    private Label deleteLabel;
    @FXML
    private TextField deleteTextField;
    @FXML
    private Button getBackButton;
    @FXML
    private Label getBackLabel;

    private DeleteMethod deleteMethod;

    public void setDeleteMethod(DeleteMethod deleteMethod) {
        this.deleteMethod = deleteMethod;
    }

    @FXML
    private void sendDeleteRequestAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        int responseCode;
        deleteMethod.setFileName(deleteTextField.getText());
        try {
            responseCode = deleteMethod.sendRequest();
            alert.setTitle("Http Client");
            if(responseCode == 200) {
                alert.setHeaderText(null);
                alert.setContentText("File successfully deleted !");
                alert.showAndWait();
            } else if(responseCode == 404) {
                alert.setHeaderText("Error");
                alert.setContentText("File doesn't exist !");
                alert.showAndWait();
            } else if(responseCode == 500) {
                alert.setHeaderText("Error");
                alert.setContentText("File doesn't delete ! Some server problems.");
                alert.showAndWait();
            }
        } catch (BadFileNameException e) {
            alert.setHeaderText("Error");
            alert.setContentText("Bad file name !");
            alert.showAndWait();
        }
    }

    @FXML
    private void getBackAction(ActionEvent event) {
        startWindowOpen();
    }

    @FXML
    private void startWindowOpen() {
        try {
            Stage primaryStage = (Stage)deleteLabel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/sample.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Http Client");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
