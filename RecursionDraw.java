
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RecursionDraw {
   public static void main(String[] args) {

       Draw blankCanvas = new Draw("test");
       blankCanvas.point(0.7, 0.7);
       blankCanvas.circle(0.5, 0.5, 0.5);
       blankCanvas.square(0.5, 0.5, 0.4);
       blankCanvas.setPenColor(new Color(150, 150, 150));
       blankCanvas.line(0.0, 0.5, 1, 0.5);

   }

    public static String nestedCircle(double x, double y, double radius, double diff, Draw page, String radiusList) {

        if (radius + diff <= 0.0000) {
            return "0.0]";
        } else {
            // subtract the diff for getting the radius of next inner circle.
            double new_radius = radius - diff;

            // cut the radius to 2 decimal points.
            new_radius = (int) (new_radius * 100) / 100.00;

            // if it is the first call, add the [ in the beginning
            if (radiusList == "") {
                page.circle(x, y, radius);
                return nestedCircle(x, y, new_radius, diff, page, "[" + radius + ", ");
            }

            // if not the first time, just add the inner circle radius to the current
            // radiusList
            else {
                page.circle(x, y, radius);
                return radiusList + nestedCircle(x, y, new_radius, diff, page, radius + ", ");
            }

        }

    }


    public static String squares(double x, double y, double halfLength, int order, Draw page) {

        // base case, return the x and y of the base square.
        // we are subtracting order as we get to the base case.
        // and we're cutting the x and y to one decimal point.
        if (order == 1) {

            x = (int) (x * 100) / 100.0;
            y = (int) (y * 100) / 100.0;
            return "[" + (x) + ", " + (y) + "]";
        }

        else {
            // calculate 4 corners of the square in respect to the center of the square.
            // and return 4 new squares drawn on these 4 corners.

            double upRight_x = x + halfLength;
            double upRight_y = y + halfLength;

            double downRight_x = x + halfLength;
            double downRight_y = y - halfLength;

            double upLeft_x = x - halfLength;
            double upLeft_y = y + halfLength;

            double downLeft_x = x - halfLength;
            double downLeft_y = y - halfLength;

            // return 4 outer squares recursively.
            return squares(upRight_x, upRight_y, halfLength / 2, order - 1, page)
                    + squares(downRight_x, downRight_y, halfLength / 2, order - 1, page)
                    + squares(upLeft_x, upLeft_y, halfLength / 2, order - 1, page)
                    + squares(downLeft_x, downLeft_y, halfLength / 2, order - 1, page);

        }

    }

    public static ArrayList<String> drillPoints(Point p1, Point p2, Point p3, int order, Draw page,
            ArrayList<String> array) {

        // if zero nothing should be drawn
        if (order == 0) {
            ArrayList<String> baseList = new ArrayList<String>();
            return baseList;
        }

        // base case, just add the points from arguments and return. without any middle points.
        if (order == 1) {
            ArrayList<String> baseList = new ArrayList<String>();

            baseList.add(p1.toString());
            baseList.add(p2.toString());
            baseList.add(p3.toString());
            return baseList;
        }

        else {

            // find the middle points, on vertices of triangle
            double middle1_x = (p1.x + p2.x) / 2;
            double middle1_y = (p1.y + p2.y) / 2;

            double middle2_x = (p2.x + p3.x) / 2;
            double middle2_y = (p2.y + p3.y) / 2;

            double middle3_x = (p3.x + p1.x) / 2;
            double middle3_y = (p3.y + p1.y) / 2;

            Point new_p1 = new Point(middle1_x, middle1_y);
            Point new_p2 = new Point(middle2_x, middle2_y);
            Point new_p3 = new Point(middle3_x, middle3_y);
            
            page.line(middle2_x, middle2_y, middle3_x, middle3_y);

            // if first call of the function in the recursion chain
            if (array.size() == 0) {
                ArrayList<String> newList = new ArrayList<String>();
                newList.add(p1.toString());
                newList.add(p2.toString());
                newList.add(p3.toString());
                newList.addAll(drillPoints(new_p1, new_p2, new_p3, order - 1, page, array));
                return newList;

            }

            // if it's not the first call of the function in the recursion chain
            else {

                // draw the horizontal line
                ArrayList<String> newList = new ArrayList<String>();
                newList.addAll(array);
                newList.addAll(drillPoints(new_p1, new_p2, new_p3, order - 1, page, array));
                return newList;
            }

        }

    }

}


class Point {
    double x;
    double y;

    public Point(double x, double y) {
    	// assigning the value of arguments to class variables.
        this.x = x;
        this.y = y;
    }

    public static Point midpoint(Point p1, Point p2) {
        DecimalFormat df = new DecimalFormat("#.##");
        double middle_x = Double.parseDouble(df.format((p1.x + p2.x) / 2));
        double middle_y = Double.parseDouble(df.format((p1.y + p2.y) / 2));
        return new Point(middle_x, middle_y);
    }

    @Override
    public String toString() {
        return "[" + this.x + ", " + this.y + "]";
    }

}
