package admin;

import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import dbUnit.dbConnection;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private TextField id;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField email;
    @FXML
    private DatePicker dob;

    @FXML
    private TableView<studentData> studentTable;
    @FXML
    private TableColumn<studentData,String> idColumn;
    @FXML
    private TableColumn<studentData,String> firstnameColumn;
    @FXML
    private TableColumn<studentData,String> lastnameColumn;
    @FXML
    private TableColumn<studentData,String> emailColumn;
    @FXML
    private TableColumn<studentData,String> dobColumn;

    private dbConnection dc;
    private ObservableList<studentData> data;
    private String sql = "SELECT * FROM students";

    public void initialize(URL url, ResourceBundle resourceBundle){
        this.dc = new dbConnection();
    }

    @FXML
    private void loadStudentData(ActionEvent event) throws SQLException{
        try{
            Connection connection = dbConnection.getConnection();
            this.data = FXCollections.observableArrayList();
            ResultSet resultSet = connection.createStatement().executeQuery(sql);

            while(resultSet.next()){
                this.data.add(new studentData(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)));
            }
        }catch (SQLException exception){
            System.err.println("Error: " + exception);
        }
        this.idColumn.setCellValueFactory(new PropertyValueFactory<studentData,String>("ID"));
        this.firstnameColumn.setCellValueFactory(new PropertyValueFactory<studentData,String>("firstName"));
        this.lastnameColumn.setCellValueFactory(new PropertyValueFactory<studentData,String>("lastName"));
        this.emailColumn.setCellValueFactory(new PropertyValueFactory<studentData,String>("email"));
        this.dobColumn.setCellValueFactory(new PropertyValueFactory<studentData,String>("DOB"));

        this.studentTable.setItems(null);
        this.studentTable.setItems(this.data );
    }

    @FXML
    private void addStudent(ActionEvent event){
        String sqlInsert = "INSERT INTO students(id,fname,lname,email,DOB) VALUES (?,?,?,?,?)";
        try{
            Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1,this.id.getText());
            preparedStatement.setString(2,this.firstname.getText());
            preparedStatement.setString(3,this.lastname.getText());
            preparedStatement.setString(4,this.email.getText());
            preparedStatement.setString(5,this.dob.getEditor().getText());

            preparedStatement.execute();
            preparedStatement.close();

        }catch (SQLException exception){
            System.err.println("Error: " + exception);
        }
    }

    @FXML
    private void clearField(ActionEvent event){
        this.id.setText("");
        this.firstname.setText("");
        this.lastname.setText("");
        this.email.setText("");
        this.dob.setValue(null);

    }
}
