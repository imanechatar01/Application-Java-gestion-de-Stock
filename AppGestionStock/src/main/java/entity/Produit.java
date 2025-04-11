/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import exceptions.InvalidePrixException;


public class Produit {
    private String code , nomP;
    private float prix;
    
    private String cat;
    public Produit(){
        
    }
    public Produit(String c ,String n , float p ,  String cat)throws InvalidePrixException{
        if(p<=0) throw new InvalidePrixException("prix invalide");
        this.code =c ; 
        this.nomP =n ;
        this.prix=p;
        
        this.cat = cat;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNomP() {
        return nomP;
    }

    public void setNomP(String nomP) {
        this.nomP = nomP;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

   
    public String getCategorie(){
        return this.cat;
    }    
    
    @Override
    public String toString(){
        return this.nomP;
    }
}

