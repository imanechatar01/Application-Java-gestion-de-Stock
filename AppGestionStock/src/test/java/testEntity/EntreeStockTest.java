package com.testEntity;

import entity.EntreeStock;
import entity.Produit;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;
import static org.junit.Assert.*;

public class EntreeStockTest {
    
    private EntreeStock entreeStock;
    private Produit produit;
    private Date dateReception;
    private String numeroFacture;
    
    @Before
    public void setUp() {
        // Initialiser un produit pour les tests
        produit = new Produit();
        produit.setCode("P001");
        produit.setNomP("Test Produit");
        produit.setPrix(100);
        
        
        // Initialiser les autres données
        dateReception = new Date();
        numeroFacture = "FACT-123";
        
        // Initialiser l'objet EntreeStock
        entreeStock = new EntreeStock();
    }
    
    @Test
    public void testConstructeurVide() {
        EntreeStock instance = new EntreeStock();
        assertNotNull("Le constructeur vide devrait créer une instance non nulle", instance);
    }
    
    @Test
    public void testConstructeurAvecParametres() {
        EntreeStock instance = new EntreeStock(1, produit, 10, dateReception, numeroFacture);
        
        assertEquals("L'ID devrait être initialisé correctement", 1, instance.getIdEntree());
        assertEquals("Le produit devrait être initialisé correctement", produit, instance.getProduit());
        assertEquals("La quantité devrait être initialisée correctement", 10, instance.getQuantite());
        assertEquals("La date de réception devrait être initialisée correctement", dateReception, instance.getDateReception());
        assertEquals("Le numéro de facture devrait être initialisé correctement", numeroFacture, instance.getNumeroFacture());
    }
    
    @Test
    public void testSetAndGetIdEntree() {
        int idEntree = 1;
        entreeStock.setIdEntree(idEntree);
        assertEquals("L'ID devrait être modifié correctement", idEntree, entreeStock.getIdEntree());
    }
    
    @Test
    public void testSetAndGetProduit() {
        entreeStock.setProduit(produit);
        assertEquals("Le produit devrait être modifié correctement", produit, entreeStock.getProduit());
    }
    
    @Test
    public void testSetAndGetQuantite() {
        int quantite = 15;
        entreeStock.setQuantite(quantite);
        assertEquals("La quantité devrait être modifiée correctement", quantite, entreeStock.getQuantite());
    }
    
    @Test
    public void testSetAndGetDateReception() {
        entreeStock.setDateReception(dateReception);
        assertEquals("La date de réception devrait être modifiée correctement", dateReception, entreeStock.getDateReception());
    }
    
    @Test
    public void testSetAndGetNumeroFacture() {
        entreeStock.setNumeroFacture(numeroFacture);
        assertEquals("Le numéro de facture devrait être modifié correctement", numeroFacture, entreeStock.getNumeroFacture());
    }
    
    @Test
    public void testChampNonInitialises() {
        EntreeStock instance = new EntreeStock();
        
        assertEquals("L'ID par défaut devrait être 0", 0, instance.getIdEntree());
        assertNull("Le produit par défaut devrait être null", instance.getProduit());
        assertEquals("La quantité par défaut devrait être 0", 0, instance.getQuantite());
        assertNull("La date de réception par défaut devrait être null", instance.getDateReception());
        assertNull("Le numéro de facture par défaut devrait être null", instance.getNumeroFacture());
    }
}