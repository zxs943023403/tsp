package algs;

import model.Node;

import java.util.Random;

public class Fire implements AlgInterface {
    private Node[] nodes;
    private double[][] dic;
    private int N;
    private int[] seq;
    private double length;
    private int temper;
    private double u,v;
    private int times;
    private Random r;
    public Fire(Node[] nodes,double u,double v,int temper,int times){
        this.length = Double.MAX_VALUE;
        this.nodes = nodes;
        this.u = u;
        this.v = v;
        this.temper = temper;
        this.N = nodes.length;
        seq = new int[this.N];
        dic = new double[this.N][this.N];
        this.times = times;
        r = new Random();
        for (int i = 0;i < N ;++i){
            for(int j = 0; j < N; ++j){
                dic[i][j] = nodes[i].dicToNode(nodes[j]);
            }
        }
    }
    public double countEnergy(int[] is){
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
    public boolean metro(double f1,double f2,double t){
        if (f2 < f1){
            return true;
        }
        double p = Math.exp((-(f2-f1)/t));
        int big = Integer.MAX_VALUE;
        if (r.nextInt(big) < p * big){
            return true;
        }
        return false;
    }
    public int[] generate(int[] a){
        int t = Math.abs(r.nextInt()%3);
        int[] i_tmp = new int[(t+1)*2 ];
        for(int i = 0;i < (t+1)*2 ;i++){
            int index = r.nextInt(N);
            boolean redo = false;
            for (int j = 0;j < (t+1)*2 ;j++){
                if (index == i_tmp[j]){
                    redo = true;
                    break;
                }
            }
            if (redo){
                i -= 1;
            }else {
                i_tmp[i] = index;
            }
        }
        for(int i = 0 ; i < i_tmp.length / 2;i ++){
            int tmpa = a[i_tmp[i]];
            a[i_tmp[i]] = a[i_tmp[i_tmp.length - 1 - i]];
            a[i_tmp[i_tmp.length - 1 - i]] = tmpa;
        }
        return a;
    }

    @Override
    public void Cal() {
        double t = temper;
        int[] t_seq = new int[N];
        for(int i = 0; i < N ;++i){
            seq[i] = i;
            t_seq[i] = i;
        }
        double olde = 0.0,newe = 1.0;
        while (t > 0 && Math.abs(newe - olde) > 0){
            int t_k = times;
            while (t_k -- != 0 && Math.abs(newe - olde) > 0){
                int[] seq_tt = new int[N];
                for(int i = 0;i < seq.length;i++){
                    seq_tt[i] = seq[i];
                }
                seq_tt = generate(seq_tt);
                olde = countEnergy(t_seq);
                newe = countEnergy(seq_tt);
                System.out.print("ole:"+olde+",new:"+newe);
                if (metro(olde,newe,t)){
                    System.out.println(",change");
                    for (int i = 0; i < N; ++i)
                        t_seq[i] = seq_tt[i];
                }else{
                    System.out.println(",not change");
                }
            }
            newe = countEnergy(t_seq);
            olde = length;
          if (metro(olde,newe,t)){
                for (int i = 0; i < N; ++i){
                    seq[i] = t_seq[i];
                }
                length = countEnergy(seq);
                t *= u;
            }else{
                t *= v;
            }
        }
        length = countEnergy(seq);
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
