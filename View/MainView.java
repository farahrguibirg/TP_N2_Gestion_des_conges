package View;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import DAO.EmployeImpl;
import DAO.HolidayImpl;

public class MainView extends JFrame {
    private EmployeView employeeView;
    private HolidayView holidayPanel;
    private JTabbedPane tabbedPane;

    public MainView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setTitle("Gestion des employ�s et des cong�s");

        employeeView = new EmployeView();
        holidayPanel = new HolidayView();
        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Employ�s", employeeView);
        tabbedPane.addTab("Cong�s", holidayPanel);

        add(tabbedPane);

        EmployeImpl daoEmployee = new EmployeImpl();
        HolidayImpl daoHoliday = new HolidayImpl();

        employeeView.populateEmployeeTable(daoEmployee);
       // holidayPanel.populateHolidayTable(daoHoliday, daoEmployee);
        //holidayPanel.populateEmployeeComboBox(daoEmployee);

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainView();
    }

    public EmployeView getEmployeeView() {
        return employeeView;
    }

    public HolidayView getHolidayPanel() {
        return holidayPanel;
    }
}
