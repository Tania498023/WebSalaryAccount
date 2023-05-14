package my.project.repositories;


import my.project.models.RecordHib;
import my.project.models.UserHib;

import java.util.List;

public interface IRepository {
    UserHib addFakeDataUser ();
 RecordHib addFakeDataRecord ();


    List<UserHib> findAll();
    void save(UserHib user);
    boolean isExist(String name, String password);
}
