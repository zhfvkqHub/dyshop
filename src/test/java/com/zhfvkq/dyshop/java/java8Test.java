package com.zhfvkq.dyshop.java;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class java8Test {

    @Test
    public void dateTest() {

        LocalDateTime now = LocalDateTime.now(); // 현재 서버에 대한 시간이 표시됨
        System.out.println(now);

        LocalDateTime birthDay = LocalDateTime.of(1999, Month.JANUARY, 20,0,0,0);
        System.out.println(birthDay);

        ZonedDateTime nowInKorea = ZonedDateTime.now(ZoneId.of("Asia/Seoul")); // LocalDateTime + Time Zone(시간대)
        System.out.println(nowInKorea);

        // format
        DateTimeFormatter isoLocalDate = DateTimeFormatter.ISO_LOCAL_DATE;
        System.out.println(now.format(isoLocalDate));
        System.out.println(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")));
        // parse
        LocalDate parse = LocalDate.parse("1999.01.20", DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        System.out.println(parse);

        // result
        // 2022-11-16T20:53:25.736113
        // 1999-01-20T00:00
        // 2022-11-16T20:53:25.736461+09:00[Asia/Seoul]
        // 2022-11-16
        // 2022-11-16 20:53:25:736
        // 1999-01-20


//        LocalDate today = LocalDate.now();
//        LocalDate thisYearBirthDay = LocalDate.of(2023, Month.JANUARY, 20);

//        // 사이의 총 년,월,일 구하기
//        Period period = Period.between(today, thisYearBirthDay);
//        System.out.println(period.getYears());
//        System.out.println(period.getMonths());
//        System.out.println(period.getDays());
//
//        // 사이의 총 일 구하기
//        System.out.println(ChronoUnit.DAYS.between(today, thisYearBirthDay));

        // result
        // 0
        // 2
        // 4
        // 65


    }
}
