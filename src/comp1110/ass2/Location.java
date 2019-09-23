package comp1110.ass2;

public class Location {
    private int X;
    private int Y;
    public Location(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }
    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public boolean equals(Location location){
       return this.X == location.X && this.Y == location.Y;
    }

    public static void main(String[] args) {
        Location l1 = new Location(1,2);
        Location l2 = new Location(1,2);
        System.out.println(l1.equals(l2));
    }

    @Override
    public String toString() {
        return "(" + this.getX() + "," + this.getY() +")";
    }

}
