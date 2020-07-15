package sentbot.model;


public class CurrentEvent extends Source {
	protected String Description;	

public CurrentEvent(int SourceKey, String Sentiment, Sector Sector, String Description) {
	super(SourceKey, Sentiment, Sector);
	this.Description = Description;
}

public String getDescription() {
	return Description;
}

public void setDescription(String description) {
	this.Description = description;
}



}
