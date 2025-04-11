/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import entity.CategorieDAO;
import entity.Produit;
import entity.ProduitDAO;
import exceptions.InvalidePrixException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author hp
 */
public class ProduitController {
    private ProduitDAO pdao = new ProduitDAO();
    private CategorieDAO catdao = new CategorieDAO();
 
    
       //  methode d'ajout d'un nouveau  produit 
    public void addNewProduit(String c , String n, float p ,int idCat)throws InvalidePrixException{
        if(pdao.checkProduit(c)){
             JOptionPane.showMessageDialog(null, "Produit déja existe !");
             return;
        }
        if(pdao.addProduit(c, n, p,  idCat)){
                JOptionPane.showMessageDialog(null, "Produit ajouté avec succé !");
                return;
        }
        JOptionPane.showMessageDialog(null, "Ajout echoué !");
            
        }
        
      //  methode de modification d'un produit  
     public void updateProduit(String currentCode, String c , String n, float p ,int idCat)throws InvalidePrixException{
         //appele de methode de modification d'un produit
     try{
         if(pdao.updateProduit(currentCode, c, n, p, idCat)){
             
                JOptionPane.showMessageDialog(null, "Produit modifier avec succé !");
            return;}
           JOptionPane.showMessageDialog(null, "Modification a  echoué !");
     }
        catch(Exception ex){
   
         System.out.println(ex.getMessage());
            
        }
     }
     
     public ArrayList<Produit> getAllProducts(){
         if(pdao.getAllProduit()!=null)
                 return pdao.getAllProduit();
        
         return null;
     }
    
     public static void main(String[]args){
           try {
               ProduitController produit = new ProduitController();
              System.out.println(produit.getAllProducts());
         } catch (Exception e) {
               System.out.println(e.getMessage());
         }
        
     }
     public void deleteProduit(String code){
         if(pdao.deleteProduit(code)){
             JOptionPane.showMessageDialog(null, "Produit été supprimer avec succée");
             return;
         }
         JOptionPane.showMessageDialog(null, "Suppression de produit a echoué");
     }
     
    
      //la valeur totale des produits
     public int totaleValue(){
         return pdao.valeurTotal();
             
     }
    
     
     //recupere la quantite d'un produit
     
     public int quantiteProduit(String code){
         if(pdao.isEntreeStockExist(code)&&pdao.isSortieStockExist(code))
             return pdao.getQuatiteFromE(code)-pdao.getQuatiteFromS(code);
         if(pdao.isEntreeStockExist(code))
             return pdao.getQuatiteFromE(code);
         return 0;
     }
    }

