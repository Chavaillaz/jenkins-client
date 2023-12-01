package com.chavaillaz.client.jenkins.domain.job.test;

import lombok.Data;

@Data
public class CoverageResult {

    private int covered;
    private int missed;
    private int percentage;
    private int percentageFloat;
    private int total;

}
