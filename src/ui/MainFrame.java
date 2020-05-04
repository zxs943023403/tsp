package ui;

import algs.*;
import model.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MainFrame extends JFrame implements ActionListener {
    private MainPanel mPanel;
    private JButton initBtn;
    private JButton calBtn;
    private JComboBox<String> box;
    public MainFrame(){
        mPanel = new MainPanel();
//        this.setLayout(new BorderLayout());
        this.setLayout(new GridLayout(1,2));
        mPanel.setBackground(Color.LIGHT_GRAY);
//        this.add(mPanel,BorderLayout.CENTER);
        this.add(mPanel);
        this.setLocation(200,200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(800,600);
//        this.setResizable(false);
        initBtn = new JButton("初始化");
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(3,1));
        JPanel p2 = new JPanel();
        JLabel label = new JLabel("选择算法:");
        box = new JComboBox<String>();
        box.addItem("基本蚁群算法");
        box.addItem("贪婪算法");
        box.addItem("遗传算法");
        box.addItem("模拟退火算法");
        p2.add(label);
        p2.add(box);
        p1.add(p2);
        calBtn = new JButton("计算");
        p1.add(initBtn);
        p1.add(calBtn);
        p1.setSize(100,600);
//        this.add(p1,BorderLayout.EAST);
        this.add(p1);
        initBtn.addActionListener(this);
        calBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == initBtn){
            Random r = new Random();
            int N = r.nextInt(25)+25;
            Node[] citys = new Node[N];
            for(int i = 0;i < citys.length;i++){
                citys[i] = new Node();
                citys[i].setX(r.nextInt(500));
                citys[i].setY(r.nextInt(500));
            }
            mPanel.nodes = null;
            mPanel.way = null;
            mPanel.setNodes(citys);
            mPanel.N = N;
        }
        if (e.getSource() == calBtn){
            if (mPanel.N == 0){
                return;
            }
            String alg = box.getSelectedItem().toString();
            AlgInterface a = null;
            switch (alg){
                case "基本蚁群算法":
                    a = new Ant(0.1,0.9,50,1,2500,mPanel.N,mPanel.nodes);
                    break;
                case "贪婪算法":
                    a = new Greedy(mPanel.nodes);
                    break;
                case "遗传算法":
                    a = new Genetic(mPanel.nodes,0.1,0.9,1000,2500);
                    break;
                case "模拟退火算法":
                    a = new Fire(mPanel.nodes,0.998,0.999,2500,1000);
                    break;
                default:
                    return;
            }
            a.Cal();
            mPanel.length = a.getLength();
            mPanel.setWay(a.getWay());
        }
    }
}
