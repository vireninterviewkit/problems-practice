package com.problems.patterns.crossdomains;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.common.model.GraphNode2;

public class CloneProblems {

	// Clone an Undirected Graph - DFS/BFS
	// Using BFS traversal to clone the graph
	public static GraphNode2 cloneGraph1(GraphNode2 root) {
		if (root == null) return null;
		Queue<GraphNode2> queue = new LinkedList<>();
		// cloneMap: node, cloneNode
		HashMap<GraphNode2, GraphNode2> cloneMap = new HashMap<>();
		cloneMap.put(root, new GraphNode2(root.label));
		queue.add(root);
		while (!queue.isEmpty()) {
			GraphNode2 currCloneNode, neighborClone, currNode;
			currNode = queue.poll();
			currCloneNode = cloneMap.get(currNode);
			for (GraphNode2 neighbor : currNode.neighbors) {
				neighborClone = cloneMap.get(neighbor);
				if (neighborClone == null) {
					neighborClone = new GraphNode2(neighbor.label);
					cloneMap.put(neighbor, neighborClone);
					queue.add(neighbor);
				}
				currCloneNode.neighbors.add(neighborClone);
			}
		}
		return cloneMap.get(root);
	}

	// DFS traversal using stack & iterative
	public static GraphNode2 cloneGraph2(GraphNode2 node) {
		if (node == null) return null;

		HashMap<GraphNode2, GraphNode2> hm = new HashMap<GraphNode2, GraphNode2>();
		LinkedList<GraphNode2> stack = new LinkedList<GraphNode2>();
		GraphNode2 head = new GraphNode2(node.label);
		hm.put(node, head);
		stack.push(node);

		while (!stack.isEmpty()) {
			GraphNode2 curnode = stack.pop();
			for (GraphNode2 aneighbor : curnode.neighbors) {// check each neighbor
				if (!hm.containsKey(aneighbor)) {// if not visited,then push to stack
					stack.push(aneighbor);
					GraphNode2 newneighbor = new GraphNode2(aneighbor.label);
					hm.put(aneighbor, newneighbor);
				}

				hm.get(curnode).neighbors.add(hm.get(aneighbor));
			}
		}

		return head;
	}

	// Using DFS traversal recursive
	public static GraphNode2 cloneGraph3(GraphNode2 root) {
		if (root == null) return root;
		// CloneMap: node label, Clone node
		HashMap<Integer, GraphNode2> cloneMap = new HashMap<>();
		return cloneGraph(root, cloneMap);
	}

	private static GraphNode2 cloneGraph(GraphNode2 root,
			HashMap<Integer, GraphNode2> cloneMap) {
		GraphNode2 clone = new GraphNode2(root.label);
		cloneMap.put(root.label, clone);
		for (GraphNode2 neigbhor : root.neighbors) {
			GraphNode2 neighborClone = cloneMap.get(neigbhor.label);
			if (neighborClone != null) {
				clone.neighbors.add(neighborClone);
			} else {
				clone.neighbors.add(cloneGraph(neigbhor, cloneMap));
			}
		}
		return clone;
	}

	/* Copy List with Random Pointer:
	 * A linked list is given such that each node contains an additional random pointer which could point to any node
	 * in the list or null.	Return a deep copy of the list.
	 */
	/* Approach1: Using HashMap: Time-O(n), Space:O(n)
	 * Approach2: Efficient Linear Approach: Time-O(n), Space-O(1)
	 */
	// Approach1:
	public Node copyRandomList1(Node head) {
		if (head == null) return head;
		Map<Integer, Node> map = new HashMap<>();
		Node curr = head;
		while (curr != null) {
			map.put(curr.val, new Node(curr.val, null, null));
			curr = curr.next;
		}
		curr = head;
		Node cloneHead = map.get(head.val);
		Node clone = cloneHead;
		while (curr != null) {
			clone.next = curr.next == null ? null : map.get(curr.next.val);
			clone.random = curr.random == null ? null : map.get(curr.random.val);

			curr = curr.next;
			clone = clone.next;
		}
		return cloneHead;
	}

	// Approach2:
	public Node copyRandomList2(Node head) {
		if (head == null) return head;
		Node curr = head, clone;
		while (curr != null) {
			clone = new Node(curr.val, curr.next, null);
			curr.next = clone;
			curr = clone.next;
		}
		curr = head;
		clone = null;
		while (curr != null) {
			clone = curr.next;
			clone.random = curr.random == null ? null : curr.random.next;
			curr = clone.next;
		}
		curr = head;
		Node cloneHead = curr.next;
		clone = cloneHead;
		while (curr != null) {
			curr.next = clone.next;
			clone.next = clone.next == null ? null : clone.next.next;

			clone = clone.next;
			curr = curr.next;
		}
		return cloneHead;
	}
}

class Node {
	public int val;
	public Node next;
	public Node random;

	public Node(int _val, Node _next, Node _random) {
		val = _val;
		next = _next;
		random = _random;
	}
}