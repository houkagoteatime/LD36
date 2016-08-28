package com.github.houkagoteatime.LD36.utils;


public class Node implements Comparable<Node> {
		public int x, y;
		public float gValue;
		public float hValue;
		public int depth;
		public Node parent;
		public Node(int xValue, int yValue) {
			x = xValue;
			y = yValue;
		}
		
		public int setParent(Node parent) {
			depth = parent.depth + 1;
			this.parent = parent;
			return depth;
		}

		@Override
		public int compareTo(Node other) {
			float fValue = gValue + hValue;
			float otherFValue = other.gValue + other.hValue;
			if(fValue < otherFValue)
				return -1;
			else if(fValue > otherFValue)
				return 1;
			else
				return 0;
		}
		
	}