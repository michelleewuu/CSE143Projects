// Michelle Wu
// 02/03/2022
// CSE 143E
// TA: Khushi Chaudhari
// Take-home Assessment #4
// This program creates the HangmanManager class that manages a game of Evil
// Hangman. Based on the user's guesses, the computer narrows down its set of
// possible answers and chooses the largest family of words that contains the
// user's guesses. In the case of a tie for the largest family of words, the
// program selects the one whose pattern comes alphabetically first.

import java.util.*;

public class HangmanManager {
   private Set<String> consideredWords;
   private int totalGuesses;
   private Set<Character> lettersGuessed;
   private String pattern;

   // Takes in a dictionary of words, a target word length, and a number of
   // maximum guesses that the user is allowed to make in order to initialize
   // the state of the game. The set of considered words contains all words 
   // of the given target length and no duplicates of any word.
   // @throws - IllegalArgumentException if length is less than 1 or max is
   //           less than 0
   // @param dictionary - the given Collection of words that the computer
   //                     has to choose from 
   // @param length - the target word length 
   // @param max - the number of guesses the user is allowed
   public HangmanManager(Collection<String> dictionary, int length, int max) {
      if (length < 1 || max < 0) {
         throw new IllegalArgumentException();
      }
      consideredWords = new TreeSet<String>();
      totalGuesses = max;
      lettersGuessed = new TreeSet<Character>();
      for (String word: dictionary) {
         if (word.length() == length) {
            consideredWords.add(word);
         }
      }
      pattern = "";
      for (int i = 0; i < length; i++) {
         pattern += "- ";
      }
   }
   
   // @return - the current set of words being considered by the HangmanManager
   public Set<String> words() {
      return consideredWords;
   }
   
   // @return - the number of guesses the player has left
   public int guessesLeft() {
      return totalGuesses;
   }
   
   // @return - the current set of letters that the player has guessed
   public Set<Character> guesses() {
      return lettersGuessed;
   }
   
   // Takes into account the guesses that have already been made and displays
   // them in the current pattern. The letters that have not been guessed are
   // displayed as dashes, and each letter/dash is separated by a space.
   // @throws - IllegalStateException if the set of considered words is empty
   // @return - the current pattern to be displayed for the game
   public String pattern() {
      if (consideredWords.isEmpty()) {
         throw new IllegalStateException();
      }
      return pattern;
   }
   
   // Records the guess that the player makes to determine which family of words
   // to use next.
   // @throws - IllegalStateException if the remaining guesses is less than 1
   //           or if the set of possible words is empty
   // @throws - IllegalArgumentException if the character that user guesses has 
   //           already been guessed
   // @param guess - the character that the user guesses
   // @return - the number of occurences of the character that the user guesses 
   //           in the new pattern and updates the number of guesses that the
   //           user has left
   public int record(char guess) {
      if (totalGuesses < 1 || consideredWords.isEmpty()) {
         throw new IllegalStateException();
      }
      if (lettersGuessed.contains(guess)) {
         throw new IllegalArgumentException();
      }
      lettersGuessed.add(guess);
      Map<String, Set<String>> wordFamily = createMap(guess);
      String temp = "";
      int max = 0;
      for (String keyValue : wordFamily.keySet()) {
         if (wordFamily.get(keyValue).size() > max) {
            pattern = keyValue;
            consideredWords = wordFamily.get(pattern);
            max = wordFamily.get(pattern).size();
         }
      }
      return trackCount(guess);
   }
   
   // Associates the current family pattern with the set of words that has the 
   // same pattern to create a Map containing the set of all possible words that
   // the computer can choose from.
   // @param guess - the character that the user guesses
   // @return - the updated word family based on the user's guess
   private Map<String, Set<String>> createMap(char guess) {
      Map<String, Set<String>> wordFamily = new TreeMap<>();
      for (String word: consideredWords) {
         String pattern = createPattern(word, guess);
         Set<String> setOfWords = new TreeSet<>();
         if (!wordFamily.containsKey(pattern)) {
            wordFamily.put(pattern, setOfWords);
         }
         wordFamily.get(pattern).add(word);
      }
      return wordFamily;
   }
   
   // Creates the pattern to be displayed based on the user's guess and the
   // possible family of words that the computer can choose from.
   // @param word - the word that the computer is considering
   // @param guess - the character that the user guesses
   // @return - the pattern that is displayed in the console
   private String createPattern(String word, char guess) {
      String display = "";
      for (int i = 0; i < word.length(); i++) {
         if (word.charAt(i) != guess) {
            display += pattern.charAt(i * 2) + " ";
         } else {
            display += guess + " ";
         }
      }
      return display;
   }
   
   // Tracks the number of occurences of the user's guess in the family of
   // words. If the occurence of the guess is zero, the number of remaining
   // guesses is decreased by one. 
   // @param guess - the character that the user guesses
   // @return - the number of occurences of the user's guess in the current
   //           set of words
   private int trackCount(char guess) {
      int count = 0;
      for (int i = 0; i < pattern.length(); i++) {
         if (pattern.charAt(i) == guess) {
            count++;
         }
      }
      if (count == 0) {
         totalGuesses--;
      }
      return count;
   }
}