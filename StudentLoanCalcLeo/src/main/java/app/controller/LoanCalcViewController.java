package app.controller;

import app.Loancompile;
import app.StudentCalc;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;

public class LoanCalcViewController implements Initializable   {

	private StudentCalc SC = null;
	
	@FXML
	private TextField LoanAmount;

	@FXML
	private TextField InterestRate;

	@FXML
	private TextField NbrOfYears;

	@FXML
	private DatePicker PaymentStartDate;

	@FXML
	private TextField AdditionalPayment;

	@FXML
	private Label lblTotalPayemnts;

	@FXML
	private Label lblTotalInterests;

	@FXML
	private TableView PaymentScheduleList;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void setMainApp(StudentCalc sc) {
		this.SC = sc;
	}
	
	/**
	 * btnCalcLoan - Fire this event when the button clicks
	 * 
	 * @version 1.0
	 * @param event
	 */
	@FXML
	private void btnCalcLoan(ActionEvent event) {
		for(int i=0;i<PaymentScheduleList.getColumns().size();++i){
			TableColumn number1=(TableColumn)PaymentScheduleList.getColumns().get(i);
			if(number1.getText().equals("Payment #")){
				number1.setCellValueFactory(new PropertyValueFactory<Loancompile.Paymentneed, String>("paymentNumber"));
			}else if(number1.getText().equals("Due Date")){
				number1.setCellValueFactory(new PropertyValueFactory<Loancompile.Paymentneed, String>("date"));
			}else if(number1.getText().equals("Payment")){
				number1.setCellValueFactory(new PropertyValueFactory<Loancompile.Paymentneed, String>("payment"));
			}else if(number1.getText().equals("Additonal Payment")){
				number1.setCellValueFactory(new PropertyValueFactory<Loancompile.Paymentneed, String>("additionalPayment"));
			}else if(number1.getText().equals("Interest")){
				number1.setCellValueFactory(new PropertyValueFactory<Loancompile.Paymentneed, String>("interest"));
			}else if(number1.getText().equals("Principle")){
				number1.setCellValueFactory(new PropertyValueFactory<Loancompile.Paymentneed, String>("principle"));
			}else if(number1.getText().equals("Balance")){
				number1.setCellValueFactory(new PropertyValueFactory<Loancompile.Paymentneed, String>("balance"));
			}
		}

		double Amount = Double.parseDouble(LoanAmount.getText());
		double Rate1;
		if(InterestRate.getText().endsWith("%")){
			Rate1=Double.parseDouble(InterestRate.getText().replace("%",""))*0.01;
		}else{
			Rate1=Double.parseDouble(InterestRate.getText());
		}
		double year1 = Double.parseDouble(NbrOfYears.getText());
		double ePayment = Double.parseDouble(AdditionalPayment.getText());
		LocalDate date=PaymentStartDate.getValue();
		Loancompile loanResolver = new Loancompile();
		ObservableList<Loancompile.Paymentneed> data
				= loanResolver.CalculatePayment(Amount,Rate1,year1,ePayment,date);
		PaymentScheduleList.setItems(data);
		lblTotalPayemnts.setText(Double.toString(loanResolver.getTotalPayments()));
		lblTotalInterests.setText(Double.toString(loanResolver.getTotalInterests()));
	}
}
