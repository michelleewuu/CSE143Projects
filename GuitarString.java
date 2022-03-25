// Michelle Wu
// 01/20/2022
// CSE 143E
// TA: Khushi Chaudhari
// Take-home Assessment #2
// This program uses the Queue interface and LinkedList implementation to
// keep track of a ring buffer and models a vibrating guitar string of a 
// given frequency.


import java.util.*;

public class GuitarString {
   public static final double ENERGY_DECAY = 0.996;
   
   private Queue<Double> ringBuffer; 
   private int capacityN;
   
   // Constructs a LinkedList of capacity N that is determined by dividing
   // the given sampling rate by the frequency value and rounding it to the
   // nearest integer. Enqueues N zeros to represent guitar string at rest.  
   // @throws - IllegalArgumentException if frequency is less than or equal
   // to 0 or if the calculated size of the ring buffer is less than 2
   // @param frequency - given frequency value used to calculate capacity
   public GuitarString(double frequency) {
      ringBuffer = new LinkedList<>();
      capacityN = (int)(Math.round(StdAudio.SAMPLE_RATE / frequency));
      if (frequency <= 0 || capacityN < 2) {
         throw new IllegalArgumentException();
      }
      for (int i = 0; i < capacityN; i++) {
         ringBuffer.add(0.0);
      }
   }
   
   // Constructs a LinkedList and initializes the contents of the ring
   // buffer to the values in the given array. 
   // @throws - IllegalArgumentException if given array contains less
   // than two elements
   // @param init - given array of decimal values
   public GuitarString(double[] init) {
      ringBuffer = new LinkedList<>();
      if (init.length < 2) {
         throw new IllegalArgumentException();
      }
      for (int i = 0; i < init.length; i++) {
         ringBuffer.add(init[i]);
      }
   }
   
   // Replaces each value within the ring buffer with a random value
   // between 0.5 and -0.5 inclusive.
   public void pluck() {
      Random rand = new Random();
      for (int i = 0; i < ringBuffer.size(); i++) {
         double randValue = rand.nextDouble() - 0.5;
         ringBuffer.remove();
         ringBuffer.add(randValue);
      }
   }
   
   // Deletes the sample at the front of the ring buffer. Computes the
   // average of the first two samples and multiples that value by the
   // given energy decay factor. Adds the resulting value to the end of
   // the ring buffer.
   public void tic() {
      double frontValue = ringBuffer.remove();
      double nextValue = ringBuffer.peek();
      double newValue = ((frontValue + nextValue) / 2) * ENERGY_DECAY;
      ringBuffer.add(newValue);
   }
   
   // Returns the current sample (value at the front of the ring buffer)
   public double sample() {
      double currentSample = ringBuffer.peek();
      return currentSample;
   }
}