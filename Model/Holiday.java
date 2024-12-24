package Model;

import java.time.LocalDate;

public class Holiday {
    private int id; 
    private int idEmployee;
    private LocalDate startDate, endDate;
    private Typeh conge;

    public Holiday(int id, int idEmployee, LocalDate startDate, LocalDate endDate, Typeh conge) {
        this.id = id;
        this.idEmployee = idEmployee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.conge = conge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Typeh getConge() {
        return conge;
    }

    public void setConge(Typeh conge) {
        this.conge = conge;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }
}
