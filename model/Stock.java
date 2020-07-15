package sentbot.model;


//Declaration of POJO Stock Extends ImpactedInvestment

public class Stock extends ImpactedInvestment {
	protected String CompanyName;

//Stock Constructor
public Stock(int InvestmentKey, String Ticker, Sector Sector, String CompanyName, int SourceKey) {
	super(InvestmentKey, Ticker, Sector, SourceKey);
	this.CompanyName = CompanyName;
}

public String getCompanyName() {
	return CompanyName;
}

public void setCompanyName(String companyName) {
	this.CompanyName = companyName;
}


}