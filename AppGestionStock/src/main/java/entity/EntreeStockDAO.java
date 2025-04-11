/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author hp
 */

    

import dataBase.DB;
import entity.EntreeStock;
import entity.Produit;
import entity.ProduitDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
;

public class EntreeStockDAO {

    
    private ProduitDAO produitDAO = new ProduitDAO();
    
  
    public EntreeStock findById(int id) throws Exception {
        EntreeStock entreeStock = null;
        String sql = "SELECT * FROM entree_stock WHERE id_entree = ?";
        PreparedStatement ps =DB.getInstance().prepareStatement(sql);
         ps.setInt(1, id);
         ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                entreeStock = new EntreeStock();
                entreeStock.setIdEntree(rs.getInt("id_entree"));
                
                Produit produit = produitDAO.findByCode(rs.getString("code_produit"));
                entreeStock.setProduit(produit);
                
                entreeStock.setQuantite(rs.getInt("quantite"));
                entreeStock.setDateReception(rs.getDate("date_reception"));
                entreeStock.setNumeroFacture(rs.getString("numero_facture"));
            }
        
        return entreeStock;
    }
    
  
    public ArrayList<EntreeStock> findAll() throws Exception {
        ArrayList<EntreeStock> entrees = new ArrayList<>();
        String sql = "SELECT * FROM entree_stock ORDER BY date_reception DESC";
        
          Statement st = DB.getInstance().createStatement();
          
             ResultSet rs = st.executeQuery(sql) ;
            
            while (rs.next()) {
                EntreeStock entreeStock = new EntreeStock();
                entreeStock.setIdEntree(rs.getInt("id_entree"));
                
                Produit produit = produitDAO.findByCode(rs.getString("code_produit"));
                entreeStock.setProduit(produit);
                
                entreeStock.setQuantite(rs.getInt("quantite"));
                entreeStock.setDateReception(rs.getDate("date_reception"));
                entreeStock.setNumeroFacture(rs.getString("numero_facture"));
                
                entrees.add(entreeStock);
            }
        
        return entrees;
    }
    
    public ArrayList<EntreeStock> findRecent() throws Exception {
        ArrayList<EntreeStock> entrees = new ArrayList<>();
        String sql = "SELECT * FROM entree_stock ORDER BY date_reception DESC ";
        
        PreparedStatement ps = DB.getInstance().prepareStatement(sql);
            
           
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                EntreeStock entreeStock = new EntreeStock();
                entreeStock.setIdEntree(rs.getInt("id_entree"));
                
                Produit produit = produitDAO.findByCode(rs.getString("code_produit"));
                entreeStock.setProduit(produit);
                
                entreeStock.setQuantite(rs.getInt("quantite"));
                entreeStock.setDateReception(rs.getDate("date_reception"));
                entreeStock.setNumeroFacture(rs.getString("numero_facture"));
                
                entrees.add(entreeStock);
            }
        
        return entrees;
    }
    
    
    public boolean save(EntreeStock entreeStock) throws Exception {
        String sql = "INSERT INTO entree_stock (code_produit, quantite, date_reception, numero_facture) VALUES (?, ?, ?, ?)";
        
        PreparedStatement ps= DB.getInstance().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, entreeStock.getProduit().getCode());
            ps.setInt(2, entreeStock.getQuantite());
            ps.setDate(3, new java.sql.Date(entreeStock.getDateReception().getTime()));
            ps.setString(4, entreeStock.getNumeroFacture());
            
            ps.executeUpdate();
            
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                entreeStock.setIdEntree(generatedKeys.getInt(1));
            }
        return true;
    }
    

    public boolean update(EntreeStock entreeStock) throws Exception {
        String sql = "UPDATE entree_stock SET code_produit = ?, quantite = ?, date_reception = ?, numero_facture = ? WHERE id_entree = ?";
        
               
             PreparedStatement stmt = DB.getInstance().prepareStatement(sql);
            
            stmt.setString(1, entreeStock.getProduit().getCode());
            stmt.setInt(2, entreeStock.getQuantite());
            stmt.setDate(3, new java.sql.Date(entreeStock.getDateReception().getTime()));
            stmt.setString(4, entreeStock.getNumeroFacture());
            stmt.setInt(5, entreeStock.getIdEntree());
            
            stmt.executeUpdate();
            return true;
        }

    

    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM entree_stock WHERE id_entree = ?";
        
       
             PreparedStatement stmt = DB.getInstance().prepareStatement(sql); 
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        
     return true;
}
}