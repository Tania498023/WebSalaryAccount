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
        req.setAttribute("user", checkUser);
        UserHib us = usersRepository.findUserByName(checkUser.toString());
        req.setAttribute("usersRole", us.getUserRoleHib());

//блок расчета з/п
        List<RecordHib> recordGroup = usersRepository.findAllRec();
        req.setAttribute("recGroup", recordGroup);
        List<UserHib> userGroup = usersRepository.findAll();

        LocalDate startDay = null;
        LocalDate endDay = null;
        try {
            startDay = LocalDate.parse(req.getSession().getAttribute("nachaloRep").toString());
            endDay = LocalDate.parse(req.getSession().getAttribute("konecRep").toString());
        } catch (Exception e) {

        }

        if (startDay == null && endDay == null) {
            LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
            startDay = firstDayOfMonth;
            LocalDate lastDayOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
            endDay = lastDayOfMonth;
        }
        req.setAttribute("startDay", startDay);
        req.setAttribute("endDay", endDay);

//отчет по всем с группировкой времени и дохода для Менеджера


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
        req.setAttribute("doxod", mapDoxod);
        req.setAttribute("reportForRec", groupDoxod);

        //отчет по времени и доходу по одному сотруднику (не менеджер)

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

        }
        req.setAttribute("checkUser", checkUser);//текущ пользователь(в т ч по кому отчет)
        req.setAttribute("sumHours", sumHours);//итоговые часы
        req.setAttribute("repForOne", repByOne);//List записей текущего пользователя
        req.setAttribute("listZarplata", zarplata);//List дохода
        req.setAttribute("salaryPerMonth", salaryPerMonth);//итоговый доход
//        req.getRequestDispatcher("/jsp/report.jsp").forward(req, resp);

//отчет по времени и доходу по одному сотруднику (для менеджера)

        List<RecordHib> repByOneForManager = new ArrayList<>();
        Integer sumHour = 0;
        if (req.getParameter("nsr")!=null) {
            String repNames = req.getSession().getAttribute("nsr").toString();

            req.getSession().setAttribute("repName", repNames);
            List<RecordHib> userRep = usersRepository.findRecByName(repNames);

    for (RecordHib item : userRep) {
        if (Helpers.getMilliSecFromDate(item.getDate()) >= Helpers.getMilliSecFromDate(startDay) && Helpers.getMilliSecFromDate(item.getDate()) <= Helpers.getMilliSecFromDate(endDay)) {

            repByOneForManager.add(item);
            sumHour += item.getHour();//для итоговой ячейки

        }

    }
                req.getSession().setAttribute("RecByName", userRep);
                req.getSession().setAttribute("listRecForReport", repByOneForManager);
                req.setAttribute("sumHour", sumHour);//итоговые часы
//                req.setAttribute("action", "forOne");//итоговые часы
  //  }
                req.getRequestDispatcher("/jsp/report.jsp").forward(req, resp);
     }

        RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/jsp/report.jsp");
        dispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // блок группировки времени(устанавливаем дату и отправляем на сервер)

        LocalDate StartD =null;
        LocalDate EndD =null;

        try {
            String startDate = LocalDate.parse(req.getParameter("startDate")).toString();
            StartD = LocalDate.parse(startDate);
            String endDate = LocalDate.parse(req.getParameter("endDate")).toString();
            EndD = LocalDate.parse(endDate);
            req.getSession().setAttribute("nachaloRep", StartD);
            req.getSession().setAttribute("konecRep", EndD);

            String nsr = req.getSession().getAttribute("repName").toString();
            req.getSession().setAttribute("repName", nsr);
//            resp.sendRedirect(req.getContextPath() + "/report");
        } catch (Exception e) {

        }
        resp.sendRedirect(req.getContextPath() + "/report");
        }

    }


