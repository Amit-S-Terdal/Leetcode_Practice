// 85. Maximal Rectangle

// Given a rows x cols binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.

 

// Example 1:


// Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
// Output: 6
// Explanation: The maximal rectangle is shown in the above picture.
// Example 2:

// Input: matrix = [["0"]]
// Output: 0
// Example 3:

// Input: matrix = [["1"]]
// Output: 1
 

// Constraints:

// rows == matrix.length
// cols == matrix[i].length
// 1 <= rows, cols <= 200
// matrix[i][j] is '0' or '1'.


// Solution: 



short st[201]={-1}, top=0;
class Solution {
public:
    static int maximalRectangle(vector<vector<char>>& matrix) {
        const unsigned short row=matrix.size(), col=matrix[0].size();
        if (row==1 && col==1) return matrix[0][0]=='1';
        unsigned short h[201]={0};//height 
        int maxArea=0;

        for(int i=0; i<row; i++){
            top=0;//stack will not be empty & st[0]=-1
            for (int j=0; j<=col; j++){
                // Count the successive '1's & store in h[j]
                h[j]=(j==col||matrix[i][j]=='0')?0:h[j]+1;

                // monotonic stack has at least element -1
                while(top>0 && (j==col||h[j]<h[st[top]])){
                    const int m=st[top--];//pop
                    const int w=j-st[top]-1;
                    const int area=h[m]*w;
                    maxArea=max(maxArea, area);
                }
                st[++top]=j;//push
            }
        }
        return maxArea;
    }
};
auto init = []() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    return 'c';
}();