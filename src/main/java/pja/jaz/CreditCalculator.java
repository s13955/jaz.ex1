package pja.jaz;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import pja.jaz.model.Credit;
import pja.jaz.model.CreditInstalment;
import pja.jaz.model.CreditType;
import pja.jaz.model.DocumentType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CreditCalculator {

    /**
     * Generates a credit/loan simulation
     *
     * @param capital Capital to be loaned
     * @param period Number of instalments
     * @param interestRate Credit interest rate, expressed in percents (3.5% = 3.5)
     * @param fixedCharge Fixed charges?
     * @param creditType Credit type, i.e. decresing or equal instalment costs
     * @return List of the credit lines
     */
    public Credit makeSimulation(double capital, int period, double interestRate,
                                 double fixedCharge, CreditType creditType) {

        Credit credit = null;

        if (null != creditType) {
            credit = new Credit(capital, period, interestRate, fixedCharge, creditType);

            double capitalPart = capital / period;
            double fixedChargePart = fixedCharge / period;
            double interestPart = 0;

            if (creditType == CreditType.EQUAL) {
                double q = 1 + ((interestRate / 100) / 12);
                double qn = Math.pow(q, period);
                interestPart = capital * qn * (q - 1) / (qn - 1) - capitalPart;
            }

            if (creditType == CreditType.DECREASING) {
                interestPart = capital * (interestRate / 100 / 12);
            }

            for (int i = 1; i <= period; i++) {
                credit.addInstalment(new CreditInstalment(i, capitalPart, interestPart, fixedChargePart));

                if (creditType == CreditType.DECREASING) {
                    interestPart = (capital - (i * capitalPart)) * (interestRate / 100 / 12);
                }
            }
        }

        return credit;
    }

    public ByteArrayOutputStream generateDocument(Credit credit, DocumentType type) {
        if (type == DocumentType.PDF) {
            int perPage = 40;
            int instalments = credit.getInstalments().size();
            int pages = (int) Math.ceil((double) instalments / perPage);
            float margin = 72;
            float leading = 1.5f * 12;

            try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                PDDocument document = new PDDocument();
                PDPage page;
                PDPageContentStream contents;

                for(int i = 0; i < pages; i++) {
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);

                    float x = page.getMediaBox().getLowerLeftX() + margin;
                    float y = page.getMediaBox().getUpperRightY() - margin;
                    contents = new PDPageContentStream(document, page);
                    contents.beginText();
                    contents.setFont(PDType1Font.HELVETICA, 12);
                    contents.newLineAtOffset(x, y);
                    contents.showText(String.format("%14s %14s %14s %14s %14s",
                            "Rata", "Kwota kapitalu", "Kwota odsetek", "Oplaty stale", "Kwota raty"));

                    int start = i * perPage;
                    int step = i * perPage + perPage > instalments ? instalments : i * perPage + perPage;

                    for (CreditInstalment instalment : credit.getInstalments().subList(start, step)) {
                        String text = String.format("%14d %14.2f zl %14.2f zl %14.2f zl %14.2f zl",
                                instalment.getInstalment(), instalment.getCapital(), instalment.getInterest(),
                                instalment.getFixedCharge(), instalment.getTotal());

                        contents.newLineAtOffset(0, -leading);
                        contents.showText(text);
                    }

                    contents.endText();
                    contents.close();
                }

                document.save(output);
                document.close();

                return output;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }
}
