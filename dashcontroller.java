package com.example.hrmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class dashcontroller {

    public Label password;
    public Label username;
    public Label errorMessage;

    public void adminclick(ActionEvent actionEvent) {
        try {
            Parent secondScene = FXMLLoader.load(getClass().getResource("admin-view.fxml"));

            Stage secondStage = new Stage();
            secondStage.setTitle("login");
            secondStage.setScene(new Scene(secondScene));
            Stage firstSceneStage = (Stage)password.getScene().getWindow();

            firstSceneStage.close();


            secondStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void exitclick(ActionEvent actionEvent) {
        try {
            Parent secondScene = FXMLLoader.load(getClass().getResource("exit-view.fxml"));

            Stage secondStage = new Stage();
            secondStage.setTitle("exit");
            secondStage.setScene(new Scene(secondScene));
            Stage firstSceneStage = (Stage)password.getScene().getWindow();

            firstSceneStage.close();


            secondStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void employeeclick(ActionEvent actionEvent) {
        try {
            Parent secondScene = FXMLLoader.load(getClass().getResource("empol-view.fxml"));

            Stage secondStage = new Stage();
            secondStage.setTitle("empol");
            secondStage.setScene(new Scene(secondScene));
            Stage firstSceneStage = (Stage)password.getScene().getWindow();

            firstSceneStage.close();


            secondStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
}

    public void logoutclick(ActionEvent actionEvent) {
        try {
            Parent secondScene = FXMLLoader.load(getClass().getResource("hello-view.fxml"));

            Stage secondStage = new Stage();
            secondStage.setTitle("login");
            secondStage.setScene(new Scene(secondScene));
            Stage firstSceneStage = (Stage)password.getScene().getWindow();

            firstSceneStage.close();


            secondStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

}

    public void login(ActionEvent actionEvent) {
    }
}