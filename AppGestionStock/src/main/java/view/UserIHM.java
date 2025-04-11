/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;
import controllers.CategorieController;
import controllers.ProduitController;
import controllers.StockController;
import entity.Categorie;

import entity.CategorieDAO;
import entity.EntreeStock;
import entity.Produit;
import entity.ProduitDAO;
import entity.SortieStock;
import exceptions.InvalidePrixException;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hp
 */
public class UserIHM extends javax.swing.JFrame {

    /**
     * Creates new form UserIHM
     */
   public UserIHM() {
    initComponents();
    
    // Initialisation des modèles de table
    mrd = (DefaultTableModel) table.getModel();

    cmdTestCat.setBackground(new java.awt.Color(153, 153, 153));
        cmdTestCat.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        cmdTestCat.setForeground(new java.awt.Color(255, 255, 255));
        cmdTestCat.setText("Gestion Categories");
    setupListeners();
    afficherTableauBord();
    remplir_categorie();
    remplircmbProduit();
    remplircmbProduit_2();
    
    GestionProduitsPanel.setVisible(false);
    ajouterProduitPanel.setVisible(false);
    dashboardPanel.setVisible(true);
}

public UserIHM(String nom) {
    initComponents();
    if (nameUser != null) {
        nameUser.setText(nom);
    } else {
        System.out.println("nameUser n'est pas initialisé !");
    }
    cmdTestCat.setBackground(new java.awt.Color(153, 153, 153));
        cmdTestCat.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        cmdTestCat.setForeground(new java.awt.Color(255, 255, 255));
        cmdTestCat.setText("Gestion Categories");
    setupListeners(); 
    afficherTableauBord(); // Affiche le tableau de bord par défaut
    remplir_categorie();
    remplircmbProduit();
    remplircmbProduit_2();
    afficherproduits();
    // À la fin de votre constructeur
GestionProduitsPanel.setVisible(false);
ajouterProduitPanel.setVisible(false);
GestionCategoriePanel.setVisible(false);
dashboardPanel.setVisible(true);
}

// gestion des boutons

/**
 * Gère la déconnexion de l'utilisateur
 */
private void deconnexion() {
    // Ajoutez le code pour retourner à l'écran de connexion
    this.dispose(); // Ferme la fenêtre actuelle

    new LoginIHM().setVisible(true);
}

