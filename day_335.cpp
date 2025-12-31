// 1970. Last Day Where You Can Still Cross

// There is a 1-based binary matrix where 0 represents land and 1 represents water. You are given integers row and col representing the number of rows and columns in the matrix, respectively.

// Initially on day 0, the entire matrix is land. However, each day a new cell becomes flooded with water. You are given a 1-based 2D array cells, where cells[i] = [ri, ci] represents that on the ith day, the cell on the rith row and cith column (1-based coordinates) will be covered with water (i.e., changed to 1).

// You want to find the last day that it is possible to walk from the top to the bottom by only walking on land cells. You can start from any cell in the top row and end at any cell in the bottom row. You can only travel in the four cardinal directions (left, right, up, and down).

// Return the last day where it is possible to walk from the top to the bottom by only walking on land cells.

 

// Example 1:


// Input: row = 2, col = 2, cells = [[1,1],[2,1],[1,2],[2,2]]
// Output: 2
// Explanation: The above image depicts how the matrix changes each day starting from day 0.
// The last day where it is possible to cross from top to bottom is on day 2.
// Example 2:


// Input: row = 2, col = 2, cells = [[1,1],[1,2],[2,1],[2,2]]
// Output: 1
// Explanation: The above image depicts how the matrix changes each day starting from day 0.
// The last day where it is possible to cross from top to bottom is on day 1.
// Example 3:


// Input: row = 3, col = 3, cells = [[1,2],[2,1],[3,3],[2,2],[1,1],[1,3],[2,3],[3,2],[3,1]]
// Output: 3
// Explanation: The above image depicts how the matrix changes each day starting from day 0.
// The last day where it is possible to cross from top to bottom is on day 3.
 

// Constraints:

// 2 <= row, col <= 2 * 10^4
// 4 <= row * col <= 2 * 10^4
// cells.length == row * col
// 1 <= ri <= row
// 1 <= ci <= col
// All the values of cells are unique.



// Solution: 



class Solution {
public:
    using int3 = tuple<int, int, int>;
    using int2 = pair<int, int>;
    // 1-indexed
    static unsigned key(int i, int j, int col){ return (i-1)*col+(j-1); }

    static int latestDayToCross(int row, int col, vector<vector<int>>& cells) {
        int N=cells.size();
        vector<int3> water(N);
        vector<vector<int>> c2idx(row, vector(col, -1));
        vector<int> water1;
        
        // Build water and find indexes for the cell=(i, j) with j=1 storing to water1
        for (int r = 0; r < N; r++) {
            int i = cells[r][0], j = cells[r][1];
            water[r] = {r, i, j};
            c2idx[i-1][j-1] = r;
        //    cout<<"r="<<r<<" i="<<i<<" j="<<j<<endl;
            if (j == 1) water1.push_back(r);
        }
        //cout<<water1.size()<<endl;
        
        priority_queue<int3, vector<int3>, greater<int3>> pq;
        bitset<20000> visited=0;
        
        for (int idx: water1){
        	int i=cells[idx][0], j=cells[idx][1];
        	int3 w={idx, i, j};
        	pq.push(w);
        	visited[key(i, j, col)]=1;
		}
        
        int max_d=INT_MIN;
        while (!pq.empty()) {
            auto [d, i, j] = pq.top();
            max_d=max(max_d, d);
        //    cout<<d<<","<<i<<","<<j<<endl;
            if (j==col) {
        //    	cout<<"done ";
        //   	cout<<d<<","<<i<<","<<j<<endl;
                return max_d;
            }
            pq.pop();
            
            int2 adj[8]={{i + 1, j - 1}, {i + 1, j}, {i + 1, j + 1}, 
            {i, j + 1},{i - 1, j + 1}, {i - 1, j}, {i - 1, j - 1}, {i, j - 1}};
            
            for (const auto& [a, b] : adj) {
                unsigned idx=key(a, b, col);
                if (a <= 0 || a > row || b <= 0 || b > col || 
				visited[idx]!= 0 ) {
                    continue;
                }
                visited[idx]=1;
                pq.push({c2idx[a-1][b-1], a, b});
            }
        }       
        return -1;
    }
};
auto init = []() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    return 'c';
}();