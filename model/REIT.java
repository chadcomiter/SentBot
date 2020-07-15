package sentbot.model;


//Declaration of POJO REIT Extends ImpactedInvestment

public class REIT extends ImpactedInvestment {
	protected String reitName;
	protected String reitType;

//REIT Constructor
public REIT(int InvestmentKey, String Ticker, Sector Sector, String reitName, String reitType, int SourceKey) {
	super(InvestmentKey, Ticker, Sector, SourceKey);
	this.reitType = reitType;
}

public String getReitName() {
	return reitName;
}

public void setReitName(String reitName) {
	this.reitName = reitName;
}

public String getreitType() {
	return reitType;
}

public void setreitType(String reitType) {
	this.reitType = reitType;
}


}