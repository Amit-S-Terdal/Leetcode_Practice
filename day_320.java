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



import java.util.*;

class Solution {

    static final int N = 161;
    static final int NEG_INF = Integer.MIN_VALUE / 4;

    int[][] profit = new int[N][2];
    ArrayList<Integer>[] children = new ArrayList[N];

    // dp[node][bossBuy][buy][budget]
    int[][][][] dp = new int[N][2][2][N];

    // visited for (node, bossBuy, buy)
    boolean[] vis = new boolean[N * 4];

    int n;

    void buildTree(int[][] hierarchy) {
        for (int i = 0; i < n; i++) children[i].clear();
        for (int[] e : hierarchy) {
            children[e[0] - 1].add(e[1] - 1);
        }
    }

    void dfs(int node, int bossBuy, int buy, int budget, int[] present) {

        int key = (node << 2) | (bossBuy << 1) | buy;
        if (vis[key]) return;
        vis[key] = true;

        int[] cache = dp[node][bossBuy][buy];
        Arrays.fill(cache, 0, budget + 1, NEG_INF);

        int cost = buy == 1
                ? (bossBuy == 1 ? present[node] / 2 : present[node])
                : 0;

        if (cost <= budget) {
            cache[cost] = buy == 1 ? profit[node][bossBuy] : 0;
        }

        int[] cur = new int[budget + 1];
        int[] merged = new int[budget + 1];
        System.arraycopy(cache, 0, cur, 0, budget + 1);

        for (int child : children[node]) {
            dfs(child, buy, 1, budget, present);
            dfs(child, 0, 0, budget, present);

            int[] take = dp[child][buy][1];
            int[] skip = dp[child][0][0];

            Arrays.fill(merged, 0, budget + 1, NEG_INF);

            for (int b = 0; b <= budget; b++) {
                if (cur[b] == NEG_INF) continue;
                for (int x = 0; b + x <= budget; x++) {
                    int best = Math.max(take[x], skip[x]);
                    if (best != NEG_INF) {
                        merged[b + x] = Math.max(merged[b + x], cur[b] + best);
                    }
                }
            }
            System.arraycopy(merged, 0, cur, 0, budget + 1);
        }

        System.arraycopy(cur, 0, cache, 0, budget + 1);
    }

    public int maxProfit(int n, int[] present, int[] future, int[][] hierarchy, int budget) {

        this.n = n;
        Arrays.fill(vis, false);

        for (int i = 0; i < n; i++) {
            children[i] = new ArrayList<>();
            profit[i][0] = future[i] - present[i];
            profit[i][1] = future[i] - present[i] / 2;
        }

        buildTree(hierarchy);

        // root: buy or not buy
        dfs(0, 0, 0, budget, present);
        dfs(0, 0, 1, budget, present);

        int ans = 0;
        for (int b = 0; b <= budget; b++) {
            ans = Math.max(ans, dp[0][0][0][b]);
            ans = Math.max(ans, dp[0][0][1][b]);
        }
        return ans;
    }
}
