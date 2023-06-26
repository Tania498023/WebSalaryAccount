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
    void delete(UserHib user);

    List<RecordHib> findAllRec();
    List<RecordHib> findRecByName(String name);
    RecordHib findRecById(int id);
    void saveRec(RecordHib record);
    void deleteRec(RecordHib record);
    void updateRec(RecordHib record);
    boolean isExist(String name, String password);


}
