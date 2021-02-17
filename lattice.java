package code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class lattice {
	/* Author: Timothy Losito
	 * Date: 2/16/21
	 * 
	 * This program is an application of a concept that we recently learned in MTH419.
	 * 
	 * "The Fundamental Theorem of Cyclic Groups" : 
	 * - Every subgroup of a cyclic group is cyclic
	 * - Also, if |<a>| = n then the order of any subgroup of <a> is a divisor of n
	 * - For each divisor k of n, the group <a> has exactly one subgroup of that order k, namely <a^(n/k)>
	 * 
	 * This program will output all of the information necessary 
	 * to draw an aesthetically pleasing lattice of the 
	 * subgroups of Zn given a value for n. The group operation is 
	 * assumed to be addition mod n for simplicity.
	 * 
	 */
	public static void main(String args[]) {
/* ----------------------------------------------------------------------------------------- */		
		/* take Zn as input */
		Scanner kb = new Scanner(System.in);
		System.out.print("Enter an n value for Zn: ");
		int n = kb.nextInt();
		System.out.println("[the group operation is assumed to be addition mod " + n + "]");
/* ----------------------------------------------------------------------------------------- */		
		/* find all divisors of n */
		ArrayList<Integer> divisors = new ArrayList<Integer>();
		for(int i = 1; i <= Math.sqrt(n); i++) {
			if(n % i == 0) {
				if(n / i == i) {
					divisors.add(i);
				}
				else {
					divisors.add(i);
					divisors.add(n / i);
				}
			}
		}
		Collections.sort(divisors);
/* ----------------------------------------------------------------------------------------- */		
		/* generate subgroups */
		HashMap<Integer, ArrayList<Integer>> subgroups = new HashMap<Integer, ArrayList<Integer>>();
		for(Integer d : divisors) {
			subgroups.put(d, new ArrayList<Integer>());
			int generator = n / d;
			int sum = 0;
			subgroups.get(d).add(sum);
			while(subgroups.get(d).size() != d) {
				sum += generator;
				if(!subgroups.get(d).contains(sum % n)) {
					subgroups.get(d).add(sum % n);
				}
			}
		}
/* ----------------------------------------------------------------------------------------- */		
		/* output Zn and generated subgroups */
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println("Z" + n + ": " + divisors);
		System.out.println("-----------------------------------------------------------------------------------------");
		for(Integer i : subgroups.keySet()) {
			if(n/i == n) {
				System.out.println("divisor: " + i + ", " + "subgroup <" + "0" + ">: " + subgroups.get(i) + " (order = " + i + ")");
				continue;
			}
			System.out.println("divisor: " + i + ", " + "subgroup <" + (n/i) + ">: " + subgroups.get(i) + " (order = " + i + ")");
		}
		System.out.println("-----------------------------------------------------------------------------------------");
/* ----------------------------------------------------------------------------------------- */		
		/* determine relationships between subgroups */
		HashMap<Integer, ArrayList<Integer>> edges = new HashMap<Integer, ArrayList<Integer>>();
		for(Integer d1 : divisors) {
			edges.put(n/d1, new ArrayList<>());
			for(Integer d2 : divisors) {
				if(d1 == d2) continue;
				if(subgroups.get(d2).containsAll(subgroups.get(d1))) {
					edges.get(n/d1).add(n/d2);
				}
			}
		}
/* ----------------------------------------------------------------------------------------- */		
		/* print the lattice information*/
		System.out.println("The lattice can be drawn from the following information:");
		for(Integer gen : edges.keySet()) {
			if(edges.get(gen).size() == 0) continue;
			if(gen == n) {
				System.out.print("<" + "0" + "> is a subgroup of ");
			}
			else {
				System.out.print("<" + gen + "> is a subgroup of ");
			}
			for(Integer parent : edges.get(gen)) {
				System.out.print("<" + parent + "> ");
			}
			System.out.println();
		}
		System.out.println("-----------------------------------------------------------------------------------------");
/* ----------------------------------------------------------------------------------------- */		
	}
}