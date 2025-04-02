package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient() {
        System.out.print("Enter Patient Name:   ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Enter Patient Age:    ");
        int age = scanner.nextInt();
        System.out.print("Enter Patient Gender:");
        scanner.nextLine();
        String gender = scanner.nextLine();
        try {
            String query = "insert into patients(name,age,gender) values(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient Added Successfully!!");
            } else {
                System.out.println("Failed to add Patient!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void viewPatients(){
        String query="select * from patients";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("Patients:   ");
            System.out.println("+------------+-----------------------+---------------+----------------+");
            System.out.println("| Patient Id | Name                  | Age           | Gender         |");
            System.out.println("+------------+-----------------------+---------------+----------------+");
            while(resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");
                int age=resultSet.getInt("age");
                String gender=resultSet.getString("gender");
                System.out.printf("| %-11s| %-22s| %-14s| %-15s|\n",id,name,age,gender);
                System.out.println("+------------+-----------------------+---------------+----------------+");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public boolean getPatientById(int id){
        String query="select * from patients where id=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
