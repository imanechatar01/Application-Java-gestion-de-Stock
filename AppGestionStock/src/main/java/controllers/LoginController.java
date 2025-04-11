/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import entity.*;
import javax.swing.JOptionPane;
import view.UserIHM;
import view.LoginIHM;
public class LoginController {
    private UserDAO dao = new UserDAO();
    private LoginIHM li ;
    
    public LoginController(LoginIHM li){
        this.li= li;
    }
    public void login(String email , String pwd){
        if(!(dao.getUser(email, pwd))){
            JOptionPane.showMessageDialog(null, "email ou mot de passe incorrecte");
           return;
        }
       
        String nom = dao.getUserName(email, pwd);
        UserIHM eu = new UserIHM(nom);
         li.setVisible(false);
        eu.setVisible(true);
      
         li.dispose();
     
        
        
      
    }
}