 private void updateButtonStyles(javax.swing.JButton selectedButton) {
    // Réinitialise tous les boutons
    cmdBord.setBackground(new java.awt.Color(153, 153, 153));
    cmdGP.setBackground(new java.awt.Color(153, 153, 153));
    cmdES.setBackground(new java.awt.Color(153, 153, 153));
    cmdSS.setBackground(new java.awt.Color(153, 153, 153));
    cmdTestCat.setBackground(new java.awt.Color(153, 153, 153));

   
    selectedButton.setBackground(new java.awt.Color(102, 102, 255));
}




/**
 * Configure les écouteurs d'événements pour les boutons
 */
private void setupListeners() {
    // ActionListener pour le bouton Tableau de bord
    cmdBord.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            afficherTableauBord();
        }
    });

  
    // ActionListener pour le bouton Entrées Stock
    cmdES.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            try {
                afficherEntreesStock();
            } catch (Exception ex) {
                Logger.getLogger(UserIHM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });

    // ActionListener pour le bouton Sorties Stock
    cmdSS.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            afficherSortiesStock();
        }
    });

    // Ajoutez un listener pour le bouton de déconnexion
    cmdLogout.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            deconnexion();
        }
    });
    cmdAjouterProduit.addActionListener(new java.awt.event.ActionListener(){
        public void actionPerformed(java.awt.event.ActionEvent evt){
            afficherAjouterProduit();
            
        }
    });
   
   
    table.getSelectionModel().addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting()) {
        remplirFormulaireDepuisTable();
    }
    
});
    cmdSaveES.addActionListener(new java.awt.event.ActionListener(){
        public void actionPerformed(java.awt.event.ActionEvent evt){
          saveEntreeStock();
           showEntreeStock();
           afficherproduits();
        }
    });
    cmdsaveSS.addActionListener(new java.awt.event.ActionListener(){
        public void actionPerformed(java.awt.event.ActionEvent evt){
            try {
                saveSS();
                showSortieStock();
                afficherproduits();
            } catch (Exception ex) {
                Logger.getLogger(UserIHM.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
    });
    cmdSaveCat.addActionListener(new java.awt.event.ActionListener(){
        public void actionPerformed(java.awt.event.ActionEvent evt){
    
        }
    });
    cmdEnregistrerCate.addActionListener(new java.awt.event.ActionListener(){
        public void actionPerformed(java.awt.event.ActionEvent evt){
            try {
                enregistrerCate();
            } catch (SQLException ex) {
                Logger.getLogger(UserIHM.class.getName()).log(Level.SEVERE, null, ex);
            }
             getAllCategories();
        }
       
    });
    cmdTestCat.addActionListener(new java.awt.event.ActionListener(){
        public void actionPerformed(java.awt.event.ActionEvent evt){
             afficherGestionCategories();
        }
       
    });
   
    

}






//Partie de gestion des Affichages


private void afficherTableauBord() {
    // Cache tous les panneaux
     
   
    remplir_categorie();
    remplircmbProduit();
    remplircmbProduit_2();
    GestionProduitsPanel.setVisible(false);
    GestionCategoriePanel.setVisible(false);
     ajouterCategorieFormPanel.setVisible(false);
     gestionEntrerStockPanel.setVisible(false);
      gestionSortieStockPanel.setVisible(false);
      testPanel.setVisible(false);
    // Affiche le dashboard avec les statistiques
    totalProduit();
    totalValue();
    afficherproduits();
    dashboardPanel.setVisible(true);
    CardLayout cl = (CardLayout) dashboardPanel.getLayout();
  
    
    // Mise à jour visuelle du bouton sélectionné
    updateButtonStyles(cmdBord);
}

/**
 * Affiche le panneau de gestion des produits
 */
private void afficherGestionProduits() {
     
    
    remplir_categorie();
    remplircmbProduit();
    remplircmbProduit_2();
    // Cache le dashboard
    dashboardPanel.setVisible(false);
    GestionCategoriePanel.setVisible(false);
     ajouterCategorieFormPanel.setVisible(false);
      gestionEntrerStockPanel.setVisible(false);
       gestionSortieStockPanel.setVisible(false);
       testPanel.setVisible(false);
    // Affiche le panneau de gestion des produits
    GestionProduitsPanel.setVisible(true);
    afficherproduits();
    // Mise à jour visuelle du bouton sélectionné
    updateButtonStyles(cmdGP);
}

/**
 * Affiche le panneau des entrées de stock 
 */
private void afficherEntreesStock() throws Exception {
  
    
    remplir_categorie();
    remplircmbProduit();
    remplircmbProduit_2();
    // À implémenter: créer et afficher le panneau pour les entrées de stock
    GestionProduitsPanel.setVisible(false);
    GestionCategoriePanel.setVisible(false);
     ajouterCategorieFormPanel.setVisible(false);
    dashboardPanel.setVisible(false);
     gestionSortieStockPanel.setVisible(false);
    gestionEntrerStockPanel.setVisible(true);
    testPanel.setVisible(false);
  showEntreeStock();
 
    updateButtonStyles(cmdES);
}

/**
 * Affiche le panneau des sorties de stock 
 */
private void afficherSortiesStock() {
    
   
    remplir_categorie();
    remplircmbProduit();
    remplircmbProduit_2();
    // À implémenter: créer et afficher le panneau pour les sorties de stock
    GestionProduitsPanel.setVisible(false);
    GestionCategoriePanel.setVisible(false);
   gestionEntrerStockPanel.setVisible(false);
   testPanel.setVisible(false);
    dashboardPanel.setVisible(false);
     gestionSortieStockPanel.setVisible(true);
     
     showSortieStock();
  updateButtonStyles(cmdSS);
}
  /*Affiche le panneau d'ajouter un nouveau produit 
 */
/**
 * Affiche le formulaire d'ajout de produit
 */
private void afficherAjouterProduit() {
    
    
    remplir_categorie();
    remplircmbProduit();
    remplircmbProduit_2();
    // Cachez tous les autres panneaux si nécessaire
    GestionProduitsPanel.setVisible(false);
    GestionCategoriePanel.setVisible(false);
    dashboardPanel.setVisible(false);
  ajouterCategorieFormPanel.setVisible(false);
 gestionEntrerStockPanel.setVisible(false);
 gestionSortieStockPanel.setVisible(false);
 testPanel.setVisible(false);
      ajouterProduitPanel.setVisible(true);
    updateButtonStyles(cmdGP);
 }

//methode pour afficher le formulaire d'ajout d'un nouveau categorie
private void afficherAjouterCatForm(){
     
    remplir_categorie();
    remplircmbProduit();
    remplircmbProduit_2();
    dashboardPanel.setVisible(false);
      ajouterProduitPanel.setVisible(false);
         GestionProduitsPanel.setVisible(false);
         GestionCategoriePanel.setVisible(false);
        gestionEntrerStockPanel.setVisible(false);
         gestionSortieStockPanel.setVisible(false);
         testPanel.setVisible(false);
         ajouterCategorieFormPanel.setVisible(true);
    
}

// afficher test des categorie panel

public void afficherGestionCategories(){
     
  
    remplir_categorie();
    remplircmbProduit();
    remplircmbProduit_2();
    dashboardPanel.setVisible(false);
      ajouterProduitPanel.setVisible(false);
         GestionProduitsPanel.setVisible(false);
         GestionCategoriePanel.setVisible(false);
        gestionEntrerStockPanel.setVisible(false);
         gestionSortieStockPanel.setVisible(false);
         ajouterCategorieFormPanel.setVisible(false);
         testPanel.setVisible(true);
         getAllCategories();
         updateButtonStyles(cmdTestCat);
}






//Partie de Gestion des Produits

//remplir le comboboxBroduit par produit 

public void remplircmbProduit(){
    cmbProduit.removeAllItems();
listProduit =pdao.getAllProduit();
    for(Produit p:listProduit){
        cmbProduit.addItem(p);
       
    }
    list.clear();
}

public void remplircmbProduit_2(){
    cmb_produit.removeAllItems();
    ArrayList<Produit> listP = pdao.getAllProduit();
    for(Produit p:listP){
        cmb_produit.addItem(p);
    }
        listP.clear();
  
}


//methode pour afficher les information d'un produit selectionner dans le formulaire
private void remplirFormulaireDepuisTable() {
   
    int selectedRow = table.getSelectedRow();
    if (selectedRow != -1) {
        
        txtCode.setText(table.getValueAt(selectedRow, 0).toString());
        txtNom.setText(table.getValueAt(selectedRow, 1).toString());
        cmbCategorie.setSelectedItem(table.getValueAt(selectedRow, 2).toString());
       
        txtPrix.setText(table.getValueAt(selectedRow, 4).toString());
        currentCode=table.getValueAt(selectedRow, 0).toString();
        
    }
}
  

//Methode de suppression d'un produit
private void supprimerProduit(){
     
    // Vérifier la réponse de l'utilisateur
   
     int selectedRow = table.getSelectedRow();
    if (selectedRow != -1) {
        int reponse = JOptionPane.showConfirmDialog(
        this,
        "Êtes-vous sûr de vouloir supprimer ce produit ?",
        "Confirmation de suppression",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE
    );
     if (reponse == JOptionPane.YES_OPTION) {
        // L'utilisateur a confirmé, procéder à la suppression
        pcontroller.deleteProduit(table.getValueAt(selectedRow, 0).toString());
        afficherproduits();
    }
      
    }
    else{
        JOptionPane.showMessageDialog(null, "vueiller selectionner un produit");
    }
        
}

private void afficherproduits(){
      mrd = (DefaultTableModel)table.getModel();
     ArrayList<Produit> listProduit;
     listProduit = pcontroller.getAllProducts();
     if(listProduit == null){
          JOptionPane.showMessageDialog(null, "getAllProduct est null ");
          return;
     }
   int quantite =0;
  int totalProduit =0;
  int totalValue=0;
       mrd.setNumRows(0);
        for(Produit p :listProduit ){
            quantite =pcontroller.quantiteProduit(p.getCode());
            mrd.addRow(new Object[]{p.getCode() , p.getNomP(), p.getCategorie(),quantite,p.getPrix()});
            totalProduit +=quantite;
            totalValue += quantite *p.getPrix();
       
        }
           lbltotalProduit.setText(String.valueOf(totalProduit));
          lblValeurTotal.setText(String.valueOf(totalValue+" DH"));
     
}


private void ajouterProduit()throws InvalidePrixException{
    String code = txtCode.getText();
    String nom = txtNom.getText();
   Categorie cat = (Categorie) cmbCategorie.getSelectedItem();
   int idCat = cat.getIdCat();
   float prix =Float.parseFloat(txtPrix.getText()) ;

   pcontroller.addNewProduit(code, nom, prix,  idCat);
   cmbProduit.removeAllItems();
   remplircmbProduit_2();
   remplircmbProduit();
}


private void annlerAjouterProduit(){
 
    dashboardPanel.setVisible(false);
      ajouterProduitPanel.setVisible(false);
         GestionProduitsPanel.setVisible(true);
}
private void afficherModifierProduit() {
    // Cachez tous les autres panneaux si nécessaire
    GestionProduitsPanel.setVisible(false);
    dashboardPanel.setVisible(false);
      ajouterProduitPanel.setVisible(true);
      
    
 }





 //partie Entree Stock 
 
//Create new EntrerStock et stocker dans base de donnee

public void saveEntreeStock(){
    try{
    Produit p = (Produit)cmbProduit.getSelectedItem();
  
    Date dr =sc.parseDate(txtDateReception.getText());
    sc.createEntreeStock(p, Integer.parseInt(txtQuatiteS.getText()), dr, txtnumFature.getText());
    txtQuatiteS.setText("");
    txtDateReception.setText("");
    txtnumFature.setText("");
    
    }catch(Exception ex){ex.printStackTrace();}
}

//afficher les entreeStock
public void showEntreeStock() {
    try{
    ArrayList<EntreeStock> es = sc.getRecentEntreesStock();
    DefaultTableModel dm = (DefaultTableModel)tableES.getModel();
    dm.setRowCount(0);
    
    for(EntreeStock entre :es){
        
      dm.addRow(new Object[]{sc.formatDate(entre.getDateReception()),entre.getProduit().getNomP(),entre.getQuantite(),entre.getNumeroFacture()});
      
     }
    }catch(Exception e){
             e.printStackTrace();
       }
    
}




//partie de sortie du stock
//insertion d'un sortie de stock dans la base de donnee

public void saveSS() throws Exception{
    Produit p = (Produit)cmb_produit.getSelectedItem();
    int quantite = Integer.parseInt(txtQuantiteSS.getText());
    Date ds = sc.parseDate(txtD_S.getText());
    sc.saveSS(p, quantite, ds, txtDestination.getText());
    txtQuantiteSS.setText("");
    txtD_S.setText("");
    txtDestination.setText("");
}

//afficher les sortie de stock
public void showSortieStock() {
    try{
    ArrayList<SortieStock> es = sc.getAllSortieStock();
    DefaultTableModel dm = (DefaultTableModel)tableSS.getModel();
    dm.setRowCount(0);
    
    for(SortieStock sortie :es){
        
      dm.addRow(new Object[]{sc.formatDate(sortie.getDateSortie()),sortie.getProduit().getNomP(),sortie.getQuantite(),sortie.getDestination()});
      
     }
    }catch(Exception e){
             e.printStackTrace();
       }
    
}








//Partie des Gestion des Categorie

//save categorie
public void enregistrerCate() throws SQLException{
    String code = txtCodeCategorie.getText();
    String nom = txtNameCat.getText();
     if (code.isEmpty() || nom.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "Veuillez remplir tous les champs",
            "Erreur",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    ccontroller.addNewCategorie(nom, code);
}

//recuperer tous les categories
public void getAllCategories(){
    DefaultTableModel m = (DefaultTableModel)tableCategorie.getModel();
    ArrayList<Categorie> l = ccontroller.getGategories();
    int i=1;
    m.setRowCount(0);
    for(Categorie c :l){
        m.addRow(new Object[]{i,c.getCode(),c.getNomCat()});
       i++;
        }
    l.clear();
 }
// supprimer Categorie
private void supprimerCat() {
    int id = tableCategorie.getSelectedRow();
     
    if (id != -1) {
        int reponse = JOptionPane.showConfirmDialog(
            this,
            "Êtes-vous sûr de vouloir supprimer cette catégorie ?",
            "Confirmation de suppression",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (reponse == JOptionPane.YES_OPTION) {
            String nomCategorie = tableCategorie.getValueAt(id, 2).toString();
            ccontroller.supprimerCat(nomCategorie);
            
          
                  getAllCategories();// Rafraîchir la table
                  cmbCategorie.removeAllItems();
                 remplir_categorie();// Rafraîchir le combobox
           
        }
    } else {
        JOptionPane.showMessageDialog(null, "Veuillez sélectionner une catégorie");
    }
}


   //Remplir le combobox par les categorie
public void remplir_categorie(){
    cmbCategorie.removeAllItems();
    list = cdao.getCategorie();
    for(Categorie cat:list){
        cmbCategorie.addItem(cat);
    }
    list.clear();
} 






// gestion de tableau de bord
public void totalProduit(){
   
   
        
}
       

public void totalValue(){
     
    
       
    
}

//recupere total quantite des produit pour l' afficher
public int getotalQuantite(int quantite){
    return quantite;
}
//recuperer la valeur totaldes produit
public int getTotalValue(int vt){
    return vt;
}











// Modifiez votre constructeur pour afficher le tableau de bord par défaut


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        naveBarePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nameUser = new javax.swing.JLabel();
        cmdLogout = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        sideBarePanel = new javax.swing.JPanel();
        cmdBord = new javax.swing.JButton();
        cmdGP = new javax.swing.JButton();
        cmdES = new javax.swing.JButton();
        cmdSS = new javax.swing.JButton();
        cmdTestCat = new javax.swing.JButton();
        dashboardPanel = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        lbltotalProduit = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        lblValeurTotal = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        gestionEntrerStockPanel = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        Produit = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtQuatiteS = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtDateReception = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtnumFature = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableES = new javax.swing.JTable();
        cmdSaveES = new javax.swing.JButton();
        cmbProduit = new javax.swing.JComboBox<>();
        testPanel = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        txtCodeCategorie = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtNameCat = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableCategorie = new javax.swing.JTable();
        cmdEnregistrerCate = new javax.swing.JButton();
        cmdSuppCat = new javax.swing.JButton();
        GestionCategoriePanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cmdAddCat = new javax.swing.JButton();
        cmdDelCat = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableCategories = new javax.swing.JTable();
        ajouterCategorieFormPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtNomCat = new javax.swing.JTextField();
        txtCodeCat = new javax.swing.JTextField();
        cmdSaveCat = new javax.swing.JButton();
        cmdCancelCat = new javax.swing.JButton();
        GestionProduitsPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        cmdAjouterProduit = new javax.swing.JButton();
        cmdModifier = new javax.swing.JButton();
        cmdSupprimer = new javax.swing.JButton();
        gestionSortieStockPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtQuantiteSS = new javax.swing.JTextField();
        txtDestination = new javax.swing.JTextField();
        txtD_S = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableSS = new javax.swing.JTable();
        cmb_produit = new javax.swing.JComboBox<>();
        cmdsaveSS = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        ajouterProduitPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtNom = new javax.swing.JTextField();
        txtPrix = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cmdCancel = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        cmdSave = new javax.swing.JButton();
        txtCode = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cmbCategorie = new javax.swing.JComboBox<>();
        lblAjouterProduit = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        naveBarePanel.setBackground(new java.awt.Color(153, 153, 153));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\hp\\Pictures\\Contacts_App_Icon-removebg-preview.png")); // NOI18N

        nameUser.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        nameUser.setForeground(new java.awt.Color(255, 255, 255));

        cmdLogout.setBackground(new java.awt.Color(204, 204, 204));
        cmdLogout.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        cmdLogout.setForeground(new java.awt.Color(0, 0, 0));
        cmdLogout.setText("Logout");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Gestion de Stock");

        javax.swing.GroupLayout naveBarePanelLayout = new javax.swing.GroupLayout(naveBarePanel);
        naveBarePanel.setLayout(naveBarePanelLayout);
        naveBarePanelLayout.setHorizontalGroup(
            naveBarePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, naveBarePanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nameUser, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(182, 182, 182)
                .addComponent(cmdLogout)
                .addContainerGap())
        );
        naveBarePanelLayout.setVerticalGroup(
            naveBarePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(naveBarePanelLayout.createSequentialGroup()
                .addGroup(naveBarePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(naveBarePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(naveBarePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(nameUser, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(naveBarePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmdLogout)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(naveBarePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 70));

        sideBarePanel.setBackground(new java.awt.Color(204, 204, 204));

        cmdBord.setBackground(new java.awt.Color(153, 153, 153));
        cmdBord.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        cmdBord.setForeground(new java.awt.Color(255, 255, 255));
        cmdBord.setText("Tableau de bord");

        cmdGP.setBackground(new java.awt.Color(153, 153, 153));
        cmdGP.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        cmdGP.setForeground(new java.awt.Color(255, 255, 255));
        cmdGP.setText("Gestion Produits");
        cmdGP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdGPActionPerformed(evt);
            }
        });

        cmdES.setBackground(new java.awt.Color(153, 153, 153));
        cmdES.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        cmdES.setForeground(new java.awt.Color(255, 255, 255));
        cmdES.setText("Entrées Stock");
        cmdES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdESActionPerformed(evt);
            }
        });

        cmdSS.setBackground(new java.awt.Color(153, 153, 153));
        cmdSS.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        cmdSS.setForeground(new java.awt.Color(255, 255, 255));
        cmdSS.setText("Sorties Stock");

        cmdTestCat.setText("Gestion des Categories");

        javax.swing.GroupLayout sideBarePanelLayout = new javax.swing.GroupLayout(sideBarePanel);
        sideBarePanel.setLayout(sideBarePanelLayout);
        sideBarePanelLayout.setHorizontalGroup(
            sideBarePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBarePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sideBarePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmdGP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmdSS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmdBord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmdES, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmdTestCat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        sideBarePanelLayout.setVerticalGroup(
            sideBarePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBarePanelLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(cmdBord, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cmdGP, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(cmdTestCat)
                .addGap(18, 18, 18)
                .addComponent(cmdES, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cmdSS, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(204, Short.MAX_VALUE))
        );

        getContentPane().add(sideBarePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 66, 210, 500));

        dashboardPanel.setBackground(new java.awt.Color(255, 255, 255));
        dashboardPanel.setLayout(new java.awt.CardLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 102), 2));

        jLabel25.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel25.setText("Total des Produits");

        lbltotalProduit.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lbltotalProduit.setForeground(new java.awt.Color(255, 51, 102));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addComponent(jLabel25)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(239, Short.MAX_VALUE)
                .addComponent(lbltotalProduit, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbltotalProduit, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 102), 2));

        jLabel29.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel29.setText("Valeur Totale");

        lblValeurTotal.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblValeurTotal.setForeground(new java.awt.Color(0, 255, 102));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(224, 224, 224)
                .addComponent(jLabel29)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblValeurTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29)
                .addGap(29, 29, 29)
                .addComponent(lblValeurTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel27.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel27.setText("Tableau de Bord");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        dashboardPanel.add(jPanel8, "card2");

        getContentPane().add(dashboardPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 66, 610, 490));

        gestionEntrerStockPanel.setBackground(new java.awt.Color(255, 255, 255));
        gestionEntrerStockPanel.setForeground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel13.setText("Entrées de Stock");

        Produit.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Produit.setText("Produit :");

        jLabel14.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel14.setText("Quantité :");

        jLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel15.setText("Date de Réception :");

        jLabel16.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel16.setText("Numéro de Facture :");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Entrées Récentes", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14))); // NOI18N
        jPanel4.setToolTipText("Entrées recents");
        jPanel4.setName("Entrées recents"); // NOI18N
        jPanel4.setLayout(new java.awt.BorderLayout());

        tableES.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Produit", "Quantité", " Facture"
            }
        ));
        jScrollPane3.setViewportView(tableES);

        jPanel4.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        cmdSaveES.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cmdSaveES.setText("Enregistrer l'entrée");
        cmdSaveES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSaveESActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Produit)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addGap(183, 183, 183)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtQuatiteS)
                    .addComponent(txtDateReception)
                    .addComponent(txtnumFature)
                    .addComponent(cmbProduit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmdSaveES)
                        .addGap(15, 15, 15))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(Produit)
                        .addGap(29, 29, 29))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(cmbProduit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtQuatiteS, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtDateReception, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtnumFature, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmdSaveES, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.getAccessibleContext().setAccessibleName("Entrées recents");

        javax.swing.GroupLayout gestionEntrerStockPanelLayout = new javax.swing.GroupLayout(gestionEntrerStockPanel);
        gestionEntrerStockPanel.setLayout(gestionEntrerStockPanelLayout);
        gestionEntrerStockPanelLayout.setHorizontalGroup(
            gestionEntrerStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gestionEntrerStockPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gestionEntrerStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gestionEntrerStockPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        gestionEntrerStockPanelLayout.setVerticalGroup(
            gestionEntrerStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gestionEntrerStockPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        getContentPane().add(gestionEntrerStockPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, 630, 500));

        testPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel22.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel22.setText("Gestion des Categorie");

        jLabel23.setText("Code Categorie :");

        txtCodeCategorie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodeCategorieActionPerformed(evt);
            }
        });

        jLabel24.setText("Nom Categorie :");

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Liste des Categories", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        tableCategorie.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Code", "Nom"
            }
        ));
        jScrollPane2.setViewportView(tableCategorie);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
        );

        cmdEnregistrerCate.setText("Ajouter");
        cmdEnregistrerCate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEnregistrerCateActionPerformed(evt);
            }
        });

        cmdSuppCat.setText("Supprimer");
        cmdSuppCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSuppCatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24))
                        .addGap(220, 220, 220)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodeCategorie)
                            .addComponent(txtNameCat)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(cmdEnregistrerCate, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmdSuppCat, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtCodeCategorie, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtNameCat, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(cmdEnregistrerCate)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdSuppCat)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout testPanelLayout = new javax.swing.GroupLayout(testPanel);
        testPanel.setLayout(testPanelLayout);
        testPanelLayout.setHorizontalGroup(
            testPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(testPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(testPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, testPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(testPanelLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        testPanelLayout.setVerticalGroup(
            testPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(testPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(testPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, 620, 490));

        GestionCategoriePanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel4.setText("Gestion des Categories");

        cmdAddCat.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        cmdAddCat.setText("Ajouter");
        cmdAddCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAddCatActionPerformed(evt);
            }
        });

        cmdDelCat.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        cmdDelCat.setText("Supprimer");
        cmdDelCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDelCatActionPerformed(evt);
            }
        });

        tableCategories.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nom Categorie", "Code"
            }
        ));
        jScrollPane5.setViewportView(tableCategories);

        javax.swing.GroupLayout GestionCategoriePanelLayout = new javax.swing.GroupLayout(GestionCategoriePanel);
        GestionCategoriePanel.setLayout(GestionCategoriePanelLayout);
        GestionCategoriePanelLayout.setHorizontalGroup(
            GestionCategoriePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GestionCategoriePanelLayout.createSequentialGroup()
                .addGroup(GestionCategoriePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GestionCategoriePanelLayout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(cmdAddCat, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93)
                        .addComponent(cmdDelCat))
                    .addGroup(GestionCategoriePanelLayout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jLabel4))
                    .addGroup(GestionCategoriePanelLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(84, Short.MAX_VALUE))
        );
        GestionCategoriePanelLayout.setVerticalGroup(
            GestionCategoriePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GestionCategoriePanelLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel4)
                .addGap(36, 36, 36)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addGroup(GestionCategoriePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdAddCat)
                    .addComponent(cmdDelCat))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        getContentPane().add(GestionCategoriePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 590, 480));

        ajouterCategorieFormPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel10.setText("Ajouter une nouvelle Catégorie");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setText("Nom Catégorie");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setText("Code Catégorie");

        txtNomCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCatActionPerformed(evt);
            }
        });

        cmdSaveCat.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cmdSaveCat.setText("Enregistrer");
        cmdSaveCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSaveCatActionPerformed(evt);
            }
        });

        cmdCancelCat.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cmdCancelCat.setText("Annuler");

        javax.swing.GroupLayout ajouterCategorieFormPanelLayout = new javax.swing.GroupLayout(ajouterCategorieFormPanel);
        ajouterCategorieFormPanel.setLayout(ajouterCategorieFormPanelLayout);
        ajouterCategorieFormPanelLayout.setHorizontalGroup(
            ajouterCategorieFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ajouterCategorieFormPanelLayout.createSequentialGroup()
                .addGroup(ajouterCategorieFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ajouterCategorieFormPanelLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel12)
                        .addGap(44, 44, 44)
                        .addComponent(txtCodeCat, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ajouterCategorieFormPanelLayout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(jLabel10)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ajouterCategorieFormPanelLayout.createSequentialGroup()
                .addContainerGap(66, Short.MAX_VALUE)
                .addGroup(ajouterCategorieFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ajouterCategorieFormPanelLayout.createSequentialGroup()
                        .addComponent(cmdSaveCat)
                        .addGap(72, 72, 72)
                        .addComponent(cmdCancelCat, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(113, 113, 113))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ajouterCategorieFormPanelLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(28, 28, 28)
                        .addComponent(txtNomCat, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(153, 153, 153))))
        );
        ajouterCategorieFormPanelLayout.setVerticalGroup(
            ajouterCategorieFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ajouterCategorieFormPanelLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel10)
                .addGap(49, 49, 49)
                .addGroup(ajouterCategorieFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNomCat, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(108, 108, 108)
                .addGroup(ajouterCategorieFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCodeCat, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(81, 81, 81)
                .addGroup(ajouterCategorieFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdSaveCat)
                    .addComponent(cmdCancelCat))
                .addContainerGap(92, Short.MAX_VALUE))
        );

        getContentPane().add(ajouterCategorieFormPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, 620, 490));

        GestionProduitsPanel.setBackground(new java.awt.Color(255, 255, 255));
        GestionProduitsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.darkGray, null));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel3.setText("Gestion des produits");

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Nom de Produit", "Catégorie", "Quantité", "Prix Unitaire"
            }
        ));
        jScrollPane1.setViewportView(table);

        cmdAjouterProduit.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cmdAjouterProduit.setText("Ajouter ");
        cmdAjouterProduit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAjouterProduitActionPerformed(evt);
            }
        });

        cmdModifier.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cmdModifier.setText("Modifier");
        cmdModifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdModifierActionPerformed(evt);
            }
        });

        cmdSupprimer.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cmdSupprimer.setText("Supprimer");
        cmdSupprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSupprimerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout GestionProduitsPanelLayout = new javax.swing.GroupLayout(GestionProduitsPanel);
        GestionProduitsPanel.setLayout(GestionProduitsPanelLayout);
        GestionProduitsPanelLayout.setHorizontalGroup(
            GestionProduitsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GestionProduitsPanelLayout.createSequentialGroup()
                .addGroup(GestionProduitsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GestionProduitsPanelLayout.createSequentialGroup()
                        .addGap(0, 36, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(GestionProduitsPanelLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(cmdAjouterProduit, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71)
                        .addComponent(cmdModifier, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(cmdSupprimer)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(GestionProduitsPanelLayout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        GestionProduitsPanelLayout.setVerticalGroup(
            GestionProduitsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GestionProduitsPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(GestionProduitsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdSupprimer)
                    .addComponent(cmdModifier)
                    .addComponent(cmdAjouterProduit))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        getContentPane().add(GestionProduitsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, 620, 490));

        gestionSortieStockPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel18.setText("Produit :");

        jLabel19.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel19.setText("Quantité :");

        jLabel20.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel20.setText("Destination :");

        jLabel21.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel21.setText("Date de Sortie :");

        txtD_S.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtD_SActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sorties Récentes", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14))); // NOI18N

        tableSS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Produit", "Quantité", "Destination"
            }
        ));
        jScrollPane4.setViewportView(tableSS);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        cmb_produit.setToolTipText("");

        cmdsaveSS.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cmdsaveSS.setText("Enregistrer la sortie");
        cmdsaveSS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdsaveSSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtD_S, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtDestination, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel18)
                                        .addGap(226, 226, 226)
                                        .addComponent(cmb_produit, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtQuantiteSS, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmdsaveSS))))
                        .addGap(26, 26, 26))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel21)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_produit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtQuantiteSS, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDestination, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtD_S, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdsaveSS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel17.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel17.setText("Sorties de Stock");

        javax.swing.GroupLayout gestionSortieStockPanelLayout = new javax.swing.GroupLayout(gestionSortieStockPanel);
        gestionSortieStockPanel.setLayout(gestionSortieStockPanelLayout);
        gestionSortieStockPanelLayout.setHorizontalGroup(
            gestionSortieStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gestionSortieStockPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gestionSortieStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gestionSortieStockPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel17))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        gestionSortieStockPanelLayout.setVerticalGroup(
            gestionSortieStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gestionSortieStockPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        getContentPane().add(gestionSortieStockPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, 620, 500));

        ajouterProduitPanel.setBackground(new java.awt.Color(255, 255, 255));
        ajouterProduitPanel.setForeground(new java.awt.Color(255, 255, 255));
        ajouterProduitPanel.setPreferredSize(new java.awt.Dimension(590, 480));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel5.setText("Code");

        txtPrix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrixActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel6.setText("Nom du Produit");

        cmdCancel.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        cmdCancel.setText("Annuler");
        cmdCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCancelActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel9.setText("Prix");

        cmdSave.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        cmdSave.setText("Enregistrer");
        cmdSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSaveActionPerformed(evt);
            }
        });

        txtCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodeActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel7.setText("Categorie");

        cmbCategorie.setModel(new DefaultComboBoxModel<>());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCode, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                            .addComponent(txtNom)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addGap(64, 64, 64)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPrix)
                            .addComponent(cmbCategorie, 0, 220, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(cmdSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 143, Short.MAX_VALUE)
                .addComponent(cmdCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNom, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbCategorie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrix, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdSave)
                    .addComponent(cmdCancel))
                .addGap(83, 83, 83))
        );

        lblAjouterProduit.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblAjouterProduit.setText("Ajouter un nouveau Produit");

        javax.swing.GroupLayout ajouterProduitPanelLayout = new javax.swing.GroupLayout(ajouterProduitPanel);
        ajouterProduitPanel.setLayout(ajouterProduitPanelLayout);
        ajouterProduitPanelLayout.setHorizontalGroup(
            ajouterProduitPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ajouterProduitPanelLayout.createSequentialGroup()
                .addGroup(ajouterProduitPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ajouterProduitPanelLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ajouterProduitPanelLayout.createSequentialGroup()
                        .addGap(164, 164, 164)
                        .addComponent(lblAjouterProduit)))
                .addGap(0, 43, Short.MAX_VALUE))
        );
        ajouterProduitPanelLayout.setVerticalGroup(
            ajouterProduitPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ajouterProduitPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lblAjouterProduit, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(ajouterProduitPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 590, 480));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdModifierActionPerformed
                                               
    // Vérifier si une ligne est sélectionnée
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Veuillez sélectionner un produit à modifier.", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Changer le titre et le texte du bouton
    lblAjouterProduit.setText("Modifier Un Produit");
    cmdSave.setText("Update");
    
    // Afficher le formulaire de modification et remplir avec les données sélectionnées
    afficherModifierProduit();
    remplirFormulaireDepuisTable();
    
    // Supprimer tous les ActionListeners existants du bouton Save
    for (ActionListener al : cmdSave.getActionListeners()) {
        cmdSave.removeActionListener(al);
    }
    
    // Ajouter un nouveau ActionListener pour la mise à jour
    cmdSave.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String code = txtCode.getText();
                String nom = txtNom.getText();
                Categorie cat = (Categorie) cmbCategorie.getSelectedItem();
                int idCat = cat.getIdCat();
                float prix = Float.parseFloat(txtPrix.getText());
              
                
                // Utiliser currentCode comme identifiant original
                pcontroller.updateProduit(currentCode, code, nom, prix,  idCat);
              
                
                    ajouterProduitPanel.setVisible(false);
                   GestionProduitsPanel.setVisible(true);
                     afficherproduits();
                       txtCode.setText("");
                txtNom.setText("");
                cmbCategorie.setSelectedIndex(0);
                txtCode.setText("");
                
                txtPrix.setText("");
               
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erreur: " + ex.getMessage(), "Erreur de modification", JOptionPane.ERROR_MESSAGE);
                System.out.println(ex.getMessage());
            }
        }
    });

    
    
    
   
       
    }//GEN-LAST:event_cmdModifierActionPerformed

    private void txtCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodeActionPerformed

    private void cmdSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSaveActionPerformed
          
    }//GEN-LAST:event_cmdSaveActionPerformed

    private void txtPrixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrixActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrixActionPerformed

    private void cmdAjouterProduitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAjouterProduitActionPerformed
         
        lblAjouterProduit.setText("Ajoute Un nouveau Produit");
    cmdSave.setText("Save");
   
    
    // Supprimer tous les ActionListeners existants du bouton Save
    for (ActionListener al : cmdSave.getActionListeners()) {
        cmdSave.removeActionListener(al);
    }
    
    // Ajouter un nouveau ActionListener pour la mise à jour
    cmdSave.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                 ajouterProduit();
                 txtCode.setText("");
                txtNom.setText("");
                cmbCategorie.setSelectedIndex(0);
                txtCode.setText("");
              
                txtPrix.setText("");
                    ajouterProduitPanel.setVisible(false);
                   GestionProduitsPanel.setVisible(true);
                     afficherproduits();

               
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erreur: " + ex.getMessage(), "Erreur de modification", JOptionPane.ERROR_MESSAGE);
                System.out.println(ex.getMessage());
            }
        }
    });

 
    }//GEN-LAST:event_cmdAjouterProduitActionPerformed

    private void cmdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCancelActionPerformed
       annlerAjouterProduit();
    }//GEN-LAST:event_cmdCancelActionPerformed

    private void cmdSupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSupprimerActionPerformed
     
            supprimerProduit();
 
    }//GEN-LAST:event_cmdSupprimerActionPerformed

    private void cmdESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdESActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdESActionPerformed

    private void cmdAddCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAddCatActionPerformed
      
       afficherAjouterCatForm();
            
    }//GEN-LAST:event_cmdAddCatActionPerformed

    private void cmdSaveCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSaveCatActionPerformed
       
    }//GEN-LAST:event_cmdSaveCatActionPerformed

    private void cmdDelCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDelCatActionPerformed
     
    }//GEN-LAST:event_cmdDelCatActionPerformed

    private void cmdGPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdGPActionPerformed
         afficherGestionProduits();
    }//GEN-LAST:event_cmdGPActionPerformed

    private void txtD_SActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtD_SActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtD_SActionPerformed

    private void cmdSaveESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSaveESActionPerformed
       
    }//GEN-LAST:event_cmdSaveESActionPerformed

    private void cmdsaveSSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdsaveSSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdsaveSSActionPerformed

    private void txtCodeCategorieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodeCategorieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodeCategorieActionPerformed

    private void cmdEnregistrerCateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEnregistrerCateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdEnregistrerCateActionPerformed

    private void cmdSuppCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSuppCatActionPerformed
       supprimerCat();
     
    }//GEN-LAST:event_cmdSuppCatActionPerformed

    private void txtNomCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomCatActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserIHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserIHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserIHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserIHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserIHM().setVisible(true);
            }
        });
    }
    
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel GestionCategoriePanel;
    private javax.swing.JPanel GestionProduitsPanel;
    private javax.swing.JLabel Produit;
    private javax.swing.JPanel ajouterCategorieFormPanel;
    private javax.swing.JPanel ajouterProduitPanel;
    private javax.swing.JComboBox<Categorie> cmbCategorie;
    private javax.swing.JComboBox<Produit> cmbProduit;
    private javax.swing.JComboBox<Produit> cmb_produit;
    private javax.swing.JButton cmdAddCat;
    private javax.swing.JButton cmdAjouterProduit;
    private javax.swing.JButton cmdBord;
    private javax.swing.JButton cmdCancel;
    private javax.swing.JButton cmdCancelCat;
    private javax.swing.JButton cmdDelCat;
    private javax.swing.JButton cmdES;
    private javax.swing.JButton cmdEnregistrerCate;
    private javax.swing.JButton cmdGP;
    private javax.swing.JButton cmdLogout;
    private javax.swing.JButton cmdModifier;
    private javax.swing.JButton cmdSS;
    private javax.swing.JButton cmdSave;
    private javax.swing.JButton cmdSaveCat;
    private javax.swing.JButton cmdSaveES;
    private javax.swing.JButton cmdSuppCat;
    private javax.swing.JButton cmdSupprimer;
    private javax.swing.JButton cmdTestCat;
    private javax.swing.JButton cmdsaveSS;
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JPanel gestionEntrerStockPanel;
    private javax.swing.JPanel gestionSortieStockPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblAjouterProduit;
    private javax.swing.JLabel lblValeurTotal;
    private javax.swing.JLabel lbltotalProduit;
    private javax.swing.JLabel nameUser;
    private javax.swing.JPanel naveBarePanel;
    private javax.swing.JPanel sideBarePanel;
    private javax.swing.JTable table;
    private javax.swing.JTable tableCategorie;
    private javax.swing.JTable tableCategories;
    private javax.swing.JTable tableES;
    private javax.swing.JTable tableSS;
    private javax.swing.JPanel testPanel;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtCodeCat;
    private javax.swing.JTextField txtCodeCategorie;
    private javax.swing.JTextField txtD_S;
    private javax.swing.JTextField txtDateReception;
    private javax.swing.JTextField txtDestination;
    private javax.swing.JTextField txtNameCat;
    private javax.swing.JTextField txtNom;
    private javax.swing.JTextField txtNomCat;
    private javax.swing.JTextField txtPrix;
    private javax.swing.JTextField txtQuantiteSS;
    private javax.swing.JTextField txtQuatiteS;
    private javax.swing.JTextField txtnumFature;
    // End of variables declaration//GEN-END:variables

private ProduitController pcontroller = new ProduitController();
private CategorieDAO cdao = new CategorieDAO();
private ProduitDAO pdao = new ProduitDAO();
private CategorieController ccontroller = new CategorieController();
private ArrayList<Categorie>list;

private ArrayList<Produit>listProduit;
private ArrayList<Produit>listProduit2;
private DefaultTableModel mrd ; 

private StockController sc = new StockController();
  

private static String currentCode;

    
    
    
    
    
}
