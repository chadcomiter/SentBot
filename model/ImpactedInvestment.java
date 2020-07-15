package sentbot.model;


//Declaration of class Investments
public class ImpactedInvestment {
	protected int InvestmentKey;
	protected String Ticker;
	protected Sector Sector;
	protected int SourceKey;

//Sector enum declaration
public enum Sector {
	energy, materials, industrials, consumerdiscretionary, consumerstaples, healthcare, financials, informationtechnology, teclecommunicationservices, utilities, realestate;
}

//Investments Constructor
public ImpactedInvestment(int InvestmentKey, String Ticker, Sector Sector, int SourceKey) {
	this.InvestmentKey = InvestmentKey;
	this.Ticker = Ticker;
	this.Sector = Sector;
	this.SourceKey = SourceKey;
	}

//Constructor for Investments that aren't affected by any sources
public ImpactedInvestment(int InvestmentKey, String Ticker, Sector Sector) {
	this.InvestmentKey = InvestmentKey;
	this.Ticker = Ticker;
	this.Sector = Sector;
	}

//Getters and Setters
public int getInvestmentKey() {
	return InvestmentKey;
}

public void setInvestmentKey(int InvestmentKey) {
	this.InvestmentKey = InvestmentKey;
}

public String getTicker() {
	return Ticker;
}

public void setTicker(String Ticker) {
	this.Ticker = Ticker;
}

public Sector getSector() {
	return Sector;
}

public void setSector(Sector Sector) {
	this.Sector = Sector;
}


public int getSourceKey() {
	return SourceKey;
}
public void setSourceKey(int SourceKey) {
	this.SourceKey = SourceKey;
}

}