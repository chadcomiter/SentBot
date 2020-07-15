package sentbot.model;


//Declaration of POJO Commodity Extends ImpactedInvestment

public class Commodity extends ImpactedInvestment {
	protected String FuturesName;

//Commodity Constructor
public Commodity(int InvestmentKey, String Ticker, Sector Sector, String FuturesName, int SourceKey) {
	super(InvestmentKey, Ticker, Sector, SourceKey);
	this.FuturesName = FuturesName;
}

public String getFuturesName() {
	return FuturesName;
}

public void setFuturesName(String FuturesName) {
	this.FuturesName = FuturesName;
}


}