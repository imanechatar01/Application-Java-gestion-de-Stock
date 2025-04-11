/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import dataBase.DB;
import java.sql.*;


public class UserDAO {
    private User user;
    
    public UserDAO(){
        
    }
    public boolean searchUser(String email){
        try {
            PreparedStatement ps = DB.getInstance().prepareStatement("select * from user where email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean addUser(String prenom, String nom, String adresse, String tel, String email, String pwd ){
        try {
             String sql ="insert into user values(null, ? ,?, ?, ?, ?, ?) ";
             
        PreparedStatement ps = DB.getInstance().prepareStatement(sql);
        ps.setString(1, nom);
        ps.setString(2, prenom);
        ps.setString(3, email);
        ps.setString(4, pwd);
        ps.setString(5, tel);
        ps.setString(6, adresse);
            int executeUpdate = ps.executeUpdate();
      
        
        
        return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
       
        
    }
     
    public boolean getUser(String email , String pwd){
        try {
            PreparedStatement ps = DB.getInstance().prepareStatement("select * from user where email =? and password = ?");
            ps.setString(1, email);
             ps.setString(2, pwd);
             ResultSet rs= ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            return false;
        }
            
        
    }
        public String getUserName(String email , String pwd){
            
        try {
            PreparedStatement ps = DB.getInstance().prepareStatement("select prenom ,nom  from user where email =? and password = ?");
            ps.setString(1, email);
             ps.setString(2, pwd);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                 String nom = rs.getString("nom");
                 String prenom = rs.getString("prenom");
                 
                 return prenom+" "+nom;
            }else{
                 return "no name";
            }
           
        } catch (Exception e) {
            return "no no name";
        }
            
        
    }
        public static void main(String [] args){
            UserDAO dao = new UserDAO();
            System.out.println(dao.getUserName("test@gmail.com", "test"));
        }
}
