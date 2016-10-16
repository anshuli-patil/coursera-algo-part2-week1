package edu.usc.anshulip.week1;

public abstract class SchedulingNode implements Comparable<SchedulingNode> {

	@Override
	public String toString() {
		return weight + " " + length;
	}

	public SchedulingNode(int jobWeight, int jobLength) {
		this.weight = jobWeight;
		this.length = jobLength;
	}

	int weight;
	int length;

}
