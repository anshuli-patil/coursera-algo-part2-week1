package edu.usc.anshulip.week1;

import java.util.HashMap;
import java.util.Map;

//assumes that data never exceeds size 
public class Heap {

	int n;
	HeapNode[] heap;

	// used internally to keep track of vertex position
	private Map<Integer, Integer> vertexPosition;

	int lastPosition = 1;

	Heap(int size) {
		n = size;
		this.heap = new HeapNode[n];
		vertexPosition = new HashMap<Integer, Integer>();
	}

	Heap(HeapNode[] heap, int size) {
		n = size;
		this.heap = new HeapNode[size];
		vertexPosition = new HashMap<Integer, Integer>();

		lastPosition = heap.length + 1;

		for (int i = 0; i < heap.length; i++) {
			this.heap[i + 1] = heap[i];
			HeapNode current = this.heap[i + 1];
			vertexPosition.put(current.vertexId, i + 1);
		}

		heapify();
	}

	public void heapify() {
		for (int i = (lastPosition / 2); i > 0; i--) {
			percolateDown(i);
		}
	}

	public HeapNode peekMin() {
		return heap[1];
	}

	// returns the index
	public int getLeftChild(int k) {
		return 2 * k;
	}

	public int getRightChild(int k) {
		return 2 * k + 1;
	}

	public int getParent(int p) {
		if (p % 2 == 0) {
			return p / 2;
		}
		return (p - 1) / 2;
	}

	public void insert(HeapNode value) {
		heap[lastPosition] = value;
		vertexPosition.put(value.vertexId, lastPosition);
		lastPosition++;

		int child = lastPosition;
		percolateUp(child);
	}

	private void percolateUp(int child) {
		int parent = getParent(child);

		while (parent != 0 && !isHeapPropertySatisfied(parent)) {
			swapHeapValues(parent, child);
			child = parent;
			parent = getParent(child);
		}
	}

	public HeapNode deleteMin() {
		HeapNode min = heap[1];
		lastPosition--;

		heap[1] = heap[lastPosition];
		vertexPosition.put(heap[1].vertexId, 1);
		// Note that this is NOT -
		// vertexPosition.put(min.vertexId, 1);

		percolateDown(1);

		// System.out.println("removing from vertexPosition "+min.vertexId+"
		// size of map "+vertexPosition.size());
		vertexPosition.remove(min.vertexId);
		return min;
	}

	private void percolateDown(int parent) {
		int child = minChild(parent);

		while (hasChild(parent) && !isHeapPropertySatisfied(parent)) {
			swapHeapValues(parent, child);
			parent = child;
			child = minChild(parent);
		}
	}

	public void updateKey(int index, int value) {
		int original = heap[index].value;
		heap[index].value = value;

		// percolate up if decreasing value from previous
		if (original > value) {
			percolateUp(index);
		}
	}

	private boolean hasChild(int parent) {
		if (getLeftChild(parent) >= lastPosition) {
			return false;
		}
		return true;
	}

	private int minChild(int parent) {

		int leftChild = getLeftChild(parent);
		int rightChild = getRightChild(parent);

		// if only child
		if (rightChild >= lastPosition) {
			return leftChild;
		}

		// compare two children
		if (heap[leftChild].value > heap[rightChild].value) {
			return rightChild;
		}
		return leftChild;
	}

	private void swapHeapValues(int parent, int child) {
		HeapNode temp = heap[parent];
		heap[parent] = heap[child];
		heap[child] = temp;

		// update auxiliary data
		vertexPosition.put(heap[parent].vertexId, parent);
		vertexPosition.put(heap[child].vertexId, child);
	}

	// locally checks a node that's not a leaf
	private boolean isHeapPropertySatisfied(int parentIndex) {

		int leftChildIndex = getLeftChild(parentIndex);
		int rightChildIndex = getRightChild(parentIndex);

		boolean rightOk = rightChildIndex >= lastPosition || heap[parentIndex].value < heap[rightChildIndex].value;
		boolean leftOk = leftChildIndex >= lastPosition || heap[parentIndex].value < heap[leftChildIndex].value;
		if (rightOk && leftOk) {
			return true;
		}
		return false;
	}

	public void print() {
		for (int i = 1; i < lastPosition; i++)
			System.out.print("(" + heap[i].vertexId + "," + heap[i].value + ")" + " ");
		System.out.println();
	}

	public int getVertexPosition(int vertexID) {
		if (vertexPosition.containsKey(vertexID)) {
			return vertexPosition.get(vertexID);
		}
		return -1;
	}

}

class HeapNode {
	int vertexId;
	int value;

	HeapNode(int vertexId, int value) {
		this.vertexId = vertexId;
		this.value = value;
	}
}
