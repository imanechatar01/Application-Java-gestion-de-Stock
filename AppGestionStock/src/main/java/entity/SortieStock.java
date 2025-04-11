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
public class SortieStock {
    private int idSortie;
    private Produit produit;
    private int quantite;
    private Date dateSortie;
    private String destination;

    public SortieStock(int idSortie, Produit produit, int quantite, Date dateSortie, String destination) {
        this.idSortie = idSortie;
        this.produit = produit;
        this.quantite = quantite;
        this.dateSortie = dateSortie;
        this.destination = destination;
    }

    public SortieStock() {
        
    }

    public int getIdSortie() {
        return idSortie;
    }

    public void setIdSortie(int idSortie) {
        this.idSortie = idSortie;
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

    public Date getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(Date dateSortie) {
        this.dateSortie = dateSortie;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    
}
