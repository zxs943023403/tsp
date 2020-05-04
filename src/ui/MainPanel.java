package ui;

import model.Node;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    public Node[] nodes;
    public int N;
    public Node[] way;
    public double length;

    public MainPanel(){
        repaint();
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
        repaint();
    }

    public void setWay(Node[] way) {
        this.way = way;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(10,10,510,510);
        g.drawString("结果为："+Double.toString(length),10,540);
        if (nodes != null){
            g.setColor(Color.RED);
            g.fillOval(nodes[0].getX()+8,nodes[0].getY()+8,8,8);
            g.setColor(Color.BLACK);
            for(int i = 1;i < nodes.length;i++){
                g.fillOval(nodes[i].getX()+8,nodes[i].getY()+8,4,4);
            }
        }
        if (way != null){
            g.drawLine(way[0].getX()+10,way[0].getY()+10,way[N-1].getX()+10,way[N-1].getY()+10);
            for(int i = 1;i < way.length;i++){
                g.drawLine(way[i].getX()+10,way[i].getY()+10,way[i-1].getX()+10,way[i-1].getY()+10);
            }
        }
    }
}
