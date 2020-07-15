package sentbot.model;


//Declaration of POJO IndexFund Extends ImpactedInvestment

public class IndexFund extends ImpactedInvestment {
	protected String IndexFundName;

//IndexFund Constructor
public IndexFund(int InvestmentKey, String Ticker, Sector Sector, String IndexFundName, int SourceKey) {
	super(InvestmentKey, Ticker, Sector, SourceKey);
	this.IndexFundName = IndexFundName;
}

public String getIndexFundName() {
	return IndexFundName;
}

public void setIndexFundName(String IndexFundName) {
	this.IndexFundName = IndexFundName;
}


}