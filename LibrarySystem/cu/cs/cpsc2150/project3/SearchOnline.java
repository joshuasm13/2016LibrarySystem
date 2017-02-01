package cu.cs.cpsc2150.project3;

/**
 * SearchOnline - Enters search into website and parses book info into table
 * 
 * @author jsm4 | Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class SearchOnline extends JTable{
	public SearchOnlineModel SM;		/** Table holding search results */
	private String [] stringToSearch;	/** String to search online */
	public Book selectedBook = null;	/** Book currently selected by user (listener) */

	/**
	 * @param s - Phrase to search online
	 * @throws IOException - If encounters network error
	 */
	SearchOnline(String s) throws IOException{
		super(new SearchOnlineModel());
		SM = (SearchOnlineModel) super.getModel();
		SM.clear();
		if(s.length()==1)
			stringToSearch[0] = s;
		else
			stringToSearch = s.split("\\s+");
		run();
	}
	/**
	 * @return book that the user has highlighted
	 */
	public Book getSelectedBook() { return selectedBook; }

	/** 
	 * Connect to website, search phrase, and parse book info
	 * @throws IOException
	 */
	public void run() throws IOException{
		selectedBook = null;
		int count = 0;
		
		String s = "http://www.abebooks.com/servlet/SearchResults?kn=";
		for(int i=0; i<stringToSearch.length; i++)
			s+= stringToSearch[i] + "+";
		s+= "&sortby=15";
			
	
		URL url = new URL(s);
		URLConnection uc = url.openConnection();
		
		InputStreamReader input = new InputStreamReader(uc.getInputStream());
		BufferedReader in = new BufferedReader(input);
		String line;
		String titleHtml = "<meta itemprop=\"name\" content=\"";
		String authorHtml = "<meta itemprop=\"author\" content=\"";
		String author = "";
		String title = "";
		
		
		while((line = in.readLine()) != null){
			line = line.toLowerCase();

			if(line.contains(titleHtml)){				
				title = line.replace(titleHtml, "");
				title = title.replace("\" />", "");
				title = title.trim();
				title = title.replace("&#x27;", "");
				count++;
			}
			if(line.contains(authorHtml)){
				author = line.replace(authorHtml, "");
				author = author.replace("\" />", "");
				author = author.trim();
				author = author.replace("&#x27;", "");
				if((count %2) == 1)
					SM.add(new Book(title,author,"---"));
			}			
		}
		
		/**
		 * Get currently selected book
		 */

        this.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
            	if(SearchOnline.this.getSelectedRow()>=0){
	                String s = SearchOnline.this.getValueAt(SearchOnline.this.getSelectedRow(), 0).toString();
	          
	                selectedBook = SM.getBook(s);
	                
            	}
            }
        });
    
		
		
	}

	
	
}
