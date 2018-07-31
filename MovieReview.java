/**
 *  @author [Augustine Valdez]
 *  
 *  @description:  [Naive machine learning program that rates your movie review]
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class MovieReview {
	public static void main(String args[]) {
		HashDictionaryLinear<String, WordEntry> words = new HashDictionaryLinear<String, WordEntry>();

		//Words that are given a positive multiplier
		ArrayList<String> buzzWords = new ArrayList<String>();
		buzzWords.add("best");
		buzzWords.add("amazing");
		buzzWords.add("awesome");
		buzzWords.add("incredible");
		buzzWords.add("stunning");
		
		//Words to ignore to make the program smarter
		ArrayList<String> articles = new ArrayList<String>();
		articles.add("a");
		articles.add("and");
		articles.add("the");

		//Words that are given a negative multiplier
		ArrayList<String> badWords = new ArrayList<String>();
		badWords.add("worst");
		badWords.add("terrible");
		badWords.add("horrible");
		badWords.add("trash");
		badWords.add("garbage");

		if (args.length != 1) {
			System.err.println("Must pass name of movie reviews file");
			System.exit(0);
		}

		// varaibles for keeping track of the input file
		String line;
		int score;
		List<String> fileLines;

		// the movie review entered by the user
		String review = " ";

		Scanner keyboard = new Scanner(System.in);

		// open input file
		try {
			// Read each line into a list
			fileLines = Files.readAllLines(Paths.get(args[0]));
		} catch(IOException e) {
			System.err.println("File " + args[0] + " not found.");
			return;
		}

		/**
		 * This logic reads through each input line and extracts each separate word.
		 */

		Iterator<String> itr = fileLines.iterator();

		while (itr.hasNext()) {
			// get the next line in the movie reviews
			line = itr.next();

			// obtain the score
			score = Integer.parseInt(line.substring(0, 1));

			// remove the score from the review
			line = line.substring(2).trim();

			// now remove all non alphanumeric characters
			line = line.replaceAll("[^a-zA-Z]+", " ");

			// splits the line into each word
			String tokens[] = line.split(" ");

			/**
			 * At this point, tokens[0] is the first word in the review,
			 * tokens[1] is the second word, and so forth.
			 */

			/**
			 * Apply the algorithm whereby you construct the dictionary that maps
			 * each word to a WordEntry object. At this point, each WordEntry object
			 * contains the total score and number of appearances of each word.
			 */

			for(int i = 0; i < tokens.length; i++) {

				if(words.contains(tokens[i])) {
					if(articles.contains(tokens[i])) {


					} else if(buzzWords.contains(tokens[i])) {
						double originalScore = words.get(tokens[i]).getScore(); 
						words.get(tokens[i]).setScore((1.5 * score) + originalScore);

						int appearances = words.get(tokens[i]).getAppearances();
						words.get(tokens[i]).setAppearances(appearances+= 1);

					} else if(badWords.contains(tokens[i])) {
						double originalScore = words.get(tokens[i]).getScore(); 
						words.get(tokens[i]).setScore((0.75 * score) + originalScore);

						int appearances = words.get(tokens[i]).getAppearances();
						words.get(tokens[i]).setAppearances(appearances+= 1);	
					} else {
						double originalScore = words.get(tokens[i]).getScore(); 
						words.get(tokens[i]).setScore(originalScore + score);

						int appearances = words.get(tokens[i]).getAppearances();
						words.get(tokens[i]).setAppearances(appearances+= 1);
					}

				} else {
					WordEntry newWord = new WordEntry(tokens[i], score, 1);
					words.put(tokens[i], newWord);

				}
			}
		}

		System.out.println("Just read " + fileLines.size() + " reviews.");



		// After movie reviews have been entered in the dictionary, prompt the user for a new movie review

		while (review.length() > 0) {
			System.out.println("Enter a review -- Press return to exit");
			review = keyboard.nextLine();


			// parse message
			review = review.replaceAll("[^a-zA-Z]+", " ");

			// split the tokens
			String tokens[] = review.split(" ");

			/**
			 * Now apply the algorithm that gets the average score for each of the
			 * keywords, and calculates an average score for the review.
			 */

			/**
			 * Now report the review based upon the idea that:
			 * 
			 * > 2 positive review
			 * 
			 * == 2 neutral review
			 * 
			 * < 2 negative review
			 */

			double averageScore = 0;
			for(int i = 0; i < tokens.length; i++) {
				if(articles.contains(tokens[i])) {

				}

				if(words.contains(tokens[i])) {
					averageScore += words.get(tokens[i]).getAverage();
				} else {
					if(!articles.contains(tokens[i])) {
						averageScore += 2;
					}
				}
			}
			averageScore = averageScore/tokens.length;

			System.out.println("The review has an average score of: " + averageScore);

			if(averageScore > 2) {
				System.out.println("Positive Review");
			}

			if(averageScore == 2) {	
				System.out.println("Neutral Review");
			}

			if(averageScore < 2) {
				System.out.println("Negative Review");
			}
		}
	}
}
