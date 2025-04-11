/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import dataBase.DB;
import exceptions.InvalidePrixException;
import java.sql.*;
import java.util.ArrayList;


/**
 *
 * @author hp
 */
public class ProduitDAO {
    private Produit p;
    private ArrayList<Produit> list;
    
    
    public boolean checkProduit(String code){
        try {
            PreparedStatement ps = DB.getInstance().prepareStatement("select * from produit where code =?");
             ps.setString(1,code);
             ResultSet rs = ps.executeQuery();
             if(rs.next())
                 return true;
             return false;
        } catch (Exception e) {
            return false;
        }
        
        
    }

    
    
    
    public boolean addProduit(String c, String n, float p, int idCat) throws InvalidePrixException {
        if (p <= 0) {  // Validation ajoutée
        throw new InvalidePrixException("Le prix doit être strictement positif");
    }
        try {
            // Vérifier d'abord que le produit n'existe pas déjà
            if (checkProduit(c)) {
                System.out.println("Le produit avec le code " + c + " existe déjà");
                return false;
            }
            
            System.out.println("Tentative d'ajout du produit: " + c);
            PreparedStatement ps = DB.getInstance().prepareStatement("insert into produit (code, nomP, prix, categorie) values(?,?,?,?)");
            ps.setString(1, c);
            ps.setString(2, n);
            ps.setFloat(3, p);
            ps.setInt(4, idCat);
            
            int result = ps.executeUpdate();
            System.out.println("Résultat de l'exécution: " + result);
            
            if(result > 0)
                return true;
            return false;
        } catch (Exception e) {
            System.out.println("Erreur dans addProduit: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateProduit(String codeActuel, String code, String n, float p, int idCat) throws InvalidePrixException {
        try {
            System.out.println("Tentative de mise à jour du produit: " + codeActuel);
            // La requête SQL contient une erreur dans l'index des paramètres
            PreparedStatement ps = DB.getInstance().prepareStatement("update produit set code=?, nomP=?, prix=?, categorie=? where code=?");
            ps.setString(1, code);
            ps.setString(2, n);
            ps.setFloat(3, p);
            ps.setInt(4, idCat);
            ps.setString(5, codeActuel); // Correction: index 5 au lieu de 6
            
            int result = ps.executeUpdate();
            System.out.println("Résultat de la mise à jour: " + result);
            
            if(result > 0)
                return true;
            return false;
        } catch (Exception e) {
            System.out.println("Erreur dans updateProduit: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteProduit(String code) {
        try {
            System.out.println("Tentative de suppression du produit: " + code);
            PreparedStatement ps = DB.getInstance().prepareStatement("delete from produit where code=?");
            ps.setString(1, code);
            
            int result = ps.executeUpdate();
            System.out.println("Résultat de la suppression: " + result);
            
            if(result > 0)
                return true;
            return false;
        } catch (Exception e) {
            System.out.println("Erreur dans deleteProduit: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    

public ArrayList<Produit> getProduit(String code){
    try {
        if(code ==null)
              return null;
         PreparedStatement ps = DB.getInstance().prepareStatement("select * from produit p inner join categorie c on p.categorie = c.id   where p.code =?");
            ps.setString(1, code);
          ResultSet rs = ps.executeQuery();
          list = new ArrayList();
          while(rs.next()){
               p = new Produit(rs.getString("code"),rs.getString("nomP"),rs.getFloat("prix"),rs.getString("nomC"));
               list.add(p);
          }
          
          return list;
    } catch (Exception e) {
        System.out.println(e.getMessage());
        return null;
    }
}
public ArrayList<Produit> getAllProduit(){
    try {
         Statement ps = DB.getInstance().createStatement();
          
          ResultSet rs = ps.executeQuery("select * from produit p inner join categorie c on p.categorie = c.id  ");
          list = new ArrayList();
          while(rs.next()){
               p = new Produit(rs.getString("code"),rs.getString("nomP"),rs.getFloat("prix"),rs.getString("nomC"));
               list.add(p);
          }
          return list;
    } catch (Exception e) {
        System.out.println(e.getMessage());
        return null;
    }
}

public Produit findByCode(String code){
    Produit pro;

    try {
        PreparedStatement ps = DB.getInstance().prepareStatement("select p.* ,c.nomC from produit p inner join categorie c on c.id= p.categorie  where p.code =?");
        ps.setString(1, code);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
         pro= new Produit(rs.getString("code"),rs.getString("nomP"),rs.getFloat("prix"),rs.getString("nomC"));

        return pro;
        }
        return null;
    } catch (InvalidePrixException | SQLException e) {
        
        
        return null;
    }
}
//total des produit
public int totalProduit(){
    try {
        int total=0 ;
        Statement st = DB.getInstance().createStatement();
        ResultSet rs = st.executeQuery("select e.quantite - s.quantite as totalProduit from entree_stock e inner join sortie_stock s on e.code_produit =s.code_produit ;");
        
        
        while(rs.next()){
            total +=  rs.getInt(1);
         }
        
        return total;
    } catch (Exception e) {
        return -1;
    }
}

//recuperer la quantite de chaque produit

public int getTotalByCode(String code){
     try {
        int total=-1 ;
        PreparedStatement st = DB.getInstance().prepareStatement("select e.quantite - s.quantite as totalProduit from entree_stock e inner join sortie_stock s on e.code_produit =s.code_produit where e.code_produit =? ;");
        st.setString(1, code);
        ResultSet rs = st.executeQuery();
        
        
        if(rs.next()){
            total =  rs.getInt(1);
         }
        
        return total;
    } catch (Exception e) {
         System.out.println(e.getMessage());
        return -1;
    }
    
}
//recuperer la valeur totel des prosuit
public int valeurTotal(){
    try {
        int total=0 ;
        Statement st = DB.getInstance().createStatement();
        ResultSet rs = st.executeQuery("select (e.quantite - s.quantite)*p.prix as valeurTotal from entree_stock e inner join sortie_stock s on e.code_produit =s.code_produit inner join produit p on e.code_produit = p.code ");
        
        
        
        while(rs.next()){
            total +=  rs.getInt(1);
         }
        
        return total;
    } catch (Exception e) {
        System.out.println(e.getMessage());
        return -1;
    }
}


//verifier un soritie de stock d'un produit
public boolean isSortieStockExist(String code){
    try{
        PreparedStatement ps = DB.getInstance().prepareStatement("select * from sortie_stock where code_produit =? ");
        ps.setString(1, code);
        ResultSet rs =ps.executeQuery();
       return rs.next();
    }
    catch(Exception e){return false;}
    
}

//verifier un entree de stock d'un produit
public boolean isEntreeStockExist(String code){
    try{
        PreparedStatement ps = DB.getInstance().prepareStatement("select * from entree_stock where code_produit =? ");
        ps.setString(1, code);
        ResultSet rs =ps.executeQuery();
       return rs.next();
    }
    catch(Exception e){return false;}
    
}

//recupere la quantite de soritie de stock d'un produit
public int getQuatiteFromS(String code){
     try{
        int quantite =0;
        PreparedStatement ps = DB.getInstance().prepareStatement("select quantite from sortie_stock where code_produit =? ");
        ps.setString(1, code);
        ResultSet rs =ps.executeQuery();
       while(rs.next()){
           quantite += rs.getInt(1);
       }
       return quantite;
    }
    catch(Exception e){
        System.out.println(e.getMessage());
        return 0;}
    
}

//recupere la quantite de entree de stock d'un produit
public int getQuatiteFromE(String code){
    try{
        int quantite =0;
        PreparedStatement ps = DB.getInstance().prepareStatement("select quantite from entree_stock where code_produit =? ");
        ps.setString(1, code);
        ResultSet rs =ps.executeQuery();
       while(rs.next()){
           quantite += rs.getInt(1);
       }
       return quantite;
    }
    catch(Exception e){return 0;}
    
}

public static void main(String[]args){
    ProduitDAO p=new ProduitDAO();
    System.out.println(p.getTotalByCode("P_001"));
     System.out.println(p.getAllProduit());
     System.out.println(p.getProduit("P_001"));
     System.out.println(p.valeurTotal());
      System.out.println(p.getQuatiteFromS("P_243"));

}
}