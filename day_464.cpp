// 1914. Cyclically Rotating a Grid

// You are given an m x n integer matrix grid​​​, where m and n are both even integers, and an integer k.

// The matrix is composed of several layers, which is shown in the below image, where each color is its own layer:



// A cyclic rotation of the matrix is done by cyclically rotating each layer in the matrix. To cyclically rotate a layer once, each element in the layer will take the place of the adjacent element in the counter-clockwise direction. An example rotation is shown below:


// Return the matrix after applying k cyclic rotations to it.

 

// Example 1:


// Input: grid = [[40,10],[30,20]], k = 1
// Output: [[10,20],[40,30]]
// Explanation: The figures above represent the grid at every state.
// Example 2:


// Input: grid = [[1,2,3,4],[5,6,7,8],[9,10,11,12],[13,14,15,16]], k = 2
// Output: [[3,4,8,12],[2,11,10,16],[1,7,6,15],[5,9,13,14]]
// Explanation: The figures above represent the grid at every state.
 

// Constraints:

// m == grid.length
// n == grid[i].length
// 2 <= m, n <= 50
// Both m and n are even integers.
// 1 <= grid[i][j] <= 5000
// 1 <= k <= 10^9




// Solution: 



int layer[196]; 

class Solution {
public:
    static inline void inc(int& id, int p){ id=(id==p-1)?0:id+1; }
    static vector<vector<int>> rotateGrid(vector<vector<int>>& grid, int k) {
        const int r=grid.size(), c=grid[0].size(), L=min(r, c)/2;
        
        for(int d=0; d<L; d++){
            const int si=d, sj=d, ei=r-d-1, ej=c-d-1;
            const int h=r-2*d, w=c-2*d; 
            const int p=(h+w-2)*2; // Perimeter for this layer
            
            int id=0;
            // Extract the layer elements
            for(int j=0; j<w-1; j++) 
                layer[id++]=grid[si][sj+j];
            for(int i=0; i<h-1; i++) 
                layer[id++]=grid[si+i][ej];
            for(int j=0; j<w-1; j++) 
                layer[id++]=grid[ei][ej-j];
            for(int i=0; i<h-1; i++) 
                layer[id++]=grid[ei-i][sj];

            // Rotate
            id=k%p;

            for(int j=0; j<w-1; j++) 
                grid[si][sj+j]=layer[id], inc(id, p);
            for(int i=0; i<h-1; i++) 
                grid[si+i][ej]=layer[id], inc(id, p);
            for(int j=0; j<w-1; j++) 
                grid[ei][ej-j]=layer[id], inc(id, p);
            for(int i=0; i<h-1; i++) 
                grid[ei-i][sj]=layer[id], inc(id, p);
        }
        return grid;
    }
};

auto init = []() {
    ios::sync_with_stdio(0);
    cin.tie(0);
    cout.tie(0);
    return 'c';
}();