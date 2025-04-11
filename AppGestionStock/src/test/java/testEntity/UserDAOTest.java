package com.testEntity;

import dataBase.DB;
import entity.UserDAO;
import java.sql.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.After;



public class UserDAOTest {
    
    private UserDAO userDAO;
    private Connection testConnection;
    private final String testEmail = "test.user@example.com";
    private final String testPwd = "password123";
    private final String testNom = "Dupont";
    private final String testPrenom = "Jean";
    private final String testTel = "0123456789";
    private final String testAdresse = "123 Rue Test";

    @Before
    public void setUp() throws SQLException {
        userDAO = new UserDAO();
        testConnection = DB.getInstance();
        cleanTestData();
    }
    
  
    
    private void cleanTestData() throws SQLException {
        try (PreparedStatement ps = testConnection.prepareStatement(
                "DELETE FROM user WHERE email = ?")) {
            ps.setString(1, testEmail);
            ps.executeUpdate();
        }
    }

    // Test pour searchUser()
    @Test
    public void testSearchUser_WhenUserExists_ReturnsTrue() throws SQLException {
        insertTestUser();
        assertTrue(userDAO.searchUser(testEmail));
    }
    
    @Test
    public void testSearchUser_WhenUserNotExists_ReturnsFalse() {
        assertFalse(userDAO.searchUser("nonexistent@example.com"));
    }
    
    @Test
    public void testSearchUser_WithNullEmail_ReturnsFalse() {
        assertFalse(userDAO.searchUser(null));
    }

    // Test pour addUser()
    @Test
    public void testAddUser_WithValidData_ReturnsTrue() throws SQLException {
        assertTrue(userDAO.addUser(testPrenom, testNom, testAdresse, testTel, testEmail, testPwd));
        assertTrue(userExists(testEmail));
    }
    
   
  

    // Test pour getUser()
    @Test
    public void testGetUser_WithCorrectCredentials_ReturnsTrue() throws SQLException {
        insertTestUser();
        assertTrue(userDAO.getUser(testEmail, testPwd));
    }
    
    @Test
    public void testGetUser_WithWrongPassword_ReturnsFalse() throws SQLException {
        insertTestUser();
        assertFalse(userDAO.getUser(testEmail, "wrongPassword"));
    }
    
    @Test
    public void testGetUser_WithNonexistentUser_ReturnsFalse() {
        assertFalse(userDAO.getUser("nonexistent@example.com", "anyPassword"));
    }

    // Test pour getUserName()
    @Test
    public void testGetUserName_WithCorrectCredentials_ReturnsFullName() throws SQLException {
        insertTestUser();
        String expected = testPrenom + " " + testNom;
        assertEquals(expected, userDAO.getUserName(testEmail, testPwd));
    }
    
    @Test
    public void testGetUserName_WithWrongCredentials_ReturnsNoName() throws SQLException {
        insertTestUser();
        assertEquals("no name", userDAO.getUserName(testEmail, "wrongPassword"));
    }
    
    @Test
    public void testGetUserName_WhenDatabaseError_ReturnsNoNoName() {
        assertEquals("no name", userDAO.getUserName(null, null));
    }

    // Méthodes utilitaires
    private void insertTestUser() throws SQLException {
        userDAO.addUser(testPrenom, testNom, testAdresse, testTel, testEmail, testPwd);
    }
    
    private boolean userExists(String email) throws SQLException {
        try (PreparedStatement ps = testConnection.prepareStatement(
                "SELECT 1 FROM user WHERE email = ?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}