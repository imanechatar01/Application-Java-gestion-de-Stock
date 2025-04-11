/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author hp
 */
public class Categorie {
    private String code;
    private String nomCat;
    private int idCat;
public Categorie (){}
    public Categorie( int idCat,String nomCat , String code) {
        this.nomCat = nomCat;
        this.idCat = idCat;
        this.code =code;
    }

    public String getNomCat() {
        return nomCat;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNomCat(String nomCat) {
        this.nomCat = nomCat;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }
    // In your Categorie class:
@Override
public String toString() {
    return getNomCat(); // Returns the category name to display in the combo box
}
    
}
