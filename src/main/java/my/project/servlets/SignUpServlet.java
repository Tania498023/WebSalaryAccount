package my.project.servlets;

import my.project.models.UserHib;
import my.project.models.UserRoleHib;
import my.project.repositories.IRepository;
import my.project.repositories.Repository;
import org.hibernate.Session;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
//определение текущего пользователя
        UserHib us = null;
        Object checkUser = req.getSession().getAttribute("user");

        req.setAttribute("user", checkUser);
        if (checkUser != null) {
            us = usersRepository.findUserByName(checkUser.toString());
            req.setAttribute("usersRole", us.getUserRoleHib());//текущий пользователь
        }
//отображение списка ролей в зависимости от того, под какой ролью вошли на регистрацию
        String Ur = "DEFAULT";
        List<String> roleForAvtorizovan = new ArrayList<>();
        if (us != null && us.getUserRoleHib().toString() == "MANAGER") {
            roleForAvtorizovan.add("MANAGER");
            roleForAvtorizovan.add("FREELANCER");
            roleForAvtorizovan.add("EMPLOYEE");
            Ur = "MANAGER";
        } else {
            roleForAvtorizovan.add("DEFAULT");
        }
        req.setAttribute("listRoleFromServer", roleForAvtorizovan);

        req.setAttribute("roleForSign", Ur);

//блок изменения и удаления
        if (req.getParameter("action") != null) {
            if (req.getParameter("action").equals("update")) {
                Integer id = Integer.parseInt(req.getParameter("id").toString());
                UserHib usUper = usersRepository.findUserById(id);
                req.getSession().setAttribute("id", usUper);
                req.setAttribute("action", "update");
            }

        }

        RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/jsp/signUp.jsp");
        dispatcher.forward(req, resp);//*перенаправление запроса на страницу signUp.jsp
        //*в методе :сервер получил запрос, вытащил пользователей(users) из Repository, затем положил users в атрибуты запроса и отправил данные на jsp.
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        req.setAttribute("action", "new");
        UserHib usUp = null;
        if (action.equals("new")) {
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

                user = new UserHib(name, userRoleHib, password, monthSalary, bonus, payPerHour);
                usersRepository.save(user);

            } catch (Exception e) {

                } finally {
                    if (user.getUserRoleHib().toString() == "DEFAULT") {
                        resp.sendRedirect(req.getContextPath() + "/login");
                    } else {
                resp.sendRedirect(req.getContextPath() + "/signUp");
                    }
                }
            }

            //  обновление и удаление
            if (action.equals("update")) {
                 UserHib id = (UserHib)(req.getSession().getAttribute("id"));
                 req.setAttribute("id",id);
//                UserHib vfgb = (UserHib)(req.getSession().getAttribute("sessionuserid"));

            if (id != null) {
                id.setLastName(req.getParameter("username"));
                id.setUserRoleHib(UserRoleHib.valueOf(req.getParameter("userrole")));
                id.setPassword(req.getParameter("userpass"));
                id.setMonthSalary(Double.parseDouble(req.getParameter("usersalary")));
                id.setBonus(Double.parseDouble(req.getParameter("userbonus")));
                id.setPayPerHour(Double.parseDouble(req.getParameter("userperhour")));
                id.setId(Integer.parseInt(req.getParameter("sessionuserid")));
                usersRepository.update(id);
                     }
                List<UserHib> usersUpdateList = usersRepository.findAll();
                req.setAttribute("usersUpdateList", usersUpdateList);
                resp.sendRedirect(req.getContextPath() + "/signUp");
            }
        }
    }


        //*в методе регистрируем получение данных с сервера


