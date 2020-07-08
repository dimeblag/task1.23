import java.util.Comparator;

public class PointComporator implements Comparator<Point> {

    public int compare(Point p1, Point p2) {
        if (p1.distanceToOrigin() - p2.distanceToOrigin() < 0) return -1;
        else if (p1.distanceToOrigin() - p2.distanceToOrigin() == 0) return 0;
        return 1;
    }
}
