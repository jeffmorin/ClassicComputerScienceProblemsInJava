// UnweightedGraph.java
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

import java.util.List;

import chapter2.GenericSearch;
import chapter2.GenericSearch.Node;

public class UnweightedGraph<V> extends Graph<V, Edge> implements Cities {

	public UnweightedGraph(List<V> vertices) {
		super(vertices);
	}

	// This is an undirected graph, so we always add
	// edges in both directions
	public void addEdge(Edge edge) {
		edges.get(edge.u).add(edge);
		edges.get(edge.v).add(edge.reversed());
	}

	// Add an edge using vertex indices (convenience method)
	public void addEdge(int u, int v) {
		addEdge(new Edge(u, v));
	}

	// Add an edge by looking up vertex indices (convenience method)
	public void addEdge(V first, V second) {
		addEdge(new Edge(indexOf(first), indexOf(second)));
	}

	// Test basic Graph construction
	public static void main(String[] args) {
		// Represents the 15 largest MSAs in the United States
		UnweightedGraph<String> cityGraph = new UnweightedGraph<>(CITIES);

		for (Route route : ROUTES) {
			cityGraph.addEdge(route.city1, route.city2);
		}
		System.out.println(cityGraph);

		Node<String> bfsResult = GenericSearch.bfs(BOSTON,
				v -> v.equals(MIAMI),
				cityGraph::neighborsOf);
		if (bfsResult == null) {
			System.out.println("No solution found using breadth-first search!");
		} else {
			List<String> path = GenericSearch.nodeToPath(bfsResult);
			System.out.println("Path from Boston to Miami:");
			System.out.println(path);
		}
	}
}
