package sentbot.model;

public class Source {
	protected int SourceKey;
	protected String Sentiment;
	protected Sector Sector;


	public enum Sector {
		energy, materials, industrials, consumerdiscretionary, consumerstaples, healthcare, financials, informationtechnology, teclecommunicationservices, utilities, realestate;
	}


public Source(int SourceKey, String Sentiment, Sector Sector) {
	this.SourceKey = SourceKey;
	this.Sentiment = Sentiment;
	this.Sector = Sector;
	}


public int getSourceKey() {
	return SourceKey;
}


public void setSourceKey(int sourceKey) {
	this.SourceKey = sourceKey;
}


public String getSentiment() {
	return Sentiment;
}


public void setSentiment(String sentiment) {
	this.Sentiment = sentiment;
}


public Sector getSector() {
	return Sector;
}


public void setSector(Sector sector) {
	this.Sector = sector;
}

	


}