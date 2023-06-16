package my.project.help;

import java.time.*;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class Helpers {
    private static final AtomicLong TIME_STAMP = new AtomicLong();
    // can have up to 1000 ids per second.
    public static long getUniqueMillis() {
        long now = System.currentTimeMillis();
        while (true) {
            long last = TIME_STAMP.get();
            if (now <= last)
                now = last + 1;
            if (TIME_STAMP.compareAndSet(last, now))
                return now;
        }
    }

    public static final long getMilliSecFromDate(LocalDate ld)
    {
        //LocalDate преобразовать в LocalDateTime

        String strLd = ld.toString() + "T10:15:30" ;//2007-12-03T10:15:30
        LocalDateTime ldt = LocalDateTime.parse(strLd);

        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();



    }
//    public static final long LDFromDate(LocalDate ld)
//    {
//
////        String strLd = ld.toString()  ;//2007-12-03T10:15:30
//        long millicek = new Date().getTime();











}
