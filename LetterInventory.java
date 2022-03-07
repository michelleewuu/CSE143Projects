// Michelle Wu
// 01/13/2022
// CSE 143E
// TA: Khushi Chaudhari
// Take-home Assessment #1
// This program implements ArrayLists to keep track of an inventory of letters
// of the alphabet. It takes in a String, computes the number of each letter in 
// the alphabet, and returns an inventory of the respective counts. It ignores 
// any non-alphabetic characters as well as the casing of all letters. 

import java.util.*;

public class LetterInventory {
   public static final int ALPHABET = 26;
   
   private int[] alphabetInventory;
   private int size;
  
   // constructs an ArrayList containing the counts of each alphabetic letter
   // that appears in the given string data
   // ignores the casing of letters and any non-alphabetic characters
   // @param data - the string being passed into the constructor
   public LetterInventory(String data) {
      alphabetInventory = new int[ALPHABET];
      
      data = data.toLowerCase();
      for (int i = 0; i < data.length(); i++) {
         char dataChar = data.charAt(i);
         if (Character.isLetter(data.charAt(i))) {
            alphabetInventory[dataChar - 'a']++;
            size++;
         }
      }       
   }
   
   // returns the count of a specific letter within the inventory, throws an 
   // IllegalArgumentException if a non-alphabetic character is passed
   // @param letter - the letter being passed into the method
   public int get(char letter) {
      letter = Character.toLowerCase(letter);
      if (!Character.isLetter(letter)) {
         throw new IllegalArgumentException();
      }
      return alphabetInventory[letter - 'a'];
   }
   
   // sets the count for the given letter to the given value
   // if letter is non-alphabetic or if value < 0, throws IllegalArgumentException
   // @param letter - the specific letter being passed in
   // @param value - the value being assigned to the letter
   public void set(char letter, int value) {
      if (!Character.isLetter(letter) || value < 0) {
         throw new IllegalArgumentException();
      }
      letter = Character.toLowerCase(letter);
      size = size - alphabetInventory[letter - 'a'];
      alphabetInventory[letter - 'a'] = value;
      size = size + value;
   }
   
   // returns the current number of elements in the list
   public int size() {
      return size;
   }
   
   // returns true if inventory is empty (all counts are 0)
   // returns false if inventory is not empty
   public boolean isEmpty() {
      return size == 0;
   }
      
   // returns a String representation of the inventory with all letters in lowercase
   // and in sorted alphabetical order
   public String toString() {
      String result = "[";
      for (int i = 0; i < ALPHABET; i++) {
         for (int j = 0; j < alphabetInventory[i]; j++) {
            result += (char) ('a' + i);
         }
      }
      result += "]";
      return result;
   }
   
   // constructs a new LetterInventory object that sums up the respective counts of each
   // letter within alphabetInventory and the other given LetterInventory 
   // returns the combinedInventory object with the combined counts
   // @param LetterInventory other - the other given inventory to be added with alphabetInventory 
   public LetterInventory add(LetterInventory other) {
      LetterInventory combinedInventory = new LetterInventory("");
      for (int i = 0; i < ALPHABET; i++) {
         combinedInventory.set((char) ('a' + i), this.alphabetInventory[i] + other.get((char) ('a' + i)));
      }
      return combinedInventory;
   }
   
   // constructs a new LetterInventory object that subtracts the counts of each letter in the other
   // given inventory from the counts in alphabetInventory 
   // returns the combinedInventory object with the respective counts
   // returns null if the resulting value < 0 
   // @param LetterInventory other - the other given inventory to be subtracted from alphabetInventory
   public LetterInventory subtract(LetterInventory other) {
      LetterInventory combinedInventory = new LetterInventory("");
      for (int i = 0; i < ALPHABET; i++) {
         int value = this.alphabetInventory[i] - other.get((char) ('a' + i));
         if (value < 0) {
            return null;
         } else {
            combinedInventory.set((char) ('a' + i), value);
         }
      }
      return combinedInventory;
   }
}