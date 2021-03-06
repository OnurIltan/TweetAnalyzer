package ozu.tweetanalyzer;


import java.util.Calendar;

import javax.swing.JFrame;

import org.jfree.ui.RefineryUtilities;
import controller.ChartController;
import controller.CosineSimilarityPanelController;
import controller.MapController;
import controller.UrlController;
import model.ChartModel;
import model.CosineSimilarityPanelModel;
import model.DatabaseModel;
import model.MapModel;
import model.UrlModel;
import view.ChartView;
import view.CosineSimilarityPanelView;
import view.MapView;
import view.UrlView;


public class App 
{


	public static void main(String[] args)
	{
		CurrentTime time = new CurrentTime();
		Calendar cal = time.getCal();
		DatabaseModel database = new DatabaseModel();
		EntityRecognition recognition = new EntityRecognition(database);
		SpamDetector spamDetector = new SpamDetector();
		TrendPanel trendPanel= new TrendPanel(database);
		SearchPanel searchPanel = new SearchPanel(trendPanel,cal);
		CosineSimilarityStream cosineStream = new CosineSimilarityStream(database,time,spamDetector);
		Search searchRecentTweets = new Search(searchPanel);
		ApplicationMainFrame appFrame = new ApplicationMainFrame();
		Stream stream = new Stream(searchPanel);
		TweetLocationFinder getLocationFromAccountInfo = new TweetLocationFinder();
		startMVCPattern(cosineStream,getLocationFromAccountInfo,searchPanel,searchRecentTweets,stream,appFrame,database,recognition,spamDetector,time);

	}



	public static void startMVCPattern(CosineSimilarityStream cosineStream,TweetLocationFinder getLocationFromAccountInfo,SearchPanel searchPanel,Search searchRecentTweets, Stream stream,ApplicationMainFrame appFrame, DatabaseModel database,  EntityRecognition recognition, SpamDetector spamDetector,  CurrentTime currentTime)
	{


		ChartModel locationChartModel = new ChartModel();
		ChartController locationController = new ChartController(locationChartModel, new ChartView());
		locationController.populateChart();

		ChartModel organizationChartModel = new ChartModel();
		ChartController organizationController = new ChartController(organizationChartModel, new ChartView());
		organizationController.populateChart();

		ChartModel personChartModel = new ChartModel();
		ChartController personController = new ChartController(personChartModel, new ChartView());
		personController.populateChart();

		ChartModel languageChartModel = new ChartModel();
		ChartController languageController = new ChartController(languageChartModel, new ChartView());
		languageController.populateChart();

		ChartModel hashtagChartModel = new ChartModel();
		ChartController hashtagController = new ChartController(hashtagChartModel, new ChartView());
		hashtagController.populateChart();

		ChartModel allWordsChartModel = new ChartModel();
		ChartController allWordsController = new ChartController(allWordsChartModel, new ChartView());
		allWordsController.populateChart();

		MapModel mapModel = new MapModel();
		MapController mapController = new MapController(mapModel, new MapView(mapModel,getLocationFromAccountInfo));
		mapController.populateMap();
		
		
		UrlModel urlModel = new UrlModel();
		UrlController urlController = new UrlController(urlModel, new UrlView(urlModel));
		urlController.populateUrlPanel();		

	
		
		CosineSimilarityPanelModel cosinePanelModel = new CosineSimilarityPanelModel();
		CosineSimilarityPanelView  cosineSimilarityPanelView = new CosineSimilarityPanelView(cosinePanelModel);
		cosineSimilarityPanelView.populateCosinePanel();
		CosineSimilarityPanelController  cosineController = new CosineSimilarityPanelController(cosinePanelModel,cosineSimilarityPanelView);

		searchPanel.populateSearchPanel(cosineStream,searchRecentTweets,stream, database, recognition, spamDetector, currentTime, mapController,locationController,
				organizationController, personController, languageController, hashtagController, urlController, allWordsController,cosineController);


		appFrame.populateApplication(cosineSimilarityPanelView,searchPanel,mapController.getPanel(),locationController.getSplitPane(),organizationController.getSplitPane(), personController.getSplitPane(), languageController.getSplitPane(), hashtagController.getSplitPane(), urlController.getView(),allWordsController.getSplitPane());
		appFrame.pack();
		RefineryUtilities.centerFrameOnScreen(appFrame);
		appFrame.setVisible(true);
		appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}


}
