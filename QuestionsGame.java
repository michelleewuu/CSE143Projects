// Michelle Wu
// 03/03/2022
// CSE 143E
// TA: Khushi Chaudhari
// Take-home Assessment #7
// This program creates the QuestionsGame class that allows the
// user to play a game of 20Questions by implementing a binary
// tree representing the computer's yes/no questions and answers.

import java.util.*;
import java.io.*;

public class QuestionsGame {
   private Scanner console;
   private QuestionNode rootOfTree;

   // Constructs a single console Scanner to read all input
   // Initializes a new QuestionsGame object with a single 
   // leaf node representing the object "computer"
   public QuestionsGame() {
      console = new Scanner(System.in);
      rootOfTree = new QuestionNode("computer");
   }
   
   // Represents and stores a single node of a binary tree.
   private static class QuestionNode {
      public QuestionNode left;
      public QuestionNode right;
      public String text; 
      
      // Takes in a String text and constructs a QuestionNode.
      // @param text - the given String text
      public QuestionNode(String text) {
         this(null, null, text);
      }
      
      // Constructs a QuestionNode with left and right branches
      // @param left - the "yes" choice of the current subtree 
      // @param right - the "no" choice of the current subtree
      // @param text - the given String text
      public QuestionNode(QuestionNode left, QuestionNode right, String text) {
         this.left = left;
         this.right = right;
         this.text = text;
      }
   }
   
   // Implemented if the client wants to replace the current tree.    
   // Reads the new tree from the inputted file. 
   // @param input - the Scanner used to read input from the user
   public void read(Scanner input) {
      rootOfTree = readHelper(input);
   }
   
   // Reads the information from the file inputted and differentiates
   // between questions and answers. 
   // Replaces the current tree with the new tree from the file.
   // @param input - the Scanner used to read input from the user
   private QuestionNode readHelper(Scanner input) {
      String question = console.nextLine();  
      String data = console.nextLine();
      QuestionNode root = new QuestionNode(data);
      if (question.equals("Q:")) {
         root.left = readHelper(input);
         root.right = readHelper(input);
      }
      return root;
   }
   
   // Stores the current tree to the output file represented by the 
   // PrintStream. Uses the stored tree to play future games. 
   // @param output - the PrintStream that stores the current tree to 
   //                 the output file 
   public void write(PrintStream output) {
      writeHelper(output, rootOfTree);
   }
   
   // Differentiates between questions and answers and stores the current
   // tree to the output file. 
   // @param output - the Printstream that stores the current tree to
   //                 the output file
   // @param root - the current root node of the tree
   private void writeHelper(PrintStream output, QuestionNode root) {
      if (root.left != null || root.right != null) {
         output.println("Q: ");
         output.println(root.text);
         writeHelper(output, root.left);
         writeHelper(output, root.right);
      } else {
         output.println("A: ");
         output.println(root.text);
      }
   } 
    
   // Plays a complete game with the user using the current question
   // tree and by asking the user a series of yes/no questions. 
   public void askQuestions() {
      rootOfTree = askQuestionsHelper(rootOfTree);
   }
   
   // Uses the current question tree to play a complete game by asking 
   // the user yes/no questions. Uses information from a lost game to 
   // replace old incorrect answer nodes with the new correct answer
   // based on the user's responses. Game ends when the computer reaches 
   // an answer leaf node. 
   // @param root - the current root node of the tree 
   // @return - the answer leaf node 
   private QuestionNode askQuestionsHelper(QuestionNode root) {
      if (root.left != null || root.right != null) {
         if (yesTo(root.text)) {
            root.left = askQuestionsHelper(root.left);
         } else {
            root.right = askQuestionsHelper(root.right);
         }
      } else {
         if (yesTo("Would your object happen to be " + root.text + "?")) {
            System.out.println("Great, I got it right!");
         } else { 
            System.out.print("What is the name of your object? ");
            String answer = console.nextLine();
            QuestionNode object = new QuestionNode(answer);
            System.out.println("Please give me a yes/no question that ");
            System.out.println("distinguishes between your object ");
            System.out.print("and mine--> ");
            String question = console.nextLine();
            if (yesTo("And what is the answer for your object?")) {
               root = new QuestionNode(object, root, question);
            } else {
               root = new QuestionNode(root, object, question);
            }
         }
      }
      return root;
   }

   // Do not modify this method in any way
   // post: asks the user a question, forcing an answer of "y" or "n";
   //       returns true if the answer was yes, returns false otherwise
   private boolean yesTo(String prompt) {
      System.out.print(prompt + " (y/n)? ");
      String response = console.nextLine().trim().toLowerCase();
      while (!response.equals("y") && !response.equals("n")) {
         System.out.println("Please answer y or n.");
         System.out.print(prompt + " (y/n)? ");
         response = console.nextLine().trim().toLowerCase();
      }
      return response.equals("y");
   }
}