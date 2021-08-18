package com.phoenix.fitness.common.utils;

import com.phoenix.fitness.common.constant.PeriodConstant;
import com.phoenix.fitness.modules.admin.dto.PeriodCountDto;
import com.phoenix.fitness.modules.admin.dto.PeriodDto;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PeriodUtil {

    //将文件转换成Byte数组
    public static String getPeriod(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        Integer hour = Integer.parseInt(simpleDateFormat.format(date));
        System.out.println(hour);
        if (hour >= 1 && hour < 5) {
            return "01:00-05:00";
        } else if (hour >= 5 && hour < 8) {
            return "05:00-08:00";
        } else if (hour == 8) {
            return "08:00-09:00";
        } else if (hour == 9) {
            return "09:00-10:00";
        } else if (hour == 10) {
            return "10:00-11:00";
        } else if (hour == 11) {
            return "11:00-12:00";
        } else if (hour == 12) {
            return "12:00-13:00";
        } else if (hour == 13) {
            return "13:00-14:00";
        } else if (hour == 14) {
            return "14:00-15:00";
        } else if (hour == 15) {
            return "15:00-16:00";
        } else if (hour == 16) {
            return "16:00-17:00";
        } else if (hour == 17) {
            return "17:00-18:00";
        } else if (hour == 18) {
            return "18:00-19:00";
        } else if (hour == 19) {
            return "19:00-20:00";
        } else if (hour == 20) {
            return "20:00-21:00";
        } else if (hour == 21) {
            return "21:00-22:00";
        } else if (hour == 22) {
            return "22:00-23:00";
        } else if (hour == 23 || hour == 0) {
            return "23:00-01:00";
        }
        return "";
    }

    //将文件转换成Byte数组
    public static void setPeriodCount(List<PeriodCountDto> periodCounts, PeriodDto period) {
        if (!CollectionUtils.isEmpty(periodCounts)) {
            for (PeriodCountDto temp : periodCounts) {
                switch (temp.getPeriod()) {
                    case PeriodConstant.oneToFive:
                        period.setOneToFive(temp.getCount());
                        break;
                    case PeriodConstant.fiveToEight:
                        period.setFiveToEight(temp.getCount());
                        break;
                    case PeriodConstant.eightToNine:
                        period.setEightToNine(temp.getCount());
                        break;
                    case PeriodConstant.nineToTen:
                        period.setNineToTen(temp.getCount());
                        break;
                    case PeriodConstant.tenToEleven:
                        period.setTenToEleven(temp.getCount());
                        break;
                    case PeriodConstant.elevenToTwelve:
                        period.setElevenToTwelve(temp.getCount());
                        break;
                    case PeriodConstant.twelveToThirteen:
                        period.setTwelveToThirteen(temp.getCount());
                        break;
                    case PeriodConstant.thirteenToFourteen:
                        period.setThirteenToFourteen(temp.getCount());
                        break;
                    case PeriodConstant.fourteenToFifteen:
                        period.setFourteenToFifteen(temp.getCount());
                        break;
                    case PeriodConstant.fifteenToSixteen:
                        period.setFifteenToSixteen(temp.getCount());
                        break;
                    case PeriodConstant.sixteenToSeventeen:
                        period.setSixteenToSeventeen(temp.getCount());
                        break;
                    case PeriodConstant.seventeenToEighteen:
                        period.setSeventeenToEighteen(temp.getCount());
                        break;
                    case PeriodConstant.eighteenToNineteen:
                        period.setEighteenToNineteen(temp.getCount());
                        break;
                    case PeriodConstant.nineteenToTwenty:
                        period.setNineteenToTwenty(temp.getCount());
                        break;
                    case PeriodConstant.twentyTo21:
                        period.setTwentyTo21(temp.getCount());
                        break;
                    case PeriodConstant.twentyOneTo22:
                        period.setTwentyOneTo22(temp.getCount());
                        break;
                    case PeriodConstant.twentyTwoTo23:
                        period.setTwentyTwoTo23(temp.getCount());
                        break;
                    case PeriodConstant.twentyThreeTo01:
                        period.setTwentyThreeTo01(temp.getCount());
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
        Date date = new Date();
        PeriodUtil.getPeriod(date);
    }


}
