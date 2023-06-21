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
        }
        catch (Exception  e){

        }

        if(startDay == null && endDay == null){
            LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
            startDay=firstDayOfMonth;
            LocalDate lastDayOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
            endDay = lastDayOfMonth;
        }
//            List<RecordHib> repRec = new ArrayList<>();
            Map<String,Integer> groupDoxod = new HashMap<>();
            Map<String,Double> mapDoxod = new HashMap<>();

        for (UserHib usItem : userGroup) {
            Double dd = 0.0;
            Double doxod = 0.0;
            Double payHour = 0.0;
            for (RecordHib item : recordGroup) {

                if (usItem.getLastName() == item.getLastName().getLastName()) {
                    if (Helpers.getMilliSecFromDate(item.getDate()) >= Helpers.getMilliSecFromDate(startDay) && Helpers.getMilliSecFromDate(item.getDate()) <= Helpers.getMilliSecFromDate(endDay))
                        if (!groupDoxod.containsKey(item.getLastName().getLastName())) {

                            groupDoxod.put(item.getLastName().getLastName(), item.getHour());
                        } else if (groupDoxod.containsKey(item.getLastName().getLastName())) {

                            int h = groupDoxod.get(item.getLastName().getLastName());
                            groupDoxod.put(item.getLastName().getLastName(), item.getHour() + h);
                        }
                    payHour = usItem.getMonthSalary() / Settings.WORKHOURSINMONTH;
                    Double bonusPerDay = usItem.getBonus() / Settings.WORKHOURSINMONTH * Settings.WORKHOURSINDAY;

                    if (item.getHour() <= Settings.WORKHOURSINDAY) {
                        doxod += payHour * item.getHour();
                    } else if (usItem.getUserRoleHib() == UserRoleHib.MANAGER) {
                        doxod += payHour * Settings.WORKHOURSINDAY + bonusPerDay;
                    } else {
                        doxod += payHour * item.getHour() + (item.getHour() - Settings.WORKHOURSINDAY) * payHour;
                    }
                    dd = doxod;
                    mapDoxod.put(item.getLastName().getLastName(), dd);
                }
            }
        }


        req.setAttribute("doxod",mapDoxod);
        req.setAttribute("reportForRec",groupDoxod);

        RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/jsp/report.jsp");
        dispatcher.forward(req, resp);

    }

//    private void raschetTotalPay(String name, List<RecordHib> timeRecord, LocalDate startDate, LocalDate endDate) {
//
//        double payPerHour = monthSalarys / Settings.WORKHOURSINMONTH;
//        double totalPay = 0;
//        double bonusPerDay = bonuses / Settings.WORKHOURSINMONTH * Settings.WORKHOURSINDAY;
//
//
//        for (RecordHib timeRecordses : timeRecord) {
//            if (name.equals(timeRecordses.getLastName().getLastName()))
//                if (Helpers.getMilliSecFromDate(timeRecordses.getDate())>=Helpers.getMilliSecFromDate(startDate)&&Helpers.getMilliSecFromDate(timeRecordses.getDate())<=Helpers.getMilliSecFromDate(endDate))
//                    if (timeRecordses.getHour() <= Settings.WORKHOURSINDAY) {
//                        totalPay += payPerHour * timeRecordses.getHour();
//                        totalHours += timeRecordses.getHour();
//                    } else if (getWorker().getUserRoleHib() == UserRoleHib.MANAGER) {
//                        totalPay += payPerHour * Settings.WORKHOURSINDAY + bonusPerDay;
//                    } else {
//                        totalPay += payPerHour * timeRecordses.getHour() + (timeRecordses.getHour() - Settings.WORKHOURSINDAY) * payPerHour;
//                    }
//            totalPays = totalPay;
//        }
//    }









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
            resp.sendRedirect(req.getContextPath() + "/report");
        } catch (Exception e) {

        }
        }

    }


