package astar;

import java.util.ArrayList;
import java.util.List;

/**
 * 集成这个类，重写getCanGo方法，调用AStarCalWay计算路径。
 * @author 左雪松
 *
 */
public abstract class AStarUtil {
	
	public List<wayPoint> AStarCalWay(wayPoint start, wayPoint...ends){
		List<wayPoint> way = new ArrayList<wayPoint>();
		List<wayPoint> open = new ArrayList<wayPoint>();
		List<wayPoint> close = new ArrayList<wayPoint>();
		
		open.add(start);
		all:while (true) {
			wayPoint todoNode = null;
			for (wayPoint w : open) {
				if (todoNode == null) {
					todoNode = w;
				}else {
					if (todoNode.getG() + todoNode.H(ends) > w.getG() + w.H(ends)) {
						todoNode = w;
					}
				}
			}
			
			if (open.size() == 0 || endGoes(open, ends) ) {
				break all;
			}
			
			open.remove(todoNode);
			close.add(todoNode);
			List<wayPoint> cango = getCanGo(todoNode);
			for (wayPoint w : cango) {
				if (close.contains(w)) {
					continue;
				}
				if (!open.contains(w)) {
					w.setParentPoint(todoNode);
					open.add(w);
				}
				if (open.size() == 0 || endGoes(open, ends) ) {
					break all;
				}
			}
		}
		
		wayPoint endPoin = getEnd(ends);
		if (endPoin == null) {
			wayPoint temp = null;
			for (wayPoint w : close) {
				if (temp == null) {
					temp = w;
				}else {
					if (temp.H(ends) > w.H(ends)) {
						temp = w;
					}
				}
			}
			endPoin = temp;
		}
		
		while (true) {
			way.add(endPoin.getParentPoint());
			endPoin = endPoin.getParentPoint();
			if (way.get(way.size()-1) == start) {
				break;
			}
		}
		
		return way;
	}
	
	public abstract List<wayPoint> getCanGo(wayPoint point);
	
	public boolean endGoes(List<wayPoint> open, wayPoint...ends ){
		for (wayPoint w : ends) {
			if (open.contains(w)) {
				return true;
			}
		}
		return false;
	}
	
	public wayPoint getEnd(wayPoint...ends ){
		wayPoint p = null;
		for (wayPoint w : ends) {
			if (w.getParentPoint() != null) {
				return w;
			}
		}
		return p;
	}
	
}
