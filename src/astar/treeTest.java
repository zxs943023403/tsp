package astar;

import java.util.ArrayList;
import java.util.List;

public class treeTest {
	
	public static wayPoint[][] wps;
	
	public static List<wayPoint> open;
	
	public static List<wayPoint> close;
	
	public static wayPoint ends;
	
	public static boolean isEnd = false;
	
	public static void main(String[] args) {
		try {
			
			open = new ArrayList<wayPoint>();
			close = new ArrayList<wayPoint>();
//			initMap(30, 30);
			initRandomMap(31, 31);
//			wps = new readMazeImage().read("d:\\maze5.jpg");
			new MyFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void initMap(int sizeX,int sizeY){
		wps = new wayPoint[sizeX][sizeY];
		for (int i = 0; i < wps.length; i++) {
			for (int j = 0; j < wps[i].length; j++) {
				wps[i][j] = new wayPoint(i, j, true);
			}
		}
	}
	
	public static void initRandomMap(int sizeX,int sizeY){
		Maze m = new Maze();
		m.setLen(sizeX);
		m.setWid(sizeY);
		m.setStartX(0);
		m.setEndX(sizeX-1);
		m.setStartY(0);
		m.setEndY(sizeY-1);
		m.init();
		char [][] maze = m.makeMaze();
		wps = new wayPoint[sizeX][sizeY];
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				wps[i][j] = new wayPoint(i, j, maze[i][j] == '0');
			}
		}
	}
	
	public List<wayPoint> getWay(wayPoint start, wayPoint end){
		ends = end;
		open.add(start);
//		AStarUtil util = new AStarSon();
		long startt = System.currentTimeMillis();
//		util.AStarCalWay(start, ends);
		calWay2(start);
		long endt = System.currentTimeMillis();
		System.out.println(endt-startt);
//		System.out.println(close);
		List<wayPoint> way = new ArrayList<wayPoint>();
		wayPoint calStart = null;
		if (ends.getParentPoint() == null) {
			wayPoint temp = null;
			for (wayPoint wayPoint : close) {
				if (temp == null) {
					temp = wayPoint;
				}else {
					if ((temp.H(ends.getX(), ends.getY()) ) > (wayPoint.H(ends.getX(), ends.getY()) )) {
						temp = wayPoint;
					}
				}
			}
			ends.setParentPoint(temp);
		}
		while (true) {
			if (calStart != null) {
				break;
			}
			way.add(ends.getParentPoint());
			ends = ends.getParentPoint();
			if (way.get(way.size()-1) == start) {
				calStart = start;
			}
		}
//		for (int i = 0; i < close.size(); i++) {
//			way.add(close.get(i));
//			if (close.get(i).isNotGood() && close.size() < i-1) {
//				wayPoint parent = close.get(i+1).getParentPoint();
//				for (int j = i; j >= 0; j--) {
//					if (!close.get(j).equals(parent)) {
//						way.remove(close.get(j));
//					}else {
//						break;
//					}
//				}
//			}
//		}
		return way;
	}
	
	//深度搜索算法
	public void calWay(wayPoint point){
		if (isEnd) {
			return;
		}
		if (open.size() == 0) {
			return ;
		}
		wayPoint nodeN = open.get(0);
		if (!close.contains(nodeN)) {
			close.add(nodeN);
		}
		open.remove(nodeN);
		if (nodeN.equals(ends)) {
			isEnd = true;
			return ;
		}
		wayPoint[] nodeGoTo = getCanGo(point);
		for (wayPoint wayPoint : nodeGoTo) {
			open.add(wayPoint);
			calWay(wayPoint);
		}
	}
	
	//A*算法
	public void calWay2(wayPoint point){
		wayPoint todoNode = null;
		for (wayPoint wp : open) {
			if (todoNode == null) {
				todoNode = wp;
			}
			if ((wp.getG()+wp.H(ends.getX(), ends.getY())) < todoNode.getG()+todoNode.H(ends.getX(), ends.getY())) {
				todoNode = wp;
			}
		}
		if (open.size() == 0 || close.contains(ends)) {
			return;
		}
		open.remove(todoNode);
		close.add(todoNode);
		wayPoint[] canGo = getCanGo(todoNode);
		wayPoint[] canGo2 = new wayPoint[canGo.length];
		int index = 0;
		for (wayPoint wp : canGo) {
			if (close.contains(wp)) {
				continue;
			}
			if (!open.contains(wp)) {
				wp.setParentPoint(todoNode);
				open.add(wp);
			}
			if (open.contains(ends) || open.size() == 0 ) {
				return;
			}
			canGo2[index++] = wp;
		}
		for (wayPoint wp : canGo2) {
			if (wp != null) {
				calWay2(wp);
			}
		}
	}
	
