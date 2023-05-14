package my.project.salary;



import my.project.help.Helpers;
import my.project.help.Settings;
import my.project.models.RecordHib;
import my.project.models.UserHib;

import java.time.LocalDate;
import java.util.List;

public class Freelancer extends Person {

    public Freelancer(UserHib user, List<RecordHib> timeRecord, LocalDate startDate, LocalDate endDate) {
        super(user, timeRecord, startDate, endDate);

        double totalPay = 0;
        for (RecordHib timeRecords : timeRecord) {
            if(user.getLastName().equals(timeRecords.getLastName().getLastName()))
                if(Helpers.getMilliSecFromDate(timeRecords.getDate())>= Helpers.getMilliSecFromDate(startDate) && Helpers.getMilliSecFromDate(timeRecords.getDate())<= Helpers.getMilliSecFromDate(endDate))
                {
                totalPay += Settings.PAYPERHOUR * timeRecords.getHour();
                }
           totalPays = totalPay;
        }
    }
}
