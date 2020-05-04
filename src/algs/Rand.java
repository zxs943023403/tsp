package algs;

import model.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Rand implements AlgInterface {
    private Node[] nodes;
    private double[][] dic;
    private Integer[] seq;
    private int times;
    private int N;
    private double length;
    public Rand(Node[] nodes,int times){
        this.nodes = nodes;
        seq = new Integer[nodes.length];
        this.times = times;
        this.N = nodes.length;
        length = Double.MAX_VALUE;
        dic = new double[this.N][this.N];
        for (int i = 0;i < N ;++i){
            for(int j = 0; j < N; ++j){
                dic[i][j] = nodes[i].dicToNode(nodes[j]);
            }
        }
    }
    public double countEnergy(Integer[] is){
        double tmp = 0;
        for(int i = 1;i < N; i ++){
            if (is[i] < 0 || is[i] >= N){
                return 0.0;
            }
            tmp += nodes[is[i]].dicToNode(nodes[is[i-1]]);
        }
        tmp += nodes[is[0]].dicToNode(nodes[is[N-1]]);
        return tmp;
    }


    @Override
    public void Cal() {
        List<Integer> li = new ArrayList<>();
        for(int i = 0;i < N ; i ++){
            li.add(i);
        }
        Integer[] a = new Integer[li.size()];
        a = li.toArray(a);
        seq = a;
        length = countEnergy(a);
        double tmp_len = Double.MAX_VALUE;
        int t = times;
        while (t != 0){
            Collections.shuffle(li);
            a = new Integer[li.size()];
            a = li.toArray(a);
            tmp_len = countEnergy(a);
            if (tmp_len < length){
                t = times;
                length = tmp_len;
                seq = a;
            }else{
                t -= 1;
            }
        }
    }

    @Override
    public double getLength() {
        return length;
    }

    @Override
    public Node[] getWay() {
        Node[] result = new Node[seq.length];
        for(int i = 0;i < seq.length;i++){
            result[i] = nodes[seq[i]];
        }
        return result;
    }
}
