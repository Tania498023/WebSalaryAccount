package my.project.salary;



import my.project.help.Helpers;
import my.project.models.RecordHib;
import my.project.models.UserHib;

import java.time.LocalDate;
import java.util.List;

public class Person {
    public LocalDate startDates;
    public LocalDate endDates;

    public int sumHours;
    private static UserHib worker;
    protected static double totalPays;
    private List<RecordHib> workRecords;

    public Person(UserHib work, List<RecordHib> workRecords,LocalDate startDates, LocalDate endDates) {
        this.startDates = startDates;
        this.endDates = endDates;
        this.worker = work;
        this.workRecords = workRecords;
    }

    public LocalDate getStartDates() {
        return startDates;
    }

    public void setStartDates(LocalDate startDates) {
        this.startDates = startDates;
    }

    public LocalDate getEndDates() {
        return endDates;
    }

    public void setEndDates(LocalDate endDates) {
        this.endDates = endDates;
    }

    public int getSumHours() {
        return sumHours;
    }

    public void setSumHours(int sumHours) {
        this.sumHours = sumHours;
    }

    public  UserHib getWorker() {
        return worker;
    }

    public static void setWorker(UserHib work) {
        Person.worker = work;
    }

    public  double getTotalPays() {
        return totalPays;
    }

    public static void setTotalPays(double totalPays) {
        Person.totalPays = totalPays;
    }

    public List<RecordHib> getWorkRecords() {
        return workRecords;
    }

    public void setWorkRecords(List<RecordHib> workRecords) {
        this.workRecords = workRecords;
    }

    public void printRepPerson(){
        System.out.println("----------------------------------------------------");
        System.out.println("Отчет по сотруднику \t" + worker.getLastName() + "\t" + worker.getUserRoleHib() + "\t за период с \t " + startDates.toString()
                + "\t по" +"\t" + endDates.toString());
        System.out.println("----------------------------------------------------");
        for (RecordHib item:workRecords) {
           if((Helpers.getMilliSecFromDate(item.getDate()) >= Helpers.getMilliSecFromDate(startDates)) && (Helpers.getMilliSecFromDate(item.getDate()) <= Helpers.getMilliSecFromDate(endDates))){

    if(worker.getLastName().equals(item.getLastName().getLastName())) {
        System.out.println(item.getDate().toString() + "\t" + item.getHour() + "\t" + item.getMessage());
        sumHours += item.getHour();
                 }
            }
        }
    }
    public void printHourPerson(){

        for (RecordHib itemS :workRecords) {
            if((Helpers.getMilliSecFromDate(itemS.getDate()) >= Helpers.getMilliSecFromDate(startDates)) && (Helpers.getMilliSecFromDate(itemS.getDate()) <= Helpers.getMilliSecFromDate(endDates))){

                if(worker.getLastName().equals(itemS.getLastName().getLastName())) {
                    System.out.println(itemS.getDate().toString() + "\t" + itemS.getHour() + "\t" + itemS.getMessage());
                    sumHours += itemS.getHour();
                }
            }
        }

    }
}
