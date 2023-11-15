package com.chavaillaz.jenkins.domain;

import lombok.Data;

@Data
public class CoverageReport {

    private CoverageResult lineCoverage;
    private CoverageResult classCoverage;
    private CoverageResult complexityScore;
    private CoverageResult instructionCoverage;
    private CoverageResult branchCoverage;

}