	public wayPoint[] getCanGo(wayPoint point){
		point.setGone(true);
		List<wayPoint> canGo = new ArrayList<wayPoint>();
		int x = point.getX();
		int y = point.getY();
		if(y +1 < wps[x].length && wps[x][y+1].isCanGo()&& !wps[x][y+1].isGone()){
			wps[x][y+1].setParentPoint(point);
			wps[x][y+1].setG(point.getG()+wps[x][y+1].getCost());
			canGo.add(wps[x][y+1]);
		}
		if (x-1>=0 && wps[x-1][y].isCanGo() && !wps[x-1][y].isGone()) {
			wps[x-1][y].setParentPoint(point);
			wps[x-1][y].setG(point.getG()+wps[x-1][y].getCost());
			canGo.add(wps[x-1][y]);
		}
		if (x +1 < wps.length && wps[x+1][y].isCanGo()&& !wps[x+1][y].isGone()) {
			wps[x+1][y].setParentPoint(point);
			wps[x+1][y].setG(point.getG()+wps[x+1][y].getCost());
			canGo.add(wps[x+1][y]);
		}
		if (y - 1 >= 0 && wps[x][y-1].isCanGo()&& !wps[x][y-1].isGone()) {
			wps[x][y-1].setParentPoint(point);
			wps[x][y-1].setG(point.getG()+wps[x][y-1].getCost());
			canGo.add(wps[x][y-1]);
		}
		
//		if (x-1 >=0 && y-1>=0 && wps[x-1][y-1].isCanGo() && !wps[x-1][y-1].isGone()) {
//			wps[x-1][y-1].setParentPoint(point);
//			wps[x-1][y-1].setG((int)(point.getG()+wps[x-1][y-1].getCost()*Math.sqrt(2)));
//			canGo.add(wps[x-1][y-1]);
//		}
//		if (x+1 <wps.length && y-1>=0 && wps[x+1][y-1].isCanGo() && !wps[x+1][y-1].isGone()) {
//			wps[x+1][y-1].setParentPoint(point);
//			wps[x+1][y-1].setG((int)(point.getG()+wps[x+1][y-1].getCost()*Math.sqrt(2)));
//			canGo.add(wps[x+1][y-1]);
//		}
//		if (x-1 >=0 && y+1<wps[x].length && wps[x-1][y+1].isCanGo() && !wps[x-1][y+1].isGone()) {
//			wps[x-1][y+1].setParentPoint(point);
//			wps[x-1][y+1].setG((int)(point.getG()+wps[x-1][y+1].getCost()*Math.sqrt(2)));
//			canGo.add(wps[x-1][y+1]);
//		}
//		if (x+1 <wps.length && y+1<wps[x].length && wps[x+1][y+1].isCanGo() && !wps[x+1][y+1].isGone()) {
//			wps[x+1][y+1].setParentPoint(point);
//			wps[x+1][y+1].setG((int)(point.getG()+wps[x+1][y+1].getCost()*Math.sqrt(2)));
//			canGo.add(wps[x+1][y+1]);
//		}
		return getMinHPoint(canGo);
	}
	
	public wayPoint[] getMinHPoint(List<wayPoint> list){
		if (list.size() == 0) {
			close.get(close.size()-1).setNotGood(true);
		}
		wayPoint[] array = new wayPoint[list.size()];
		array = list.toArray(array);
		wayPoint temp = null;
		//A*算法不需要根据H排序
//		for (int i = 0; i < array.length; i++) {
//			for (int j=0;j<array.length-1-i;j++) {
//				if (array[j].H(ends.getX(), ends.getY()) > array[j + 1].H(ends.getX(), ends.getY())) {
//					temp = array[j];
//					array[j] = array[j + 1];
//					array[j + 1] = temp;
//				}
//			}
//		}
		return array;
	}
	
}
