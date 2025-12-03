// 3625. Count Number of Trapezoids II

// You are given a 2D integer array points where points[i] = [xi, yi] represents the coordinates of the ith point on the Cartesian plane.

// Return the number of unique trapezoids that can be formed by choosing any four distinct points from points.

// A trapezoid is a convex quadrilateral with at least one pair of parallel sides. Two lines are parallel if and only if they have the same slope.

 

// Example 1:

// Input: points = [[-3,2],[3,0],[2,3],[3,2],[2,-3]]

// Output: 2

// Explanation:



// There are two distinct ways to pick four points that form a trapezoid:

// The points [-3,2], [2,3], [3,2], [2,-3] form one trapezoid.
// The points [2,3], [3,2], [3,0], [2,-3] form another trapezoid.
// Example 2:

// Input: points = [[0,0],[1,0],[0,1],[2,1]]

// Output: 1

// Explanation:



// There is only one trapezoid which can be formed.

 

// Constraints:

// 4 <= points.length <= 500
// –1000 <= xi, yi <= 1000
// All points are pairwise distinct.



// Solution:



import java.util.*;

class Solution {
    static class Int2 implements Comparable<Int2> {
        int a, b;
        Int2(int a, int b) { this.a = a; this.b = b; }

        @Override
        public int compareTo(Int2 o) {
            if (a != o.a) return Integer.compare(a, o.a);
            return Integer.compare(b, o.b);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Int2)) return false;
            Int2 x = (Int2)o;
            return a == x.a && b == x.b;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }
    }

    static class Int4 implements Comparable<Int4> {
        Int2 first, second;
        Int4(Int2 a, Int2 b) { first = a; second = b; }

        @Override
        public int compareTo(Int4 o) {
            int c = first.compareTo(o.first);
            if (c != 0) return c;
            return second.compareTo(o.second);
        }
    }

    private static int gcd(int a, int b) {
        a = Math.abs(a); b = Math.abs(b);
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }

    // same formula as original C++ cntShapes
    private static int cntShapes(int total, List<Integer> group) {
        int sum = 0;
        for (int x : group) sum += x * (total - x);
        return sum;
    }

    public int countTrapezoids(int[][] points) {
        int n = points.length;
        int n2 = n * (n - 1) / 2;

        Int4[] slope_inter = new Int4[n2];
        Int4[] midPt_slope = new Int4[n2];

        // Build pair arrays
        int k = 0;
        for (int i = 0; i < n - 1; i++) {
            int x0 = points[i][0], y0 = points[i][1];
            for (int j = i + 1; j < n; j++, k++) {
                int x1 = points[j][0], y1 = points[j][1];

                int a = y1 - y0;
                int b = x0 - x1;
                int c = y0 * x1 - y1 * x0;

                // normalize sign like the C++ code
                if (a == 0 && b < 0) {
                    b = -b; c = -c;
                } else if (a < 0) {
                    a = -a; b = -b; c = -c;
                }

                int gm = gcd(a, b);
                int gc = gcd(gm, c);

                Int2 slope = new Int2(a / gm, b / gm);
                Int2 inter = new Int2(c / gc, gm / gc);
                Int2 mid = new Int2(x0 + x1, y0 + y1);

                slope_inter[k] = new Int4(slope, inter);
                midPt_slope[k] = new Int4(mid, slope);
            }
        }

        Arrays.sort(slope_inter);
        Arrays.sort(midPt_slope);

        long cnt = 0;

        // -------- slope/intercept counting (add)
        {
            List<Integer> B = new ArrayList<>();

            Int2 slope = slope_inter[0].first;
            Int2 inter = slope_inter[0].second;

            int sameM = 1, sameB = 1;

            for (int i = 1; i < n2; i++) {
                Int4 cur = slope_inter[i];
                if (cur.first.equals(slope)) {
                    sameM++;
                    if (cur.second.equals(inter)) {
                        sameB++;
                    } else {
                        B.add(sameB);
                        sameB = 1;
                        inter = cur.second;
                    }
                } else {
                    B.add(sameB);
                    cnt += cntShapes(sameM, B) / 2;

                    B.clear();
                    slope = cur.first;
                    inter = cur.second;
                    sameM = sameB = 1;
                }
            }

            B.add(sameB);
            cnt += cntShapes(sameM, B) / 2;
        }

        // -------- midpoint / slope counting (subtract)
        {
            List<Integer> L = new ArrayList<>();

            Int2 mid = midPt_slope[0].first;
            Int2 slope = midPt_slope[0].second;

            int sameMid = 1, sameM = 1;

            for (int i = 1; i < n2; i++) {
                Int4 cur = midPt_slope[i];

                if (cur.first.equals(mid)) {
                    sameMid++;
                    if (cur.second.equals(slope)) {
                        sameM++;
                    } else {
                        L.add(sameM);
                        sameM = 1;
                        slope = cur.second;
                    }
                } else {
                    L.add(sameM);
                    cnt -= cntShapes(sameMid, L) / 2;

                    L.clear();
                    mid = cur.first;
                    slope = cur.second;
                    sameMid = sameM = 1;
                }
            }

            L.add(sameM);
            cnt -= cntShapes(sameMid, L) / 2;
        }

        return (int)cnt;
    }
}
