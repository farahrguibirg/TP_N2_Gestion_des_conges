package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import DAO.EmployeImpl;
import Model.Employe;
import Model.Poste;
import Model.Role;
import java.awt.event.ActionListener;

public class EmployeView extends JPanel {
    public JTextField txtNom, txtPrenom, txtEmail, txtTelephone, txtSalaire;
    public JComboBox<Role> comboRole;
    public JComboBox<Poste> comboPoste;
    public DefaultTableModel model;
    public JButton btnAjouter, btnModifier, btnSupprimer, btnAfficher;
    public JTable table;

    public EmployeView() {
        setLayout(null);

        // Initialisation des champs de texte
        txtNom = new JTextField();
        txtPrenom = new JTextField();
        txtEmail = new JTextField();
        txtTelephone = new JTextField();
        txtSalaire = new JTextField();

        // Initialisation des ComboBox
        comboRole = new JComboBox<>(Role.values());
        comboPoste = new JComboBox<>(Poste.values());

        // Initialisation des boutons
        btnAjouter = new JButton("Ajouter");
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");
        btnAfficher = new JButton("Réinitialiser");

        // Placement des composants
        addLabelAndField("Nom:", 20, 20, txtNom, 600);
        addLabelAndField("Prénom:", 20, 60, txtPrenom, 600);
        addLabelAndField("Email:", 20, 100, txtEmail, 600);
        addLabelAndField("Téléphone:", 20, 140, txtTelephone, 600);
        addLabelAndField("Salaire:", 20, 180, txtSalaire, 600);
        addComboBox("Rôle:", 20, 220, comboRole, 600);
        addComboBox("Poste:", 20, 260, comboPoste, 600);

        // Placement des boutons
        btnAjouter.setBounds(20, 320, 150, 30);
        btnModifier.setBounds(180, 320, 150, 30);
        btnSupprimer.setBounds(340, 320, 150, 30);
        btnAfficher.setBounds(500, 320, 150, 30);

        add(btnAjouter);
        add(btnModifier);
        add(btnSupprimer);
        add(btnAfficher);

        // Table pour afficher les employés
        model = new DefaultTableModel(
            new String[]{"ID", "Nom", "Prénom", "Téléphone", "Email", "Salaire", "Rôle", "Poste", "Solde"}, 
            0
        );
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 380, 940, 150);
        add(scrollPane);

        hideIdColumn(); // Masquer la colonne ID à l'utilisateur
    }

    // Méthodes auxiliaires pour ajouter les labels et les champs de texte
    private void addLabelAndField(String label, int x, int y, JTextField textField, int width) {
        JLabel lbl = new JLabel(label);
        lbl.setBounds(x, y, 100, 30);
        add(lbl);
        textField.setBounds(x + 100, y, width, 30);
        add(textField);
    }

    private void addComboBox(String label, int x, int y, JComboBox<?> comboBox, int width) {
        JLabel lbl = new JLabel(label);
        lbl.setBounds(x, y, 100, 30);
        add(lbl);
        comboBox.setBounds(x + 100, y, width, 30);
        add(comboBox);
    }

    // Méthode pour cacher la colonne ID
    private void hideIdColumn() {
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    // Méthode pour effacer les champs
    public void clearFields() {
        txtNom.setText("");
        txtPrenom.setText("");
        txtEmail.setText("");
        txtTelephone.setText("");
        txtSalaire.setText("");
        comboRole.setSelectedIndex(0);
        comboPoste.setSelectedIndex(0);
    }

    // Met à jour le tableau avec les données des employés
    public void populateEmployeeTable(EmployeImpl daoEmployee) {
        model.setRowCount(0);
        for (Employe emp : daoEmployee.getAll()) {
            model.addRow(new Object[]{
                emp.getId(),
                emp.getNom(),
                emp.getPrenom(),
                emp.getTelephone(),
                emp.getEmail(),
                emp.getSalaire(),
                emp.getRole(),
                emp.getPoste(),
                emp.getSolde()  // Afficher le solde mis à jour ici
            });
        }
        model.fireTableDataChanged(); // Actualiser le tableau après les modifications
    }

    public DefaultTableModel getModel() {
        return model;
    }

    // Getters pour accéder aux champs et boutons
    public JTextField getNomField() {
        return txtNom;
    }

    public JTextField getPrenomField() {
        return txtPrenom;
    }

    public JTextField getEmailField() {
        return txtEmail;
    }

    public JTextField getTelephoneField() {
        return txtTelephone;
    }

    public JTextField getSalaireField() {
        return txtSalaire;
    }

    public JComboBox<Role> getRoleComboBox() {
        return comboRole;
    }

    public JComboBox<Poste> getPosteComboBox() {
        return comboPoste;
    }

    public JButton getAddButton() {
        return btnAjouter;
    }

    public JButton getUpdateButton() {
        return btnModifier;
    }

    public JButton getDeleteButton() {
        return btnSupprimer;
    }

    public JButton getRefreshButton() {
        return btnAfficher;
    }

    public JTable getEmployeeTable() {
        return table;
    }

    // Méthode pour ajouter les listeners aux boutons
    public void addButtonListeners(ActionListener addListener, ActionListener modifyListener, ActionListener deleteListener, ActionListener resetListener) {
        btnAjouter.addActionListener(addListener);
        btnModifier.addActionListener(modifyListener);
        btnSupprimer.addActionListener(deleteListener);
        btnAfficher.addActionListener(resetListener);
    }
}
