package ozu.tweetanalyzer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.ChartController;
import model.DatabaseModel;

public class SearchPanel  {

	private JLabel  namelabel= new JLabel("Enter query: ", JLabel.RIGHT);
	private JPanel controlPanel = new JPanel();	      
	private final JTextField userText = new JTextField(10);	        
	private JButton loginButton = new JButton("SEARCH");	  
	private JButton restartButton = new JButton("RESTART");	
	private JLabel label = new JLabel("<html>This is a tool to analyze real-time tweets. You need to enter a query that can be a specific event<BR>and after you enter the search button user can see the tweets visualization from world map<BR>and also user can see the names, organizations, locations and tweets<BR>languages as a graph. This tool uses Stanford named entity recognizer tool to identify tweets and recognizes<BR>which names, locations and organization names mentioned in a tweet, and if Twitter user shared his/her location, user also can see where<BR>tweet tweeted in world map as a visualization.</html>");





	public JPanel populateSearchPanel(Stream stream,TwitterAuthorization authorize,final DatabaseModel database, final EntityRecognition recognition,final SpamDetector spamDetector, final CurrentTime currentTime,
			final ChartController locationController, final ChartController organizationController,final ChartController personController,final ChartController languageController,final ChartController hashtagController,final ChartController urlController){

		label.setBackground(Color.blue);
		label.setVerticalTextPosition(JLabel.BOTTOM);
		label.setHorizontalTextPosition(JLabel.CENTER);
		controlPanel.add(namelabel);
		controlPanel.add(userText);	     
		controlPanel.add(loginButton);
		controlPanel.add(restartButton);
		controlPanel.add(label);

		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {     
				database.setSearchQuery(userText.getText());
				stream.startStream(authorize, database, recognition, spamDetector, currentTime, locationController, organizationController, personController, languageController, hashtagController, urlController);


			}
		}); 

		return controlPanel;


	}



	public JLabel getNamelabel() {
		return namelabel;
	}



	public void setNamelabel(JLabel namelabel) {
		this.namelabel = namelabel;
	}



	public JPanel getControlPanel() {
		return controlPanel;
	}



	public void setControlPanel(JPanel controlPanel) {
		this.controlPanel = controlPanel;
	}



	public JButton getLoginButton() {
		return loginButton;
	}



	public void setLoginButton(JButton loginButton) {
		this.loginButton = loginButton;
	}



	public JButton getRestartButton() {
		return restartButton;
	}



	public void setRestartButton(JButton restartButton) {
		this.restartButton = restartButton;
	}



	public JLabel getLabel() {
		return label;
	}



	public void setLabel(JLabel label) {
		this.label = label;
	}



	public JTextField getUserText() {
		return userText;
	}











}
