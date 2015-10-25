package com.hgj.daobot.test.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import com.hgj.daobot.core.CB;
import com.hgj.daobot.test.dao.AuthorDAO;
import com.hgj.daobot.test.dao.BookDAO;
import com.hgj.daobot.test.eo.AuthorEO;
import com.hgj.daobot.test.eo.BookEO;

public class TestUtils {
	
	public static final String[] AUTHOR_NAMES = { "PEDRO","PABLO","HUGO","PALERMO","RODRIGO","JUAN","EUSTACIO","MISAEL" };
	
	public static final String[] AUTHOR_LAST_NAMES = { "GODINEZ","PEREZ","ROBLES","VELAZQUEZ","GUERRERO","SLIM","ESPINOZA","ROGRIGUEZ" };
	
	public static List<BookEO> generateAndSaveBooks(String mainTitle,int units,BookDAO dao){
		
		List<BookEO> bookList = new ArrayList<BookEO>();
		
		for (int i = 0; i < units; i++) {
			BookEO book = TestUtils.generateAndSaveBook(mainTitle+" "+i,dao);
			bookList.add(book);
		}
		
		
		return bookList;
	}
	
	public static BookEO generateAndSaveBook(String title,BookDAO dao){
		BookEO book = generateBook(title);
		dao.save(book);
		return book;
	}
	
	public static BookEO generateBook(String title){

		BookEO book = new BookEO();
		book.setTitle(title);
		book.setReleaseDate(randomDate(2000, 2015));
		book.setDescription("Some Description");
		book.setUnitsSold(randInt(50, 80000));
		book.setUnitsBought(randInt(3000, 80000));
		

		return book;
	}
	
	
	public static List<BookEO> generateAndSaveBooksWithAutor(String mainTitle,int units,BookDAO bookDAO,AuthorDAO authorDAO){
		
		List<BookEO> bookList = new ArrayList<BookEO>();
		
		for (int i = 0; i < units; i++) {
			BookEO book = TestUtils.generateBookWithAuthor(mainTitle+" "+i,authorDAO);
			bookDAO.save(book);
			bookList.add(book);
		}
		
		
		return bookList;
	}
	
	public static BookEO generateBookWithAuthor(String title,AuthorDAO authorDAO){

		BookEO book = new BookEO();
		book.setTitle(title);
		book.setReleaseDate(randomDate(2000, 2015));
		book.setDescription("Some Description");
		book.setUnitsSold(randInt(50, 80000));
		book.setUnitsBought(randInt(3000, 80000));
		book.setAuthor(findOrSave(authorDAO));
		return book;
	}
	
	public static AuthorEO findOrSave(AuthorDAO authorDAO){
		
		final String fullName = generateAuthorName();
		
		AuthorEO author = authorDAO.find(new CB(){{
			eq("name",fullName);
		}});
		
		if(author == null){
			author = generateAuthor(fullName);
			authorDAO.save(author);
		}
		
		return author;
	}
	

	public static AuthorEO generateAuthor(String name){
		
		AuthorEO author = new AuthorEO();
		author.setName(name);
		return author;
	}
	
	public static String generateAuthorName(){
		return AUTHOR_NAMES[randInt(0, 7)]+" "+AUTHOR_LAST_NAMES[randInt(0, 7)];
	}
	
	public static Date randomDate(int lowyear,int highyear){
		
		GregorianCalendar gc = new GregorianCalendar();
		

		int year = randInt(lowyear, highyear);
		
		gc.set(Calendar.YEAR, year);
		
		int dayOfYear = randInt(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
		
		gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
		
		
		
		return new Date(gc.getTimeInMillis());
		
	}
	
	/**
	 * Returns a pseudo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min Minimum value
	 * @param max Maximum value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public static String loadJsonQuery(String fileName){
	    
	    InputStream jsonFile = TestUtils.class.getClassLoader().getResourceAsStream("jsonQueries/"+fileName);
	    
	    return getStringFromInputStream(jsonFile);
	    
	}
	
	// convert InputStream to String
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

}
