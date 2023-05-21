package my.project.servlets;

import my.project.help.Helpers;
import my.project.models.RecordHib;
import my.project.models.UserHib;
import my.project.models.UserRoleHib;
import my.project.repositories.IRepository;
import my.project.repositories.Repository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private IRepository usersRepository;

    @Override
    public void init() throws ServletException {
        this.usersRepository = new Repository();

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<RecordHib> records = usersRepository.findAllRec();
        req.setAttribute("usersFromServer", records);

        List<UserHib> users = usersRepository.findAll();
        req.setAttribute("roleByName", users);
        List<String> listUsers = new ArrayList<>();
        for (UserHib item: users) {
            var usByName = item.getLastName();
            listUsers.add(usByName);
        }
        

        Object checkUser = req.getSession().getAttribute("user");
        req.setAttribute("user", checkUser);
        UserHib us = usersRepository.findUserByName(checkUser.toString());
        req.setAttribute("usersRole", us.getUserRoleHib());


//        HttpSession session = req.getSession();
////        // кладем в атрибуты сессии атрибут user с именем пользователя
//        session.setAttribute("user",checkUser);
//
//        // перенаправляем на страницу home
        req.getServletContext().getRequestDispatcher("/signUp").forward(req, resp);
//

          req.setAttribute("usersName", listUsers);
          req.getServletContext().getRequestDispatcher("/jsp/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        try {
            String date =req.getParameter("date");
            LocalDate d = LocalDate.parse(date);
            String hour = req.getParameter("hour");
            Integer h = Integer.valueOf(hour);
            String message = req.getParameter("message");
            String lastName = req.getParameter("lastName");
            RecordHib rec = new RecordHib(d,h,message,usersRepository.findUserByName(lastName));
            usersRepository.saveRec(rec);
            req.setAttribute("listRec", rec);
        }
        catch (Exception e){

        }

       doGet(req, resp);

    }
}
