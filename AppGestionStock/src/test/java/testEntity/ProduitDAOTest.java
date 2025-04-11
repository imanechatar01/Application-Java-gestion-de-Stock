/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.testEntity;
import dataBase.DB;
import entity.Produit;
import entity.ProduitDAO;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import exceptions.InvalidePrixException;
import java.sql.Connection;
import java.util.ArrayList;
import org.junit.After;

/**
 *
 * @author hp
 */
public class ProduitDAOTest {
   
    
    
    private ProduitDAO produitDAO;
    private String testCode = "TEST_P001";
    private String testNom = "Produit Test";
    private float testPrix = 99.99f;
    private int testCategorieId = 65;
    
    @Before
    public void setUp() {
        produitDAO = new ProduitDAO();
        // Nettoyer les données de test au cas où
        
    }
    @After
    public void deleteProduit(){
        produitDAO.deleteProduit(testCode);
    }
    // 1. Tests pour la méthode checkProduit
    
  
    
    @Test
    public void testCheckProduitNotExists() {
        // Tester avec un code qui n'existe pas
        boolean exists = produitDAO.checkProduit("CODE_INEXISTANT");
        assertFalse("Le produit ne devrait pas exister", exists);
    }
    
    @Test
    public void testCheckProduitWithNullCode() {
        // Tester avec un code null pour déclencher une exception
        boolean exists = produitDAO.checkProduit(null);
        assertFalse("La méthode devrait gérer le cas null sans erreur", exists);
    }
    
    // 2. Tests pour la méthode addProduit
    
    @Test
    public void testAddProduitSuccess() throws InvalidePrixException {
        boolean result = produitDAO.addProduit(testCode, testNom, testPrix, testCategorieId);
        assertTrue("L'ajout du produit devrait réussir", result);
        
        // Vérifier que le produit a été ajouté
        assertTrue("Le produit devrait exister après ajout", produitDAO.checkProduit(testCode));
    }
    
    @Test
    public void testAddProduitDuplicate() throws InvalidePrixException {
        // Ajouter le produit une première fois
        produitDAO.addProduit(testCode, testNom, testPrix, testCategorieId);
        
        // Essayer d'ajouter le même produit une deuxième fois
        boolean result = produitDAO.addProduit(testCode, testNom, testPrix, testCategorieId);
        assertFalse("L'ajout d'un produit en double devrait échouer", result);
    }
    
    @Test
    public void testAddProduitWithInvalidCategory() throws InvalidePrixException {
        // Tester avec un ID de catégorie qui n'existe pas
        int invalidCategoryId = 9999;
        boolean result = produitDAO.addProduit("NEW_CODE", testNom, testPrix, invalidCategoryId);
        assertFalse("L'ajout d'un produit avec une catégorie invalide devrait échouer", result);
    }
    
    @Test(expected = InvalidePrixException.class)
    public void testAddProduitWithNegativePrice() throws InvalidePrixException {
       
            produitDAO.addProduit("NEG_PRICE", testNom, -10.0f, testCategorieId);
    }
    
    
     
    // 3. Tests pour la méthode updateProduit
    
    @Test
    public void testUpdateProduitSuccess() throws InvalidePrixException {
        // Ajouter d'abord un produit
        produitDAO.addProduit(testCode, testNom, testPrix, testCategorieId);
        
        // Modifier le produit
        String nouveauNom = "Produit Modifié";
        float nouveauPrix = 129.99f;
        boolean result = produitDAO.updateProduit(testCode, testCode, nouveauNom, nouveauPrix, testCategorieId);
        assertTrue("La mise à jour du produit devrait réussir", result);
        
        // Vérifier que le produit a été modifié
        Produit produitModifie = produitDAO.findByCode(testCode);
        assertEquals("Le nom du produit devrait être modifié", nouveauNom, produitModifie.getNomP());
        assertEquals("Le prix du produit devrait être modifié", nouveauPrix, produitModifie.getPrix(), 0.001);
    }
    
    
    
    @Test
    public void testUpdateProduitNonExistent() throws InvalidePrixException {
        // Essayer de modifier un produit qui n'existe pas
        boolean result = produitDAO.updateProduit("CODE_INEXISTANT", "NEW_CODE", testNom, testPrix, testCategorieId);
        assertFalse("La mise à jour d'un produit inexistant devrait échouer", result);
    }
    
    @Test
    public void testUpdateProduitWithInvalidCategory() throws InvalidePrixException {
        // Ajouter d'abord un produit
        produitDAO.addProduit(testCode, testNom, testPrix, testCategorieId);
        
        // Essayer de modifier avec un ID de catégorie invalide
        int invalidCategoryId = 9999;
        boolean result = produitDAO.updateProduit(testCode, testCode, testNom, testPrix, invalidCategoryId);
        assertFalse("La mise à jour avec une catégorie invalide devrait échouer", result);
    }
    
    // 4. Tests pour la méthode deleteProduit
    
    @Test
    public void testDeleteProduitSuccess() throws InvalidePrixException {
        // Ajouter d'abord un produit
        produitDAO.addProduit(testCode, testNom, testPrix, testCategorieId);
        
        // Supprimer le produit
        boolean result = produitDAO.deleteProduit(testCode);
        assertTrue("La suppression du produit devrait réussir", result);
        
        // Vérifier que le produit a été supprimé
        assertFalse("Le produit ne devrait plus exister après suppression", produitDAO.checkProduit(testCode));
    }
    
    @Test
    public void testDeleteProduitNonExistent() {
        // Essayer de supprimer un produit qui n'existe pas
        boolean result = produitDAO.deleteProduit("CODE_INEXISTANT");
        assertFalse("La suppression d'un produit inexistant devrait échouer", result);
    }
    
