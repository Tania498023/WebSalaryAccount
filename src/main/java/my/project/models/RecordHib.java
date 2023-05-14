package my.project.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "salary_record")
public class RecordHib {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;
    private Integer hour;
    private String message;


   @ManyToOne
   @JoinColumn(name = "lastName")
    private UserHib lastName;
    public RecordHib() {
    }

    public RecordHib(Integer id, LocalDate date, Integer hour, String message, UserHib lastName) {
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.message = message;
        this.lastName = lastName;

    }

    public RecordHib(LocalDate date, Integer hour, String message) {
        this.date = date;
        this.hour = hour;
        this.message = message;
    }

    public RecordHib(LocalDate date, Integer hour, String message, UserHib lastName) {
        this.date = date;
        this.hour = hour;
        this.message = message;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserHib getLastName() {
        return lastName;
    }

    public void setLastName(UserHib lastName) {
        this.lastName = lastName;
    }
    @Override
    public String toString() {
        return this.id + " " + this.date + " " + this.hour+ " "+ this.message+" "+ this.lastName;
    }
}
