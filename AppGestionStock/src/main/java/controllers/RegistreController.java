/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;
import entity.*;
import javax.swing.JOptionPane;
import view.LoginIHM;
import view.RegistreIHM;
/**
 *
 * @author hp
 */
public class RegistreController {
    private UserDAO dao = new UserDAO();
    private RegistreIHM ri; 
    public RegistreController(RegistreIHM r){
        this.ri=r;
    }
    
    public void addUser(String prenom, String nom, String adresse, String tel, String email, String pwd){
        try {
             if(dao.searchUser(email)){
            JOptionPane.showMessageDialog(null, "email deja existe");
            
            return ;
        }
        
        if((dao.addUser(prenom, nom, adresse, tel, email, pwd))){
            JOptionPane.showMessageDialog(null, "compte creer avec succee");
            
            new LoginIHM().setVisible(true);
            this.ri.dispose();
            
            return;
        }
         
             JOptionPane.showMessageDialog(null, "Erreur lors de creation , essayer plus tard");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
       
                    
    }
   
}
