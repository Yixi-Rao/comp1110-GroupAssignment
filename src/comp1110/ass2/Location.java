package comp1110.ass2;

/*
authorship details:  written by the group
 */

/**
 * A class that store the in formation of the Location which is consists of x and y coordinates
 */
public class Location {
    private int X; //X identifies the column where the left-most square of the piece is in
    private int Y; // Y identifies the row where the top square of th piece is in
    public Location(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }
    public int getX() {
        return X;
    } //get the X of the piece

    public int getY() {
        return Y;
    }//get the Y of the piece

    /**
     * Override the equal method and if two locations are equivalent then they x and y must be the same
     * @param location the Location of the point will be compared
     * @return true if they are equivalent
     */
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
    }//From piece's location to String

}
