package astar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame implements ActionListener {
	MyPanel myPanel;
	public MyFrame(){
		myPanel = new MyPanel();
		this.setLayout(new GridLayout(1, 2));
		this.add(myPanel);
		this.setLocation(200, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(420, 420);
		JButton wayChange = new JButton("换路");
		JButton setStart = new JButton("开始");
		JButton setEnd = new JButton("结束");
		JButton calWay = new JButton("计算");
		JButton clear = new JButton("重来");
		JPanel btn1 = new JPanel();
		btn1.add(wayChange);
		JPanel btn2 = new JPanel();
		btn2.add(setStart);
		JPanel btn3 = new JPanel();
		btn3.add(setEnd);
		JPanel btn4 = new JPanel();
		btn4.add(calWay);
		JPanel btn5 = new JPanel();
		btn5.add(clear);
		JPanel btns = new JPanel();
		btns.setLayout(new GridLayout(5, 1));
		btns.add(btn1);
		btns.add(btn2);
		btns.add(btn3);
		btns.add(btn4);
		btns.add(btn5);
		wayChange.setActionCommand("1");
		setStart.setActionCommand("2");
		setEnd.setActionCommand("3");
		calWay.setActionCommand("4");
		clear.setActionCommand("5");
		wayChange.addActionListener(this);
		setStart.addActionListener(this);
		setEnd.addActionListener(this);
		calWay.addActionListener(this);
		clear.addActionListener(this);
		btns.setSize(100, 420);
		this.add(btns);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			myPanel.clickType = Integer.valueOf(e.getActionCommand());
			if ("4".equals(e.getActionCommand())) {
				myPanel.calWay();
				myPanel.repaint();
			}
			if ("5".equals(e.getActionCommand())) {
//				treeTest.wps = new readMazeImage().read("d:\\maze5.jpg");
//				treeTest.close = new ArrayList<wayPoint>();
//				treeTest.open = new ArrayList<wayPoint>();
//				treeTest.isEnd = false;
				treeTest.initRandomMap(31, 31);
				myPanel.repaint();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}
