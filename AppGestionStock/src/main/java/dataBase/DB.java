/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataBase;

import java.sql.*;
/**
 *
 * @author hp
 */
public class DB {
    private static  DB obj = null;
    private static Connection con = null;

   
    
   private DB(){
       try{
           con = DriverManager.getConnection("jdbc:mysql://localhost/gestionStock","root","");
           
       }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
   public static Connection getInstance(){
       if (obj == null ) obj = new DB();
       return con ;
   }
   
   
}

