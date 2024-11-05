package com.example.hrmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;

public class employeeController {


    @FXML
    private TableView<Employee> tableView;
    @FXML
    private TableColumn<Employee, Integer> employeeIdColumn;
    @FXML
    private TableColumn<Employee, String> nameColumn;
    @FXML
    private TableColumn<Employee, String> positionColumn;
    @FXML
    private TableColumn<Employee, String> departmentColumn;
    @FXML
    private TableColumn<Employee, String> emailColumn;
    @FXML
    private TableColumn<Employee, String> phoneColumn;
    @FXML
    private TableColumn<Employee, String> salaryColumn;

    @FXML
    private TextField employeeid;
    @FXML
    private TextField name;
    @FXML
    private TextField position;
    @FXML
    private TextField department;
    @FXML
    private TextField email;
    @FXML
    private TextField phone;
    @FXML
    private TextField salary;

    private final String jdbcUrl = "jdbc:mysql://localhost:3306/hr_management";
    private final String dbUser = "root";
    private final String dbPassword = "";

    private ObservableList<Employee> employeeList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize table columns with data properties
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));

        tableView.setItems(employeeList);
        //loadEmployeeData(); // Load employee data when the application starts
    }


    // Load data from the database into the TableView
    public void loadEmployeeData() {
        employeeList.clear();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM Employee_detail";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("employee_id");
                String name = resultSet.getString("name");
                String position = resultSet.getString("position");
                String department = resultSet.getString("department");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String salary = resultSet.getString("salary");

                employeeList.add(new Employee(id, name, position, department, email, phone, salary));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert new employee data into the database
    public void insertEmployee(ActionEvent actionEvent) {
        String name = this.name.getText();
        String position = this.position.getText();
        String department = this.department.getText();
        String email = this.email.getText();
        String phone = this.phone.getText();
        Double salary = Double.valueOf(this.salary.getText());

        String Fsalary=String.valueOf(calculateSalary(salary));

        if (name.isEmpty() || position.isEmpty() || department.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            System.out.println("Please fill all the fields.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "INSERT INTO Employee_detail(name, position, department, email, phone, salary) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, position);
            preparedStatement.setString(3, department);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, phone);


            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new employee record was inserted successfully!");
                employeeList.add(new Employee(0, name, position, department, email, phone, salary));  // Set employeeId as needed
                clearFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update existing employee data in the database
    public void updateEmployee(ActionEvent actionEvent) {
        Employee selectedEmployee = tableView.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            System.out.println("Please select an employee record to update.");
            return;
        }

        String name = this.name.getText();
        String position = this.position.getText();
        String department = this.department.getText();
        String email = this.email.getText();
        String phone = this.phone.getText();
        Double salary = Double.valueOf(this.salary.getText());

        String Fsalary=String.valueOf(calculateSalary(salary));

        if (name.isEmpty() || position.isEmpty() || department.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            System.out.println("Please fill all the fields.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "UPDATE Employee_detail SET name = ?, position = ?, department = ?, email = ?, phone = ?, salary = ? WHERE employee_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, position);
            preparedStatement.setString(3, department);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, phone);
            preparedStatement.setString(6, String.valueOf(salary));
            preparedStatement.setInt(7, selectedEmployee.getEmployeeId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Employee record updated successfully!");
                selectedEmployee.setName(name);
                selectedEmployee.setPosition(position);
                selectedEmployee.setDepartment(department);
                selectedEmployee.setEmail(email);
                selectedEmployee.setPhone(phone);
                selectedEmployee.setSalary(String.valueOf(salary));
                tableView.refresh();
                clearFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Clear input fields
    private void clearFields() {
        employeeid.clear();
        name.clear();
        position.clear();
        department.clear();
        email.clear();
        phone.clear();
        salary.clear();
    }

    public void loadEmployee(ActionEvent actionEvent) {// Load data from the database into the TableView
            employeeList.clear();  // Clear the list before loading new data
            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String query = "SELECT * FROM Employee_detail";  // Query to retrieve all employees
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    int id = resultSet.getInt("employee_id");
                    String name = resultSet.getString("name");
                    String position = resultSet.getString("position");
                    String department = resultSet.getString("department");
                    String email = resultSet.getString("email");
                    String phone = resultSet.getString("phone");
                    String salary = resultSet.getString("salary");

                    // Create an Employee object and add it to the list
                    employeeList.add(new Employee(id, name, position, department, email, phone, salary));
                }

                // Refresh the TableView with updated data
                tableView.setItems(employeeList);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    public void deleteEmployee(ActionEvent actionEvent) {

            Employee selectedEmployee = tableView.getSelectionModel().getSelectedItem();
            if (selectedEmployee == null) {
                System.out.println("Please select an employee record to delete.");
                return;
            }

            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String query = "DELETE FROM Employee_detail WHERE employee_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, selectedEmployee.getEmployeeId());

                int rowsDeleted = preparedStatement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Employee record deleted successfully!");
                    employeeList.remove(selectedEmployee);
                    clearFields();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        public Double calculateSalary(Double Salary){
        Double yearly =12*Salary;
        return yearly;
    }
}

