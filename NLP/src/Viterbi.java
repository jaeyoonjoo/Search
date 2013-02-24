/* File:      Viterbi.java
* 
* The Viterbi algorithm in Java
* Author: Paul Fodor <pfodor@cs.sunysb.edu>
* Stony Brook University, 2007
* Python version: http://en.wikipedia.org/wiki/Viterbi_algorithm
*/
//import java.io.Serializable;

public class Viterbi{
  private static String[] states = {"Rainy", "Sunny"};
  private static String[] observations = {"walk", "shop", "clean"};
  private static double[] start_probability = {0.6, 0.4};
  private static double[][] transition_probability = {{0.7, 0.3}, {0.4, 0.6}};
  private static double[][] emission_probability = {{0.1, 0.4, 0.5}, {0.6, 0.3, 0.1}};
  private static class TNode{
    public double prob;
    public int[] v_path;
    public double  v_prob;
    public TNode(double prob, int[] v_path, double v_prob){
      this.prob = prob;
      this.v_path = copyIntArray(v_path);
      this.v_prob = v_prob;
    }

  }
  
  private static int[] copyIntArray(int[] ia){
    int[] newIa = new int[ia.length];
    for(int i=0; i<ia.length; i++){
      newIa[i] = ia[i];
    }
    return newIa;
  }
  
  private static int[] copyIntArray(int[] ia, int newInt){
    int[] newIa = new int[ia.length + 1];
    for(int i=0; i<ia.length; i++){
      newIa[i] = ia[i];
    }
    newIa[ia.length] = newInt;
    return newIa;
  }
  
  // forwardViterbi(observations, states, start_probability, transition_probability, emission_probability)
  public static void forwardViterbi(String[] y, String[] X, double[] sp, double[][] tp, double[][] ep) {
    TNode[] T = new TNode[X.length];
    for(int state=0; state<X.length; state++){
      int[] intArray = new int[1];
      intArray[0] = state;
      T[state] = new TNode(sp[state], intArray, sp[state]);
    }
    

    for(int output=0; output<y.length; output++){
      TNode[] U = new TNode[X.length];
      for(int next_state=0; next_state<X.length; next_state++){
        double total = 0;
        int[] argmax = new int[0];
        double valmax = 0;
        for(int state=0; state<X.length; state++){
          double prob = T[state].prob;
          int[] v_path = copyIntArray(T[state].v_path);
          double v_prob = T[state].v_prob;
          double p = ep[state][output] * tp[state][next_state];
          
          prob *= p;
          v_prob *= p;
          total += prob;
          if(v_prob>valmax){
            argmax = copyIntArray(v_path, next_state);
            valmax = v_prob;
          }
        }
        U[next_state] = new TNode(total, argmax, valmax);
      }
      T = U;
    }
    // apply sum/max to the final states:
    double total = 0;
    int[] argmax = new int[0];
    double valmax = 0;
    for(int state=0; state<X.length; state++){
      double prob = T[state].prob;
      int[] v_path = copyIntArray(T[state].v_path);
      double v_prob = T[state].v_prob;
      total += prob;
      if(v_prob>valmax){
        argmax = copyIntArray(v_path);
        valmax = v_prob;
      }
    }
    
    System.out.print(" Probability of the state:" + total + ".\n Viterbi path: ["); 
    for(int i = 0; i<argmax.length; i++){
      System.out.print(states[argmax[i]] + ", ");  
    }
    System.out.println("].\n Probability of the whole system: " + valmax);
    return;
  }

  public static void main(String[] args) throws Exception {
    System.out.print("\nStates: ");
    for (int i = 0; i < states.length; i++){
      System.out.print(states[i] + ", ");  
    }
    System.out.print("\n\nObservations: ");
    for (int i = 0; i < observations.length; i++){
      System.out.print(observations[i] + ", ");  
    }
    System.out.print("\n\nStart probability: ");
    for (int i = 0; i < states.length; i++){
      System.out.print(states[i] + ": " + start_probability[i] + ", ");  
    }
    System.out.println("\n\nTransition probability:");
    for (int i = 0; i < states.length; i++){
      System.out.print(" " + states[i] + ": {");
      for (int j = 0; j < states.length; j++){
        System.out.print("  " + states[j] + ": " + transition_probability[i][j] + ", ");  
      }
      System.out.println("}");
    }
    System.out.println("\n\nEmission probability:");
    for (int i = 0; i < states.length; i++){
      System.out.print(" " + states[i] + ": {");
      for (int j = 0; j < observations.length; j++){
        System.out.print("  " + observations[j] + ": " + emission_probability[i][j] + ", ");  
      }
      System.out.println("}");
    }
    forwardViterbi(observations, states, start_probability, transition_probability, emission_probability);
  }
}
