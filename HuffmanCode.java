//	Michelle	Wu
//	03/10/2022
//	CSE 143E
//	TA: Khushi Chaudhari
//	Take-home Assessment	#8
//	This program creates the HuffmanCode class that compresses and decompresses
// the file that the user inputs by implementing a binary tree to keep track of
// the integers that make up each character in the message. 

import java.util.*;
import java.io.*;

public class HuffmanCode {
   private HuffmanNode overallRoot;

   // Constructs a HuffmanCode object that takes in an array of frequencies and
   // sorts them in ascending order based on frequency. Removes two nodes at a
   // time and combines their frequencies to construct a single binary tree. 
   // @param frequencies - the given array of frequencies
	public HuffmanCode(int[] frequencies)	{
		Queue<HuffmanNode> pq = new PriorityQueue<HuffmanNode>();
      for (int i = 0; i < frequencies.length; i++) {
         if (frequencies[i] > 0) {
            HuffmanNode node = new HuffmanNode(i, frequencies[i], null, null);
            pq.add(node);
         }
      }
      while(pq.size() > 1) {
         HuffmanNode node1 = pq.remove();
         HuffmanNode node2 = pq.remove();
         int combinedFreq = node1.freq + node2.freq;
         HuffmanNode result = new HuffmanNode(0, combinedFreq, node1, node2);
         pq.add(result);
      }
      overallRoot = pq.remove();
	}
	
   // Represents and stores a single node of the binary tree.
	private static class HuffmanNode implements Comparable<HuffmanNode> {
      public int asciiValue;
      public int freq;
      public HuffmanNode left;
      public HuffmanNode right;
      
      // Constructs a HuffmanNode with left and right branches.
      // @param asciiValue - the corresponding ASCII value of the character
      // @param freq - the given frequency of the character
      // @param left - the left node of the current subtree
      // @param right - the right node of the current subtree
      public HuffmanNode(int asciiValue, int freq, HuffmanNode left, 
                         HuffmanNode right) {
         this.asciiValue = asciiValue;
         this.freq = freq;
         this.left = left;
         this.right = right;
      }
      
      // Takes in the respective ASCII value and frequency of a character to
      // construct a new HuffmanNode
      // @param asciiValue - the corresponding ASCII value of the character
      // @param freq - the given frequency of the character
      public HuffmanNode(int asciiValue, int freq) {
         this(asciiValue, freq, null, null);
      }      
      
      // Returns a negative value is the frequency is less than the one it is
      // being compared to and a positive value if it is greater.
      // @param other - the node that is being compared
      public int compareTo(HuffmanNode other) {
         return freq - other.freq;
      }   
	}
	
   // Reads in a previously constructed file and rebuilds a HuffmanCode
   // object
   // @param input - the Scanner used to read input from the file 
	public HuffmanCode(Scanner input) {
      overallRoot = new HuffmanNode(0, 0);
      while (input.hasNextLine()) {
         int asciiValue = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         overallRoot = huffmanCodeHelper(overallRoot, asciiValue, code); 
      }
	}
   
   // Rebuilds the Huffman tree from the given code file produced by
   // compression. 
   // @param root - current root of the binary tree
   // @param asciiValue - the corresponding ASCII value of the character
   // @param code - current sequence of Huffman code
   private HuffmanNode huffmanCodeHelper(HuffmanNode root, int asciiValue,
                                         String code) {
      if (root == null) {
         root = new HuffmanNode(0, 0);
      } 
      if (code.length() == 1) {
         if (code.charAt(0) == '0') {
            root.left = new HuffmanNode(asciiValue, 0);
         } else {
            root.right = new HuffmanNode(asciiValue, 0);
         }
      } else {
         if (code.charAt(0) == '0') {
            root.left = huffmanCodeHelper(root.left, asciiValue, 
                                          code.substring(1));
         } else {
            root.right = huffmanCodeHelper(root.right, asciiValue, 
                                           code.substring(1));
         }
      }
      return root;
   }
	
   // Stores the current Huffman codes to the given output stream
   // @param output - the PrintStream file that stores the output of the
   //                 compression
	public void save(PrintStream output) {
	   String code = "";
      saveHelper(output, overallRoot, code);
	}
   
   // Traverses the tree until it reaches a leaf node and prints out the 
   // ASCII value and binary code for each character.
   // @param output - the PrintStream file that stores the output of the 
   //                 compression
   // @param root - current root of the binary tree
   // @param code - current sequence of Huffman code
   private void saveHelper(PrintStream output, HuffmanNode root, 
                           String code) {
      if (root != null) {
         if (root.left == null && root.right == null) {
            output.println(root.asciiValue);
            output.println(code);
         } else {
            saveHelper(output, root.left, code + "0");
            saveHelper(output, root.right, code + "1");
         }
      }
   }
	
   // Reads individual bits from the input stream to translate the
   // compressed file back into a decompressed file. 
   // @param input - the input stream of the tree's Huffman code
   // @param output - the PrintStream file that stores the output 
   //                 of the decompression
	public void translate(BitInputStream input, PrintStream output) {
	   while (input.hasNextBit()) {
         translateHelper(input, overallRoot, output);
      }
	}
   
   // Reads the compressed file bit by bit to translate the previously
   // compressed message into the original decompressed message. Writes
   // the coressponding characters to the output.
   // @param output - the PrintStream file that stores the output 
   //                 of the decompression
   private void translateHelper(BitInputStream input, HuffmanNode root,
                                PrintStream output) {
      if (root.left != null && root.right != null) {
         int bit = input.nextBit();
         if (bit == 0) {
            translateHelper(input, root.left, output);
         } else {
            translateHelper(input, root.right, output);
         }
      } else {
         output.write(root.asciiValue);
      }
   }
}
