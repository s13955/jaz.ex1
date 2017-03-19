package pja.jaz;

import org.junit.Test;
import pja.jaz.model.Credit;
import pja.jaz.model.CreditType;
import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class CreditCalculatorTest {

    @Test
    public void makeSimulation_InvalidParameters_Null() {
        Credit result = new CreditCalculator().makeSimulation(0, 0, 0, 0, null);
        assertThat(result).isNull();
    }

    @Test
    public void makeSimulation_EqualInstalments_Credit() {
        Credit result = new CreditCalculator().makeSimulation(100000, 10, 3.5, 0, CreditType.EQUAL);
        assertThat(result).isNotNull();
        assertThat(result.getInstalments()).hasSize(10);
    }

    @Test
    public void makeSimulation_DecreasingInstalments_Credit() {
        Credit result = new CreditCalculator().makeSimulation(100000, 10, 3.5, 0, CreditType.DECREASING);
        assertThat(result).isNotNull();
        assertThat(result.getInstalments()).hasSize(10);
    }

    @Test
    public void generateDocument_InvalidParameters_Null() {
        ByteArrayOutputStream result = new CreditCalculator().generateDocument(null, null);
        assertThat(result).isNull();
    }
}
