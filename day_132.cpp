// 386. Lexicographical Numbers

// Given an integer n, return all the numbers in the range [1, n] sorted in lexicographical order.

// You must write an algorithm that runs in O(n) time and uses O(1) extra space. 

 

// Example 1:

// Input: n = 13
// Output: [1,10,11,12,13,2,3,4,5,6,7,8,9]
// Example 2:

// Input: n = 2
// Output: [1,2]
 

// Constraints:

// 1 <= n <= 5 * 10^4


// Solution: 


using namespace std;

class Solution {
public:
    static vector<int> lexicalOrder(int n) {
        vector<int> ans;
        for (int i = 1; i <= 9; ++i) {
            dfs(i, n, ans);
        }
        return ans;
    }

private:
    static void dfs(int cur, int n, vector<int>& ans) {
        if (cur > n) return;
        ans.push_back(cur);
        for (int d = 0; d <= 9; ++d) {
            int next = cur * 10 + d;
            if (next > n) break;
            dfs(next, n, ans);
        }
    }
};