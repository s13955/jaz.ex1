package pja.jaz.model;

public class CreditInstalment {

    private int instalment;
    private double capital;
    private double interest;
    private double fixedCharge;

    public CreditInstalment() {
    }

    public CreditInstalment(int instalment, double capital, double interest, double fixedCharge) {
        this.instalment = instalment;
        this.capital = capital;
        this.interest = interest;
        this.fixedCharge = fixedCharge;
    }

    public int getInstalment() {
        return instalment;
    }

    public void setInstalment(int instalment) {
        this.instalment = instalment;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getFixedCharge() {
        return fixedCharge;
    }

    public void setFixedCharge(double fixedCharge) {
        this.fixedCharge = fixedCharge;
    }

    public double getTotal() {
        return capital + interest + fixedCharge;
    }

    @Override
    public String toString() {
        return String.format("CreditInstalment{instalment=%s, capital=%s, interest=%s, fixedCharge=%s}",
                instalment, capital, interest, fixedCharge);
    }
}
