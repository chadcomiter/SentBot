package sentbot.model;


public class NewsHeadline extends Source {
	protected String NewsSource;	
	protected String Headline;


public NewsHeadline(int SourceKey, String Sentiment, Sector Sector, String NewsSource) {
	super(SourceKey, Sentiment, Sector);
	this.NewsSource = NewsSource;
}

public String getNewsSource() {
	return NewsSource;
}

public void setNewsSource(String NewsSource) {
	this.NewsSource = NewsSource;
}

public String getHeadline() {
	return Headline;
}

public void setHeadline(String Headline) {
	this.Headline = Headline;
}

}