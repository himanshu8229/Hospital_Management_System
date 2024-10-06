package HospitalManagementSystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;
    public Patient(Connection connection, Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }
    public void addPatient(){
        scanner.nextLine();
        System.out.println("Enter patient name: ");
        String name=scanner.nextLine();

        System.out.println("Enter patient age: ");
        int age=scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the gender: ");
        String gender=scanner.nextLine();


        try{
            String query="INSERT INTO patient(name,age,gender) VALUES(?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);
            int affectedRows=preparedStatement.executeUpdate();
            if (affectedRows>0){
                System.out.println("Data inserted successfully");
            }else {
                System.out.println("Data added unsuccessfully");
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void viewPatient(){
        String query="SELECT * FROM patient";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");
                int age=resultSet.getInt("age");
                String gender=resultSet.getString("gender");
                System.out.printf(String.valueOf(id),name,age,gender);
//                System.out.println(id);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();

        }
    }
    public boolean getPatientById(int id){
        String query="select * from patient where id=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("patients: ");
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
