/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.testEntity;
import entity.Produit;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import exceptions.InvalidePrixException;


/**
 *
 * @author hp
 */
public class TestProduit {
    
    


    
    private Produit produit;
    
    @Before
    public void setUp() throws InvalidePrixException {
        // Cette méthode s'exécute avant chaque test
        produit = new Produit("P001", "Ordinateur portable", 1200.0f, "Informatique");
    }
    
    @Test
    public void testCreationProduit() {
        assertEquals("P001", produit.getCode());
        assertEquals("Ordinateur portable", produit.getNomP());
        assertEquals(1200.0f, produit.getPrix(), 0.001);
        assertEquals("Informatique", produit.getCategorie());
    }
    
    @Test
    public void testSettersGetters() {
        // Test des setters
        produit.setCode("P002");
        produit.setNomP("Tablette");
        produit.setPrix(500.0f);
        
        // Vérification avec les getters
        assertEquals("P002", produit.getCode());
        assertEquals("Tablette", produit.getNomP());
        assertEquals(500.0f, produit.getPrix(), 0.001);
    }
    
    @Test(expected = InvalidePrixException.class)
    public void testPrixNegatif() throws InvalidePrixException {
        // Ce test vérifie que l'exception est bien levée pour un prix négatif
        new Produit("P003", "Clavier", -50.0f, "Périphériques");
    }
    
    @Test(expected = InvalidePrixException.class)
    public void testPrixZero() throws InvalidePrixException {
        // Ce test vérifie que l'exception est bien levée pour un prix égal à zéro
        new Produit("P004", "Souris", 0.0f, "Périphériques");
    }
    
    @Test
    public void testToString() {
        // Vérification que toString retourne bien le nom du produit
        assertEquals("Ordinateur portable", produit.toString());
    }
}

    

