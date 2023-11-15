package com.chavaillaz.jenkins.domain;

import lombok.Data;

@Data
public class CoverageResult {

    private int covered;
    private int missed;
    private int percentage;
    private int percentageFloat;
    private int total;

}
