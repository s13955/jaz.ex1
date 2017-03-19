package pja.jaz.servlet;

import org.junit.Test;
import org.mockito.Mockito;
import pja.jaz.model.Credit;
import pja.jaz.model.CreditType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.io.IOException;
import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;

public class CreditCalculatorServletTest extends Mockito {

    @Test
    public void getField_NotRequiredInvalidParameters_Null() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String value = new CreditCalculatorServlet().getField(request, null, false);
        assertThat(value).isNull();
    }

    @Test(expected = ValidationException.class)
    public void getField_RequiredInvalidParameters_Exception() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String value = new CreditCalculatorServlet().getField(request, null, true);
    }

    @Test
    public void getField_NotRequiredMissingFieldName_Null() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String value = new CreditCalculatorServlet().getField(request, "test", false);
        assertThat(value).isNull();
    }

    @Test
    public void getField_NotRequiredCorrectFieldName_True() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("fieldName")).thenReturn("value");

        String value = new CreditCalculatorServlet().getField(request, "fieldName", false);
        assertThat(value).isEqualTo("value");
    }

    @Test
    public void processCredit_MissingParameters_RedirectToForm() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter printWriter = mock(PrintWriter.class);

        when(response.getWriter()).thenReturn(printWriter);

        Credit credit = new CreditCalculatorServlet().processCredit(request, response);

        assertThat(credit).isNull();
        verify(response).sendRedirect("/");
    }

    @Test
    public void processCredit_CorrectParameters_Credit() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("capital")).thenReturn("100000");
        when(request.getParameter("instalments")).thenReturn("144");
        when(request.getParameter("interestRate")).thenReturn("3.5");
        when(request.getParameter("fixedCharge")).thenReturn("0");
        when(request.getParameter("type")).thenReturn("EQUAL");

        Credit credit = new CreditCalculatorServlet().processCredit(request, response);

        assertThat(credit).isNotNull();
        assertThat(credit.getCapital()).isEqualTo(100000);
        assertThat(credit.getInstalments()).size().isEqualTo(144);
        assertThat(credit.getInterestRate()).isEqualTo(3.5);
        assertThat(credit.getFixedCharges()).isEqualTo(0);
        assertThat(credit.getType()).isEqualTo(CreditType.EQUAL);
    }
}
