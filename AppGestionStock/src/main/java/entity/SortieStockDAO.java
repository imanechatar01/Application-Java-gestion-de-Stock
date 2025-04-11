/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import dataBase.DB;
import exceptions.InvalidePrixException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class SortieStockDAO {
    private SortieStock ss;
    private ProduitDAO produitDAO= new ProduitDAO();
    
    
    //methode pour inserer une sortie de stock
    
    public boolean saveSS(SortieStock ss){
        try{
            PreparedStatement ps = DB.getInstance().prepareStatement("insert into sortie_stock (code_produit, quantite, date_sortie, destination) values(?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, ss.getProduit().getCode());
            ps.setInt(2, ss.getQuantite());
           ps.setDate(3, new java.sql.Date(ss.getDateSortie().getTime()));
           ps.setString(4, ss.getDestination());
           ps.executeUpdate();
           
           ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                ss.setIdSortie(generatedKeys.getInt(1));
            }
           return true;
            
            
        }catch(Exception ex){
            return false;
        }
    }
      public ArrayList<SortieStock> findAll() throws Exception {
        ArrayList<SortieStock> entrees = new ArrayList<>();
        String sql = "SELECT * FROM sortie_stock ORDER BY date_sortie DESC";
        
          Statement st = DB.getInstance().createStatement();
          
             ResultSet rs = st.executeQuery(sql) ;
            
            while (rs.next()) {
                SortieStock sortieStock = new SortieStock();
                sortieStock.setIdSortie(rs.getInt("id_sortie"));
                
                Produit produit = produitDAO.findByCode(rs.getString("code_produit"));
                sortieStock.setProduit(produit);
                
                sortieStock.setQuantite(rs.getInt("quantite"));
                sortieStock.setDateSortie(rs.getDate("date_sortie"));
                sortieStock.setDestination(rs.getString("destination"));
                
                entrees.add(sortieStock);
            }
        
        return entrees;
    }
 public static void main(String []args) throws Exception{
     SortieStockDAO dao = new SortieStockDAO();
     System.out.println(dao.findAll());
 }
}
