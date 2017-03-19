package pja.jaz.model;

import java.util.ArrayList;
import java.util.List;

public class Credit {

    private double capital;
    private int period;
    private double interestRate;
    private double fixedCharges;
    private CreditType type;

    private List<CreditInstalment> instalments = new ArrayList<>(360);

    public Credit() {
    }

    public Credit(double capital, int period, double interestRate, double fixedCharges, CreditType type) {
        this.capital = capital;
        this.period = period;
        this.interestRate = interestRate;
        this.fixedCharges = fixedCharges;
        this.type = type;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getFixedCharges() {
        return fixedCharges;
    }

    public void setFixedCharges(double fixedCharges) {
        this.fixedCharges = fixedCharges;
    }

    public CreditType getType() {
        return type;
    }

    public void setType(CreditType type) {
        this.type = type;
    }

    public List<CreditInstalment> getInstalments() {
        return instalments;
    }

    public void addInstalment(CreditInstalment creditInstalment) {
        this.instalments.add(creditInstalment);
    }

    public double getTotalCapital() {
        return instalments.stream().mapToDouble(CreditInstalment::getCapital).sum();
    }

    public double getTotalInterest() {
        return instalments.stream().mapToDouble(CreditInstalment::getInterest).sum();
    }

    public double getTotalFixedCharges() {
        return instalments.stream().mapToDouble(CreditInstalment::getFixedCharge).sum();
    }

    public double getTotalCreditCost() {
        return instalments.stream().mapToDouble(CreditInstalment::getTotal).sum();
    }
}
