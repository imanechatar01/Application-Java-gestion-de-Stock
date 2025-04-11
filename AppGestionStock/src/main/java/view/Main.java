/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.SwingUtilities;

/**
 *
 * @author hp
 */
public class Main {
     public static void main(String[] args) {
        // Démarrage de l'application
        System.out.println("Bienvenue dans l'application de gestion de stock !");
       SwingUtilities.invokeLater(() -> new LoginIHM());  // ou autre lancement d'interface
    }
}
