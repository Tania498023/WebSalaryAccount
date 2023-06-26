package my.project.servlets;

import my.project.help.Helpers;
import my.project.models.RecordHib;
import my.project.models.UserHib;
import my.project.repositories.IRepository;
import my.project.repositories.Repository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private IRepository usersRepository;

    @Override
    public void init() throws ServletException {
        this.usersRepository = new Repository();

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //получаем все записи из БД
        List<RecordHib> records = usersRepository.findAllRec();
        req.getSession().setAttribute("usersFromServer", records);

//получаем список имен всех пользователей из БД
        List<UserHib> users = usersRepository.findAll();
        req.setAttribute("roleByName", users);
        List<String> listUsers = new ArrayList<>();
        for (UserHib item : users) {
            var usByName = item.getLastName();
            listUsers.add(usByName);
        }
        req.setAttribute("usersName", listUsers);

//получаем текущего пользователя
        Object checkUser = req.getSession().getAttribute("user");//получаем атрибут "user" из сессии
        req.setAttribute("user", checkUser);
        UserHib us = usersRepository.findUserByName(checkUser.toString());
        req.setAttribute("usersRole", us.getUserRoleHib());

//устанавливаем область видимости полей в зависимости от роли текущего пользователя
        String RoleForHome = "DEFAULT";
        if (us != null && us.getUserRoleHib().toString() == "MANAGER") {
            RoleForHome = "MANAGER";
        }
        req.setAttribute("chekRoleForHome", RoleForHome);
//изменить
        if (req.getParameter("action") != null) {

            if (req.getParameter("action").equals("update")) {

                Integer selectId = Integer.parseInt(req.getParameter("idSelectedRec"));
                RecordHib recUpdate = usersRepository.findRecById(selectId);
                req.getSession().setAttribute("recForUpdate", recUpdate);//!
                req.setAttribute("action", "update");
                req.getRequestDispatcher("/jsp/home.jsp").forward(req, resp);
            }

        }
//          имя для отчета на страницу rep
        if (req.getParameter("nsr")!=null) {

            String repName = req.getParameter("nsr");

            req.getSession().setAttribute("nsr", repName);
            req.getRequestDispatcher("/jsp/report.jsp").forward(req, resp);//не трогаем,перенаправляет на страницу report иначе остаемся на home
        }
        RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/jsp/home.jsp");
        dispatcher.forward(req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        req.setAttribute("action", "new");


        if (action != null) {
            if (action.equals("new")) {
//создание новой записи
                try {
                    String date = req.getParameter("date");
                    LocalDate d = LocalDate.parse(date);
                    String hour = req.getParameter("hour");
                    Integer h = Integer.valueOf(hour);
                    String message = req.getParameter("message");
                    String lastName = req.getParameter("lastName");
                    RecordHib rec = new RecordHib(d, h, message, usersRepository.findUserByName(lastName));
                    usersRepository.saveRec(rec);
                    req.setAttribute("listRec", rec);
                } catch (Exception e) {

                }
                resp.sendRedirect(req.getContextPath() + "/home");
            }



//изменение
            if (action.equals("update")) {
                RecordHib recUp = (RecordHib) (req.getSession().getAttribute("recForUpdate"));
                req.setAttribute("recEdit", recUp);

                if (recUp != null) {
                    recUp.setDate(LocalDate.parse(req.getParameter("recDate")));
                    recUp.setHour(Integer.parseInt(req.getParameter("recHours")));
                    recUp.setMessage(req.getParameter("recMess"));

                    usersRepository.updateRec(recUp);

                }

                List<RecordHib> recUpdateList = usersRepository.findAllRec();
                resp.sendRedirect(req.getContextPath() + "/home");

            }
        }
        //удаление записи
        if (req.getParameter("idSelectedRec") != null) {
            Integer selectId = Integer.parseInt(req.getParameter("idSelectedRec"));
            RecordHib recDel = usersRepository.findRecById(selectId);
            usersRepository.deleteRec(recDel);

            List<RecordHib> recUpdateList = usersRepository.findAllRec();
            resp.sendRedirect(req.getContextPath() + "/home");
        }
// блок группировки времени(устанавливаем дату и отправляем на сервер)

         LocalDate StartD =null;
         LocalDate EndD =null;

        try {
            String startDate = LocalDate.parse(req.getParameter("startDate")).toString();
            StartD = LocalDate.parse(startDate);
            String endDate = LocalDate.parse(req.getParameter("endDate")).toString();
            EndD = LocalDate.parse(endDate);
            req.getSession().setAttribute("nachalo", StartD);
            req.getSession().setAttribute("konec", EndD);
            resp.sendRedirect(req.getContextPath() + "/home");
        } catch (Exception e) {

        }



    }

}
