package com.testEntity;

import controllers.ProduitController;
import entity.Produit;
import entity.ProduitDAO;
import exceptions.InvalidePrixException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.After;

public class ProduitControllerTest {
    
    private ProduitController produitController;
    private ProduitDAO pdao;
    
    private final String testCode = "TEST123";
    private final String testNom = "Produit Test";
    private final float testPrix = 99.99f;
    private final int testCatId = 66;
    private final String codeInexistant = "CODE_INEXISTANT";

    @Before
    public void setUp() {
        produitController = new ProduitController();
        pdao = new ProduitDAO();
        // Nettoyage initial
        pdao.deleteProduit(testCode);
    }
    
    @After
    public void tearDown() {
        // Nettoyage final
        pdao.deleteProduit(testCode);
    }

    // Cas nominal - Ajout réussi
    @Test
    public void testAddNewProduit() throws InvalidePrixException {
        produitController.addNewProduit(testCode, testNom, testPrix, testCatId);
        assertTrue(pdao.checkProduit(testCode));
    }

    // Cas erreur - Produit déjà existant
    @Test
    public void testAddNewProduit_AlreadyExists() throws InvalidePrixException {
        // Premier ajout
        pdao.addProduit(testCode, testNom, testPrix, testCatId);
        // Deuxième ajout
        produitController.addNewProduit(testCode, "Nouveau nom", 50.0f, testCatId);
        // Vérifie que le produit original n'a pas changé
        Produit p = pdao.findByCode(testCode);
        assertEquals(testNom, p.getNomP());
    }

    // Cas erreur - Prix invalide
    @Test(expected = InvalidePrixException.class)
    public void testAddNewProduit_InvalidPrice() throws InvalidePrixException {
        produitController.addNewProduit(testCode, testNom, -10.0f, testCatId);
    }

    // Cas nominal - Modification réussie
    @Test
    public void testUpdateProduit_Success() throws InvalidePrixException {
        // Ajout initial
        pdao.addProduit(testCode, testNom, testPrix, testCatId);
        // Modification
        String nouveauNom = "Produit Modifié";
        produitController.updateProduit(testCode, testCode, nouveauNom, 150.0f, testCatId);
        // Vérification
        Produit p = pdao.findByCode(testCode);
        assertEquals(nouveauNom, p.getNomP());
    }

    // Cas erreur - Produit inexistant
    @Test
    public void testUpdateProduit_NotExists() throws InvalidePrixException {
        boolean result = pdao.updateProduit(codeInexistant, codeInexistant, testNom, testPrix, testCatId);
        assertFalse(result);
    }

    // Cas nominal - Récupération avec résultats
    @Test
    public void testGetAllProducts_WithResults() throws InvalidePrixException {
        pdao.addProduit(testCode, testNom, testPrix, testCatId);
        ArrayList<Produit> result = produitController.getAllProducts();
        assertFalse(result.isEmpty());
    }

    // Cas limite - Aucun produit
    @Test
public void testGetAllProducts_Empty() {
    // 1. Vider la base de données des produits de test
    for (Produit p : pdao.getAllProduit()) {
        pdao.deleteProduit(p.getCode());
    }
    
    // 2. Tester avec une base vide
    ArrayList<Produit> result = produitController.getAllProducts();
    
    // 3. Vérification selon le comportement attendu
    if (result == null) {
        // Comportement attendu: retourne null
        assertNull("Devrait retourner null pour une base vide", result);
    } else {
        // Comportement attendu: retourne liste vide
        assertTrue("Devrait retourner une liste vide", result.isEmpty());
    }
}

    // Cas nominal - Suppression réussie
    @Test
    public void testDeleteProduit_Success() throws InvalidePrixException {
        pdao.addProduit(testCode, testNom, testPrix, testCatId);
        produitController.deleteProduit(testCode);
        assertFalse(pdao.checkProduit(testCode));
    }

    // Cas erreur - Suppression produit inexistant
    @Test
    public void testDeleteProduit_NotExists() {
        produitController.deleteProduit(codeInexistant);
        // Devrait échuter silencieusement
    }

    // Cas nominal - Calcul valeur totale
    @Test
    public void testTotaleValue() throws InvalidePrixException {
        pdao.addProduit(testCode, testNom, testPrix, testCatId);
        int total = produitController.totaleValue();
        assertTrue(total >= 0);
    }

    // Cas nominal - Quantité avec entrées/sorties
    @Test
    public void testQuantiteProduit_WithStock() throws InvalidePrixException {
        pdao.addProduit(testCode, testNom, testPrix, testCatId);
        int quantite = produitController.quantiteProduit(testCode);
        assertTrue(quantite >= 0);
    }

    // Cas limite - Aucun stock
    @Test
    public void testQuantiteProduit_NoStock() {
        int quantite = produitController.quantiteProduit(codeInexistant);
        assertEquals(0, quantite);
    }

    // Test des messages utilisateur (optionnel)
    @Test
    public void testUserFeedback() throws InvalidePrixException {
        // Teste que les messages sont bien affichés
        // Nécessiterait un mock de JOptionPane
    }
}