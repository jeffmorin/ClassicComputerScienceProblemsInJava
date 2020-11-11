// WeightedGraph.java
// From Classic Computer Science Problems in Java Chapter 4
// Copyright 2020 David Kopec
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package chapter4;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.IntConsumer;

public class WeightedGraph<V> extends Graph<V, WeightedEdge> implements Cities {

	public WeightedGraph(List<V> vertices) {
		super(vertices);
	}

	// This is an undirected graph, so we always add
	// edges in both directions
	public void addEdge(WeightedEdge edge) {
		edges.get(edge.u).add(edge);
		edges.get(edge.v).add(edge.reversed());
	}

	public void addEdge(int u, int v, float weight) {
		addEdge(new WeightedEdge(u, v, weight));
	}

	public void addEdge(V first, V second, float weight) {
		addEdge(indexOf(first), indexOf(second), weight);
	}

	// Make it easy to pretty-print a Graph
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < getVertexCount(); i++) {
			sb.append(vertexAt(i));
			sb.append(" -> ");
			sb.append(Arrays.toString(edgesOf(i).stream()
					.map(we -> "(" + vertexAt(we.v) + ", " + we.weight + ")").toArray()));
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}

	public static double totalWeight(List<WeightedEdge> path) {
		return path.stream().mapToDouble(we -> we.weight).sum();
	}

	// Find the minimum-spanning tree of this graph using Jarnik's algorithm
	// *start* is the vertex index to start the search at
	public List<WeightedEdge> mst(int start) {
		LinkedList<WeightedEdge> result = new LinkedList<>(); // final mst
		if (start < 0 || start > (getVertexCount() - 1)) {
			return result;
		}
		PriorityQueue<WeightedEdge> pq = new PriorityQueue<>();
		boolean[] visited = new boolean[getVertexCount()]; // where we've been

		// this is like a "visit" inner function
		IntConsumer visit = index -> {
			visited[index] = true; // mark as visited
			for (WeightedEdge edge : edgesOf(index)) {
				// add all edges coming from here to pq
				if (!visited[edge.v]) {
					pq.offer(edge);
				}
			}
		};

		visit.accept(start); // the first vertex is where everything begins
		while (!pq.isEmpty()) { // keep going while there are edges to process
			WeightedEdge edge = pq.poll();
			if (visited[edge.v]) {
				continue; // don't ever revisit
			}
			// this is the current smallest, so add it to solution
			result.add(edge);
			visit.accept(edge.v); // visit where this connects
		}

		return result;
	}

	public void printWeightedPath(List<WeightedEdge> wp) {
		for (WeightedEdge edge : wp) {
			System.out.println(vertexAt(edge.u) + " " + edge.weight + "> " + vertexAt(edge.v));
		}
		System.out.println("Total Weight: " + totalWeight(wp));
	}

	public static final class DijkstraNode implements Comparable<DijkstraNode> {
		public final int vertex;
		public final double distance;

		public DijkstraNode(int vertex, double distance) {
			this.vertex = vertex;
			this.distance = distance;
		}

		@Override
		public int compareTo(DijkstraNode other) {
			Double mine = distance;
			Double theirs = other.distance;
			return mine.compareTo(theirs);
		}
	}

	public static final class DijkstraResult {
		public final double[] distances;
		public final Map<Integer, WeightedEdge> pathMap;

		public DijkstraResult(double[] distances, Map<Integer, WeightedEdge> pathMap) {
			this.distances = distances;
			this.pathMap = pathMap;
		}
	}

	public DijkstraResult dijkstra(V root) {
		int first = indexOf(root); // find starting index
		// distances are unknown at first
		double[] distances = new double[getVertexCount()];
		distances[first] = 0; // root's distance to root is 0
		boolean[] visited = new boolean[getVertexCount()]; // where we've been
		visited[first] = true;
		// how we got to each vertex
		HashMap<Integer, WeightedEdge> pathMap = new HashMap<>();
		PriorityQueue<DijkstraNode> pq = new PriorityQueue<>();
		pq.offer(new DijkstraNode(first, 0));

		while (!pq.isEmpty()) {
			int u = pq.poll().vertex; // explore the next closest vertex
			double distU = distances[u]; // should already have seen it
			// look at every edge/vertex from the vertex in question
			for (WeightedEdge we : edgesOf(u)) {
				// the old distance to this vertex
				double distV = distances[we.v];
				// the new distance to this vertex
				double pathWeight = we.weight + distU;
				// new vertex or found shorter path?
				if (!visited[we.v] || (distV > pathWeight)) {
					visited[we.v] = true;
					// update the distance to this vertex
					distances[we.v] = pathWeight;
					// update the edge on the shortest path to this vertex
					pathMap.put(we.v, we);
					// explore it in the future
					pq.offer(new DijkstraNode(we.v, pathWeight));
				}
			}
		}

		return new DijkstraResult(distances, pathMap);
	}

	// Helper function to get easier access to dijkstra results
	public Map<V, Double> distanceArrayToDistanceMap(double[] distances) {
		HashMap<V, Double> distanceMap = new HashMap<>();
		for (int i = 0; i < distances.length; i++) {
			distanceMap.put(vertexAt(i), distances[i]);
		}
		return distanceMap;
	}

	// Takes a map of edges to reach each node and return a list of
	// edges that goes from *start* to *end*
	public static List<WeightedEdge> pathMapToPath(int start, int end, Map<Integer, WeightedEdge> pathMap) {
		if (pathMap.size() == 0) {
			return List.of();
		}
		LinkedList<WeightedEdge> path = new LinkedList<>();
		WeightedEdge edge = pathMap.get(end);
		path.add(edge);
		while (edge.u != start) {
			edge = pathMap.get(edge.u);
			path.add(edge);
		}
		Collections.reverse(path);
		return path;
	}

	// Test basic Graph construction
	public static void main(String[] args) {
		// Represents the 15 largest MSAs in the United States
		WeightedGraph<String> cityGraph2 = new WeightedGraph<>(CITIES);

		for (Route route : ROUTES) {
			cityGraph2.addEdge(route.city1, route.city2, route.distance);
		}

		System.out.println(cityGraph2);

		List<WeightedEdge> mst = cityGraph2.mst(0);
		cityGraph2.printWeightedPath(mst);

		System.out.println(); // spacing

		DijkstraResult dijkstraResult = cityGraph2.dijkstra(LOS_ANGELES);
		Map<String, Double> nameDistance = cityGraph2.distanceArrayToDistanceMap(dijkstraResult.distances);
		System.out.println("Distances from Los Angeles:");
		nameDistance.forEach((name, distance) -> System.out.println(name + " : " + distance));

		System.out.println(); // spacing

		System.out.println("Shortest path from Los Angeles to Boston:");
		List<WeightedEdge> path = pathMapToPath(cityGraph2.indexOf(LOS_ANGELES), cityGraph2.indexOf(BOSTON),
				dijkstraResult.pathMap);
		cityGraph2.printWeightedPath(path);
	}

}
