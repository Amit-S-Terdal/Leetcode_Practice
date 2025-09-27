// 812. Largest Triangle Area

// Given an array of points on the X-Y plane points where points[i] = [xi, yi], return the area of the largest triangle that can be formed by any three different points. Answers within 10-5 of the actual answer will be accepted.

 

// Example 1:


// Input: points = [[0,0],[0,1],[1,0],[0,2],[2,0]]
// Output: 2.00000
// Explanation: The five points are shown in the above figure. The red triangle is the largest.
// Example 2:

// Input: points = [[1,0],[0,0],[0,1]]
// Output: 0.50000
 

// Constraints:

// 3 <= points.length <= 50
// -50 <= xi, yi <= 50
// All the given points are unique.



// Solution:


import java.util.*;

class Solution {
    private static class Point implements Comparable<Point> {
        short x, y;
        Point(int x, int y) {
            this.x = (short)x;
            this.y = (short)y;
        }

        @Override
        public int compareTo(Point other) {
            if (this.x != other.x)
                return this.x - other.x;
            return this.y - other.y;
        }
    }

    private static int cross(Point O, Point A, Point B) {
        return (A.x - O.x) * (B.y - O.y) - (A.y - O.y) * (B.x - O.x);
    }

    private static double maxArea(List<Point> points) {
        double maxArea = 0;
        int n = points.size();
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    double area = Math.abs(cross(points.get(i), points.get(j), points.get(k)));
                    maxArea = Math.max(maxArea, area);
                }
            }
        }
        return maxArea * 0.5;
    }

    public double largestTriangleArea(int[][] points) {
        int n = points.length;
        List<Point> P = new ArrayList<>();
        for (int[] point : points) {
            P.add(new Point(point[0], point[1]));
        }

        Collections.sort(P);

        List<Point> hull = new ArrayList<>();
        
        // Lower hull
        for (Point p : P) {
            while (hull.size() >= 2 && cross(hull.get(hull.size() - 2), hull.get(hull.size() - 1), p) <= 0) {
                hull.remove(hull.size() - 1);
            }
            hull.add(p);
        }

        // Upper hull
        int t = hull.size() + 1;
        for (int i = P.size() - 1; i >= 0; i--) {
            Point p = P.get(i);
            while (hull.size() >= t && cross(hull.get(hull.size() - 2), hull.get(hull.size() - 1), p) <= 0) {
                hull.remove(hull.size() - 1);
            }
            hull.add(p);
        }

        // Remove duplicate start point
        hull.remove(hull.size() - 1);

        return maxArea(hull);
    }
}
