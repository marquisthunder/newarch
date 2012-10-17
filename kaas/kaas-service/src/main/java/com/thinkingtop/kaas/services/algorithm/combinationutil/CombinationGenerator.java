package com.thinkingtop.kaas.services.algorithm.combinationutil;

//--------------------------------------
//Systematically generate combinations.
//--------------------------------------

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;


/**
 * Combination Generator class
 * @author roadahead
 *
 */
public class CombinationGenerator {

    private int[] a;
    private int n;
    private int r;
    private BigInteger numLeft;
    private BigInteger total;

    // ------------
    // Constructor
    // ------------

    public CombinationGenerator(int n, int r) {
        if (r > n) {
            throw new IllegalArgumentException();
        }
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.r = r;
        a = new int[r];
        BigInteger nFact = getFactorial(n);
        BigInteger rFact = getFactorial(r);
        BigInteger nminusrFact = getFactorial(n - r);
        total = nFact.divide(rFact.multiply(nminusrFact));
        reset();
    }

    // ------
    // Reset
    // ------

    public void reset() {
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        numLeft = new BigInteger(total.toString());
    }

    // ------------------------------------------------
    // Return number of combinations not yet generated
    // ------------------------------------------------

    public BigInteger getNumLeft() {
        return numLeft;
    }

    // -----------------------------
    // Are there more combinations?
    // -----------------------------

    public boolean hasMore() {
        return numLeft.compareTo(BigInteger.ZERO) == 1;
    }

    // ------------------------------------
    // Return total number of combinations
    // ------------------------------------

    public BigInteger getTotal() {
        return total;
    }

    // ------------------
    // Compute factorial
    // ------------------

    private static BigInteger getFactorial(int n) {
        BigInteger fact = BigInteger.ONE;
        for (int i = n; i > 1; i--) {
            fact = fact.multiply(new BigInteger(Integer.toString(i)));
        }
        return fact;
    }

    // --------------------------------------------------------
    // Generate next combination (algorithm from Rosen p. 286)
    // --------------------------------------------------------

    public int[] getNext() {

        if (numLeft.equals(total)) {
            numLeft = numLeft.subtract(BigInteger.ONE);
            return a;
        }

        int i = r - 1;
        while (a[i] == n - r + i) {
            i--;
        }
        a[i] = a[i] + 1;
        for (int j = i + 1; j < r; j++) {
            a[j] = a[i] + j - i;
        }

        numLeft = numLeft.subtract(BigInteger.ONE);
        return a;

    }

    public static  void main(String[] argv){
        String[] elements = {"a", "b", "c" , "d"};
        /*int[] indices;
        CombinationGenerator x = new CombinationGenerator (elements.length, 1);
        StringBuffer combination;
        StringBuffer combination2;
        while (x.hasMore ()) {
          combination = new StringBuffer ();
          combination2 = new StringBuffer ();
          indices = x.getNext ();
          String[] elements2 = new String[elements.length];
          for (int i = 0; i < indices.length; i++) {
        	  System.out.println(indices[i]);
        	elements2[indices[i]]="1";
            combination.append (elements[indices[i]]);
          }
          for(int i=0;i<elements2.length;i++){
        	  if(elements2[i]==null){
        		  combination2.append (elements[i]);
        	  }
          }
          System.out.println (combination.toString ()+"=>"+combination2.toString ());
        }*/
        
        
        /*Set<String> idlist = new HashSet<String>();
        idlist.add("a");
        idlist.add("b");
        idlist.add("c");
        idlist.add("d");
        String[] idliststr =  idlist.toArray(new String[0]);
        for(String i : idliststr){
        	System.out.println(i);
        }
        Map<String, Integer> submitMap= new HashMap<String, Integer>();
        genc(idliststr,submitMap);
        System.out.println("a:" +submitMap.get("a"));
        System.out.println("b:" +submitMap.get("b"));*/
        /*int n = 1000;
        int i = 4;
        long size = 1;
        for(int j=0;j<i;j++){
        	size *= (n-j);
        }
        size *= (i-1);
        System.out.println(size);*/
        //Logger logger=Logger.getLogger(AprioriRunner.class);        
    }
    
    public static void genc(String[] idliststr,Map<String, Integer> submitMap){
    	for(String i : idliststr){
    		submitMap.put(i, 12);
    	}
    }

}
