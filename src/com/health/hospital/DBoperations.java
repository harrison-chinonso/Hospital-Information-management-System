/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.health.hospital;

/**
 *
 * @author chinex
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DBoperations {
    private Connection con;
    private PreparedStatement pt;
    private ResultSet rs;
    private String userDb = "root";
    private String passwordDb = "";
    private String urlDb = "jdbc:mysql://localhost:3306/form_db";
    
    public DBoperations()
    {
        try {
            con = DriverManager.getConnection(urlDb, userDb, passwordDb);
            
        } catch (SQLException ex) {
            Logger.getLogger(DBoperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Map login(String un, String pwd)
    {
        try {
            String command = "SELECT * FROM staff_tb WHERE username=? AND password = ?";
            pt = con.prepareStatement(command);
            pt.setString(1, un);
            pt.setString(2, pwd);
            
            rs = pt.executeQuery();
            
            if(rs.next())
            {
                Map <String, String> userInfo = new HashMap<>();
                userInfo.put("firstName", rs.getString("firstname"));
                userInfo.put("lastName", rs.getString("lastname"));
                userInfo.put("username", rs.getString("username"));
                userInfo.put("Role", rs.getString("Role"));
                return userInfo;
            }
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DBoperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void Personalcardsave (String cardNo, String ph, String addr, String occp, 
            String date, String mStatus, int age, String rel, String nation, 
            String st, String kinName, String kinAddr, String kinPh, String kinRelation)
            
    {
        
        try {
            String command = "INSERT INTO patient_card_tb (card_type,card_no, phone_no, address, occupation, date_of_reg_start, marital_status, age, religion, nationality, state, kin_name, kin_relationship, kin_phone_no, kin_address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
            pt = con.prepareStatement(command);
            pt.setString(1, "PERSONAL CARD");
            pt.setString(2, cardNo);
            pt.setString(3, ph);
            pt.setString(4, addr);
            pt.setString(5, occp);
            pt.setString(6, date);
            pt.setString(7, mStatus);
            pt.setInt(8, age);
            pt.setString(9, rel);
            pt.setString(10, nation);
            pt.setString(11, st);
            pt.setString(12, kinName);
            pt.setString(13, kinRelation);
            pt.setString(14, kinPh);
            pt.setString(15, kinAddr);
            
            int row = pt.executeUpdate();
            
            if(row>0)
            {
                JOptionPane.showMessageDialog(null, "SUCCESS");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "fAILURE");
            }          
        } catch (SQLException ex) {
            Logger.getLogger(DBoperations.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }

   public void HMOcardsave(String cardNo, String ph, String addr, String occp, String Sdate, String Edate, String mStatus, int age, String rel, String nation, String st, 
                           String kinName, String kinAddr, String kinPh, String kinRelation, String EnID, String Hplan, String Hname, String Cname) 
              {
      try {
            String command = "INSERT INTO patient_card_tb (card_type,card_no, phone_no, address, occupation, date_of_reg_start,date_of_reg_end, marital_status, age, religion, nationality, state, kin_name, kin_relationship, kin_phone_no, kin_address,Enrol_id,hmo_plan,hmo_name,company_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
            pt = con.prepareStatement(command);
            pt.setString(1, "HMO CARD");
            pt.setString(2, cardNo);
            pt.setString(3, ph);
            pt.setString(4, addr);
            pt.setString(5, occp);
            pt.setString(6, Sdate);
            pt.setString(7, Edate);
            pt.setString(8, mStatus);
            pt.setInt(9, age);
            pt.setString(10, rel);
            pt.setString(11, nation);
            pt.setString(12, st);
            pt.setString(13, kinName);
            pt.setString(14, kinRelation);
            pt.setString(15, kinAddr);
            pt.setString(16, kinPh);
            pt.setString(17, EnID);
            pt.setString(18, Hplan);
            pt.setString(19, Hname);
            pt.setString(20, Cname);
                       
            int row = pt.executeUpdate();
            
            if(row>0)
            {
                JOptionPane.showMessageDialog(null, "SUCCESS");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "fAILURE");
            }          
        } catch (SQLException ex) {
            Logger.getLogger(DBoperations.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
   public boolean cardValid(String cardNo)
   {
         try {
             String command = "(SELECT * FROM patient_card_tb WHERE card_no = ?)";
             
             pt = con.prepareStatement(command);
             pt.setString(1, cardNo);
             rs = pt.executeQuery();
             
             if (rs.next())
             {
                 return true;
             }
         
         } catch (SQLException ex) {
             Logger.getLogger(DBoperations.class.getName()).log(Level.SEVERE, null, ex);        
         }
       return false; 
   }

    public int getCardNoCount(String cardNo) 
    {
         try {
             String command = "(SELECT card_id AS num FROM patient_treatment_record_tb WHERE card_no = ?)";
             
             pt = con.prepareStatement(command);
             pt.setString(1, cardNo);
             rs = pt.executeQuery();
             rs.last();

             String num = rs.getString("num");
             int sign = num.indexOf("_");
             String num1 = num.substring(sign+1);
             int count = Integer.parseInt(num1);
             return count;
             
         } catch (SQLException ex) {
             Logger.getLogger(DBoperations.class.getName()).log(Level.SEVERE, null, ex);
         }
         return 0;
    }
    public String saveOngoingTreatmentRecord (String cardId,String cardNo, String fullname, String startDate, String dobStr, String patSym)
    {
         try {
             String command = "INSERT INTO patient_treatment_record_tb(card_id, card_no, full_name, start_date, dob, symptom) VALUES (?, ?, ?, ?, ?, ?)";
             pt = con.prepareStatement(command);
             pt.setString(1, cardId);
             pt.setString(2, cardNo);
             pt.setString(3, fullname);
             pt.setString(4, startDate);
             pt.setString(5, dobStr);
             pt.setString(6, patSym);
             
              int row = pt.executeUpdate();
         if (row>0)
         {
             return "success";
         }
         } catch (SQLException ex) {
             Logger.getLogger(DBoperations.class.getName()).log(Level.SEVERE, null, ex);
         }
        return "FAILURE";
    }

     public HashMap<String, String[]> getAllTreatmentRecordByCardNo(String cardNo) 
     {
         try {
            
             HashMap<String, String[]> objMap = new HashMap<>();
             String command = "SELECT card_id, full_name, start_date, end_date, status FROM patient_treatment_record_tb WHERE card_no=?";
             pt = con.prepareStatement(command);
             pt.setString(1, cardNo);
             rs = pt.executeQuery();
             int count = 0;
             while (rs.next())
             {
                 String dataArray[] = new String [6];
                 dataArray[0] = rs.getString("card_id");
                 dataArray[1] = rs.getString("full_name");
                 dataArray[2] = rs.getString("start_date");
                 dataArray[3] = rs.getString("end_date");
                 dataArray[4] = rs.getString("status");
                
                 String countStr = String.valueOf(count);
                 objMap.put(countStr, dataArray);
                 
                 count++;
             }
             return objMap;
         } catch (SQLException ex) {
             Logger.getLogger(DBoperations.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null; 
     }
public void 
ANTENATALcardsave(String cardNo, String ph, String addr, String occp, String Sdate, String mStatus, int age, String rel,
  String nation, String st,String sName, String sAddr, String sPhone, String sOccu, String sNation, String sReligion, 
  String contact1Name,String contact1phone, String contact1relation, String contact2Name, String contact2phone,String contact2relation)
    
   {
         try {
            String command = "INSERT INTO patient_card_tb(card_type,card_no,phone_no,address,occupation,date_of_reg_start,marital_status, age, religion, nationality, state, spouseName,spouseAddr, spousePhone, spouseOccu, spouseNationality,spouseReligion,contact1name,contact1phone,contact1relation,contact2name,contact2phone,contact2relation) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)";
            pt = con.prepareStatement(command);
            pt.setString(1, "POST/ANTE-NATAL CARD");
            pt.setString(2, cardNo);
            pt.setString(3, ph);
            pt.setString(4, addr);
            pt.setString(5, occp);
            pt.setString(6, Sdate);
            pt.setString(7, mStatus);
            pt.setInt(8, age);
            pt.setString(9,rel );   
            pt.setString(10,nation );
            pt.setString(11,st);
            pt.setString(12, sName);
            pt.setString(13, sAddr);
            pt.setString(14, sPhone);
            pt.setString(15, sOccu);
            pt.setString(16, sNation);
            pt.setString(17, sReligion);
            pt.setString(18, contact1Name);
            pt.setString(19, contact1phone);
            pt.setString(20, contact1relation);
            pt.setString(21, contact2Name);
            pt.setString(22, contact2phone);
            pt.setString(23, contact2relation);
                       
            int row = pt.executeUpdate();
            
            if(row>0)
            {
                JOptionPane.showMessageDialog(null, "CONGRATULATIONS REGISTERED");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "FAILURE");
            }          
        } catch (SQLException ex) {
            Logger.getLogger(DBoperations.class.getName()).log(Level.SEVERE, null, ex);
        }        
   }
}

