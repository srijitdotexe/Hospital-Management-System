package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Patient {
    private Scanner sc;
    private Connection con;

    public Patient(Connection con, Scanner sc){
        this.con = con;
        this.sc = sc;
    }

    public void addPatient()
    {
        System.out.println("Enter Patient Name: ");
        String name = sc.next();
        System.out.println("Enter Patient Age: ");
        int age = sc.nextInt();
        System.out.println("Enter Patient Gender (Male/Female/Other): ");
        String gender = sc.next();

        try{
            String query = "INSERT INTO patients(name,age, gender) VALUES(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1,name);
            pstmt.setInt(2,age);
            pstmt.setString(3,gender);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0)
                System.out.println("Patient Added Successfully!");
            else
                System.out.println("Failed to add Patient!");


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void viewPatients()
    {
        String query = "Select * from patients";
        try
        {
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Patients: \n");
            System.out.println("+-------+---------------------------+--------+----------+");
            System.out.println("| id    | name                      | age    | gender   |");
            System.out.println("+-------+---------------------------+--------+----------+");
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                System.out.printf("|%-7s|%-27s|%-8s|%-10s|\n",id,name,age,gender);
                System.out.println("+-------+---------------------------+--------+----------+");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public boolean getPatient(int id)
    {
        String query = "Select * from patients where id = ?";
        try{
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1,id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                return true;
            else
                return false;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
