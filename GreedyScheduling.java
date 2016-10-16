package edu.usc.anshulip.week1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class GreedyScheduling {

	SchedulingNode[] nodes;
	int n;

	public static void main(String[] args) {
		GreedyScheduling problem = new GreedyScheduling();
		problem.getProblemInfo();
		problem.printSolution();
	}

	void printSolution() {
		Arrays.sort(nodes);
		long weightedCompletionTime = 0;

		int completingTime = 0;
		for (int i = 0; i < n; i++) {
			completingTime += nodes[i].length;
			weightedCompletionTime += nodes[i].weight * completingTime;
		}
		System.out.println(weightedCompletionTime);
	}

	void getProblemInfo() {
		BufferedReader bufferedFileReader = null;

		try {
			bufferedFileReader = new BufferedReader(new FileReader("jobs.txt"));

			this.n = Integer.parseInt(read(bufferedFileReader));
			nodes = new SchedulingNode[n];

			for (int i = 0; i < n; i++) {
				String line = read(bufferedFileReader);
				String[] splitLine = line.split(" ");

				int jobWeight = Integer.parseInt(splitLine[0]);
				int jobLength = Integer.parseInt(splitLine[1]);
				nodes[i] = (new SuboptimalNode(jobWeight, jobLength));
				// nodes[i] = (new OptimalNode(jobWeight, jobLength));

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String read(BufferedReader bufferedFileReader) {
		try {
			return bufferedFileReader.readLine().trim();
		} catch (IOException e) {
			// do nothing
		}
		return null;
	}

}
