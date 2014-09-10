package uk.ac.sanger.mig.analysis.maths.trendline;

public class LogTrendLine extends OLSTrendLine {
    @Override
    protected double[] xVector(double x) {
        return new double[]{1, Math.log(x)};
    }

    @Override
    protected boolean logY() {
        return false;
    }
}