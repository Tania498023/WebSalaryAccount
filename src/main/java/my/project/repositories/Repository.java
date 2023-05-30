package my.project.repositories;

import my.project.app.MyDbWork;
import my.project.models.RecordHib;
import my.project.models.UserHib;
import my.project.models.UserRoleHib;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
   public static MyDbWork listUsersInDb;
    public Repository() {
      listUsersInDb = new MyDbWork();
        CheckUsersInDb();

    }
    public void CheckUsersInDb(){

        List<UserHib> userHibList = listUsersInDb.GetUserHibList();//список объектов user из таблицы(БД)
        if (userHibList.isEmpty()) {
            listUsersInDb.SaveSessionUser(addFakeDataUser());

         }

    }
        public UserHib addFakeDataUser () {

            UserHib users = new UserHib("ЯЯ", UserRoleHib.MANAGER,"111111");
            return users;
        }
        //метод пока не нужен,в будущем удалить, если не будем использовать
    public RecordHib addFakeDataRecord () {

        RecordHib rec = new RecordHib(LocalDate.now().minusDays(3),8,"ok",addFakeDataUser());
        return rec;
    }
    public RecordHib addFakeDataRecord (UserHib us) {

        RecordHib rec = new RecordHib(LocalDate.now().minusDays(3),8,"ok",us);
        return rec;
    }
    public List<UserHib> findAll() {

      return listUsersInDb.GetUserHibList();

    }
   public UserHib findUserByName(String lastName){
        UserHib curUs = null;

        for (UserHib user : listUsersInDb.GetUserHibList()){
            if(user.getLastName().equals(lastName)){
                curUs = user;
            }
        }
       return curUs;
    }
    public List<RecordHib> findAllRec() {

        return listUsersInDb.GetRecHibList();

    }
    @Override
    public  List<RecordHib> findRecByName(String name) {
        List <RecordHib> recByName = new ArrayList<>();
    for (RecordHib rec : listUsersInDb.GetRecHibList()) {
        if (rec.getLastName().getLastName().equals(name)) {
            recByName.add(rec);
        }
      }
    return recByName;
}
    @Override
    public void save(UserHib user) {
      listUsersInDb.SaveSessionUser(user);
    }
    @Override
    public void saveRec(RecordHib record) {
        listUsersInDb.SaveSessionRec(record);
    }
    @Override
    public boolean isExist(String name, String password) {
        for (UserHib user : listUsersInDb.GetUserHibList()) {
            if (user.getLastName().equals(name)&&user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }


}
