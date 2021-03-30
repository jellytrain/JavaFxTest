package LoginApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbUnit.dbConnection;

public class LoginModel {
    Connection connection;

    public LoginModel(){
        try{
            this.connection = dbConnection.getConnection();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        if(this.connection == null){
            System.exit(1);
        }
    }

    public boolean isDataBaseConnected(){
        return this.connection != null;
    }

    public boolean isLogin(String user,String password,String opt) throws Exception{

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM login where username = ? and password = ? and division = ?";

        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1,user);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,opt);

            resultSet = preparedStatement.executeQuery();

            boolean boll1;

            if(resultSet.next()) {
                return true;
            }
            return false;
        }catch (SQLException ex){
            return false;
        }

        finally {
            preparedStatement.close();
            resultSet.close();
        }
    }
}
