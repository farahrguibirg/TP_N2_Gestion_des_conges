package Model;

import DAO.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class HolidayModel {
    private HolidayImpl DAOh;
    private EmployeImpl DAOe;

    public HolidayModel(HolidayImpl DAOh, EmployeImpl DAOe) {
        this.DAOh = DAOh;
        this.DAOe = DAOe;
    }

    public boolean addh(int holidayId, int idEmployee, LocalDate startDate, LocalDate endDate, Typeh conge, boolean isUpdate) {
        Employe emp = DAOe.findById(idEmployee);
        if (emp == null) {
            System.err.println("Erreur : Employé introuvable.");
            return false;
        }

        int day = calculateDaysBetween(startDate, endDate);
        if (day > 25 || emp.getSolde() < day) {
            System.err.println("Erreur : Solde insuffisant ou durée trop longue.");
            return false;
        }

        if (isUpdate) {
            Holiday existingHoliday = DAOh.findById(holidayId);
            if (existingHoliday == null) {
                System.err.println("Erreur : Congé introuvable.");
                return false;
            }

            int previousDuration = calculateDaysBetween(existingHoliday.getStartDate(), existingHoliday.getEndDate());
            emp.setSolde(emp.getSolde() + previousDuration);
        }

        int newSolde = emp.getSolde() - day;

        // Vérification du solde avant de le mettre à jour
        if (newSolde <= 0) {
            System.err.println("Erreur : Le solde de l'employé ne peut pas être égal ou inférieur à zéro.");
            return false;
        }

        emp.setSolde(newSolde);
        DAOe.update(emp);

        Holiday holiday = new Holiday(holidayId, idEmployee, startDate, endDate, conge);
        if (isUpdate) {
            DAOh.update(holiday);
        } else {
            DAOh.add(holiday);
        }
        return true;
    }

    public boolean deleteh(int holidayId) {
        Holiday holiday = DAOh.findById(holidayId);
        if (holiday == null) {
            System.err.println("Erreur : Congé introuvable.");
            return false;
        }

        Employe emp = DAOe.findById(holiday.getIdEmployee());
        if (emp != null) {
            int dayDiff = calculateDaysBetween(holiday.getStartDate(), holiday.getEndDate());
            emp.setSolde(emp.getSolde() + dayDiff);
            DAOe.update(emp);
        }

        DAOh.delete(holidayId);
        return true;
    }

    public List<Object[]> getHolidays() {
        List<Object[]> data = new ArrayList<>();
        for (Holiday holiday : DAOh.getAll()) {
            Employe emp = DAOe.findById(holiday.getIdEmployee());
            String employeeName = (emp != null) ? emp.getNom() + " " + emp.getPrenom() : "Inconnu";
            data.add(new Object[] {
                holiday.getId(),
                employeeName,
                holiday.getStartDate(),
                holiday.getEndDate(),
                holiday.getConge()
            });
        }
        return data;
    }

    private int calculateDaysBetween(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }
}
