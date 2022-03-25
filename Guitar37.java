// skeleton version of the class

public class Guitar37 implements Guitar {
   public static final String KEYBOARD =
      "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";  // keyboard layout

   private GuitarString[] strings;
   private int ticCount;
   
   public Guitar37() {
      int size = KEYBOARD.length();
      strings = new GuitarString[size];
      for (int i = 0; i < size; i++) {
         strings[i] = new GuitarString(440.0 * Math.pow(2, (i-24)/12));
      }
   }
   
   // value 0 represents Concert-A
   public void playNote(int pitch) {
      int note = pitch + 24;
      strings[note].pluck();
   }
   
   public boolean hasString(char key) {
       int index = KEYBOARD.indexOf(key); // is this line of code redundant if i have it in the following method?
       if (index != -1) {
         return true;
       }
   }
   
   public void pluck(char key) {
      if (!hasString(key)) {
         throw new IllegalArgumentException();
      }
      int index = KEYBOARD.indexOf(key);
      strings[index].pluck();
   }
   
   public double sample() {
      double sampleSum = 0.0;
      for (int i = 0; i < KEYBOARD.length(); i++) {
         sampleSum += strings[i].sample();
      }
      return sampleSum;
   }
   
   public void tic() {
      for (int i = 0; i < KEYBOARD.length(); i++) {
         strings[i].tic();
      }
      ticCount++;
   }
   
   public void time() {
      return ticCount;
   }
   
   
}
