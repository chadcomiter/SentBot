package sentbot.tools;

import sentbot.dal.*;
import sentbot.model.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;



public class Inserter {

	public static void main(String[] args) throws SQLException {
		// DAO instances.Inserter
		SourceDao sourceDao = SourceDao.getInstance();
		ImpactedInvestmentDao impactedInvestmentDao = ImpactedInvestmentDao.getInstance();
		StockDao stockDao = StockDao.getInstance();
		CommodityDao commodityDao = CommodityDao.getInstance();
		IndexFundDao indexFundDao = IndexFundDao.getInstance();
		REITDao rEITDao = REITDao.getInstance();
		EconomicDataDao economicDataDao = EconomicDataDao.getInstance();
		CurrentEventDao currentEventDao = CurrentEventDao.getInstance();
		NewsHeadlineDao newsHeadlineDao = NewsHeadlineDao.getInstance();
		CommentDao commentDao = CommentDao.getInstance();
		
		// INSERT objects from our model.
		
		EconomicData economicData = new EconomicData(1,"bullish",Source.Sector.financials, "econdata description");
		economicData = economicDataDao.create(economicData);
		CurrentEvent currentEvent = new CurrentEvent(2,"bullish",Source.Sector.healthcare,"event description");
		currentEvent = currentEventDao.create(currentEvent);
		NewsHeadline newsHeadline = new NewsHeadline(3,"bearish",Source.Sector.energy,"newssource","headline");
		newsHeadline = newsHeadlineDao.create(newsHeadline);
		Comment comment = new Comment (4,"bearish",Source.Sector.materials,"CommentText","ticker");
		comment = commentDao.create(comment);
		
		
		ImpactedInvestment impactedInvestment = new ImpactedInvestment( "stockBase",ImpactedInvestment.Sector.financials ,economicData.getSourceKey());
		impactedInvestment = impactedInvestmentDao.create(impactedInvestment);
		ImpactedInvestment impactedInvestment2 = new ImpactedInvestment("futureBase",ImpactedInvestment.Sector.healthcare ,currentEvent.getSourceKey());
		impactedInvestment2 = impactedInvestmentDao.create(impactedInvestment2);
		ImpactedInvestment impactedInvestment3 = new ImpactedInvestment("reitBase",ImpactedInvestment.Sector.realestate ,newsHeadline.getSourceKey());
		impactedInvestment3 = impactedInvestmentDao.create(impactedInvestment3);
		ImpactedInvestment impactedInvestment4 = new ImpactedInvestment("etfBase",ImpactedInvestment.Sector.materials ,comment.getSourceKey());
		impactedInvestment4 = impactedInvestmentDao.create(impactedInvestment4);
		
		Stock stock = new Stock("stock",ImpactedInvestment.Sector.financials ,"companyname",economicData.getSourceKey());
		stock = stockDao.create(stock);
		Commodity commodity = new Commodity("future",ImpactedInvestment.Sector.healthcare ,"futurename",currentEvent.getSourceKey());
		commodity = commodityDao.create(commodity);		
		IndexFund indexFund = new IndexFund("etf",ImpactedInvestment.Sector.materials ,"IndexFundName",comment.getSourceKey());
		indexFund = indexFundDao.create(indexFund);
		REIT reit = new REIT("reit",ImpactedInvestment.Sector.energy ,"reitName","reit type",newsHeadline.getSourceKey());
		reit = rEITDao.create(reit);
		
		// READ.
		List<ImpactedInvestment> i1 = impactedInvestmentDao.getImpactedInvestmentFromSector(ImpactedInvestment.Sector.healthcare);
		for(ImpactedInvestment i : i1) {
			System.out.format("Looping impactedInvestment: ticker:%s sector:%s  \n",
				i.getTicker(), i.getSector().name());
		}
		List<Stock> s1 = stockDao.getStockFromSector(ImpactedInvestment.Sector.financials);
		for(Stock s : s1) {
			System.out.format("Looping stock: ticker:%s sector:%s  \n",
				s.getTicker(), s.getSector().name());
		}
		List<Commodity> c1 = commodityDao.getCommodityFromSector(Commodity.Sector.healthcare);
		for(Commodity c : c1) {
			System.out.format("Looping commodity: ticker:%s sector:%s  \n",
				c.getTicker(), c.getSector().name());
		}
		List<REIT> r1 = rEITDao.getREITFromSector(REIT.Sector.energy);
		for(REIT r : r1) {
			System.out.format("Looping rEIT: ticker:%s sector:%s  \n",
				r.getTicker(), r.getSector().name());
		}
		List<IndexFund> I1 = indexFundDao.getIndexFundFromSector(IndexFund.Sector.energy);
		for(IndexFund I : I1) {
			System.out.format("Looping indexFund: ticker:%s sector:%s  \n",
				I.getTicker(), I.getSector().name());
		}
		List<Source> so1 = sourceDao.getSourcesBySentiment("bullish");
		for(Source so : so1) {
			System.out.format("Looping source: senti:%s sector:%s  \n",
				so.getSentiment(), so.getSector().name());
		}
		Source so2 = sourceDao.getSourceBySourceKey(1);
			System.out.format("Looping source: senti:%s sector:%s  \n",
				so2.getSentiment(), so2.getSector().name());
		
		List<Comment> co1 = commentDao.getCommentsByTickerSentiment("ticker", "bearish");
		for(Comment co : co1) {
			System.out.format("Looping comment: ticker:%s sector:%s  \n",
				co.getTicker(), co.getSentiment());
		}
		List<Comment> co2 = commentDao.getCommentsBySectorSentiment(Source.Sector.materials, "bearish");
		for(Comment co : co2) {
			System.out.format("Looping comment: ticker:%s sector:%s  \n",
				co.getTicker(), co.getSentiment());
		}
		List<EconomicData> ec1 = economicDataDao.getEconomicDatasBySectorSentiment(Source.Sector.financials,"bullish");
		for(EconomicData e : ec1) {
			System.out.format("Looping economicData: sector:%s sent:%s  \n",
				e.getSector(), e.getSentiment());
		}
		List<CurrentEvent> cu1 = currentEventDao.getCurrentEventsBySectorSentiment(Source.Sector.healthcare,"bullish");
		for(CurrentEvent cu : cu1) {
			System.out.format("Looping currentEvent: sector:%s sent:%s  \n",
				cu.getSector(), cu.getSentiment());
		}
		List<NewsHeadline> ne1 = newsHeadlineDao.getNewsHeadlineBySectorSentiment(Source.Sector.energy,"bearish");
		for(NewsHeadline ne : ne1) {
			System.out.format("Looping newsHeadline: sector:%s sent:%s  \n",
				ne.getSector(), ne.getSentiment());
		}
		List<NewsHeadline> ne2 = newsHeadlineDao.getNewsHeadlineBySectorSourceSentiment(Source.Sector.energy,"bearish","newssource");
		for(NewsHeadline ne : ne2) {
			System.out.format("Looping newsHeadline: sector:%s sent:%s source:%s  \n",
				ne.getSector(), ne.getSentiment(),ne.getNewsSource());
		}
		// updates
		ImpactedInvestment newII = impactedInvestmentDao.updateSector(impactedInvestment, ImpactedInvestment.Sector.teclecommunicationservices);
		Stock newS = stockDao.updateSector(stock, ImpactedInvestment.Sector.informationtechnology);
		Commodity newC = commodityDao.updateContent(commodity, "newFuturesName");
		REIT newR = rEITDao.updateSector(reit, ImpactedInvestment.Sector.consumerstaples);
		IndexFund newI = indexFundDao.updateContent(indexFund, "newIndexFundName");
		Source sourceS = sourceDao.updateSentiment(comment, "neutral");
		Comment commentC = commentDao.updateCommentText(comment, "new comment text");
		Comment commentD = commentDao.updateSentiment(comment, "bullish");
		EconomicData ed = economicDataDao.updateDescription(economicData, "newDescription");
		CurrentEvent ce = currentEventDao.updateDescription(currentEvent, "newDescription");
		NewsHeadline newsH = newsHeadlineDao.updateHeadline(newsHeadline, "newHeadline");
		NewsHeadline newsH2 = newsHeadlineDao.updateSentiment(newsH, "bullish");
		
		
		
		// deletes
		impactedInvestmentDao.delete(impactedInvestment);
		impactedInvestmentDao.delete(impactedInvestment2);
		impactedInvestmentDao.delete(impactedInvestment3);
		impactedInvestmentDao.delete(impactedInvestment4);
		stockDao.delete(stock);
		commodityDao.delete(commodity);
		rEITDao.delete(reit);
		indexFundDao.delete(indexFund);
	
		commentDao.delete(comment);
		economicDataDao.delete(economicData);
		currentEventDao.delete(currentEvent);
		newsHeadlineDao.delete(newsHeadline);
		
		System.out.println("Finished!");
		
			}
		}
