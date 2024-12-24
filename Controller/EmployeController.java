package Controller;

import Model.*;
import View.*;

import javax.swing.*;
import java.util.List;

public class EmployeController {
    private EmployeView view;
    private EmployeModel model;

    public EmployeController(EmployeModel model, EmployeView view) {
        this.view = view;
        this.model = model;
        initializeListeners();
        displayEmployees();
    }

    private void initializeListeners() {
        view.getAddButton().addActionListener(e -> addEmployee());
        view.getUpdateButton().addActionListener(e -> updateEmployee());
        view.getDeleteButton().addActionListener(e -> deleteEmployee());
        view.getRefreshButton().addActionListener(e -> displayEmployees());
        view.getEmployeeTable().getSelectionModel().addListSelectionListener(e -> populateFieldsFromTable());
    }

    private void addEmployee() {
        try {
            String nom = view.txtNom.getText().trim();
            String prenom = view.txtPrenom.getText().trim();
            String email = view.txtEmail.getText().trim();
            String telephone = view.txtTelephone.getText().trim();
            double salaire = Double.parseDouble(view.txtSalaire.getText().trim());
            Role role = (Role) view.comboRole.getSelectedItem();
            Poste poste = (Poste) view.comboPoste.getSelectedItem();

            if (model.addEmploye(nom, prenom, email, telephone, salaire, role, poste)) {
                showMessage("Employé ajouté avec succès !");
                displayEmployees();
            }
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void updateEmployee() {
        try {
            int selectedRow = view.getEmployeeTable().getSelectedRow();
            if (selectedRow == -1) throw new Exception("Aucun employé sélectionné.");

            int id = Integer.parseInt(view.getModel().getValueAt(selectedRow, 0).toString()); // Récupère l'ID masqué
            String nom = view.txtNom.getText().trim();
            String prenom = view.txtPrenom.getText().trim();
            String email = view.txtEmail.getText().trim();
            String telephone = view.txtTelephone.getText().trim();
            double salaire = Double.parseDouble(view.txtSalaire.getText().trim());
            Role role = (Role) view.comboRole.getSelectedItem();
            Poste poste = (Poste) view.comboPoste.getSelectedItem();

            // On n'inclut plus la demande de mise à jour du solde ici
            if (model.updateEmploye(id, nom, prenom, email, telephone, salaire, role, poste)) {
                showMessage("Employé mis à jour avec succès !");
                displayEmployees();
            }
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void deleteEmployee() {
        try {
            int selectedRow = view.getEmployeeTable().getSelectedRow();
            if (selectedRow == -1) throw new Exception("Aucun employé sélectionné.");
            
            int id = Integer.parseInt(view.getModel().getValueAt(selectedRow, 0).toString()); // Récupère l'ID masqué

            if (model.deleteEmploye(id)) {
                showMessage("Employé supprimé avec succès !");
                displayEmployees();
            }
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void displayEmployees() {
        List<Object[]> employees = model.getAllElements();
        view.getModel().setRowCount(0);
        for (Object[] employee : employees) {
            view.getModel().addRow(new Object[]{
                employee[0], // ID (colonne masquée)
                employee[1], // Nom
                employee[2], // Prénom
                employee[3], // Téléphone
                employee[4], // Email
                employee[5], // Salaire
                employee[6], // Rôle
                employee[7], // Poste
                employee[8]  // Solde
            });
        }
        // Cache la colonne de l'ID
        view.getEmployeeTable().getColumnModel().getColumn(0).setMinWidth(0);
        view.getEmployeeTable().getColumnModel().getColumn(0).setMaxWidth(0);
        view.getEmployeeTable().getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    private void populateFieldsFromTable() {
        int selectedRow = view.getEmployeeTable().getSelectedRow();
        if (selectedRow != -1) {
            view.txtNom.setText(view.getModel().getValueAt(selectedRow, 1).toString());
            view.txtPrenom.setText(view.getModel().getValueAt(selectedRow, 2).toString());
            view.txtEmail.setText(view.getModel().getValueAt(selectedRow, 4).toString());
            view.txtTelephone.setText(view.getModel().getValueAt(selectedRow, 3).toString());
            view.txtSalaire.setText(view.getModel().getValueAt(selectedRow, 5).toString());
            view.comboRole.setSelectedItem(view.getModel().getValueAt(selectedRow, 6));
            view.comboPoste.setSelectedItem(view.getModel().getValueAt(selectedRow, 7));
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(view, message);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(view, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
