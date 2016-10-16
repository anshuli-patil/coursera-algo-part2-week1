package edu.usc.anshulip.week1;

//undirected Edge
public class UndirectedEdge implements Comparable<UndirectedEdge> {
	public int vertex1ID;
	public int vertex2ID;
	public int cost; // cost of traveling from startLocation to endLocation

	public UndirectedEdge(int cost, int vertex1ID, int vertex2ID) {
		this.cost = cost;
		this.vertex1ID = vertex1ID;
		this.vertex2ID = vertex2ID;
	}

	@Override
	public int compareTo(UndirectedEdge o) {
		return this.cost - o.cost;
	}
}
