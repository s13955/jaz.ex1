package pja.jaz.servlet;

import pja.jaz.CreditCalculator;
import pja.jaz.model.Credit;
import pja.jaz.model.CreditType;
import pja.jaz.model.DocumentType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@WebServlet("/generate")
public class CreditCalculatorServlet extends HttpServlet {

    private static final long serialVersionUID = 6312252786182702635L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Credit credit = processCredit(request, response);

        if (null != credit) {
            ByteArrayOutputStream output = new CreditCalculator().generateDocument(credit, DocumentType.PDF);
            response.addHeader("Content-Type", "application/pdf");
            response.getOutputStream().write(output.toByteArray());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Credit credit = processCredit(request, response);

        request.setAttribute("credit", credit);
        include("simulation.jsp", request, response);
    }

    protected Credit processCredit(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Credit credit = null;

        try {
            Double capital = Double.valueOf(getField(request, "capital", true));
            Integer instalments = Integer.valueOf(getField(request, "instalments", true));
            Double interestRate = Double.valueOf(getField(request, "interestRate", true));
            Double fixedCharge = Double.valueOf(getField(request, "fixedCharge", true));
            String type = getField(request, "type", true);

            credit = new CreditCalculator().makeSimulation(capital, instalments, interestRate, fixedCharge,
                    CreditType.valueOf(type));

        } catch (ValidationException e) {
            response.sendRedirect("/");
        }

        return credit;
    }

    protected void include(String page, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        request.getRequestDispatcher(page).include(request, response);
    }

    protected String getField(HttpServletRequest request, String name, boolean required) {
        String value = request.getParameter(name);

        if (null == value || value.trim().isEmpty()) {
            if (required) {
                throw new ValidationException("Field is required");
            } else {
                value = null;
            }
        }

        return value;
    }
}
