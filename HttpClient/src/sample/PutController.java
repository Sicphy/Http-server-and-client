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
import javafx.event.ActionEvent;

import java.awt.*;

public class PutController {
    @FXML
    private TextField putTextField;
    @FXML
    private Label putLabel;
    @FXML
    private Button openButton;
    @FXML
    private Button sendPutRequestButton;
    @FXML
    private Button getBackButton;
    @FXML
    private Label getBackLabel;

    private PutMethod putMethod;

    public void setPutMethod(PutMethod putMethod) {
        this.putMethod = putMethod;
    }

    @FXML
    private void sendPutRequestAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        int responseCode;
        putMethod.setFilePath(putTextField.getText());
        alert.setTitle("Http Client");
        try {
            responseCode = putMethod.sendRequest();
            if (responseCode == 201) {
                alert.setHeaderText(null);
                alert.setContentText("File successfully created");
                alert.showAndWait();
            } else if (responseCode == 500) {
                alert.setHeaderText("Error");
                alert.setContentText("File doesn't created ! Some server problems.");
                alert.showAndWait();
            }
        } catch (BadFileNameException e) {
            alert.setHeaderText("Error");
            alert.setContentText("Bad file name !");
            alert.showAndWait();
        }
    }

    @FXML
    private void openAction(ActionEvent event) {
        FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        String file = dialog.getDirectory() + dialog.getFile();
        putTextField.setText(file);
    }

    @FXML
    private void getBackAction(ActionEvent event) {
        startWindowOpen();
    }

    @FXML
    private void startWindowOpen() {
        try {
            Stage primaryStage = (Stage)putLabel.getScene().getWindow();
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