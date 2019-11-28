package com.markdeane.hamiltonian;

import java.util.ArrayList;
import java.util.Stack;

public class HamiltonianAlgorithm {

	private int degree, iterations=0;
	private int[][] adjacencyMatrix;
	private ArrayList<Integer> hamiltonianPath = new ArrayList<Integer>();
	private Stack<Integer> stack = new Stack<Integer>();

	public HamiltonianAlgorithm(int[][] adjacencyMatrix) {

		this.adjacencyMatrix = adjacencyMatrix;
		this.degree = adjacencyMatrix[0].length;
	}


	//	Calls the recursive function and displays the result.
	public void displayBruteForce() {
		if(isFeasible()) {
			int start = findStart();
			//int start = 0;
			System.out.println("Starting node: " + (start+1));

			if(start == 0) {
				int root = start;
				while(!findPath(root)) {
					if(root >= degree-1) {
						System.out.println("No feasible solution found...");
						return;
					}else {
						root++;
						stack.clear();
					}
				}
			} else {
				if(!findPath(start)) {
					System.out.println("No feasible solution found...");
					return;
				}
			}

			while(!stack.isEmpty()) {
				hamiltonianPath.add(stack.pop());
			}
			System.out.println("\nSOLUTION: ");
			System.out.println(hamiltonianPath.toString());
			return;
		}
		System.out.println("No feasible solution found...");
	}

	// The recursive function that calculates the hamiltonian findPath.
	// Iterates through all possible paths using a Stack.
	private boolean findPath(int position) {

		stack.push(position + 1);
		System.out.println(stack.toString());
		iterations++;

		for(int i = 0; i < degree; i++) {
			if(adjacencyMatrix[position][i] == 1 && !stack.contains(i+1)) {
				if(findPath(i)) {
					return true;
				}
			}
		}

		if(stack.size() >= degree) {
			return true;
		}

		stack.pop();
		return false;
	}

	// Finds a feasible starting point for the hamiltonian path. Returns
	// the index of the first vertex with degree 1. If one cannot be found,
	// it returns 0 as the starting index.
	private int findStart() {
		int connections = 0;
		for(int i = 0; i < degree; i++) {
			connections = 0;

			for(int j = 0; j < degree; j++) {
				if(adjacencyMatrix[i][j] == 1) {
					connections++;
				}
			}

			if(connections == 1) {
				return i;
			}
		}
		return 0;
	}


	// A hamiltonian path may only exist if there are 2 or fewer
	// endpoints (vertices in the graph that have exactly 1 edge
	// connected to it. Reterns true if there are 2 or fewer endpoints.
	private boolean isFeasible() {
		int endpoints = 0;
		int connections = 0;
		for(int i = 0; i < degree; i++) {
			connections = 0;
			for(int j = 0; j < degree; j++) {
				if(adjacencyMatrix[i][j] == 1) {
					connections++;
				}
			}
			if(connections == 1) {
				endpoints++;
				if(endpoints > 2) {
					return false;
				}
			}
		}
		return true;
	}

	public int getIterations() {
		return iterations;
	}
}
