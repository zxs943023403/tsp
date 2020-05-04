package algs;

import model.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Greedy implements AlgInterface {
    private Node[] nodes;
    private Node[] way;
    private double[][] dic;
    private int[] visit;
    private int N;
    private double length;
    public Greedy(Node[] nodes){
        this.nodes = nodes;
        int max = nodes.length;
        way = new Node[max];
        dic = new double[max][max];
        visit = new int[max];
        N = max;
        length = Double.MAX_VALUE;
    }

    @Override
    public void Cal() {
        List<Node> ln = new ArrayList<Node>();
        for (Node n:nodes) {
            ln.add(n);
        }
        cal1();
        for(int i = 1;i < nodes.length;i++){
            Node first = ln.remove(0);
            ln.add(first);
            nodes = ln.toArray(nodes);
            cal1();
        }
    }

    public void cal1(){
        for (int i = 0;i < N ;++i){
            for(int j = 0; j < N; ++j){
                dic[i][j] = nodes[i].dicToNode(nodes[j]);
            }
        }
        visit = new int[way.length];
        Node[] tmp_way = new Node[way.length];
        tmp_way[0] = nodes[0];
        visit[0] = 1;
        int next_index = 0;
        double tmp_len = 0;
        for(int index = 1;index < nodes.length;index++){
            double[] dics = dic[next_index];
            double tmp = Double.MAX_VALUE;
            int n_tmp = 0;
            for(int i = 0;i < dics.length;i++){
                if (dics[i] < tmp && i != next_index && visit[i] == 0){
                    n_tmp = i;
                    tmp = dics[i];
                }
            }
            next_index = n_tmp;
            tmp_way[index] = nodes[next_index];
            visit[next_index] = 1;
            tmp_len += tmp;
        }
        if (length > tmp_len){
            length = tmp_len;
            way = tmp_way;
        }
    }

    @Override
    public double getLength() {
        return length;
    }

    @Override
    public Node[] getWay() {
        return way;
    }
}
