package astar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class MyPanel extends JPanel implements MouseListener {
	
	public int clickType = 0;
	
	public MyPanel() {
		this.addMouseListener(this);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (int i = 0; i < 20; i++) {
			g.setColor(Color.BLACK);
			g.drawLine(20*i, 0, 20*i, 400);
			g.drawLine(0, 20*i, 400, 20*i);
		}
		wayPoint[][] wps = treeTest.wps;
		for (int i = 0; i < wps.length; i++) {
			for (int j = 0; j < wps[i].length; j++) {
				if (!wps[i][j].isCanGo()) {
					g.setColor(Color.black);
					g.fillRect(i*20, j*20, 20, 20);
					g.drawRect(i*20, j*20, 20, 20);
				}else {
					g.setColor(Color.GRAY);
					g.fillRect(i*20, j*20, 20, 20);
					g.setColor(Color.black);
					g.drawRect(i*20, j*20, 20, 20);
				}
				if (wps[i][j].isStart()) {
					g.setColor(Color.GREEN);
					g.fillRect(i*20, j*20, 20, 20);
					g.setColor(Color.black);
					g.drawRect(i*20, j*20, 20, 20);
				}
				if (wps[i][j].isEnd()) {
					g.setColor(Color.RED);
					g.fillRect(i*20, j*20, 20, 20);
					g.setColor(Color.black);
					g.drawRect(i*20, j*20, 20, 20);
				}
				if (wps[i][j].isWay()) {
					g.setColor(Color.YELLOW);
					g.fillRect(i*20, j*20, 20, 20);
					g.setColor(Color.black);
					g.drawRect(i*20, j*20, 20, 20);
				}
//				g.setColor(Color.black);
//				g.drawString(wps[i][j].getG()+"", i*20+10, j*20+10);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int clickX = e.getX();
		int clickY = e.getY();
		int clickIndexX = clickX/20;
		int clickIndexY = clickY/20;
//		System.out.println(clickX+"-"+clickY);
//		System.out.println(clickIndexX + "-" + clickIndexY);
//		System.out.println(clickIndexX+":"+clickIndexY+treeTest.wps[clickIndexX][clickIndexY]);
		switch (clickType) {
		case 1:
//			treeTest.wps[clickIndexX][clickIndexY].setCanGo(!treeTest.wps[clickIndexX][clickIndexY].isCanGo());
			break;
		case 2:
			treeTest.wps[clickIndexX][clickIndexY].setStart(!treeTest.wps[clickIndexX][clickIndexY].isStart());
			break;
		case 3:
			treeTest.wps[clickIndexX][clickIndexY].setEnd(!treeTest.wps[clickIndexX][clickIndexY].isEnd());
			break;
		default:
			break;
		}
		repaint();
	}
	
	public void calWay(){
		wayPoint startPoint = null;
//		wayPoint endPoint = null;
		List<wayPoint> endPoint = new ArrayList<wayPoint>();
		for (int i = 0; i < treeTest.wps.length; i++) {
			for (int j = 0; j < treeTest.wps[i].length; j++) {
				if (treeTest.wps[i][j].isStart()) {
					startPoint = treeTest.wps[i][j];
				}
				if (treeTest.wps[i][j].isEnd()) {
//					endPoint = treeTest.wps[i][j];
					endPoint.add(treeTest.wps[i][j]);
				}
//				if (startPoint != null && endPoint != null) {
//					break;
//				}
			}
		}
//		List<wayPoint> way = new treeTest().getWay(startPoint, endPoint);
		wayPoint[] ends = new wayPoint[endPoint.size()];
		ends = endPoint.toArray(ends);
		AStarSon util = new AStarSon();
		List<wayPoint> way = util.AStarCalWay(startPoint, ends);
		for (wayPoint wp : way) {
			wp.setWay(true);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int clickX = e.getX();
		int clickY = e.getY();
		int clickIndexX = clickX/20;
		int clickIndexY = clickY/20;
		if (clickType == 1) {
			treeTest.wps[clickIndexX][clickIndexY].setCanGo(!treeTest.wps[clickIndexX][clickIndexY].isCanGo());
			repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
}
