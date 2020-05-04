package algs;

import model.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Genetic implements AlgInterface {
    private int num;
    private int pop_size;
    private int[][] seq_pop;
    private double[] pop_energy;
    private double[] fitness;
    private double sum_fit;
    private double sum_energy;
    private double u,v;
    private Node[] nodes;
    private double[][] dic;
    private int N;
    private int[] way;
    private double length;
    private int times;
    private Random r;
    public Genetic(Node[] cities,double u,double v,int size,int times){
        this.nodes = cities;
        this.num = cities.length;
        this.N = num;
        this.u = u;
        this.v = v;
        this.pop_size = size;
        this.times = times;
        seq_pop = new int[pop_size][num];
        pop_energy = new double[pop_size];
        fitness = new double[pop_size];
        dic = new double[num][num];
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

    public int[] generate(int[] a){
        boolean[] v = new boolean[num];
        for(int i = 0;i < N;++i){
            int tt = r.nextInt(N);
            boolean vtt = v[tt];
            while (vtt){
                List<Integer> tmp = new ArrayList<>();
                for(int j = 0;j < v.length;j++){
                    if (!v[j]){
                        tmp.add(j);
                    }
                }
                int l1 = tmp.size();
                if (l1 == 0){
                    tt = -1;
                    break;
                }else{
                    int t1 = r.nextInt(l1);
                    tt = tmp.get(t1);
                    vtt = v[tt];
                }
            }
            if (tt != -1){
                v[tt] = true;
                a[i] = tt;
            }
        }
        return a;
    }

    public void set_fitness(){
        sum_fit = 0;
        fitness[0] = sum_energy / pop_energy[0];
        sum_fit += fitness[0];
        for(int i = 1;i < pop_size;++i){
            sum_fit += sum_energy/pop_energy[i];
            fitness[i] = sum_fit;
        }
        for(int i = 0;i < pop_size;++i){
            fitness[i] /= sum_fit;
        }
    }

    public int generate_pop(){
        double len = Double.MAX_VALUE;
        int maxi = -1;
        for (int i = 0;i < pop_size;++i){
            seq_pop[i] = generate(seq_pop[i]);
            pop_energy[i] = countEnergy(seq_pop[i]);
            sum_energy += pop_energy[i];
            if (len > pop_energy[i]){
                maxi = i;
                len = pop_energy[i];
            }
        }
        length = countEnergy(seq_pop[maxi]);
        return maxi;
    }

    public int cho(){
        double tmp = r.nextInt(1000) / 1000.0;
        for(int i = 0; i < pop_size; ++i){
            if (fitness[i] > tmp){
                return i;
            }
        }
        return 0;
    }

    public int choose(){
        int[][] t_seq = new int[pop_size][num];
        int t = 0;
        int maxi = -1;
        double len = Double.MAX_VALUE;
        sum_energy = 0;
        for(int i = 0;i < pop_size; ++i){
            t = cho();
            for (int j = 0 ; j < N ; ++j){
                t_seq[i][j] = seq_pop[t][j];
            }
            pop_energy[i] = countEnergy(t_seq[i]);
            if (len > pop_energy[i]){
                maxi = i;
                len = pop_energy[i];
            }
        }
        for(int i = 0;i < pop_size; ++i){
            for (int j =0 ;j < N ;++j){
                seq_pop[i][j] = t_seq[i][j];
            }
        }
        return maxi;
    }

    //解决序列冲突
    public int[] solve(int[] seq){
        boolean[] v = new boolean[num];
        for(int i = 0;i < N ;++i){
            if (seq[i] < 0 || seq[i] > N){
                return seq;
            }
            if (!v[seq[i]]){
                v[seq[i]] = true;
            }else{
                List<Integer> c = new ArrayList<>();
                for(int j = 0;j < N ;++ j){
                    if (!v[j]){
                        c.add(j);
                    }
                }
                if (c.size() != 0){
                    int tm = r.nextInt(c.size());
                    v[c.get(tm)] = true;
                    seq[i] = c.get(tm);
                }
            }
        }
        return seq;
    }

    public int recom(){
        int[][] t_seq = new int[2][num];
        double len = Double.MAX_VALUE;
        int maxi = -1;
        for (int i = 0; i < pop_size; i += 2){
            double tt = r.nextInt(1000) / 1000.0;
            if (tt > v)
                continue;
            int tmp = r.nextInt(N);
            for(int j = 0 ; j < N ; ++ j){
                if (j >= tmp && j <= N){
                    t_seq[0][j] = seq_pop[i + 1][j];
                    t_seq[1][j] = seq_pop[i][j];
                }else{
                    t_seq[0][j] = seq_pop[i][j];
                    t_seq[1][j] = seq_pop[i + 1][j];
                }
            }
            t_seq[0] = solve(t_seq[0]);
            t_seq[1] = solve(t_seq[1]);
            if (countEnergy(t_seq[0]) < pop_energy[i]){
                for (int j = 0; j < N; ++j) {
                    seq_pop[i][j] = t_seq[0][j];
                }
            }
            if (countEnergy(t_seq[1]) < pop_energy[i+1]){
                for (int j = 0; j < N; ++j) {
                    seq_pop[i+1][j] = t_seq[1][j];
                }
            }
        }
        sum_energy = 0;
        for (int i = 0; i < pop_size; ++i) {
            pop_energy[i] = countEnergy(seq_pop[i]);
            sum_energy += pop_energy[i];
            if (len > pop_energy[i]) {
                len = pop_energy[i];
                maxi = i;
            }
        }
        set_fitness();
        return maxi;
    }

    public int vari(){
        int maxi = -1;
        double len = Double.MAX_VALUE,tmp = 0.0;
        int t1 = 0 ,t2 = 0;
        for(int i = 0;i < N ; ++ i){
            tmp = r.nextInt(1000) / 1000.0;
            if (tmp < u ){
                t1 = r.nextInt(N);
                t2 = r.nextInt(N);
                int st = seq_pop[i][t1];
                seq_pop[i][t1] = seq_pop[i][t2];
                seq_pop[i][t2] = st;
                pop_energy[i] = countEnergy(seq_pop[i]);
            }
        }
        sum_energy = 0;
        for (int i = 0; i < pop_size; ++i) {
            sum_energy += pop_energy[i];
            if (len > pop_energy[i]) {
                len = pop_energy[i];
                maxi = i;
            }
        }
        set_fitness();
        return maxi;
    }

    @Override
    public void Cal(){
        int ts = times;
        int maxi = -1,t = -2;
        double len = Double.MAX_VALUE;
        t = generate_pop();
        if (t >= 0 && len >= pop_energy[t]){
            len = pop_energy[t];
            maxi = t;
        }
        set_fitness();
        while(ts -- != 0){
            sum_fit = 0;
            sum_energy = 0;
            if (length > len){
                length = len;
                way = seq_pop[maxi];
            }
            t = choose();
            if (t >= 0 && len > pop_energy[t]){
                len = pop_energy[t];
                maxi = t;
            }
            t = recom();
            if (t >= 0 && len > pop_energy[t]){
                len = pop_energy[t];
                maxi = t;
            }
            t = vari();
            if (t >= 0 && len > pop_energy[t]){
                len = pop_energy[t];
                maxi = t;
            }
            System.out.println("第"+(times-ts)+"次循环，结果为："+len+"，最优为："+length);
        }
        if (length > len){
            length = len;
            way = seq_pop[maxi];
        }
    }

    @Override
    public double getLength() {
        return length;
    }

    @Override
    public Node[] getWay() {
        Node[] result = new Node[way.length];
        for(int i = 0;i < way.length; i ++){
            result[i] = nodes[way[i]];
        }
        return result;
    }

}
