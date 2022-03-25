// Michelle Wu
// 02/24/2022
// CSE 143E
// TA: Khushi Chaudhari
// Take-home Assessment #6
// This program creates the AnagramSolver class that implements a given
// dictionary in order to search for and print all of the possible anagram
// combinations of a given word or phrase. 

import java.util.*;
 
public class AnagramSolver {
   private List<String> dictionary;
   private Map<String, LetterInventory> anagramMap;

   // Takes in a given list of words from a dictionary and preprocesses it
   // in order to construct a new AnagramSolver.
   // @param dictionary - the given list of words in the dictionary
   public AnagramSolver(List<String> dictionary) {
      this.dictionary = dictionary;
      anagramMap = new HashMap<>();
      for (String word : dictionary) {
         anagramMap.put(word, new LetterInventory(word));
      }
   }
   
   // Searches the entire dictionary for words that are relevant to the given phrase
   // and prints out all of the possible combinations that have the same letters as
   // the given phrase and include at most max number of words.  
   // @throws - IllegalArgumentException if max is less than 0
   // @param text - the given phrase
   // @param max - the maximum number of words that the anagram can contain
   public void print(String text, int max) {
      if (max < 0) {
         throw new IllegalArgumentException();
      }
      List<String> shortDictionary = new ArrayList<>();
      LetterInventory inventory = new LetterInventory(text);
      pruneDictionary(text, inventory, shortDictionary);
      List<String> anagrams = new ArrayList<>();
      printCombinations(inventory, max, shortDictionary, anagrams);
   }
   
   // Prunes the dictionary by checking if a word can be subtracted from the 
   // given phrase. If the word is relevant, it is added to the reduced dictionary.
   // @param text - the given phrase that is being searched
   // @param inventory - the given letter inventory
   // @param shortDictionary - the reduced dictionary that only contains words that
   //                          are relevant to the given phrase 
   private void pruneDictionary(String text, LetterInventory inventory, 
                                List<String> shortDictionary) {
      for (String word : dictionary) {
         if (inventory.subtract(anagramMap.get(word)) != null) {
            shortDictionary.add(word);
         }
      }
   }
   
   // Searches the list of possible combinations from the inventory and prints out
   // those that are anagrams of the given phrase. 
   // @param inventory - the given letter inventory
   // @param max - the maximum number of words that the anagram can contain
   // @param shortDictionary - the reduced dictionary that only contains words that
   //                          are relevant to the given phrase
   // @param anagrams - the list of possible combinations that are anagrams of the
   //                   the given phrase 
   private void printCombinations(LetterInventory inventory, int max,
                                  List<String> shortDictionary, List<String> anagrams) {
      if (inventory.isEmpty()) {
         System.out.println(anagrams);
      }
      if (max == 0 || max > anagrams.size()) {
         for (String word : shortDictionary) {
            LetterInventory newInventory = inventory.subtract(anagramMap.get(word));
            if (newInventory != null) {
               anagrams.add(word);
               printCombinations(newInventory, max, shortDictionary, anagrams);
               anagrams.remove(anagrams.size() - 1);
            }
         }
      }
   } 
}