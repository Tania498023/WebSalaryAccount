package my.project.servlets;

import my.project.help.Helpers;
import my.project.help.Settings;
import my.project.models.RecordHib;
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
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

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
        req.getSession().setAttribute("user", checkUser);
        UserHib us = usersRepository.findUserByName(checkUser.toString());
        req.getSession().setAttribute("usersRole", us.getUserRoleHib());

//блок расчета з/п
        List<RecordHib> recordGroup = usersRepository.findAllRec();
//        req.getSession().setAttribute("recGroup", recordGroup);
//        List<UserHib> userGroup = usersRepository.findAll();
//        req.getSession().setAttribute("recGroup", recordGroup);

        //устанавливаем период
        LocalDate startDay = null;
        LocalDate endDay = null;

        if (startDay == null && endDay == null) {
            LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
            startDay = firstDayOfMonth;
            LocalDate lastDayOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
            endDay = lastDayOfMonth;
        }
        try {
            startDay = LocalDate.parse(req.getSession().getAttribute("startDay").toString());
            endDay = LocalDate.parse(req.getSession().getAttribute("endDay").toString());
        } catch (Exception e) {

        }
        req.getSession().setAttribute("startDay", startDay);
        req.getSession().setAttribute("endDay", endDay);

        //отчет по времени и доходу по одному сотруднику (для менеджера)

        List<RecordHib> repByOneForManager = new ArrayList<>();
        Integer sumHour = 0;
        String visOne = "def";
        if (req.getSession().getAttribute("visibleForOne") != null) {
            if (req.getSession().getAttribute("visibleForOne").equals("oneRep")) {
                visOne = "oneRep";
                req.getSession().setAttribute("ff", visOne);
                String repNames = req.getSession().getAttribute("userForReport").toString();
                req.getSession().setAttribute("userForReport", repNames);

                List<RecordHib> userRep = usersRepository.findRecByName(repNames);

                for (RecordHib item : userRep) {
                    if (Helpers.getMilliSecFromDate(item.getDate()) >= Helpers.getMilliSecFromDate(startDay) && Helpers.getMilliSecFromDate(item.getDate()) <= Helpers.getMilliSecFromDate(endDay)) {
                        repByOneForManager.add(item);
                        sumHour += item.getHour();//для итоговой ячейки
                    }
                }
                req.setAttribute("startDay", startDay);//должны уйти на jsp
                req.setAttribute("endDay", endDay);
                req.getSession().setAttribute("listRecForReport", repByOneForManager);
                req.setAttribute("sumHour", sumHour);//итоговые часы

                req.getRequestDispatcher("/jsp/report.jsp").forward(req, resp);
            } else {
                req.getSession().setAttribute("ff", "");
            }
        }


        //отчет по времени и доходу по одному сотруднику (не менеджер)
        //(оставила в Get для примера)

        List<RecordHib> repByOne = new ArrayList<>();
        List<Double> zarplata = new ArrayList<>();

        Integer sumHours = 0;
        Double summaDoxod = 0.0;
        Double salary = 0.0;
        Double salaryPerMonth = 0.0;
        for (RecordHib item : recordGroup) {
            if (item.getLastName().getLastName().equals(checkUser)) {
                if (Helpers.getMilliSecFromDate(item.getDate()) >= Helpers.getMilliSecFromDate(startDay) && Helpers.getMilliSecFromDate(item.getDate()) <= Helpers.getMilliSecFromDate(endDay)) {
                    repByOne.add(item);

                    sumHours += item.getHour();//для итоговой ячейки

                    Double perPayHours = item.getLastName().getMonthSalary() / Settings.WORKHOURSINMONTH;
                    Double bonusPerDay = item.getLastName().getBonus() / Settings.WORKHOURSINMONTH * Settings.WORKHOURSINDAY;
                    if (item.getLastName().getUserRoleHib() == UserRoleHib.FREELANCER) {
                        salary = item.getLastName().getPayPerHour() * item.getHour();
                    }

                    if (item.getHour() <= Settings.WORKHOURSINDAY && item.getLastName().getUserRoleHib() != UserRoleHib.FREELANCER) {
                        salary = perPayHours * item.getHour();
                    } else if (item.getLastName().getUserRoleHib() == UserRoleHib.MANAGER) {
                        salary = perPayHours * Settings.WORKHOURSINDAY + bonusPerDay;
                    } else if (item.getLastName().getUserRoleHib() == UserRoleHib.EMPLOYEE) {
                        salary = perPayHours * item.getHour() + (item.getHour() - Settings.WORKHOURSINDAY) * perPayHours;
                    }
                    summaDoxod = salary;
                    zarplata.add(summaDoxod);
                    salaryPerMonth += summaDoxod;

                }
            }
            req.setAttribute("checkUser", checkUser);//текущ пользователь(в т ч по кому отчет)
            req.setAttribute("sumHours", sumHours);//итоговые часы
            req.setAttribute("repForOne", repByOne);//List записей текущего пользователя
            req.setAttribute("listZarplata", zarplata);//List дохода
            req.setAttribute("salaryPerMonth", salaryPerMonth);//итоговый доход
        }

        RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/jsp/report.jsp");
        dispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        List<RecordHib> recordGroup = usersRepository.findAllRec();
        List<UserHib> userGroup = usersRepository.findAll();
        req.getSession().setAttribute("recGroup", recordGroup);
        req.getSession().setAttribute("usGroup", userGroup);

        // блок группировки времени(устанавливаем дату и отправляем на сервер)
        LocalDate startDay = null;
        LocalDate endDay = null;

        try {
            String startDate = LocalDate.parse(req.getParameter("startDate")).toString();//получаем с jsp страницы
            startDay = LocalDate.parse(startDate);
            String endDate = LocalDate.parse(req.getParameter("endDate")).toString();
            endDay = LocalDate.parse(endDate);

        } catch (Exception e) {

        }
        if (startDay == null && endDay == null) {
            LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
            startDay = firstDayOfMonth;
            LocalDate lastDayOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
            endDay = lastDayOfMonth;
        }
        req.getSession().setAttribute("startDay", startDay);//должны уйти на jsp
        req.getSession().setAttribute("endDay", endDay);



