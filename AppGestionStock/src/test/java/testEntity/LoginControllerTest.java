package com.testEntity;

import controllers.LoginController;
import dataBase.DB;
import entity.UserDAO;
import dataBase.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.After;
import view.LoginIHM;

public class LoginControllerTest {
    
    private LoginController loginController;
    private UserDAO userDao;
    private TestLoginIHM loginIhm; // Utilisation d'une sous-classe pour le test
    private Connection testConnection;
    
    private final String testEmail = "test.user@example.com";
    private final String testPwd = "password123";
    private final String testNom = "Dupont";
    private final String testPrenom = "Jean";
    private final String testTel = "0123456789";
    private final String testAdresse = "123 Rue Test";

    // Sous-classe testable de LoginIHM
    private static class TestLoginIHM extends LoginIHM {
        private boolean visible = true;
        
        @Override
        public void setVisible(boolean visible) {
            this.visible = visible;
        }
        
        @Override
        public boolean isVisible() {
            return visible;
        }
    }

    @Before
    public void setUp() throws Exception {
        userDao = new UserDAO();
        loginIhm = new TestLoginIHM();
        loginController = new LoginController(loginIhm);
        testConnection = DB.getInstance();
        cleanTestData();
    }
    
    @After
    public void tearDown() throws Exception {
        cleanTestData();
       
    }
    
    private void cleanTestData() throws Exception {
        try (PreparedStatement ps = testConnection.prepareStatement(
                 "DELETE FROM user WHERE email = ?")) {
            ps.setString(1, testEmail);
            ps.executeUpdate();
        }
    }

    @Test
    public void testLoginSuccess_NominalCase() throws Exception {
        // Arrange
        boolean added = userDao.addUser(testPrenom, testNom, testAdresse, testTel, testEmail, testPwd);
        assertTrue("L'utilisateur devrait être ajouté", added);
        
        // Act
        loginController.login(testEmail, testPwd);
        
        // Assert
        assertFalse("La fenêtre login devrait être fermée", loginIhm.isVisible());
    }

    @Test
    public void testLoginFailure_WrongEmail() throws Exception {
        // Arrange
        userDao.addUser(testPrenom, testNom, testAdresse, testTel, testEmail, testPwd);
        
        // Act
        loginController.login("wrong@example.com", testPwd);
        
        // Assert
        assertTrue("La fenêtre login devrait rester ouverte", loginIhm.isVisible());
    }

    @Test
    public void testLoginFailure_WrongPassword() throws Exception {
        // Arrange
        userDao.addUser(testPrenom, testNom, testAdresse, testTel, testEmail, testPwd);
        
        // Act
        loginController.login(testEmail, "wrongpassword");
        
        // Assert
        assertTrue("La fenêtre login devrait rester ouverte", loginIhm.isVisible());
    }

    @Test
    public void testLoginFailure_NullEmail() throws Exception {
        // Act
        loginController.login(null, testPwd);
        
        // Assert
        assertTrue("La fenêtre login devrait rester ouverte", loginIhm.isVisible());
    }

    @Test
    public void testLoginFailure_NullPassword() throws Exception {
        // Act
        loginController.login(testEmail, null);
        
        // Assert
        assertTrue("La fenêtre login devrait rester ouverte", loginIhm.isVisible());
    }

    @Test
    public void testLoginFailure_UserNotExists() throws Exception {
        // Act
        loginController.login("nonexistent@example.com", "anypassword");
        
        // Assert
        assertTrue("La fenêtre login devrait rester ouverte", loginIhm.isVisible());
    }
}