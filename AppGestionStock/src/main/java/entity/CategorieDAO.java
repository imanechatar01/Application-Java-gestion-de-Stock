/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import dataBase.DB;
import java.sql.*;

import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class CategorieDAO {
    private Categorie cat;
    private ArrayList<Categorie> list = new ArrayList();
    
    public boolean addCategorie(String nomCat , String code) throws SQLException{
        PreparedStatement ps =DB.getInstance().prepareStatement("insert into categorie (nomC, code) values(?, ?)");
        ps.setString(1,nomCat);
          ps.setString(2,code);
        if(ps.executeUpdate()>0)
             return true;
        return false;
       
    }
    public ArrayList<Categorie> getCategorie(){
        try {
            Statement st = DB.getInstance().createStatement();
            ResultSet rs = st.executeQuery("select * from categorie");
            while(rs.next()){
                cat = new Categorie(rs.getInt("id"), rs.getString("nomC"),rs.getString("code"));
                list.add(cat);
            }
            return list;
        
            
        } catch (Exception e) {
            return null;
        }
    }
    //methode pour supprimer une categorie
    
    public boolean deleteCat(String nomCat){
        try {
            PreparedStatement ps=DB.getInstance().prepareStatement("delete from categorie where nomC = ?");
            ps.setString(1,nomCat);
            int i = ps.executeUpdate();
            if(i!=0)
                return true;
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    //recuperer une categorie par nom
    public boolean getCategorie(String nomC){
        try {
            PreparedStatement ps = DB.getInstance().prepareStatement("select * from categorie where nomC =?");
            ps.setString(1, nomC);
            return ps.executeQuery().next();
        } catch (Exception e) {
            return false;
        }
    }
    
   public static void main(String []argrs) throws SQLException{
       CategorieDAO dao = new CategorieDAO();
     
   }
}

