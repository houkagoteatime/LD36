package com.github.houkagoteatime.LD36.utils;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.houkagoteatime.LD36.levels.Level;



public class PathFinder {
	private ArrayList<Node> closed;
	private ArrayList<Node> open;
	private Node[][] nodes;
	private int maxSearch;
	public static final int TILE_SIZE = 16;
	private Level level;
	private TiledMap map;
	public PathFinder(Level level, int max) {
		maxSearch = max;
		this.level = level;
		TiledMap map = level.getTiledMap();
		this.map = map;
		MapProperties prop = map.getProperties();
		int width = prop.get("width", Integer.class);
		int height = prop.get("height", Integer.class);
		nodes = new Node[width][height];
		for(int i = 0; i<width; i++) {
			for(int j = 0; j<height; j++) {
				nodes[i][j] = new Node(i, j);
			}
		}
		open = new ArrayList<>();
		closed = new ArrayList<>();
	}

	public ArrayList<Node> findPath(Vector2 start, Vector2 end) {
		int startX = (int)start.x;
		int startY = (int)start.y;
		int endX = (int)end.x;
		int endY = (int)end.y;
		nodes[startX][startY].gValue = 0;
		nodes[startX][startY].depth = 0;
		open.add(nodes[startX][startY]);
		int depth = 0;
		while(depth < maxSearch && open.size() != 0) {
			Node current = open.get(0);
			if(current == nodes[endX][endY]) {
				break;
			}
			open.remove(current);
			closed.add(current);
			Collections.sort(closed);
			for(int x = -1; x <=1; x++) {
				for(int y = -1; y<= 1; y++) {
					if(x == 0 && y == 0)
						break;
					int xNeighbor = x + current.x;
					int yNeighbor = y+ current.y;
					if(isValidLocation(xNeighbor, yNeighbor)) {
						float nextCost = current.gValue + 1;
						Node neighbor = nodes[xNeighbor][yNeighbor];
						if(nextCost < neighbor.gValue) {
							if(open.contains(neighbor))
								open.remove(neighbor);
							if(closed.contains(neighbor))
								closed.remove(neighbor);
						}
						if(!open.contains(neighbor) && !closed.contains(neighbor)) {
							neighbor.gValue = nextCost;
							neighbor.hValue = calculateHeurestic(xNeighbor, yNeighbor, endX, endY);
							depth = Math.max(maxSearch, neighbor.setParent(current));
							open.add(neighbor);
						}
					}



				}
			}
		}

		if(nodes[endX][endY].parent == null)
			return null;
		ArrayList<Node> path = new ArrayList<>();
		Node target = nodes[endX][endY];
		while(target != nodes[startX][startY]) {
			path.add(0, target);
			if(target.parent != null)
				target = target.parent;
		}
		return path;
	}

	public float calculateHeurestic(int startX, int startY, int endX, int endY) {	
		return (float)Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
	}
	public boolean isValidLocation(int xPos, int yPos) {
		boolean outOfBounds = xPos < 0 || yPos < 0 || xPos >= nodes.length || yPos >= nodes[0].length;
		return !(outOfBounds || invalidTile(xPos, yPos));
	}

	public boolean invalidTile(int xPos, int yPos) {
		for(PolygonMapObject obj : level.getMapObjects().getByType(PolygonMapObject.class)) {
			if(collidesObj(obj.getPolygon(), xPos, yPos))
				return true;
		}
		return false;
	}
	public boolean collidesObj(Polygon p, int xPos, int yPos) {
		Rectangle n = new Rectangle(xPos * TILE_SIZE, yPos * TILE_SIZE, 13, 13);
		if(p.getBoundingRectangle().overlaps((n)))
			return true;
		return false;
	}

}
