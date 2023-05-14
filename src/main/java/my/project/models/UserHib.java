package my.project.models;

import javax.persistence.*;
import java.util.List;


@Entity

@Table(name = "salary_user")
public class UserHib {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String lastName;
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "userrole")
    private UserRoleHib userRole;
   @OneToMany
    private List<RecordHib> records;

    public UserHib() {
    }

    public UserHib(Integer id, String lastName,String password, UserRoleHib userRole, List<RecordHib> records) {
        this.id = id;
        this.lastName = lastName;
        this.password = password;
        this.userRole = userRole;
        this.records = records;
    }
    public UserHib(String lastName,String password) {

        this.lastName = lastName;
        this.password = password;
    }
    public UserHib(Integer id, String lastName, UserRoleHib userRole) {
        this.id = id;
        this.lastName = lastName;
        this.userRole = userRole;
    }

    public UserHib(String lastName, UserRoleHib userRole) {
        this.lastName = lastName;
        this.userRole = userRole;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserRoleHib getUserRoleHib() {
        return userRole;
    }

    public void setUserRoleHib(UserRoleHib userRole) {
        this.userRole = userRole;
    }

    public List<RecordHib> getRecords() {
        return records;
    }

    public void setRecords(List<RecordHib> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return this.id + " " + this.lastName + " " + this.password + " " + this.userRole+ " "+ this.records;
    }
}