//отчет по всем с группировкой времени и дохода для Менеджера

        if (req.getParameter("id") != null) {
            if (req.getParameter("id").equals("groupRep")) {
                if (req.getSession().getAttribute("idForJsp").equals("visibility")) {
                    req.getSession().setAttribute("idForJsp", "visibility");

                    Map<String, Integer> groupDoxod = new HashMap<>();
                    Map<String, Double> mapDoxod = new HashMap<>();

                    for (UserHib usItem : userGroup) {
                        Double sumdoxod = 0.0;
                        Double doxod = 0.0;
                        Double payHour = 0.0;
                        for (RecordHib item : recordGroup) {

                            if (usItem.getLastName() == item.getLastName().getLastName()) {
                                if (Helpers.getMilliSecFromDate(item.getDate()) >= Helpers.getMilliSecFromDate(startDay) && Helpers.getMilliSecFromDate(item.getDate()) <= Helpers.getMilliSecFromDate(endDay)) {
                                    if (!groupDoxod.containsKey(item.getLastName().getLastName())) {

                                        groupDoxod.put(item.getLastName().getLastName(), item.getHour());
                                    } else if (groupDoxod.containsKey(item.getLastName().getLastName())) {

                                        int h = groupDoxod.get(item.getLastName().getLastName());
                                        groupDoxod.put(item.getLastName().getLastName(), item.getHour() + h);
                                    }
                                    payHour = usItem.getMonthSalary() / Settings.WORKHOURSINMONTH;
                                    Double bonusPerDay = usItem.getBonus() / Settings.WORKHOURSINMONTH * Settings.WORKHOURSINDAY;
                                    if (usItem.getUserRoleHib() == UserRoleHib.FREELANCER) {
                                        doxod += usItem.getPayPerHour() * item.getHour();
                                    }
                                    if (item.getHour() <= Settings.WORKHOURSINDAY && usItem.getUserRoleHib() != UserRoleHib.FREELANCER) {
                                        doxod += payHour * item.getHour();
                                    } else if (usItem.getUserRoleHib() == UserRoleHib.MANAGER) {
                                        doxod += payHour * Settings.WORKHOURSINDAY + bonusPerDay;
                                    } else if (usItem.getUserRoleHib() == UserRoleHib.EMPLOYEE) {
                                        doxod += payHour * item.getHour() + (item.getHour() - Settings.WORKHOURSINDAY) * payHour;
                                    }
                                    sumdoxod = doxod;
                                    mapDoxod.put(item.getLastName().getLastName(), sumdoxod);
                                }
                            }
                        }
                    }

                    req.getSession().setAttribute("doxod", mapDoxod);
                    req.getSession().setAttribute("reportForRec", groupDoxod);

                }
            }
        } else {
            req.getSession().setAttribute("idForJsp", "");
        }
        resp.sendRedirect(req.getContextPath() + "/report");
    }
}





