// 1301. Number of Paths with Max Score

// You are given a square board of characters. You can move on the board starting at the bottom right square marked with the character 'S'.

// You need to reach the top left square marked with the character 'E'. The rest of the squares are labeled either with a numeric character 1, 2, ..., 9 or with an obstacle 'X'. In one move you can go up, left or up-left (diagonally) only if there is no obstacle there.

// Return a list of two integers: the first integer is the maximum sum of numeric characters you can collect, and the second is the number of such paths that you can take to get that maximum sum, taken modulo 10^9 + 7.

// In case there is no path, return [0, 0].

 

// Example 1:

// Input: board = ["E23","2X2","12S"]
// Output: [7,1]
// Example 2:

// Input: board = ["E12","1X1","21S"]
// Output: [4,2]
// Example 3:

// Input: board = ["E11","XXX","11S"]
// Output: [0,0]
 

// Constraints:

// 2 <= board.length == board[i].length <= 100




// Solution: 




// reduced space
constexpr int mod=1e9+7, N=100;
using ll=long long;
using int2=pair<int, int>;// (max sum, ways getting max sum)
constexpr int2 None={0, -1};
int2 dp[2][N];
class Solution {
public:
    static vector<int> pathsWithMaxScore(vector<string>& board) {
        const int n=board.size();
        bool last=(n-1)&1;
        fill_n(dp[last], n, None);// fill with {0, -1}
        dp[last][n-1]={0, 1};
        // last row
        for(int j=n-2; j>=0; j--){
            const char c=board[n-1][j];
            if (c=='X') break;
            dp[last][j].first=dp[last][j+1].first+c-'0';
            dp[last][j].second=1;
        }
        board[0][0]='0';
        for(int i=n-2; i>=0; i--){
            const char cR=board[i][n-1];
            bool cur=i&1, prv=(i+1)&1;
            if (cR=='X' || dp[prv][n-1].second==-1) 
                dp[cur][n-1]=None;
            else 
                dp[cur][n-1]={dp[prv][n-1].first+cR-'0', 1};
            for(int j=n-2; j>=0; j--){
                const int c=board[i][j];
                if (c=='X'){
                    dp[cur][j]=None;
                    continue;
                }
                auto& [r0, r1]=dp[cur][j+1];
                auto& [d0, d1]=dp[prv][j];
                auto& [s0, s1]=dp[prv][j+1];
                int prvM=-1;
                if (r1>0) prvM=max(prvM, r0);
                if (d1>0) prvM=max(prvM, d0);
                if (s1>0) prvM=max(prvM, s0);
                if (prvM==-1) {
                    dp[cur][j]=None; 
                    continue;
                }
                ll ways=0;
                ways+=(r1>0 && prvM==r0)?r1:0;
                ways+=(d1>0 && prvM==d0)?d1:0;
                ways+=(s1>0 && prvM==s0)?s1:0;
                dp[cur][j]={prvM+c-'0',  ways%mod};
            }
        }
        auto& [sum, ways]=dp[0][0];
        return (ways<=0)?vector<int>({0, 0}):vector<int>({sum, ways});
    }
};
auto init = []() {
    ios::sync_with_stdio(0);
    cin.tie(0);
    cout.tie(0);
    return 'c';
}();