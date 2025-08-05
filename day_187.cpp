// 2561. Rearranging Fruits

// You have two fruit baskets containing n fruits each. You are given two 0-indexed integer arrays basket1 and basket2 representing the cost of fruit in each basket. You want to make both baskets equal. To do so, you can use the following operation as many times as you want:

// Chose two indices i and j, and swap the ith fruit of basket1 with the jth fruit of basket2.
// The cost of the swap is min(basket1[i],basket2[j]).
// Two baskets are considered equal if sorting them according to the fruit cost makes them exactly the same baskets.

// Return the minimum cost to make both the baskets equal or -1 if impossible.

 

// Example 1:

// Input: basket1 = [4,2,2,2], basket2 = [1,4,1,2]
// Output: 1
// Explanation: Swap index 1 of basket1 with index 0 of basket2, which has cost 1. Now basket1 = [4,1,2,2] and basket2 = [2,4,1,2]. Rearranging both the arrays makes them equal.
// Example 2:

// Input: basket1 = [2,3,4,1], basket2 = [3,2,5,1]
// Output: -1
// Explanation: It can be shown that it is impossible to make both the baskets equal.
 

// Constraints:

// basket1.length == basket2.length
// 1 <= basket1.length <= 10^5
// 1 <= basket1[i],basket2[i] <= 10^9



// Solution:

class Solution {
    public:
        using int2=pair<int, int>;
        long long minCost(vector<int>& basket1, vector<int>& basket2) {
            const int n=basket1.size();
            unordered_map<int, int> freq;
            freq.reserve(n);
    
            int minX=INT_MAX;// May swap twice with minX
            for (int i=0; i<n; i++) {
                freq[basket1[i]]++;
                freq[basket2[i]]--;
                minX=min(minX, min(basket1[i], basket2[i]));
            }
    
            vector<int2> B1, B2; // need to swap for backets
            for (auto& [x, f] : freq) {
                if (f%2!=0) return -1;
                if (f>0) B1.emplace_back(x, f/2);
                else if (f<0) B2.emplace_back(x, -f/2);
            }
    
            sort(B1.begin(), B1.end());
            sort(B2.begin(), B2.end());
    
            long long cost=0;
            const int m1=B1.size(), m2=B2.size();
            // Greedy here
            for (int i=0, j=m2-1; i<m1 && j>=0;) {
                auto& [b1, f1]=B1[i];
                auto& [b2, f2]=B2[j];
                int f0=min(f1, f2);
                // direct swap or 2 times swaps with minX
                long long swapCost=min(min(b1, b2), 2*minX);
                cost+=swapCost*f0;
    
                f1-=f0;
                f2-=f0;
                i+=(f1==0);
                j-=(f2==0);
            }
    
            return cost;
        }
    };
    