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
        List<UserHib> users = usersRepository.findAll();//*отсюда будем выводить список пользователей в html
        req.setAttribute("usersFromServer", users);//*в атрибут запроса положили пользователей

        UserHib us=null;
        Object checkUser = req.getSession().getAttribute("user");

        req.setAttribute("user", checkUser);
        if (checkUser!=null) {
            us = usersRepository.findUserByName(checkUser.toString());
            req.setAttribute("usersRole", us.getUserRoleHib());
        }

        String Ur = "DEFAULT";
        List<String> roleForAvtorizovan = new ArrayList<>();
        if(us!=null&&us.getUserRoleHib().toString()=="MANAGER") {
            roleForAvtorizovan.add("MANAGER");
            roleForAvtorizovan.add("FREELANCER");
            roleForAvtorizovan.add("EMPLOYEE");
            Ur = "MANAGER";
        }
        else {
            roleForAvtorizovan.add("DEFAULT");
        }
        req.setAttribute("listRoleFromServer", roleForAvtorizovan);

        req.setAttribute("roleForSign", Ur);

        RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/jsp/signUp.jsp");
        dispatcher.forward(req, resp);//*перенаправление запроса на страницу signUp.jsp
        //*в методе :сервер получил запрос, вытащил пользователей(users) из Repository, затем положил users в атрибуты запроса и отправил данные на jsp.
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // вытащили данные регистрации
        req.setCharacterEncoding("UTF-8");
        UserHib user = null;
        try {
            String name = req.getParameter("name");//из req забираем параметр name
            String role = req.getParameter("role");
            UserRoleHib userRoleHib = UserRoleHib.valueOf(role);
            String password = req.getParameter("password");
            String oklad = req.getParameter("monthSalary");
            Double monthSalary = Double.valueOf(oklad);
            String premia = req.getParameter("bonus");
            Double bonus = Double.valueOf(premia);
            String peyHour = req.getParameter("payPerHour");
            Double payPerHour = Double.valueOf(peyHour);

            // создали пользователя и сохранили его в хранилище
            user = new UserHib(name, userRoleHib, password,monthSalary,bonus,payPerHour);
            usersRepository.save(user);

        } catch (Exception e) {

        }
        if (user.getUserRoleHib().toString()=="DEFAULT"){
            resp.sendRedirect(req.getContextPath() + "/login");
        }
       else {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
       // doGet(req, resp);//перенаправление данных на страницу signUp через doGet
    }
    //*в методе регистрируем получение данных с сервера
}
