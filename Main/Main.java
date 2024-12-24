/*package main;

import Controller.*;
import DAO.*;
import Model.*;
import View.*;

public class Main {
    public static void main(String[] args) {
        MainView mainView = new MainView();
        EmployeView viewEmployee = mainView.getEmployeeView();
        HolidayView viewHoliday = mainView.getHolidayPanel();

        HolidayImpl daoHoliday = new HolidayImpl();
        EmployeImpl daoEmployee = new EmployeImpl();

        HolidayModel modelHoliday = new HolidayModel(daoHoliday, daoEmployee);
        EmployeModel modelEmployee = new EmployeModel(daoEmployee);
        
        new HolidayController(viewHoliday, modelHoliday);
        new EmployeController(modelEmployee, viewEmployee);
    }
}*/
package main;

import Controller.*;
import DAO.*;
import Model.*;
import View.*;

public class Main {
    public static void main(String[] args) {
        try {
            MainView mainView = new MainView();
            EmployeView viewEmployee = mainView.getEmployeeView();
            HolidayView viewHoliday = mainView.getHolidayPanel();
            
            HolidayImpl daoHoliday = new HolidayImpl();
            EmployeImpl daoEmployee = new EmployeImpl();
            
            HolidayModel modelHoliday = new HolidayModel(daoHoliday, daoEmployee);
            EmployeModel modelEmployee = new EmployeModel(daoEmployee);
            
            HolidayController holidayController = new HolidayController(viewHoliday, modelHoliday);
            EmployeController employeeController = new EmployeController(modelEmployee, viewEmployee);
            
            mainView.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}