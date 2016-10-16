package edu.usc.anshulip.week1;

public class SuboptimalNode extends SchedulingNode {

	public SuboptimalNode(int jobWeight, int jobLength) {
		super(jobWeight, jobLength);
	}

	public int getMetricDistance() {
		return (weight - length);
	}

	@Override
	public int compareTo(SchedulingNode o) {
		int difference = this.getMetricDistance();
		int oDifference = ((SuboptimalNode) o).getMetricDistance();
		if (difference > oDifference) {
			return -1;
		}
		if (difference < oDifference) {
			return 1;
		}
		return o.weight - weight;

	}
}
