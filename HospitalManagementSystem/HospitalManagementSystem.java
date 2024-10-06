package HospitalManagementSystem;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url="jdbc:mysql://127.0.0.1:3306/hospital";
    private static final String username="root";
    private static final String password="toor";

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("success");
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {

            Connection connection= DriverManager.getConnection(url,username,password);
            System.out.println("successfully connected");
            Patient patient=new Patient(connection,scanner);
            Doctor doctor=new Doctor(connection);
            while (true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. exit");

                System.out.println("Enter your choice");
                int choice=scanner.nextInt();
                switch (choice){
                    case 1:
                        //add patient
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        //view patient
                        patient.viewPatient();
                        System.out.println();
                        break;
                    case 3:
                        //view doctors
                        doctor.viewDoctor();
                        System.out.println();
                        break;
                    case 4:
                        //book appointment
                        bookAppointement(patient,doctor,connection,scanner);
                        System.out.println();
                        break;
                    case 5:
                        //exit
                        return;
                    default:
                        System.out.println("Enter right choice");
                        break;
                }
            }

        }

        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void bookAppointement(Patient patient,Doctor doctor,Connection connection,Scanner scanner){
        System.out.println("Enter Patient Id: ");
        int patientId=scanner.nextInt();
        System.out.println("Enter doctor Id: ");
        int doctorId=scanner.nextInt();
        System.out.println("Enter appointment date (YYYY-MM-DD): ");
        String appointmentDate=scanner.next();
        if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)){
            if(checkDoctorAvailability(doctorId,appointmentDate, connection)){
                String appointmentQuery="INSERT INTO appointment(patient_id,doctor_id,appointment_date) VALUES(?,?,?)";
                try {
                    PreparedStatement preparedStatement=connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1,patientId);
                    preparedStatement.setInt(2,doctorId);
                    preparedStatement.setString(1,appointmentDate);
                    int rowsAffected=preparedStatement.executeUpdate();
                    if (rowsAffected>0){
                        System.out.println("Appointment Booked");
                    }else {
                        System.out.println("Not Booked");
                    }

                }catch (SQLException e){
                    e.printStackTrace();
                }

            }else {
                System.out.println("Doctor not available on this date   ");
            }

        }else{
            System.out.println("Either doctor or patient doesn't exist!! ");
        }
    }
    public static boolean checkDoctorAvailability(int doctorId, String appointmentDate,Connection connection){
        String query="SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,doctorId);
            preparedStatement.setString(2,appointmentDate);
            ResultSet resultSet=preparedStatement.executeQuery();
            if (resultSet.next()){
                int count=resultSet.getInt(1);
                if (count==0){
                    return true;
                }else {
                    return false;
                }
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
