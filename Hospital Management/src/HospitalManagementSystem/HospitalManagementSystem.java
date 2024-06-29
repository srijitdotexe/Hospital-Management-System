package HospitalManagementSystem;

import java.sql.*;
import java.util.*;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String uname = "root";
    private static final String pwd = "9907";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        try{
            Connection con = DriverManager.getConnection(url,uname,pwd);
            Patient p = new Patient(con,sc);
            Doctor d = new Doctor(con);
            while (true)
            {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1.Add Patient");
                System.out.println("2.View Patient");
                System.out.println("3.View Doctor");
                System.out.println("4.Book Appointment");
                System.out.println("5.Exit");
                System.out.println("Enter your choice: ");
                int ch = sc.nextInt();
                switch(ch)
                {
                    case 1:
                        p.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        p.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        d.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        bookAppointment(p,d,con,sc);
                        System.out.println();
                        break;
                    case 5:
                        System.out.println("Thank you for using our system. Bye!");
                        return;
                    default:
                        System.out.println("Enter valid choice!!");
                }


            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient p, Doctor d, Connection con, Scanner sc)
    {
        System.out.println("Enter Patient ID: ");
        int pid = sc.nextInt();
        System.out.println("Enter Doctor ID: ");
        int did = sc.nextInt();
        System.out.println("Enter Appointment Date (YYYY-MM-DD): ");
        String date = sc.next();
        if (p.getPatient(pid) && d.getDoctor(did))
        {
            if(isAvailable(did, date, con))
            {
                String query = "INSERT INTO appointments(pid,did,appointment_date) VALUES (?,?,?)";
                try{
                    PreparedStatement pstmt = con.prepareStatement(query);
                    pstmt.setInt(1,pid);
                    pstmt.setInt(2,did);
                    pstmt.setString(3,date);
                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected>0)
                        System.out.println("Appointment Booked!!");
                    else
                        System.out.println("Failed to Book Appointment! Try again!");
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
            else
                System.out.println("Doctor not Available on this date!!");
        }
        else
            System.out.println("Either Doctor or Patient doesn't exist!!");
    }

    public static boolean isAvailable(int did, String date, Connection con)
    {
        String query = "SELECT COUNT(*) FROM appointments WHERE did=? and appointment_date=?";
        try{
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1,did);
            pstmt.setString(2, date);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
            {
                int count = rs.getInt(1);
                if (count == 0)
                    return true;
                else
                    return false;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

}
