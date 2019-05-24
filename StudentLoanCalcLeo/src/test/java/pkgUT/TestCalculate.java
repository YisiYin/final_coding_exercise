package pkgUT;

import app.Loancompile;
import javafx.collections.ObservableList;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class TestCalculate {
    @Test
    public void testCalculate(){
        double loanAmount=650.00;double interestRate=0.5;int periodOfYears=1;
        LocalDate firstPaymentDate= LocalDate.of(2019,5,1);
        double additionalPayment=0.00;

        double expectedPaymentPerMonth[]={
                69.93, 69.93, 69.93, 69.93, 69.93, 69.93, 69.93, 69.93, 69.93, 69.93, 69.93, 69.93
        };
        double expectedInterestPerMonth[]={
                27.08, 25.30, 23.44, 21.50, 19.48, 17.38, 15.19, 12.91, 10.54, 8.06, 5.48, 2.80
        };
       double expectedPrincipalPerMonth[]={
               42.85, 44.63, 46.49, 48.43, 50.45, 52.55, 54.74, 57.02, 59.39, 61.87, 64.45, 67.13
       };
       double expectedBalancePerMonth[]={
               607.15, 562.52, 516.03, 467.60, 417.15, 364.60, 309.86, 252.84, 193.45, 131.58, 67.13, 0.00
       };

       double expected_Total_Payment=839.16;
       double expected_Total_Interest=189.16;
        Loancompile loancompile1 = new Loancompile();
        ObservableList<Loancompile.Paymentneed> data
                = loancompile1.CalculatePayment(loanAmount,interestRate,periodOfYears,additionalPayment,firstPaymentDate);

        for(int i=1;i<data.size();i++){
            assertEquals(Double.parseDouble(data.get(i).getPayment()),expectedPaymentPerMonth[i-1],0.01);
            assertEquals(Double.parseDouble(data.get(i).getInterest()),expectedInterestPerMonth[i-1],0.01);
            assertEquals(Double.parseDouble(data.get(i).getPrinciple()),expectedPrincipalPerMonth[i-1],0.01);
            assertEquals(Double.parseDouble(data.get(i).getBalance()),expectedBalancePerMonth[i-1],0.01);
        }
        assertEquals(expected_Total_Interest,loancompile1.getTotalInterests(),0.01);
        assertEquals(expected_Total_Payment,loancompile1.getTotalPayments(),0.01);
    }
}
