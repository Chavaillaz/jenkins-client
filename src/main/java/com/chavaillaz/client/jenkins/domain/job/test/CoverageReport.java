package com.chavaillaz.client.jenkins.domain.job.test;

import lombok.Data;

@Data
public class CoverageReport {

    private CoverageResult lineCoverage;
    private CoverageResult classCoverage;
    private CoverageResult complexityScore;
    private CoverageResult instructionCoverage;
    private CoverageResult branchCoverage;

}
