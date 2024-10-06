package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;

    public Doctor(Connection connection){
        this.connection=connection;


    }
    public void viewDoctor(){
        String query="SELECT * FROM doctors";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");

                String specialisation=resultSet.getString("specialisation");
                System.out.printf(String.valueOf(id),name,specialisation);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();

        }
    }
    public boolean getDoctorById(int id){
        String query="select * from doctors where id=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("Doctors: ");


            if (resultSet.next()){
                return true;
            }else {
                return false;
            }


        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;

    }



}


