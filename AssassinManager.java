// Michelle Wu
// 01/27/2022
// CSE 143E
// TA: Khushi Chaudhari
// Take-home Assessment #3
// This program mimics an Assassin game by taking in a list of names and
// establishing a kill ring, where each player stalks the person directly
// after them on the list. When a player is assassinated, they are removed
// from the kill ring and moved to the front of the graveyard. To accomodate
// their removal, their target is passed on to their assassin. The game is
// over when there is only one player left in the kill ring. 

import java.util.*;

public class AssassinManager {
   private AssassinNode killRingFront;
   private AssassinNode graveyardFront;
   
   // Takes in a list of names to build a kill ring of connected assassin
   // nodes in the same order of the given names
   // @throws - IllegalArgumentException if given list of names is empty
   public AssassinManager(List<String> names) {
      if (names.size() == 0) {
         throw new IllegalArgumentException();
      }
      for (int i = 0; i < names.size(); i++) {
         String name = names.get(i);
         AssassinNode assassin = new AssassinNode(name);
         if (killRingFront == null) {
            killRingFront = assassin;
         } else {
            AssassinNode current = killRingFront;
            while (current.next != null) {
               current = current.next;
            }
            current.next = assassin;
         }
      }
   }
   
   // Prints the current list of names in the kill ring and who
   // each individual is stalking 
   // If the game is over, prints the winner stalking him/herself.
   public void printKillRing() {
      AssassinNode current = killRingFront;
      if (current.next == null) {
         System.out.println("    " + current.name + " is stalking "
                            + killRingFront.name);
      } else {
         while (current.next != null) {
            System.out.println("    " + current.name + " is stalking "
                               + current.next.name);
            current = current.next;
         }
         System.out.println("    " + current.name + " is stalking "
                            + killRingFront.name);
      }
   }
   
   // Prints the current list of names in the graveyard in the
   // opposite order in which they were assassinated and who they
   // were assassinated by
   // If the graveyard is empty, no output is produced
   public void printGraveyard() {
      AssassinNode current = graveyardFront;
      while (current != null) {
         System.out.println("    " + current.name + " was killed by "
                            + current.killer);
         current = current.next;
      }
   }
   
   // Checks the respective LinkedList for the given name
   // @param current - the linked list node being compared
   // @param name - the given name being sought for within the list
   // @return - true if the linked list contains the given name,
   //           false otherwise 
   private boolean checkList(AssassinNode current, String name) {
      while (current != null) {
         if (current.name.equalsIgnoreCase(name)) {
            return true;
         } else {
            current = current.next;
         }
      }
      return false;   
   }
   
   // @param name - the given name being compared with its case ignored
   // @return - true if the current kill ring contains the given name, 
   //           false otherwise
   public boolean killRingContains(String name) {
      AssassinNode current = killRingFront;
      return checkList(current, name);
   }
   
   // @param name - the given name being compared with its case ignored
   // @return - true if the current graveyard contains the given name,
   //           false otherwise
   public boolean graveyardContains(String name) {
      AssassinNode current = graveyardFront;
      return checkList(current, name);
   }
   
   // @return - true if the game is over (only one person left in kill
   //           ring), false otherwise
   public boolean gameOver() {
      return killRingFront.next == null;
   }
    
   // @return - null if the game is not over, otherwise winner's name
   public String winner() {
      if (!gameOver()) {
         return null;
      }
      return killRingFront.name;
   }
   
   // Records the assassination of the given person, removes them from the
   // kill ring and moves them into the graveyard 
   // @throws - IllegalStateException if the game is over and if the kill
   //           ring does not contain the given name
   // @throws - IllegalArgumentException if the kill ring does not contain
   //           the given name
   // @param name - the given name being compared, case ignored
   public void kill(String name) {
      if (gameOver()) {
         throw new IllegalStateException();
      } else if (!killRingContains(name)) {
         throw new IllegalArgumentException();
      }
      AssassinNode current = killRingFront;
      if (current.name.equalsIgnoreCase(name)) {
         killRingFront = current.next;
         current.next = graveyardFront;
         graveyardFront = current;
         current = killRingFront;
         while (current.next != null) {
            current = current.next;
         }
         graveyardFront.killer = current.name;
      } else {
         while (current.next != null) {
            String victimName = current.next.name;
            if (victimName.equalsIgnoreCase(name)) {
               AssassinNode victim = current.next;
               victim.killer = current.name;
               current.next = victim.next;
               victim.next = graveyardFront;
               graveyardFront = victim;
            } else {
               current = current.next;
            }
         }
      }
   }
}