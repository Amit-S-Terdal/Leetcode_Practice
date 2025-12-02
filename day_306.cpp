// 3623. Count Number of Trapezoids I

// You are given a 2D integer array points, where points[i] = [xi, yi] represents the coordinates of the ith point on the Cartesian plane.

// A horizontal trapezoid is a convex quadrilateral with at least one pair of horizontal sides (i.e. parallel to the x-axis). Two lines are parallel if and only if they have the same slope.

// Return the number of unique horizontal trapezoids that can be formed by choosing any four distinct points from points.

// Since the answer may be very large, return it modulo 109 + 7.

 

// Example 1:

// Input: points = [[1,0],[2,0],[3,0],[2,2],[3,2]]

// Output: 3

// Explanation:



// There are three distinct ways to pick four points that form a horizontal trapezoid:

// Using points [1,0], [2,0], [3,2], and [2,2].
// Using points [2,0], [3,0], [3,2], and [2,2].
// Using points [1,0], [3,0], [3,2], and [2,2].
// Example 2:

// Input: points = [[0,0],[1,0],[0,1],[2,1]]

// Output: 1

// Explanation:



// There is only one horizontal trapezoid that can be formed.

 

// Constraints:

// 4 <= points.length <= 10^5
// –10^8 <= xi, yi <= 10^8
// All points are pairwise distinct.



// Solution: 



class Solution {
public:
    static int countTrapezoids(vector<vector<int>>& points) {
        const long long mod=1e9+7, n=points.size();
        int* y=(int*)alloca(n*sizeof(int));
        int i=0;
        for(auto& p: points) y[i++]=p[1];
        sort(y, y+n);
        long long sum=0, c2=0, c=0;
        int f=1, prev=y[0];
        for(int i=1; i<n; i++){
            if (prev==y[i]) f++;
            else{
                c=f*(f-1LL)/2LL;
                sum+=c;
                c2+=c*c;
                f=1;
                prev=y[i];
            }
        }
        c=f*(f-1LL)/2LL;
        sum+=c;
        c2+=c*c;
        return (sum*sum-c2)/2%mod;

    }
};