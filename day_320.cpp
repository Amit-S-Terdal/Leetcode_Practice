// 3562. Maximum Profit from Trading Stocks with Discounts

// You are given an integer n, representing the number of employees in a company. Each employee is assigned a unique ID from 1 to n, and employee 1 is the CEO. You are given two 1-based integer arrays, present and future, each of length n, where:

// present[i] represents the current price at which the ith employee can buy a stock today.
// future[i] represents the expected price at which the ith employee can sell the stock tomorrow.
// The company's hierarchy is represented by a 2D integer array hierarchy, where hierarchy[i] = [ui, vi] means that employee ui is the direct boss of employee vi.

// Additionally, you have an integer budget representing the total funds available for investment.

// However, the company has a discount policy: if an employee's direct boss purchases their own stock, then the employee can buy their stock at half the original price (floor(present[v] / 2)).

// Return the maximum profit that can be achieved without exceeding the given budget.

// Note:

// You may buy each stock at most once.
// You cannot use any profit earned from future stock prices to fund additional investments and must buy only from budget.
 

// Example 1:

// Input: n = 2, present = [1,2], future = [4,3], hierarchy = [[1,2]], budget = 3

// Output: 5

// Explanation:



// Employee 1 buys the stock at price 1 and earns a profit of 4 - 1 = 3.
// Since Employee 1 is the direct boss of Employee 2, Employee 2 gets a discounted price of floor(2 / 2) = 1.
// Employee 2 buys the stock at price 1 and earns a profit of 3 - 1 = 2.
// The total buying cost is 1 + 1 = 2 <= budget. Thus, the maximum total profit achieved is 3 + 2 = 5.
// Example 2:

// Input: n = 2, present = [3,4], future = [5,8], hierarchy = [[1,2]], budget = 4

// Output: 4

// Explanation:



// Employee 2 buys the stock at price 4 and earns a profit of 8 - 4 = 4.
// Since both employees cannot buy together, the maximum profit is 4.
// Example 3:

// Input: n = 3, present = [4,6,8], future = [7,9,11], hierarchy = [[1,2],[1,3]], budget = 10

// Output: 10

// Explanation:



// Employee 1 buys the stock at price 4 and earns a profit of 7 - 4 = 3.
// Employee 3 would get a discounted price of floor(8 / 2) = 4 and earns a profit of 11 - 4 = 7.
// Employee 1 and Employee 3 buy their stocks at a total cost of 4 + 4 = 8 <= budget. Thus, the maximum total profit achieved is 3 + 7 = 10.
// Example 4:

// Input: n = 3, present = [5,2,3], future = [8,5,6], hierarchy = [[1,2],[2,3]], budget = 7

// Output: 12

// Explanation:



// Employee 1 buys the stock at price 5 and earns a profit of 8 - 5 = 3.
// Employee 2 would get a discounted price of floor(2 / 2) = 1 and earns a profit of 5 - 1 = 4.
// Employee 3 would get a discounted price of floor(3 / 2) = 1 and earns a profit of 6 - 1 = 5.
// The total cost becomes 5 + 1 + 1 = 7 <= budget. Thus, the maximum total profit achieved is 3 + 4 + 5 = 12.
 

// Constraints:

// 1 <= n <= 160
// present.length, future.length == n
// 1 <= present[i], future[i] <= 50
// hierarchy.length == n - 1
// hierarchy[i] == [ui, vi]
// 1 <= ui, vi <= n
// ui != vi
// 1 <= budget <= 160
// There are no duplicate edges.
// Employee 1 is the direct or indirect boss of every employee.
// The input graph hierarchy is guaranteed to have no cycles.



// Solution:



const int N=161 ,N4=161*4;
int profit[N][2];
vector<int> children[N];
int dp[N][2][2][N]; // node, bossBuy, buy, budget
bitset<N4> vis=0; // visit for (node, bossBuy,  buy)  

class Solution {
public:
    int n;

    void build_tree(const vector<vector<int>>& hierarchy) {
        for (int i=0; i<n; i++) children[i].clear();
        for (auto& e : hierarchy)
            children[e[0]-1].push_back(e[1]-1);
    }

    void dfs(int node, bool bossBuy, bool buy, int budget,
             const vector<int>& present) {

        if (vis[(node<<2)|(bossBuy<<1)|buy]) return;
        vis[(node<<2)|(bossBuy<<1)|buy]=1;

        int* cache=dp[node][bossBuy][buy];
        fill(cache, cache+budget+1, INT_MIN);

        int cost=buy?(bossBuy?present[node]/2:present[node]):0;
        if (cost<=budget)
            cache[cost]=buy?profit[node][bossBuy]:0;

        int* cur=(int*)alloca(sizeof(int)*(budget+1));
        int* merged=(int*)alloca(sizeof(int)*(budget+1));
        memcpy(cur, cache, sizeof(int)*(budget+1));

        for (int child : children[node]) {
            dfs(child, buy, 1, budget, present);
            dfs(child, 0, 0, budget, present);

            int* take=dp[child][buy][1];
            int* skip=dp[child][0][0];

            fill(merged, merged+budget+1, INT_MIN);

            for (int b=0; b<=budget; b++) if (cur[b]!=INT_MIN) {
                for (int x=0; b+x <= budget; x++) {
                    int best=max(take[x], skip[x]);
                    if (best!=INT_MIN)
                        merged[b+x]=max(merged[b+x], cur[b]+best);
                }
            }
            memcpy(cur, merged, sizeof(int)*(budget+1));
        }
        memcpy(cache, cur, sizeof(int)*(budget+1));
    }

    int maxProfit(int n, vector<int>& present, vector<int>& future,
                  vector<vector<int>>& hierarchy, int budget) {
        this->n=n;
        vis.reset();

        for (int i=0; i<n; i++) {
            profit[i][0]=future[i]-present[i];
            profit[i][1]=future[i]-present[i]/2;
        }

        build_tree(hierarchy);

        // root buys or does not buy
        dfs(0, 0, 0, budget, present);
        dfs(0, 0, 1, budget, present);

        int ans=0;
        for (int b=0; b<=budget; b++) {
            ans=max(ans, dp[0][0][0][b]);
            ans=max(ans, dp[0][0][1][b]);
        }
        return ans;
    }
};