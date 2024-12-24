package Model;

import DAO.EmployeImpl;
import java.util.ArrayList;
import java.util.List;

public class EmployeModel {
    private EmployeImpl dao;

    public EmployeModel(EmployeImpl dao) {
        this.dao = dao;
    }

    // Méthode pour ajouter un employé
    public boolean addEmploye(String nom, String prenom, String email, String telephone, double salaire, Role role, Poste poste) {
        if (salaire <= 0) {
            System.out.println("Le salaire doit être supérieur à 0 !");
            return false;
        }
        if (email == null || !email.contains("@")) {
            System.out.println("L'email n'est pas valide !");
            return false;
        }
        Employe nvEmploye = new Employe(0, nom, prenom, email, telephone, salaire, role, poste);
        dao.add(nvEmploye);
        return true;
    }

    // Méthode pour modifier un employé sans gérer son solde
    public boolean updateEmploye(int id, String nom, String prenom, String email, String telephone, double salaire, Role role, Poste poste) {
        Employe emp = new Employe(id, nom, prenom, email, telephone, salaire, role, poste);
        dao.update(emp);  // Mise à jour de l'employé dans la base de données sans toucher au solde
        return true;
    }

    // Méthode pour supprimer un employé
    public boolean deleteEmploye(int id) {
        dao.delete(id);
        return true;
    }

    // Méthode pour récupérer tous les employés
    public List<Object[]> getAllElements() {
        List<Object[]> data = new ArrayList<>();
        try {
            List<Employe> employees = dao.getAll();
            for (Employe element : employees) {
                Object[] row = {
                    element.getId(),
                    element.getNom(),
                    element.getPrenom(),
                    element.getTelephone(),
                    element.getEmail(),
                    element.getSalaire(),
                    element.getRole(),
                    element.getPoste(),
                    element.getSolde(),  // Conserver l'affichage du solde dans le tableau, mais ne pas le modifier
                };
                data.add(row);
            }
        } catch (Exception e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
        }
        return data;
    }
}
