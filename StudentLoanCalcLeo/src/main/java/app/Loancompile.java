package app;

import java.time.LocalDate;

import org.apache.poi.ss.formula.functions.FinanceLib;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Loancompile {
    private final ObservableList<Paymentneed> information = FXCollections.observableArrayList();
    
    private double tPayments;
    
    private double tInterests;

    public double getTotalPayments() {
        tPayments=0;
        
        for(int i=1;i<information.size();i++){
            tPayments = tPayments+Double.parseDouble(information.get(i).getPayment());
        }
        tPayments=Rounding2(tPayments);
        return tPayments;
    }

    public double getTotalInterests() {
        tInterests=0;
        for(int i=1;i<information.size();i++){
            tInterests=tInterests+Double.parseDouble(information.get(i).getInterest());
        }
        tInterests=Rounding2(tInterests);
        return tInterests;
    }

    public ObservableList<Paymentneed> CalculatePayment(double total_loan_amount, double interestRate, double total_Number_Of_Years, double addPayment, LocalDate exactdate){
        double databalance=Rounding2(total_loan_amount-addPayment);
        information.add(new Paymentneed(null,null,null,null,null,null,String.format("%.2f",databalance)));
        double pmt = Rounding2(Math.abs(FinanceLib.pmt(interestRate/12.00, total_Number_Of_Years*12.00, total_loan_amount, 0, false)));

        int initial_payment_number=0;
        while(databalance>0){
        	double principal;
            double pay;
        	double realinterest=Rounding2(databalance*interestRate/12.00);
            
            if(Rounding2(pmt-(databalance+realinterest))>=-0.01){
                principal=databalance;
                pay=Rounding2(databalance+realinterest);
                databalance=0;
            }else{
                principal=Rounding2(pmt-realinterest);
                pay=pmt;
                databalance=Rounding2(databalance-principal);
            }
            initial_payment_number++;
            information.add(
                    new Paymentneed(Integer.toString(initial_payment_number),exactdate.toString(),String.format("%.2f",pay),null,String.format("%.2f",realinterest),String.format("%.2f",principal),String.format("%.2f",databalance)));
            exactdate=exactdate.plusMonths(1);
        }
        return information;
    }

    public static double Rounding2(double value_1) {
        return ((double)Math.round(value_1*100))/100;
    }

    public static class Paymentneed {
        private final SimpleStringProperty NumberofPayment;private final SimpleStringProperty exactdate1;
        private final SimpleStringProperty totalpayment;private final SimpleStringProperty additionalPayment;
        private final SimpleStringProperty annualyinterest;private final SimpleStringProperty principle;
        private final SimpleStringProperty balance;

        public Paymentneed(String paymentNumber, String date, String payment, String additionalPayment, String interest, String principle, String balance) {
            this.NumberofPayment = new SimpleStringProperty(paymentNumber);this.exactdate1 = new SimpleStringProperty(date);
            this.totalpayment = new SimpleStringProperty(payment);this.additionalPayment = new SimpleStringProperty(additionalPayment);
            this.annualyinterest = new SimpleStringProperty(interest);this.principle = new SimpleStringProperty(principle);
            this.balance = new SimpleStringProperty(balance);
        }

        public String getPaymentNumber() {
            return this.NumberofPayment.get();
        }
        public String getBalance() {
            return this.balance.get();
        }

        public void setBalance(String balance) {
            this.balance.set(balance);
        }

        

        public void setDate(String date) {
            this.exactdate1.set(date);
        }

        public String getPayment() {
            return this.totalpayment.get();
        }

        public void setPayment(String payment) {
            this.totalpayment.set(payment);
        }

        public String getAdditionalPayment() {
            return this.additionalPayment.get();
        }

        public void setAdditionalPayment(String additionalPayment) {
            this.additionalPayment.set(additionalPayment);
        }

        public String getInterest() {
            return this.annualyinterest.get();
        }

        public void setInterest(String interest) {
            this.annualyinterest.set(interest);
        }

        public String getPrinciple() {
            return this.principle.get();
        }

        public void setPrinciple(String principle) {
            this.principle.set(principle);
        }
        
        public void setPaymentNumber(String paymentNumber) {
            this.NumberofPayment.set(paymentNumber);
        }

        public String getDate() {
            return this.exactdate1.get();
        }

        
    }
}
