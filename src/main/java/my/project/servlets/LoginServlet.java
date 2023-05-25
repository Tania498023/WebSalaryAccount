package my.project.servlets;



import my.project.help.Message;
import my.project.models.UserHib;
import my.project.models.UserRoleHib;
import my.project.repositories.IRepository;
import my.project.repositories.Repository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // ссылка на хранилище пользователей
    private IRepository usersRepository;


    @Override
    public void init() throws ServletException {
        this.usersRepository = new Repository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // вытаскиваем из запроса имя пользователя и его пароль
        req.setCharacterEncoding("UTF-8");
//        List<UserHib> checkRole = new ArrayList<>();

        String name = req.getParameter("name");
        String password = req.getParameter("password");

        // если пользователь есть в системе
        if (usersRepository.isExist(name, password)) {
            // создаем для него сессию
            HttpSession session = req.getSession();
            // кладем в атрибуты сессии атрибут user с именем пользователя
            session.setAttribute("user",name);
            session.setAttribute("userPass",password);



            // перенаправляем на страницу home

            resp.sendRedirect(req.getContextPath() + "/home");
        }
        else {
        resp.sendRedirect(req.getContextPath() + "/login");//пока остаемся на странице login и нажимаем кнопку Регистрация для перехода на страницу SignUp
        Message.infoBox("Логин или пароль введен неверно!","Message");

        }

    }
}
