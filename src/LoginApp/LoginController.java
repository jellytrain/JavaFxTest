package LoginApp;

import admin.AdminController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import students.StudentController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    LoginModel loginModel = new LoginModel();

    @FXML
    private Label dbstatus;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<option> comboBox;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginStatus;

    public void initialize(URL url, ResourceBundle resourceBundle){
        if(this.loginModel.isDataBaseConnected()){
            this.dbstatus.setText("Connected to DataBase");
        }else{
            this.dbstatus.setText("Not connected to DataBase");
        }
        this.comboBox.setItems(FXCollections.observableArrayList(option.values()));
    }

    @FXML
    public void Login(ActionEvent event){
        try{

            if(this.loginModel.isLogin(this.username.getText(),this.password.getText(),((option)this.comboBox.getValue()).toString())){
                Stage stage = (Stage) this.loginButton.getScene().getWindow();
                stage.close();
                switch (((option)this.comboBox.getValue()).toString()){
                    case "Admin":
                        adminLogin();
                        break;
                    case "Student":
                        studentLogin();
                        break;
                }
            }else{
                this.loginStatus.setText("Wrong Credentials.");
            }
        }catch (Exception localException){

        }
    }

    public void studentLogin(){
        try{
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/students/studentFXML.fxml").openStream());
            StudentController studentController = (StudentController) loader.getController();

            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.setTitle("Student DashBoard");
            userStage.setResizable(false);
            userStage.show();

        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

    public void adminLogin(){
        try{
            Stage adminStage = new Stage();
            FXMLLoader adminLoader = new FXMLLoader();
            Pane adminRoot = (Pane) adminLoader.load(getClass().getResource("/admin/Admin.fxml").openStream());
            AdminController adminController = (AdminController) adminLoader.getController();

            Scene scene = new Scene(adminRoot);

            adminStage.setScene(scene);
            adminStage.setTitle("Admin DashBoard");
            adminStage.setResizable(false);
            adminStage.show();

        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

}
