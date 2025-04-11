/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;

/**
 *
 * @author hp
 */
public class EntreeStock {
    private int idEntree;
    private Produit produit;
    private int quantite;
    private Date dateReception;
    private String numeroFacture;
    
    public EntreeStock() {}
    
    public EntreeStock(int idEntree, Produit produit, int quantite, Date dateReception, String numeroFacture) {
        this.idEntree = idEntree;
        this.produit = produit;
        this.quantite = quantite;
        this.dateReception = dateReception;
        this.numeroFacture = numeroFacture;
    }
    
    // Getters et Setters
    public int getIdEntree() {
        return idEntree;
    }
    
    public void setIdEntree(int idEntree) {
        this.idEntree = idEntree;
    }
    
    public Produit getProduit() {
        return produit;
    }
    
    public void setProduit(Produit produit) {
        this.produit = produit;
    }
    
    public int getQuantite() {
        return quantite;
    }
    
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    
    public Date getDateReception() {
        return dateReception;
    }
    
    public void setDateReception(Date dateReception) {
        this.dateReception = dateReception;
    }
    
    public String getNumeroFacture() {
        return numeroFacture;
    }
    
    public void setNumeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
    }
}
