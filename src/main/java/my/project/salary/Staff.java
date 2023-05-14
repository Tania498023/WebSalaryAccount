package my.project.salary;




import my.project.help.Helpers;
import my.project.help.Settings;
import my.project.models.RecordHib;
import my.project.models.UserHib;
import my.project.models.UserRoleHib;

import java.time.LocalDate;
import java.util.List;

public class Staff extends Person {
    public int totalHours;
    private double monthSalarys;
    double bonuses;

    public double getMonthSalary() {
        return monthSalarys;
    }


    public Staff(double monthSalary, UserHib user, List<RecordHib> timeRecord, LocalDate startDate, LocalDate endDate, double bonus) {
      super(user,timeRecord,startDate, endDate);
        bonuses = bonus;
        monthSalarys = monthSalary;
        raschetTotalPay(user.getLastName(), timeRecord, startDate, endDate);
    }

    private void raschetTotalPay(String name, List<RecordHib> timeRecord, LocalDate startDate, LocalDate endDate) {

        double payPerHour = monthSalarys / Settings.WORKHOURSINMONTH;
        double totalPay = 0;
        double bonusPerDay = bonuses / Settings.WORKHOURSINMONTH * Settings.WORKHOURSINDAY;


        for (RecordHib timeRecordses : timeRecord) {
            if (name.equals(timeRecordses.getLastName().getLastName()))
                 if (Helpers.getMilliSecFromDate(timeRecordses.getDate())>=Helpers.getMilliSecFromDate(startDate)&&Helpers.getMilliSecFromDate(timeRecordses.getDate())<=Helpers.getMilliSecFromDate(endDate))
                if (timeRecordses.getHour() <= Settings.WORKHOURSINDAY) {
                    totalPay += payPerHour * timeRecordses.getHour();
                    totalHours += timeRecordses.getHour();
                } else if (getWorker().getUserRoleHib() == UserRoleHib.MANAGER) {
                    totalPay += payPerHour * Settings.WORKHOURSINDAY + bonusPerDay;
                } else {
                    totalPay += payPerHour * timeRecordses.getHour() + (timeRecordses.getHour() - Settings.WORKHOURSINDAY) * payPerHour;
                }
            totalPays = totalPay;
        }
    }
}
