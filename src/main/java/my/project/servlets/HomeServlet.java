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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 14.03.2018
 * HomeServlet
 *
 * Сервлет, который работает со страницей home
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private IRepository usersRepository;
    public String currentUser;
    @Override
    public void init() throws ServletException {
        this.usersRepository = new Repository();

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<RecordHib> records = usersRepository.findAllRec();
        req.setAttribute("usersFromServer", records);
        List<UserHib> users = usersRepository.findAll();
        List<String> listUsers = new ArrayList<>();
        for (UserHib item: users) {
            var usByName = item.getLastName();
            listUsers.add(usByName);
        }
          req.setAttribute("userFromServer", listUsers);
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
        }
        catch (Exception e){

        }

        doGet(req, resp);

        // получаем параметр запроса
        String color = req.getParameter("color");
        // создаем Cookie с данным значением
        Cookie colorCookie = new Cookie("color", color);
        // кладем в ответ
        resp.addCookie(colorCookie);
        // перенаправляем пользователя обратно на страницу home
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
