package com.testEntity;

import entity.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {
    
    private User user;
    private final String testPrenom = "Jean";
    private final String testNom = "Dupont";
    private final String testAdresse = "123 Rue Test";
    private final String testTel = "0123456789";
    private final String testEmail = "jean.dupont@example.com";
    private final String testPwd = "password123";

    @Before
    public void setUp() {
        user = new User();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull("L'utilisateur devrait être créé", user);
        assertNull("Le prénom devrait être null", user.getPrenom());
        assertNull("Le nom devrait être null", user.getNom());
        assertNull("L'adresse devrait être null", user.getAdresse());
        assertNull("Le téléphone devrait être null", user.getTel());
        assertNull("L'email devrait être null", user.getEmail());
        assertNull("Le mot de passe devrait être null", user.getPwd());
    }

    @Test
    public void testParameterizedConstructor() {
        User paramUser = new User(testPrenom, testNom, testAdresse, 
                                testTel, testEmail, testPwd);
        
        assertEquals(testPrenom, paramUser.getPrenom());
        assertEquals(testNom, paramUser.getNom());
        assertEquals(testAdresse, paramUser.getAdresse());
        assertEquals(testTel, paramUser.getTel());
        assertEquals(testEmail, paramUser.getEmail());
        assertEquals(testPwd, paramUser.getPwd());
    }

    @Test
    public void testSettersAndGetters() {
        // Test des setters
        user.setPrenom(testPrenom);
        user.setNom(testNom);
        user.setAdresse(testAdresse);
        user.setTel(testTel);
        user.setEmail(testEmail);
        user.setPwd(testPwd);
        
        // Vérification des getters
        assertEquals(testPrenom, user.getPrenom());
        assertEquals(testNom, user.getNom());
        assertEquals(testAdresse, user.getAdresse());
        assertEquals(testTel, user.getTel());
        assertEquals(testEmail, user.getEmail());
        assertEquals(testPwd, user.getPwd());
    }

    @Test
    public void testSetPrenom() {
        user.setPrenom(testPrenom);
        assertEquals(testPrenom, user.getPrenom());
        
        user.setPrenom(null);
        assertNull(user.getPrenom());
    }

    @Test
    public void testSetNom() {
        user.setNom(testNom);
        assertEquals(testNom, user.getNom());
        
        user.setNom(null);
        assertNull(user.getNom());
    }

    @Test
    public void testSetAdresse() {
        user.setAdresse(testAdresse);
        assertEquals(testAdresse, user.getAdresse());
        
        user.setAdresse(null);
        assertNull(user.getAdresse());
    }

    @Test
    public void testSetTel() {
        user.setTel(testTel);
        assertEquals(testTel, user.getTel());
        
        user.setTel(null);
        assertNull(user.getTel());
    }

    @Test
    public void testSetEmail() {
        user.setEmail(testEmail);
        assertEquals(testEmail, user.getEmail());
        
        user.setEmail(null);
        assertNull(user.getEmail());
    }

    @Test
    public void testSetPwd() {
        user.setPwd(testPwd);
        assertEquals(testPwd, user.getPwd());
        
        user.setPwd(null);
        assertNull(user.getPwd());
    }

    @Test
    public void testEdgeCases() {
        // Test avec chaînes vides
        user.setPrenom("");
        assertEquals("", user.getPrenom());
        
        // Test avec espaces
        user.setNom("  ");
        assertEquals("  ", user.getNom());
        
        // Test avec caractères spéciaux
        String specialChars = "é@àç_ù";
        user.setAdresse(specialChars);
        assertEquals(specialChars, user.getAdresse());
    }
}