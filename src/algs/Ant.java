package algs;

import model.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//基本蚁群算法
public class Ant implements AlgInterface {
    //信息素下降幅度
    private double u;
    //正确概率
    private double v;
    //蚂蚁数
    private int ant_num;
    //常数因子
    private int Q;
    //最大迭代周期
    private int T;
    //最大节点数量
    private int max;
    //两点间的距离
    private double[][] dic;
    //节点数组
    private Node[] nodes;
    //最优节点序列
    private int[] seq;
    //最优路径长度
    private double length;
    //real citys
    private int N;

    private static Random r = new Random();

    public Ant(double u, double v, int ant_num, int q, int t, int n, Node[] nodes) {
        this.u = u;
        this.v = v;
        this.ant_num = ant_num;
        Q = q;
        T = t;
        N = n;
        length = Integer.MAX_VALUE;
        this.nodes = nodes;
        this.max = nodes.length;
        dic = new double[max][max];
        seq = new int[max];
        for (int i = 0;i < N ;++i){
            for(int j = 0; j < N; ++j){
                dic[i][j] = nodes[i].dicToNode(nodes[j]);
            }
        }
    }

    public double[][] setMess(double[][] ds){
        for(int i = 0;i < N;i++){
            for (int j = 0; j<N; ++j) {
                ds[i][j] = Q / dic[i][j];
            }
        }
        return ds;
    }

    public double countEnergy(int[] is){
        double tmp = 0;
        for(int i = 1;i < N; i ++){
            tmp += nodes[is[i]].dicToNode(nodes[is[i-1]]);
        }
        tmp += nodes[is[0]].dicToNode(nodes[is[N-1]]);
        return tmp;
    }

    @Override
    public void Cal(){
        int t = T;
        while (t-- != 0){
            double[][] mess = new double[max][max];
            mess = setMess(mess);
            int s;
            int[] visit = new int[max];
            int[] t_seq = new int[max];
            int[] best_seq = new int[max];
            double best_length;
            s = ant_num;
            best_length = Integer.MAX_VALUE;
            while (s-- != 0){
                visit = new int[max];
                int tmp = r.nextInt(N);
                t_seq[0] = tmp;
                visit[tmp] = 1;
                int mini = -1;
                double ans = 0;
                for (int i = 1;i < N; ++i){
                    //寻找最大信息素的路
                    double temp = ((r.nextInt(Integer.MAX_VALUE - 1)) % 100) / 100.0;
                    if (temp > v){
                        //选择错误
                        int tt = r.nextInt(N);
                        int vtt = visit[tt];
                        while (vtt == 1){
                            List<Integer> can = new ArrayList<>();
                            for(int ic = 0;ic < visit.length;ic++){
                                if (visit[ic] == 0){
                                    can.add(ic);
                                }
                            }
                            int i1 = can.size();
                            if (i1 == 0){
                                tt = -1;
                                break;
                            }else{
                                int t1 = r.nextInt(i1);
                                tt = can.get(t1);
                                vtt = visit[tt];
                            }
                        }
                        if (tt != -1){
                            visit[tt] = 1;
                            t_seq[i] = tt;
                        }
                    }else{
                        ans = -1;
                        mini = -1;
                        for(int j = 0;j <N;++j){
                            if (visit[j] == 0 && ans < mess[t_seq[i-1]][j]){
                                ans = mess[t_seq[i-1]][j];
                                mini = j;
                            }
                        }
                        if (mini != -1){
                            visit[mini] = 1;
                            t_seq[i] = mini;
                        }
                    }
                }
                if (countEnergy(t_seq)<best_length){
                    for (int i = 0;i < N;i++){
                        best_seq[i] = t_seq[i];
                    }
                    best_length = countEnergy(best_seq);
                }
            }
            double thislength = countEnergy(best_seq);
            if (thislength<length){
                for (int i = 0;i < N;i++){
                    seq[i] = best_seq[i];
                }
                length = countEnergy(seq);
            }
            System.out.println("第"+(T-t)+"次循环，结果为："+thislength+"，最优为："+length);
            for (int i = 0;i < N ;++i){
                for(int j = 0; j < N; ++j){
                    mess[i][j] *= u;
                    mess[i][j] += (1-u) * Q / best_length;
                }
            }
        }
    }

    @Override
    public double getLength() {
        return length;
    }

    @Override
    public Node[] getWay() {
        Node[] result = new Node[N];
        for(int i = 0;i < N; i ++){
            result[i] = nodes[seq[i]];
        }
        return result;
    }
}
