// 2977. Minimum Cost to Convert String II

// You are given two 0-indexed strings source and target, both of length n and consisting of lowercase English characters. You are also given two 0-indexed string arrays original and changed, and an integer array cost, where cost[i] represents the cost of converting the string original[i] to the string changed[i].

// You start with the string source. In one operation, you can pick a substring x from the string, and change it to y at a cost of z if there exists any index j such that cost[j] == z, original[j] == x, and changed[j] == y. You are allowed to do any number of operations, but any pair of operations must satisfy either of these two conditions:

// The substrings picked in the operations are source[a..b] and source[c..d] with either b < c or d < a. In other words, the indices picked in both operations are disjoint.
// The substrings picked in the operations are source[a..b] and source[c..d] with a == c and b == d. In other words, the indices picked in both operations are identical.
// Return the minimum cost to convert the string source to the string target using any number of operations. If it is impossible to convert source to target, return -1.

// Note that there may exist indices i, j such that original[j] == original[i] and changed[j] == changed[i].

 

// Example 1:

// Input: source = "abcd", target = "acbe", original = ["a","b","c","c","e","d"], changed = ["b","c","b","e","b","e"], cost = [2,5,5,1,2,20]
// Output: 28
// Explanation: To convert "abcd" to "acbe", do the following operations:
// - Change substring source[1..1] from "b" to "c" at a cost of 5.
// - Change substring source[2..2] from "c" to "e" at a cost of 1.
// - Change substring source[2..2] from "e" to "b" at a cost of 2.
// - Change substring source[3..3] from "d" to "e" at a cost of 20.
// The total cost incurred is 5 + 1 + 2 + 20 = 28. 
// It can be shown that this is the minimum possible cost.
// Example 2:

// Input: source = "abcdefgh", target = "acdeeghh", original = ["bcd","fgh","thh"], changed = ["cde","thh","ghh"], cost = [1,3,5]
// Output: 9
// Explanation: To convert "abcdefgh" to "acdeeghh", do the following operations:
// - Change substring source[1..3] from "bcd" to "cde" at a cost of 1.
// - Change substring source[5..7] from "fgh" to "thh" at a cost of 3. We can do this operation because indices [5,7] are disjoint with indices picked in the first operation.
// - Change substring source[5..7] from "thh" to "ghh" at a cost of 5. We can do this operation because indices [5,7] are disjoint with indices picked in the first operation, and identical with indices picked in the second operation.
// The total cost incurred is 1 + 3 + 5 = 9.
// It can be shown that this is the minimum possible cost.
// Example 3:

// Input: source = "abcdefgh", target = "addddddd", original = ["bcd","defgh"], changed = ["ddd","ddddd"], cost = [100,1578]
// Output: -1
// Explanation: It is impossible to convert "abcdefgh" to "addddddd".
// If you select substring source[1..3] as the first operation to change "abcdefgh" to "adddefgh", you cannot select substring source[3..7] as the second operation because it has a common index, 3, with the first operation.
// If you select substring source[3..7] as the first operation to change "abcdefgh" to "abcddddd", you cannot select substring source[1..3] as the second operation because it has a common index, 3, with the first operation.
 

// Constraints:

// 1 <= source.length == target.length <= 1000
// source, target consist only of lowercase English characters.
// 1 <= cost.length == original.length == changed.length <= 100
// 1 <= original[i].length == changed[i].length <= source.length
// original[i], changed[i] consist only of lowercase English characters.
// original[i] != changed[i]
// 1 <= cost[i] <= 10^6


// Solution: 


// Use DP+ Floyd Warshall's algorithm
uint64_t D[201][201];
uint64_t dp[1001];
class Solution {
public:
    unordered_map<string, int> id;
    unordered_set<int> lens;

    void init() {// fill all with ULLONG_MAX for all at 1 times
        if (D[0][0]==ULLONG_MAX) return;
        memset(D, 255, sizeof(D));
    }
    void FW(int m, int& sz, vector<string>& original, vector<string>& changed, vector<int>& cost) {
        int row, col;
        for (int i=0; i<m; i++) {
            string& s=original[i];
            string& t=changed[i];

            auto it=id.find(s);
            if (it==id.end()) {
                row=sz++;
                int slen=s.size();
                auto it_lens=lens.find(slen);
                if (it_lens==lens.end())
                    lens.insert(slen);
                id[s]=row;
            }
            else
                row=it->second;
            it=id.find(t);
            if (it==id.end()) {
                col=sz++;
                id[t]=col;
            }
            else
                col=it->second;
            D[row][col]=min(D[row][col], (uint64_t)cost[i]);
        }
        for (int i=0; i<sz; i++) D[i][i]=0;

        for (int k=0; k<sz; k++) {
            for (int i=0; i<sz; i++) {
                if (D[i][k]<ULLONG_MAX) {
                    for (int j=0; j<sz; j++) {
                        if (D[k][j]<LLONG_MAX) {
                            D[i][j]=min(D[i][j], D[i][k] + D[k][j]);
                        }
                    }
                }
            }
        }
    }

    long long minimumCost(string& source, string& target,
                          vector<string>& original,
                          vector<string>& changed,
                          vector<int>& cost) {
        init();
        const int m=original.size(), n=source.size();       
        id.reserve(m*2);
        lens.reserve(n);
        int sz=0;
        FW(m, sz, original, changed, cost);
        
        memset(dp, 255, sizeof(uint64_t)*(n+1));
        dp[0]=0;
        for (int i=0; i<n; i++) {
            if (dp[i]==ULLONG_MAX) continue;
            if (source[i]==target[i])
                dp[i+1]=min(dp[i+1], dp[i]);

            for (int t : lens) {
                if (i+t>n) continue;

                string s_sub=source.substr(i, t);
                string t_sub=target.substr(i, t);
                auto it=id.find(s_sub);
                int x=(it!=id.end()) ? it->second : -1;
                it=id.find(t_sub);
                int y=(it!=id.end()) ? it->second : -1;

                if (x>=0 && y>=0 && D[x][y]<ULLONG_MAX) {
                    dp[i+t]=min(dp[i+t], dp[i]+D[x][y]);
                }
            }
        }
        for (int i=0; i<sz; i++)// reset for the next testcase
            memset(D[i], 255, sizeof(uint64_t)*sz);
        return dp[n]==ULLONG_MAX ? -1 : dp[n];
    }
};
auto init = []() {
    ios::sync_with_stdio(0);
    cin.tie(0);
    cout.tie(0);
    return 'c';
}();