package com.phoenix.fitness.modules.admin.dto;

import lombok.Data;

@Data
public class PeriodDto {

    private String typeName;
    /**
     * 01:00-05:00数量
     */
    private Integer oneToFive = 0;
    /**
     * 05:00-08:00
     */
    private Integer fiveToEight = 0;
    /**
     * 08:00-09:00
     */
    private Integer eightToNine = 0;
    /**
     * 09:00-10:00
     */
    private Integer nineToTen = 0;
    /**
     * 10:00-11:00
     */
    private Integer tenToEleven = 0;
    /**
     * 11:00-12:00
     */
    private Integer elevenToTwelve = 0;
    /**
     * 12:00-13:00
     */
    private Integer twelveToThirteen = 0;
    /**
     * 13:00-14:00
     */
    private Integer thirteenToFourteen = 0;
    /**
     * 14:00-15:00
     */
    private Integer fourteenToFifteen = 0;
    /**
     * 15:00-16:00
     */
    private Integer fifteenToSixteen = 0;
    /**
     * 16:00-17:00
     */
    private Integer sixteenToSeventeen = 0;
    /**
     * 17:00-18:00
     */
    private Integer seventeenToEighteen = 0;
    /**
     * 18:00-19:00
     */
    private Integer eighteenToNineteen = 0;
    /**
     * 19:00-20:00
     */
    private Integer nineteenToTwenty = 0;
    /**
     * 20:00-21:00
     */
    private Integer twentyTo21 = 0;
    /**
     * 21:00-22:00
     */
    private Integer twentyOneTo22 = 0;
    /**
     * 22:00-23:00
     */
    private Integer twentyTwoTo23 = 0;
    /**
     * 23:00-01:00
     */
    private Integer twentyThreeTo01 = 0;
}
