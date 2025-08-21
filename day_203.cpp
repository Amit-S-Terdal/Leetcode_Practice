// 679. 24 Game

// You are given an integer array cards of length 4. You have four cards, each containing a number in the range [1, 9]. You should arrange the numbers on these cards in a mathematical expression using the operators ['+', '-', '*', '/'] and the parentheses '(' and ')' to get the value 24.

// You are restricted with the following rules:

// The division operator '/' represents real division, not integer division.
// For example, 4 / (1 - 2 / 3) = 4 / (1 / 3) = 12.
// Every operation done is between two numbers. In particular, we cannot use '-' as a unary operator.
// For example, if cards = [1, 1, 1, 1], the expression "-1 - 1 - 1 - 1" is not allowed.
// You cannot concatenate numbers together
// For example, if cards = [1, 2, 1, 2], the expression "12 + 12" is not valid.
// Return true if you can get such expression that evaluates to 24, and false otherwise.

 

// Example 1:

// Input: cards = [4,1,8,7]
// Output: true
// Explanation: (8-4) * (7-1) = 24
// Example 2:

// Input: cards = [1,2,1,2]
// Output: false
 

// Constraints:

// cards.length == 4
// 1 <= cards[i] <= 9

// Solution:


#include <vector>
#include <cmath>
using namespace std;

class Solution {
public:
    bool res = false;
    const double eps = 1e-3;

    bool judgePoint24(vector<int>& nums) {
        vector<double> arr;
        arr.reserve(nums.size());
        for (int n : nums) arr.push_back(static_cast<double>(n));
        helper(arr);
        return res;
    }

private:
    void helper(vector<double>& arr) {
        if (res) return;
        if (arr.size() == 1) {
            if (fabs(arr[0] - 24.0) < eps) res = true;
            return;
        }
        for (int i = 0; i < (int)arr.size(); ++i) {
            for (int j = 0; j < i; ++j) {
                double p1 = arr[i], p2 = arr[j];
                vector<double> next;
                next.reserve(6);
                next.push_back(p1 + p2);
                next.push_back(p1 - p2);
                next.push_back(p2 - p1);
                next.push_back(p1 * p2);
                if (fabs(p2) > eps) next.push_back(p1 / p2);
                if (fabs(p1) > eps) next.push_back(p2 / p1);

                // remove i and j (remove larger index first)
                vector<double> saved = arr; // simpler: copy, then try each next
                for (double n : next) {
                    arr = saved;
                    // remove i and j
                    if (i > j) {
                        arr.erase(arr.begin() + i);
                        arr.erase(arr.begin() + j);
                    } else {
                        arr.erase(arr.begin() + j);
                        arr.erase(arr.begin() + i);
                    }
                    arr.push_back(n);
                    helper(arr);
                    if (res) return;
                }
                arr = saved; // restore
            }
        }
    }
};