    // 5. Tests pour la méthode findByCode
    
    @Test
    public void testFindByCodeExists() throws InvalidePrixException {
        // Ajouter d'abord un produit
        produitDAO.addProduit(testCode, testNom, testPrix, testCategorieId);
        
        // Chercher le produit
        Produit produit = produitDAO.findByCode(testCode);
        assertNotNull("Le produit devrait être trouvé", produit);
        assertEquals("Le code du produit doit correspondre", testCode, produit.getCode());
    }
    
    @Test
    public void testFindByCodeNotExists() {
        // Chercher un produit qui n'existe pas
        Produit produit = produitDAO.findByCode("CODE_INEXISTANT");
        assertNull("Le produit ne devrait pas être trouvé", produit);
    }
    
    // 6. Tests pour la méthode getAllProduit
    
    @Test
    public void testGetAllProduit() throws InvalidePrixException {
        // Ajouter d'abord un produit
      
          produitDAO.addProduit(testCode, testNom, testPrix, testCategorieId);
        // Récupérer tous les produits
        ArrayList<Produit> produits = produitDAO.getAllProduit();
        assertNotNull("La liste des produits ne devrait pas être null", produits);
        assertFalse("La liste des produits ne devrait pas être vide", produits.isEmpty());
    }
    
    // 7. Tests pour les méthodes de stock
    
    @Test
    public void testIsSortieStockExist() {
        // Test pour un produit sans sortie de stock
        boolean result = produitDAO.isSortieStockExist(testCode);
        assertFalse("Aucune sortie de stock ne devrait exister pour ce produit", result);
        
        // Ajouter une sortie de stock serait nécessaire pour tester le cas positif
    }
    
    @Test
    public void testIsEntreeStockExist() {
        // Test pour un produit sans entrée de stock
        boolean result = produitDAO.isEntreeStockExist(testCode);
        assertFalse("Aucune entrée de stock ne devrait exister pour ce produit", result);
        
        // Ajouter une entrée de stock serait nécessaire pour tester le cas positif
    }
    
    @Test
    public void testGetQuatiteFromS() {
        // Test pour un produit sans sortie de stock
        int quantite = produitDAO.getQuatiteFromS(testCode);
        assertEquals("La quantité devrait être 0 pour un produit sans sortie", 0, quantite);
    }
    
    @Test
    public void testGetQuatiteFromE() {
        // Test pour un produit sans entrée de stock
        int quantite = produitDAO.getQuatiteFromE(testCode);
        assertEquals("La quantité devrait être 0 pour un produit sans entrée", 0, quantite);
    }
    
    // 8. Tests pour les méthodes de calcul
    
    @Test
    public void testTotalProduit() {
        // Ce test nécessite des données spécifiques en base
        int total = produitDAO.totalProduit();
        assertTrue("Le total devrait être -1 en cas d'erreur ou un nombre positif ou nul", total >= -1);
    }
    
    @Test
    public void testGetTotalByCode() {
        // Test pour un produit sans stock
        int total = produitDAO.getTotalByCode(testCode);
        assertEquals("Le total devrait être -1 pour un produit sans entrée/sortie", -1, total);
    }
    
    @Test
    public void testValeurTotal() {
        // Ce test nécessite des données spécifiques en base
        int valeur = produitDAO.valeurTotal();
        assertTrue("La valeur totale devrait être -1 en cas d'erreur ou un nombre positif ou nul", valeur >= -1);
    }
    
    @Test
public void testGetProduit_ProduitExistant() throws InvalidePrixException {
    // Arrange
    produitDAO.addProduit(testCode, testNom, testPrix, testCategorieId);
    
    // Act
    ArrayList<Produit> result = produitDAO.getProduit(testCode);
    
    // Assert
    assertNotNull("La liste ne devrait pas être null", result);
    assertEquals("La liste devrait contenir 1 produit", 1, result.size());
    assertEquals("Le code du produit doit correspondre", testCode, result.get(0).getCode());
    assertEquals("Le nom du produit doit correspondre", testNom, result.get(0).getNomP());
    assertEquals("Le prix du produit doit correspondre", testPrix, result.get(0).getPrix(), 0.001);
}

@Test
public void testGetProduit_ProduitInexistant() {
    // Act
    ArrayList<Produit> result = produitDAO.getProduit("CODE_INEXISTANT");
    
    // Assert
    assertNotNull("La liste ne devrait pas être null même si le produit n'existe pas", result);
    assertTrue("La liste devrait être vide pour un produit inexistant", result.isEmpty());
}

@Test
public void testGetProduit_AvecCodeNull() {
    // Act
    ArrayList<Produit> result = produitDAO.getProduit(null);
    
    // Assert
    assertNull("Devrait retourner null pour un code null", result);
}

@Test
public void testGetProduit_MultiplesProduitsMemeCode() throws InvalidePrixException {
    // Arrange - Ajouter 2 produits avec le même code (si votre modèle le permet)
    produitDAO.addProduit(testCode, testNom, testPrix, testCategorieId);
    produitDAO.addProduit(testCode, "Autre Produit", 199.99f, testCategorieId);
    
    // Act
    ArrayList<Produit> result = produitDAO.getProduit(testCode);
    
    // Assert
    assertNotNull(result);
    assertEquals("Devrait retourner tous les produits avec ce code", 1, result.size());
}


    
    @After
    public void tearDown() {
        // Nettoyer les données de test
        produitDAO.deleteProduit(testCode);
    }
}
    
    
    
    
