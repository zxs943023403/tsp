package model;

public class Node {
    public int x;
    public int y;

    public double dicToNode (Node n){
        return Math.sqrt(Math.pow(this.getX()-n.getX(),2)+Math.pow(this.getY() - n.getY(),2));
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
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
