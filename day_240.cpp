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


#include <utility>
#include <vector>
#include <algorithm>
using std::vector;
using std::pair;

class Solution {
public:
    using int2 = std::pair<short, short>;

    static int cross(const int2& O, const int2& P, const int2& Q){
        const auto& [a0, a1]=O; 
        const auto& [b0, b1]=P; 
        const auto& [c0, c1]=Q; 
        return (b0-a0)*(c1-a1)-(b1-a1)*(c0-a0);
    }

    static double maxArea(const vector<int2>& P, int n){
        double maxA=0;
        for(int i=0; i<n-2; i++){
            for(int j=i+1; j<n-1; j++){
                for(int k=j+1; k<n; k++){
                    double area=abs(cross(P[i],P[j],P[k]));
                    maxA=std::max(maxA, area);
                }
            }
        }
        return 0.5*maxA;
    }

    static double largestTriangleArea(vector<vector<int>>& points) {
        const int n=points.size();
        vector<int2> P(n);
        for(int i=0; i<n; i++) P[i]={points[i][0], points[i][1]};
        sort(P.begin(), P.end());

        vector<int2> Hull(2*n);
        int k=0;
        // lower hull
        for(int i=0; i<n; i++){
            while(k>=2 && cross(Hull[k-2], Hull[k-1], P[i])<=0)
                k--;
            Hull[k++]=P[i];
        }
        // upper hull
        for(int i=n-1, t=k+1; i>=0; i--){
            while(k>=t && cross(Hull[k-2], Hull[k-1], P[i])<=0) 
                k--;
            Hull[k++]=P[i];
        }
        // remove duplicate start point
        return maxArea(Hull, k-1);
    }
};