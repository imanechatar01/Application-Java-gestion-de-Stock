package controllers;

import entity.EntreeStock;
import entity.Produit;
import entity.EntreeStockDAO;
import entity.ProduitDAO;
import entity.SortieStock;
import entity.SortieStockDAO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * Contrôleur pour gérer les entrées de stock
 */
public class StockController {
    private ProduitDAO produitDAO;
    private EntreeStockDAO entreeStockDAO;
    private SortieStockDAO sortieStockDAO;
    
    private static StockController instance;
    
    public StockController() {
        produitDAO = new ProduitDAO();
        entreeStockDAO = new EntreeStockDAO();
        sortieStockDAO = new SortieStockDAO();
    }
    

    public Produit getProduitByCode(String code) throws Exception {
        return produitDAO.findByCode(code);
    }
    
 
  
    /**
     * Récupère toutes les entrées de stock
     * @return Liste de toutes les entrées de stock
     * @throws Exception En cas d'erreur d'accès à la base de données
     */
    public ArrayList<EntreeStock> getAllEntreesStock() throws Exception {
        return entreeStockDAO.findAll();
    }
    
    /**
     * Récupère les entrées de stock récentes
     
     * @return Liste des entrées de stock récentes
     * @throws Exception En cas d'erreur d'accès à la base de données
     */
    public ArrayList<EntreeStock> getRecentEntreesStock() throws Exception {
        return entreeStockDAO.findRecent();
    }
    
    /**
     * Récupère une entrée de stock par son ID
     * @param id ID de l'entrée à rechercher
     * @return L'entrée de stock trouvée ou null si non trouvée
     * @throws Exception En cas d'erreur d'accès à la base de données
     */
    public EntreeStock getEntreeStockById(int id) throws Exception {
        return entreeStockDAO.findById(id);
    }
    
    /**
     * Enregistre ou met à jour une entrée de stock
     * @param entreeStock L'entrée de stock à sauvegarder
     * @return true si l'opération a réussi
     * @throws Exception En cas d'erreur d'accès à la base de données
     */
    public boolean saveEntreeStock(EntreeStock entreeStock) throws Exception {
        if (entreeStock.getIdEntree() == 0) {
            return entreeStockDAO.save(entreeStock);
        } else {
            return entreeStockDAO.update(entreeStock);
        }
    }
    
    /**
     * Supprime une entrée de stock
     * @param id ID de l'entrée à supprimer
     * @return true si l'opération a réussi
     * @throws Exception En cas d'erreur d'accès à la base de données
     */
    public boolean deleteEntreeStock(int id) throws Exception {
        return entreeStockDAO.delete(id);
    }
    
    /**
     * Crée une nouvelle entrée de stock
     * @param produit Le produit concerné
     * @param quantite La quantité entrée
     * @param dateReception La date de réception
     * @param numeroFacture Le numéro de facture
     * @return L'entrée de stock créée
     * @throws Exception En cas d'erreur d'accès à la base de données
     */
    public void createEntreeStock(Produit produit, int quantite, Date dateReception, String numeroFacture) throws Exception {
        EntreeStock entreeStock = new EntreeStock();
        entreeStock.setProduit(produit);
        entreeStock.setQuantite(quantite);
        entreeStock.setDateReception(dateReception);
        entreeStock.setNumeroFacture(numeroFacture);
        
      if( entreeStockDAO.save(entreeStock)){
          JOptionPane.showMessageDialog(null, "ajouter avec succee");
             return;
      }
       JOptionPane.showMessageDialog(null, "ajout echouwe");
    }
    
    /**
     * Formate une date en chaîne de caractères
     * @param date Date à formater
     * @return La date formatée (dd/MM/yyyy)
     */
    public String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
    
    /**
     * Convertit une chaîne de caractères en date
     * @param dateStr Chaîne à convertir (format dd/MM/yyyy)
     * @return La date résultante
     * @throws Exception En cas d'erreur de parsing
     */
    public Date parseDate(String dateStr) throws Exception {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.parse(dateStr);
    }
    
    /**
     * Vérifie si les données d'entrée sont valides
     * @param produit Produit
     * @param quantite Quantité
     * @param dateReception Date de réception
     * @param numeroFacture Numéro de facture
     * @return Message d'erreur ou null si toutes les données sont valides
     */
    public String validateEntreeStock(Produit produit, int quantite, Date dateReception, String numeroFacture) {
        if (produit == null) {
            return "Veuillez sélectionner un produit";
        }
        
        if (quantite <= 0) {
            return "La quantité doit être supérieure à zéro";
        }
        
        if (dateReception == null) {
            return "Veuillez entrer une date de réception valide";
        }
        
        if (numeroFacture == null || numeroFacture.trim().isEmpty()) {
            return "Veuillez entrer un numéro de facture";
        }
        
        return null; // Pas d'erreur
    }
    
    
    //Gestion de Sortie des Stock
    
    //insertion une sortie d'un stock
    public void saveSS(Produit p, int quantite , Date ds, String dest ){
        SortieStock ss = new SortieStock();
        ss.setProduit(p);
        ss.setQuantite(quantite);
        ss.setDateSortie(ds);
        ss.setDestination(dest);
        if(sortieStockDAO.saveSS(ss)){
            JOptionPane.showMessageDialog(null, "sortie du stock inserer avec succee");
            return;
        }
        JOptionPane.showMessageDialog(null, "insertion de sortie du stock a echoué");
    }
    
    
    //recuperer les sortie de stock
     public ArrayList<SortieStock> getAllSortieStock() throws Exception {
        return sortieStockDAO.findAll();
    }
    
}