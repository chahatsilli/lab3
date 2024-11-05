package com.example.hrmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class adminController {

    public TextField admin;
    @FXML
    private Label username;
    @FXML
    private Label password;

    @FXML
    private TableView<Admin> tableview;

    @FXML
    private TableColumn<Admin, Integer> admin_id;
    @FXML
    private TableColumn<Admin, String> name;
    @FXML
    private TableColumn<Admin, String> email;
    @FXML
    private TableColumn<Admin, String> pasword;

    @FXML
    private TextField adminIdField;
    @FXML
    private TextField nam;
    @FXML
    private TextField emai;
    @FXML
    private TextField passwor;

    private final String jdbcUrl = "jdbc:mysql://localhost:3306/ hr_management";
    private final String dbUser = "root";
    private final String dbPassword = "";

    private ObservableList<Admin> adminList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up columns with corresponding properties in Admin class
        admin_id.setCellValueFactory(new PropertyValueFactory<>("adminId"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        pasword.setCellValueFactory(new PropertyValueFactory<>("password"));

        tableview.setItems(adminList);
        //SloadAdminData(); // Load data when the application starts
    }

    // Method to load data from the database into the TableView
    public void loadAdminData() {
        adminList.clear();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM Admin";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("admin_id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");

                adminList.add(new Admin(id, name, email, password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to insert data into the database
    public void insertdata(ActionEvent actionEvent) {
        String name = nam.getText();
        String email = emai.getText();
        String password = passwor.getText();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("Please fill all the fields.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "INSERT INTO Admin (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new admin record was inserted successfully!");
                adminList.add(new Admin(0, name, email, password));  // The adminId can be 0 or set as needed
                clearFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update data in the database
    public void updatedata(ActionEvent actionEvent) {
        Admin selectedAdmin = tableview.getSelectionModel().getSelectedItem();
        if (selectedAdmin == null) {
            System.out.println("Please select an admin record to update.");
            return;
        }

        String name = nam.getText();
        String email = emai.getText();
        String password = passwor.getText();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("Please fill all the fields.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "UPDATE Admin SET name = ?, email = ?, password = ? WHERE admin_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setInt(4, selectedAdmin.getAdminId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Admin record updated successfully!");
                selectedAdmin.setName(name);
                selectedAdmin.setEmail(email);
                selectedAdmin.setPassword(password);
                tableview.refresh();
                clearFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete data from the database
    public void deletedata(ActionEvent actionEvent) {
        Admin selectedAdmin = tableview.getSelectionModel().getSelectedItem();
        if (selectedAdmin == null) {
            System.out.println("Please select an admin record to delete.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "DELETE FROM Admin WHERE admin_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, selectedAdmin.getAdminId());

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Admin record deleted successfully!");
                adminList.remove(selectedAdmin);
                clearFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to clear input fields after an operation
    private void clearFields() {
        nam.clear();
        emai.clear();
        passwor.clear();
    }

    // View data handler for refreshing the table view
    public void viewdata(ActionEvent actionEvent) {
        loadAdminData();
    }

    public void backdata(ActionEvent actionEvent) {
        try {
            Parent secondScene = FXMLLoader.load(getClass().getResource("dash-view.fxml"));

            Stage secondStage = new Stage();
            secondStage.setTitle("back");
            secondStage.setScene(new Scene(secondScene));
            Stage firstSceneStage = (Stage)password.getScene().getWindow();

            firstSceneStage.close();


            secondStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
