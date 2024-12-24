package Controller;

import java.time.LocalDate;
import javax.swing.*;
import Model.*;
import View.HolidayView;
import java.util.List;

public class HolidayController {
    private HolidayView view;
    private HolidayModel model;

    public HolidayController(HolidayView view, HolidayModel model) {
        this.view = view;
        this.model = model;
        initializeListeners();
        refreshDisplay();
    }

    private void initializeListeners() {
    	view.getajouterButton().addActionListener(e -> {
    	    System.out.println("Clic sur ajouterButton");
    	    try {
    	        addh(false);
    	    } catch (Exception ex) {
    	        showError(ex.getMessage());
    	    }
    	});

    	view.getmodButton().addActionListener(e -> {
    	    System.out.println("Modification du congé : " + view.getmodButton().getText() + " - État : " + view.getmodButton().isEnabled());
    	    addh(true); // true pour une mise à jour
    	});

    	view.getsuppButton().addActionListener(e -> {
    	    System.out.println("Suppression du congé : " + view.getsuppButton().getText() + " - État : " + view.getsuppButton().isEnabled());
    	    deleteh();
    	});

    	view.getHolidayTable().getSelectionModel().addListSelectionListener(e -> {
    	    System.out.println("Sélection de la ligne dans le tableau de congé.");
    	    populateFieldsFromTable();
    	});

    	view.getRefButton().addActionListener(e -> {
    	    System.out.println("Rafraîchissement de l'affichage des congés.");
    	    refreshDisplay();
    	});
    }
    private void addh(boolean isUpdate) {
        try {
            int holidayId = isUpdate ? getSelectedHolidayId() : 0;
            int employeeId = getSelectedEmployeeId();
            LocalDate startDate = parseDate(view.getStartDateField().getText().trim());
            LocalDate endDate = parseDate(view.getEndDateField().getText().trim());
            Typeh holidayType = (Typeh) view.getHolidayTypeComboBox().getSelectedItem();

            // Vérification des dates : la date de début doit être actuelle ou future
            if (startDate.isBefore(LocalDate.now())) {
                showError("La date de début doit être égale ou postérieure à la date actuelle.");
                return;
            }

            // Vérification que la date de début est antérieure à la date de fin
            if (!startDate.isBefore(endDate)) {
                showError("La date de début doit être antérieure à la date de fin.");
                return;
            }

            // Vérification que la date de fin est égale ou postérieure à la date de début
            if (endDate.isBefore(startDate)) {
                showError("La date de fin doit être égale ou postérieure à la date de début.");
                return;
            }

            // Appel au modèle pour ajouter ou mettre à jour le congé
            boolean success = model.addh(holidayId, employeeId, startDate, endDate, holidayType, isUpdate);
            if (success) {
                showMessage(isUpdate ? "Congé mis à jour avec succès !" : "Congé ajouté avec succès !", "Confirmation");
                refreshDisplay();
            } else {
                showError("Échec de l'opération sur le congé : Solde insuffisant ou congé trop long.");
            }
        } catch (Exception e) {
            showError("Erreur : " + e.getMessage());
        }
    }
    private void deleteh() {
        try {
            int holidayId = getSelectedHolidayId();
            if (model.deleteh(holidayId)) {
                showMessage("Congé supprimé avec succès !", "Confirmation");
                refreshDisplay();
            } else {
                showError("Échec de la suppression du congé.");
            }
        } catch (Exception e) {
            showError("Erreur : " + e.getMessage());
        }
    }

    private void populateFieldsFromTable() {
        int selectedRow = view.getHolidayTable().getSelectedRow();
        if (selectedRow < 0) return;

        try {
            // Remplir les champs avec les valeurs extraites du tableau
            Object employeeValue = view.getHolidayTable().getValueAt(selectedRow, 1);
            for (int i = 0; i < view.getEmployeeComboBox().getItemCount(); i++) {
                if (view.getEmployeeComboBox().getItemAt(i).toString().equals(employeeValue.toString())) {
                    view.getEmployeeComboBox().setSelectedItem(view.getEmployeeComboBox().getItemAt(i));
                    break;
                }
            }
            view.getStartDateField().setText(view.getHolidayTable().getValueAt(selectedRow, 2).toString());
            view.getEndDateField().setText(view.getHolidayTable().getValueAt(selectedRow, 3).toString());
            view.getHolidayTypeComboBox().setSelectedItem(view.getHolidayTable().getValueAt(selectedRow, 4).toString());
        } catch (Exception e) {
            showError("Erreur lors du remplissage des champs : " + e.getMessage());
        }
    }

    private void refreshDisplay() {
        List<Object[]> holidays = model.getHolidays();
        view.getTableModelh().setRowCount(0);
        for (Object[] holiday : holidays) {
            view.getTableModelh().addRow(holiday);
        }
    }

    private int getSelectedHolidayId() throws Exception {
        int selectedRow = view.getHolidayTable().getSelectedRow();
        if (selectedRow < 0) throw new Exception("Aucun congé sélectionné.");
        return parseInteger(view.getHolidayTable().getValueAt(selectedRow, 0));
    }

    private int getSelectedEmployeeId() throws Exception {
        Object selectedEmployee = view.getEmployeeComboBox().getSelectedItem();
        if (selectedEmployee instanceof Employe) {
            return ((Employe) selectedEmployee).getId();
        }
        throw new Exception("Employé sélectionné invalide.");
    }

    private LocalDate parseDate(String dateText) {
        return LocalDate.parse(dateText);
    }

    private int parseInteger(Object value) throws Exception {
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof String) {
            return Integer.parseInt((String) value);
        }
        throw new Exception("Valeur entière invalide : " + value);
    }

    private void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
