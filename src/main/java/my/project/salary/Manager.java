package my.project.salary;



import my.project.models.RecordHib;
import my.project.models.UserHib;

import java.time.LocalDate;
import java.util.List;

public class Manager extends Staff {
    public static  double monthBonuses = 20000;

    public static int stavka = 200000;



    public Manager(UserHib user, List<RecordHib> timeRecord, LocalDate startDate, LocalDate endDate) {
        super(stavka, user, timeRecord, startDate, endDate,monthBonuses);

    }
}
