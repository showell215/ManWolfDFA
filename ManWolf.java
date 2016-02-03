/**
 * DFA simulation of the Man-Wolf-Cabbage brain teaser.
 *
 * Input string containing consecutive moves from the language {g, w, c, n}
 * is giving by command line argument. Each character represents
 * the Man crossing the river with either Goat, Wolf, Cabbage, or None.
 * Program compares each move to the list of legal moves stored in delta[][],
 * and outputs the result of validating the string.
 *
 * @author Steven Howell
 *
 */
public class ManWolf {

   /** state, initialized to 0 (start state) */
   private int state;

   /** Transitions corresponding to chars in alphabet */
   private static final int N = 0;
   private static final int G = 1;
   private static final int W = 2;
   private static final int C = 3;

   /** All possible legal states */
   private static final int q0 = 0;   //0 E: mwgc W: (INITIAL)
   private static final int q1 = 1;   //1 E: wc W: mg
   private static final int q2 = 2;   //2 E: mwc W: g
   private static final int q3 = 3;   //3 E: c W: mwg
   private static final int q4 = 4;   //4 E: w W: mgc
   private static final int q5 = 5;   //5 E: mgc W: w
   private static final int q6 = 6;   //6 E: mgw W: c
   private static final int q7 = 7;   //7 E: g W: mwc
   private static final int q8 = 8;   //8 E: mg W: wc
   private static final int q9 = 9;   //9* E: W: mwgc;(FINAL)
   private static final int qE = 10;  //10  ERROR state

   /** Transition table for all inputs in alphabet */
   private static int[][] delta = {
   //      inputs
   //  n   g   w   c    from state...
      {qE, q1, qE, qE}, //0 E: mwgc W: 
      {q2, q0, qE, qE}, //1 E: wc W: mg
      {q1, qE, q3, q4}, //2 E: mwc W: g
      {qE, q5, q2, qE}, //3 E: c W: mwg
      {qE, q6, qE, q2}, //4 E: w W: mgc
      {qE, q3, qE, q7}, //5 E: mgc W: w
      {qE, q4, q7, qE}, //6 E: mgw W: c
      {q8, qE, q6, q5}, //7 E: g W: mwc
      {q7, q9, qE, qE}, //8 E: mg W: wc
      {qE, q8, qE, qE}, //9*E: W: mwgc (FINAL)
      {qE, qE, qE, qE}  //10  ERROR
   };
   /**
    * Creates a new ManWolf instance, resets the state, processes the string,
    * and reports to console.
    * @param in the String containing move sequence
    */
   public ManWolf(String in) {
      reset();
      process(in);
      if (evaluate()) {
         System.out.println("accept");
      } else {
         System.out.println("reject");
      }
   }
   /**
    * Evaluates the String to either accept or reject.
    * @return true if ending state is q9, false otherwise
    */
   private boolean evaluate() {
      return (this.state == q9);
   }
   /**
    * Resets the state to q0, the starting state
    */
   private void reset() {
      this.state = q0;
   }
   /**
    * Processes the move sequence String character by character, updating the
    * state each time according to the delta[][] table.
    * @param in the String containing the move sequence
    */
   private void process (String in) {
      System.out.println("String is: " + in);
      int length = in.length();
      int input = -1;
      for (int i = 0; i < length; i++) {
         char c = in.charAt(i);
         //match char to delta input index
         System.out.println("char " + i + " is \'" + c + "\'");
         switch (c) {
            case 'n': input = N;
               break;
            case 'g': input = G;
               break;
            case 'w': input = W;
               break;
            case 'c': input = C;
               break;
            default: // remain at -1, will go to error state
         }
         System.out.println("input is: " + input);
         System.out.println("State is: " + state);
         //Change state based in the input character (move) and current state
         //according to the table
         try {
            this.state = delta[this.state][input];
            System.out.println("Changing to state " + this.state);
         //If state is not in the table, go to error state
         } catch (ArrayIndexOutOfBoundsException e) {
            this.state = qE;
         }
      }
      //Now at end of string, in our last state
   }
   /**
    * Main method. 1 command line argument is String move sequence. Starts program.
    * @param args args[0] is move sequence
    */
   public static void main(String[] args) {
      if (args.length != 1) {
         throw new IllegalArgumentException("Usage: java ManWolf <input_String>");
      }
      new ManWolf(args[0]);
   }
}