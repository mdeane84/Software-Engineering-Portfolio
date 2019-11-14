package com.markdeane.hamiltonian;

import java.util.Scanner;

public class SquareSumDriver {
	public static void main(String args[]) {
		
		Scanner scanner = new Scanner(System.in);
		
		boolean valid = false;
		int degree = 0;
		
		System.out.println("Insert a number, preferrably 42 or smaller. Big numbers slow me down :(");
		while(!valid) {
			try {
				degree = scanner.nextInt();
				valid = true;
			} catch (Exception e) {
				System.out.println("Invalid Input: please input an integer.");
				valid = false;
			}
		}
		
		// Construct and Display Adjacency Matrix
		int adjacencyMatrix[][] = getSquareSumAdjacency(degree);
		displayMatrix(adjacencyMatrix);
		
		HamiltonianAlgorithm hamiltonian = new HamiltonianAlgorithm(adjacencyMatrix);
		
		long startTime = System.nanoTime();
		hamiltonian.displayBruteForce();
		double estimatedTime = (double) ((System.nanoTime() - startTime) * Math.pow(10, -9));
		
		System.out.printf("Time Elapsed: %.4f seconds", estimatedTime);
		System.out.println("\nIterations: " + hamiltonian.getIterations());
	}
	
	public static int[][] getSquareSumAdjacency(int degree) {
		int adjacencyMatrix[][] = new int[degree][degree];
		
		for(int i = 0; i < degree; i++) {
			for(int j = 0; j < degree; j++) {
				
				double sr = Math.sqrt((double) ((i+1) + (j+1)));
				if((sr - Math.floor(sr) == 0) && i != j) {
					adjacencyMatrix[i][j] = 1;
				} else {
					adjacencyMatrix[i][j] = 0;
				}
			}
		}
		
		return adjacencyMatrix;
	}
	
	public static void displayMatrix(int[][] adjacencyMatrix) {
		System.out.print("    ");
		for(int i = 0; i < adjacencyMatrix[0].length; i++) {
			System.out.print((i+1)%10 + " ");
		}
		System.out.println("\n");
		for(int i = 0; i < adjacencyMatrix[0].length; i++) {
			System.out.print((i+1)%10 + "   ");
			for(int j = 0; j < adjacencyMatrix[0].length; j++) {
				System.out.print(adjacencyMatrix[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
}
