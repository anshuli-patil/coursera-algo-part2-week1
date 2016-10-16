package edu.usc.anshulip.week1;

public class OptimalNode extends SchedulingNode {

	public OptimalNode(int jobWeight, int jobLength) {
		super(jobWeight, jobLength);
	}

	public float getComparisonRatio() {
		return ((float) weight) / ((float) length);
	}

	@Override
	public int compareTo(SchedulingNode o) {

		if (this.getComparisonRatio() > ((OptimalNode) o).getComparisonRatio()) {
			return -1;
		}
		if (this.getComparisonRatio() < ((OptimalNode) o).getComparisonRatio()) {
			return 1;
		}
		return 0;
	}
}