/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import entity.Categorie;
import entity.CategorieDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author hp
 */
public class CategorieController {
    
    private CategorieDAO dao = new CategorieDAO();
    
    public ArrayList<Categorie> getGategories(){
        return dao.getCategorie();
    }
    
    public void addNewCategorie(String cat , String code) throws SQLException{
        if(dao.getCategorie(cat)){
             JOptionPane.showMessageDialog(null, "Catégorie déja existe");
             return;
        }
        if(dao.addCategorie(cat, code)){
           JOptionPane.showMessageDialog(null, "Categorie ajouter avec succé");
            return;
        }
            JOptionPane.showMessageDialog(null, "Erreur lord d'ajout la categorie");
    }
    //supprimer une categorie
    
    public void supprimerCat(String nomC){
        if(dao.deleteCat(nomC)){
             JOptionPane.showMessageDialog(null, "Categorie suprimer avec succée avec succé");
            return;}
        JOptionPane.showMessageDialog(null, "Erreur lord de suppression");
    }
    
}
