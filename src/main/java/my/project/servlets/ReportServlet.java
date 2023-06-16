package my.project.servlets;

import my.project.help.Helpers;
import my.project.models.RecordHib;
import my.project.models.UserHib;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/report")
public class ReportServlet extends HttpServlet {
    private IRepository usersRepository;

    @Override
    public void init() throws ServletException {
        this.usersRepository = new Repository();

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//получаем текущего пользователя
        Object checkUser = req.getSession().getAttribute("user");//получаем атрибут "user" из сессии
        req.setAttribute("user", checkUser);
        UserHib us = usersRepository.findUserByName(checkUser.toString());
        req.setAttribute("usersRole", us.getUserRoleHib());



        RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/jsp/report.jsp");
        dispatcher.forward(req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        }

    }


