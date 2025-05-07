// 1128. Number of Equivalent Domino Pairs

// Given a list of dominoes, dominoes[i] = [a, b] is equivalent to dominoes[j] = [c, d] if and only if either (a == c and b == d), or (a == d and b == c) - that is, one domino can be rotated to be equal to another domino.

// Return the number of pairs (i, j) for which 0 <= i < j < dominoes.length, and dominoes[i] is equivalent to dominoes[j].

 

// Example 1:

// Input: dominoes = [[1,2],[2,1],[3,4],[5,6]]
// Output: 1
// Example 2:

// Input: dominoes = [[1,2],[1,2],[1,1],[1,2],[2,2]]
// Output: 3
 

// Constraints:

// 1 <= dominoes.length <= 4 * 10^4
// dominoes[i].length == 2
// 1 <= dominoes[i][j] <= 9


// Solution:


class Solution {
    public:
        int numEquivDominoPairs(vector<vector<int>>& dominoes) {
            int count[100] = {}; // Since 1 <= domino[i][j] <= 9, max key is 9*10 + 9 = 99
            int res = 0;
    
            for (auto& d : dominoes) {
                int a = d[0], b = d[1];
                int key = a < b ? a * 10 + b : b * 10 + a;
                res += count[key];
                count[key]++;
            }
    
            return res;
        }
    };
    