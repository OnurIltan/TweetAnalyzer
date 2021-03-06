package ozu.tweetanalyzer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import model.DatabaseModel;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TrendPanel extends JPanel {


	private static final long serialVersionUID = 1L;
	private JList<String> list;
	private DatabaseModel database;
	private ConfigurationBuilder cb;
	private DefaultListModel<String> model;

	public TrendPanel(DatabaseModel database) {
		this.database = database;
		getTrendsFromTwitter();
		createTrendPanel(database);

	}

	private void createTrendPanel(DatabaseModel database) {
		setLayout(new BorderLayout());
		model = new DefaultListModel<String>();
		list = new JList<String>(model);
		JScrollPane pane = new JScrollPane(list);
		int i  = 0 ;
		for (String t : database.getTrendTopicList()) {
			if(i==20)break;
			model.addElement(t);
			database.getTrendTopicArray()[i] = t;
			i++;
		}   


		TitledBorder spamTitle = new TitledBorder(new LineBorder(Color.WHITE, 1),"TRENDS OF THE DAY");
		spamTitle.setTitleFont(new Font("Arial", Font.BOLD, 14));
		spamTitle.setTitleJustification(TitledBorder.CENTER);
		spamTitle.setTitlePosition(TitledBorder.TOP);
		pane.setBorder(spamTitle);
		add(pane, BorderLayout.NORTH);
	}


	public void getTrendsFromTwitter(){
		ConfigurationBuilder cb = defineConfBuilder();
		try {
			Twitter twitter = new TwitterFactory(cb.build()).getInstance();
			//int turkeyTrends = getTrendsFromTurkey(twitter)23424969;
			Trends trends = twitter.getPlaceTrends(1);//takes worldwide trends
			int i  = 0;
			for (Trend trend : trends.getTrends()) {
				if(!isProbablyArabic(trend.getName()) && !containsHanScript(trend.getName()) &&
						!hasJapanese(trend.getName()) && !hasKorean(trend.getName())
						&&!hasRussian(trend.getName())){
					if(i==20)break;
					database.getTrendTopicList().add(trend.getName());
					i++;
				}
			}
		} catch (TwitterException e) {
			e.printStackTrace();
			System.out.println("Failed to get current trends: " + e.getMessage());
		}
	}

	private ConfigurationBuilder defineConfBuilder(){
		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey("gYBOmrWOnlS69DScInBWrTGFr")
				.setOAuthConsumerSecret("wv8LHjQnCUjR28ALBVinPFRIiufvCQoh82qvmCo2ufkioZVFkU")
				.setOAuthAccessToken("964455826084777984-OxlNnBZYownQAwRAa57c61UApqsYUJ3")
				.setOAuthAccessTokenSecret("frUPdfMpefBgWrwGOFn292o4emhUdYg31gpTy9PijLldW");
		cb.setJSONStoreEnabled(true);
		return cb;
	}
	public boolean isProbablyArabic(String s) {
		for (int i = 0; i < s.length();) {
			int c = s.codePointAt(i);
			if (c >= 0x0600 && c <= 0x06E0)
				return true;
			i += Character.charCount(c);            
		}
		return false;
	}
	public boolean containsHanScript(String s) {
		return s.codePoints().anyMatch(
				codepoint ->
				Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.HAN);
	}


	public boolean hasKorean(CharSequence charSequence) {
		boolean hasKorean = false;
		for (char c : charSequence.toString().toCharArray()) {
			if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HANGUL_JAMO
					|| Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO
					|| Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HANGUL_SYLLABLES) {
				hasKorean = true;
				break;
			}
		}

		return hasKorean;
	}

	public boolean hasJapanese(CharSequence charSequence) {
		boolean hasJapanese = false;
		for (char c : charSequence.toString().toCharArray()) {
			if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
					|| Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HIRAGANA
					|| Character.UnicodeBlock.of(c) == Character.UnicodeBlock.KATAKANA
					|| Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
					|| Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
					|| Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) {
				hasJapanese = true;
				break;
			}
		}

		return hasJapanese;
	}




	public boolean hasRussian(String charSequence){

		boolean isRussian = false;

		for(int i = 0; i < charSequence.length(); i++) {
			if(Character.UnicodeBlock.of(charSequence.charAt(i)) == Character.UnicodeBlock.CYRILLIC) {
				isRussian = true;
				break;

			}
		}

		return isRussian;
	}

	public JList<String> getList() {
		return list;
	}


	public void setList(JList<String> list) {
		this.list = list;
	}







}
