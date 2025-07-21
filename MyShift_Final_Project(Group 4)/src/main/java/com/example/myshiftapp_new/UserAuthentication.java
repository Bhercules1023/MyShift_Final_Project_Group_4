package com.example.myshiftapp_new;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserAuthentication {

    public static User verifyEmployee(String username, String password){

        String verifyEmployee = "SELECT ID, username FROM Employees WHERE username = ? AND password = ?";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(verifyEmployee)){
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            if(result.next()){
                int id = result.getInt("ID");
                String user = result.getString("username");
                return new User(id, user);
            }
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User verifyAdmin(String adminUsername, String adminPassword){

        String verifyAdmin = "SELECT ID, adminUsername FROM Management WHERE adminUsername = ? AND adminPassword = ?";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(verifyAdmin)){
            statement.setString(1, adminUsername);
            statement.setString(2, adminPassword);

            ResultSet result = statement.executeQuery();

            if(result.next()){
                int id = result.getInt("ID");
                String user = result.getString("adminUsername");
                return new User(id, user);
            }
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
