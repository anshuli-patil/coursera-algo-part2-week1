package edu.usc.anshulip.week1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PrimsAlgorithm {

	/*
	 * - Initialize X = {s} [s ∈ V chosen arbitrarily] - T = ∅ [invariant: X =
	 * vertices spanned by tree-so-far T] - While X != V - Let e = (u, v) be the
	 * cheapest edge of G with u ∈ X, v ∈/ X. - Add e to T, add v to X.
	 * 
	 * - For each edge (v,w) ∈ E: - If w ∈ V − X → The only whose key might have
	 * changed - Delete w from heap - Recompute key[w] := min{key[w],cvw } -
	 * Re-Insert into heap
	 */

	int m; // number of edges
	int n; // number of vertices

	Heap vertices;
	private HeapNode[] heapInfo;
	int start;

	// remember that graph is undirected.
	private Map<Integer, List<UndirectedEdge>> outgoingEdges;

	PrimsAlgorithm() {
		start = getProblemInfo();
		vertices = new Heap(heapInfo, n);
	}

	public static void main(String[] args) {
		System.out.println(new PrimsAlgorithm().getMSTCost());
	}

	public int getMSTCost() {
		// vertex ID as key
		Map<Integer, Integer> X = new HashMap<Integer, Integer>();
		X.put(start, 1);
		int mstCost = 0;

		while (X.size() != n) {
			vertices.print();

			HeapNode vertexFromHeap = vertices.deleteMin();
			int minCrossingEdge = vertexFromHeap.value;

			int vertexId = vertexFromHeap.vertexId;
			X.put(vertexId, 1);
			mstCost += minCrossingEdge;

			updateHeap(vertexId);

		}

		return mstCost;
	}

	private void updateHeap(int vertexId) {
		// System.out.println("updating heap of size "+ vertices.lastPosition+"
		// for "+vertexId);
		List<UndirectedEdge> edgesFromV = outgoingEdges.get(vertexId);
		for (UndirectedEdge vw : edgesFromV) {
			int wId = vw.vertex2ID;
			int wHeapPosition = vertices.getVertexPosition(wId);

			// If w is in the heap (i.e., it's still not in T)
			if (wHeapPosition != -1) {
				int wKey = vertices.heap[wHeapPosition].value;
				if (wKey > vw.cost) {
					vertices.updateKey(wHeapPosition, vw.cost);
					// System.out.println(vertices.getVertexPosition(4)+"
					// updating value of "+wId+" to "+vw.cost);
				}
			}
		}

	}

	private int getProblemInfo() {
		BufferedReader bufferedFileReader = null;

		// bookkeeping Data to keep track of vertices added to heap
		Map<Integer, Integer> verticesMap = new HashMap<Integer, Integer>();
		int startNode = 0;

		try {
			bufferedFileReader = new BufferedReader(new FileReader("edges.txt"));
			String[] nValues = read(bufferedFileReader).split(" ");
			this.n = Integer.parseInt(nValues[0]);
			this.m = Integer.parseInt(nValues[1]);

			heapInfo = new HeapNode[n - 1];
			outgoingEdges = new HashMap<Integer, List<UndirectedEdge>>();

			for (int i = 0; i < m; i++) {
				String line = read(bufferedFileReader);
				String[] splitLine = line.split(" ");

				int vertex1 = Integer.parseInt(splitLine[0]);
				int vertex2 = Integer.parseInt(splitLine[1]);
				int cost = Integer.parseInt(splitLine[2]);

				if (i == 0) {
					startNode = vertex1;
				}
				// undirected graph!
				addOutgoingEdges(vertex1, vertex2, cost);
				addOutgoingEdges(vertex2, vertex1, cost);

				verticesMap.put(vertex1, 1);
				verticesMap.put(vertex2, 1);// Don't forget this!
			}

			initializeHeap(verticesMap, startNode);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return startNode;
	}

	private void initializeHeap(Map<Integer, Integer> verticesMap, int startNode) {
		// System.out.println((verticesMap.size()-1)+" -> num of nodes in
		// heapInfo");
		int heapCount = 0;
		List<UndirectedEdge> initCrossingEdges = outgoingEdges.get(startNode);
		for (UndirectedEdge crossEdge : initCrossingEdges) {
			heapInfo[heapCount] = new HeapNode(crossEdge.vertex2ID, crossEdge.cost);
			heapCount++;
			verticesMap.remove(crossEdge.vertex2ID);
		}
		verticesMap.remove(startNode);

		Iterator<Entry<Integer, Integer>> it = verticesMap.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry<Integer, Integer> pair = it.next();

			// nodes not neighbors of StartNode are not crossing the initial
			// Vertex Cut
			heapInfo[heapCount] = new HeapNode(pair.getKey(), Integer.MAX_VALUE);
			heapCount++;

			it.remove();
		}
		// System.out.println("heapCount "+heapCount+" num of heapNodes
		// "+heapInfo.length);

	}

	private void addOutgoingEdges(int vertex1, int vertex2, int cost) {
		UndirectedEdge e = new UndirectedEdge(cost, vertex1, vertex2);
		if (outgoingEdges.containsKey(vertex1)) {
			outgoingEdges.get(vertex1).add(e);
		} else {
			List<UndirectedEdge> newList = new ArrayList<UndirectedEdge>();
			newList.add(e);
			outgoingEdges.put(vertex1, newList);
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
