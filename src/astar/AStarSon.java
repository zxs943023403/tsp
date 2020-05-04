package astar;

import java.util.ArrayList;
import java.util.List;

public class AStarSon extends AStarUtil {
	
	@Override
	public List<wayPoint> getCanGo(wayPoint point) {
		wayPoint[][] wps = treeTest.wps;
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
		return canGo;
	}
	
}
