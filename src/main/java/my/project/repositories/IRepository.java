package my.project.repositories;


import my.project.models.RecordHib;
import my.project.models.UserHib;

import java.util.List;

public interface IRepository {
    UserHib addFakeDataUser ();
    RecordHib addFakeDataRecord ();

    List<UserHib> findAll();
    UserHib findUserByName(String lastName);
    UserHib findUserById(int id);
    void save(UserHib user);
    void update(UserHib user);

    List<RecordHib> findAllRec();
    List<RecordHib> findRecByName(String name);
    void saveRec(RecordHib record);
    boolean isExist(String name, String password);


}
