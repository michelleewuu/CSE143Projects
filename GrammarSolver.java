// Michelle Wu
// 02/10/2022
// CSE 143E
// TA: Khushi Chaudhari
// Take-home Assessment #5
// This program creates the GrammarSolver class that accepts a list of rules
// for a grammar in Backus-Naur Form and randomly generates elements of that
// grammar based on the user's input. 

import java.util.*;

public class GrammarSolver {
   private Map<String, String[]> grammarMap;
   
   // Takes in a list of grammar rules and breaks them apart to construct
   // a GrammarSolver. Stores the split rules into a Map for future use.
   // @throws - IllegalArgumentException if the list is empty or if there
   //           are two or more entries for the same non-terminal
   // @param rules - the given set of rules for GrammarSolver to execute
   public GrammarSolver(List<String> rules) {
      if (rules.isEmpty()) {
         throw new IllegalArgumentException();
      }
      grammarMap = new TreeMap<>();
      for (String str: rules) {
         String[] splitRules = str.split("::=");
         String nonTerminal = splitRules[0];
         if (grammarMap.containsKey(nonTerminal)) {
            throw new IllegalArgumentException();
         }
         String terminal = splitRules[1].trim();
         String[] terminals = terminal.split("\\|");
         grammarMap.put(nonTerminal, terminals);
      }
   }

   // @param symbol - the given symbol in the grammar
   // @return - true if the symbol is a non-terminal, false otherwise
   public boolean grammarContains(String symbol) {
      return grammarMap.containsKey(symbol);
   }
   
   // @return - a sorted String representation of the nonterminal symbols
   //           from the given grammar list 
   public String getSymbols() {
      return grammarMap.keySet().toString();
   }
   
   // Generates the number of occurences based on the user's input
   // @throws - IllegalArgumentException if times is negative or if the
   //           String argument is not a non-terminal in the grammar
   // @param symbol - the given symbol in the grammar
   // @param times - the number of times random occurences of the given 
   //                symbol should be generated
   // @return - a String array of consisting of "times" random occurences
   //           of the given symbol 
   public String[] generate(String symbol, int times) {
      if (times < 0 || !grammarContains(symbol)) {
         throw new IllegalArgumentException();
      }
      String[] grammarString = new String[times];
      for (int i = 0; i < times; i++) {
         grammarString[i] = generateString(symbol).toString();
      }
      return grammarString;
   }
   
   // Generates random occurences of a given symbol from the given grammar
   // @param symbol - the given symbol in the grammar
   // @return - random strings of a combination of non-terminal symbols
   private String generateString(String symbol) {
      if (!grammarContains(symbol)) {
         return symbol;
      } else {
         String result = "";
         String[] rules = grammarMap.get(symbol);
         int random = (int) (Math.random() * rules.length);
         String[] splitRules = rules[random].split("\\s+");
         for (int i = 0; i < splitRules.length; i++) {
            result += " " + generateString(splitRules[i]);
         }
         return result.trim();
      }
   }
}