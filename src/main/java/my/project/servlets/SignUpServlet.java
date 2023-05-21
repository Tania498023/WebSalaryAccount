package my.project.servlets;



import my.project.models.UserHib;
import my.project.models.UserRoleHib;
import my.project.repositories.IRepository;
import my.project.repositories.Repository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {

    private IRepository usersRepository;

    @Override
    public void init() throws ServletException {
        this.usersRepository = new Repository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserHib> users = usersRepository.findAll();
        req.setAttribute("usersFromServer", users);

        RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/jsp/signUp.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // вытащили данные регистрации
        req.setCharacterEncoding("UTF-8");
        try {
            String name = req.getParameter("name");
            String role = req.getParameter("role");
            UserRoleHib userRoleHib = UserRoleHib.valueOf(role);
            String password = req.getParameter("password");


            // создали пользователя и сохранили его в хранилище
            UserHib user = new UserHib(name, userRoleHib, password);
            usersRepository.save(user);

        } catch (Exception e) {

        }
        doGet(req, resp);
    }
}
