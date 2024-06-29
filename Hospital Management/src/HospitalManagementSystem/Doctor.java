package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {

    private Connection con;

    public Doctor(Connection con){
        this.con = con;

    }

    public void viewDoctors()
    {
        String query = "Select * from doctors";
        try
        {
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Doctors: \n");
            System.out.println("+-------+---------------------------+-------------------+");
            System.out.println("| id    | name                      | specialization    |");
            System.out.println("+-------+---------------------------+-------------------+");
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");
                System.out.printf("|%-7s|%-27s|%-19s|\n",id,name,specialization);
                System.out.println("+-------+---------------------------+-------------------+");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public boolean getDoctor(int id)
    {
        String query = "Select * from doctors where id = ?";
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
