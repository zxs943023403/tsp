package astar;

public class wayPoint {
	private int x;
	private int y;
	private boolean canGo;
	private boolean isGone;
	private boolean isNotGood;
	private boolean isStart;
	private boolean isEnd;
	private boolean isWay;
	private wayPoint parentPoint;
	private int G = 0;
	private int cost = 10;
	public int H(int endX,int endY){
		return Math.abs((endX-this.x)*10)+Math.abs((endY-this.y)*10);
	}
	public int H(wayPoint[] ends){
		int h = -1;
		for (wayPoint w : ends) {
			if (h == -1) {
				h = H(w.getX(), w.getY());
			}else {
				h = h < H(w.getX(), w.getY())?h:H(w.getX(), w.getY());
			}
		}
		return h;
	}
	public int getG() {
		return G;
	}
	public void setG(int g) {
		G = g;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public boolean isWay() {
		return isWay;
	}
	public void setWay(boolean isWay) {
		this.isWay = isWay;
	}
	public boolean isStart() {
		return isStart;
	}
	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}
	public boolean isEnd() {
		return isEnd;
	}
	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}
	public boolean isNotGood() {
		return isNotGood;
	}
	public void setNotGood(boolean isNotGood) {
		this.isNotGood = isNotGood;
	}
	public wayPoint getParentPoint() {
		return parentPoint;
	}
	public void setParentPoint(wayPoint parentPoint) {
		this.parentPoint = parentPoint;
	}
	public boolean isGone() {
		return isGone;
	}
	public void setGone(boolean isGone) {
		this.isGone = isGone;
	}
	public wayPoint(int x, int y, boolean canGo) {
		super();
		this.x = x;
		this.y = y;
		this.canGo = canGo;
	}
	public boolean isCanGo() {
		return canGo;
	}
	public void setCanGo(boolean canGo) {
		this.canGo = canGo;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		wayPoint compareTo = (wayPoint) obj;
		if (this.x == compareTo.x && this.y == compareTo.y) {
			return true;
		}
		return false;
	}
}
