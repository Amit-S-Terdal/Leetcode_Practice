// 3272. Find the Count of Good Integers

// You are given two positive integers n and k.

// An integer x is called k-palindromic if:

// x is a palindrome.
// x is divisible by k.
// An integer is called good if its digits can be rearranged to form a k-palindromic integer. For example, for k = 2, 2020 can be rearranged to form the k-palindromic integer 2002, whereas 1010 cannot be rearranged to form a k-palindromic integer.

// Return the count of good integers containing n digits.

// Note that any integer must not have leading zeros, neither before nor after rearrangement. For example, 1010 cannot be rearranged to form 101.

 

// Example 1:

// Input: n = 3, k = 5

// Output: 27

// Explanation:

// Some of the good integers are:

// 551 because it can be rearranged to form 515.
// 525 because it is already k-palindromic.
// Example 2:

// Input: n = 1, k = 4

// Output: 2

// Explanation:

// The two good integers are 4 and 8.

// Example 3:

// Input: n = 5, k = 6

// Output: 2468

 

// Constraints:

// 1 <= n <= 10
// 1 <= k <= 9


// Solution: 



class Solution {
    public:
        long long countGoodIntegers(int n, int k) {
            unordered_set<string> dict;
    
            int base = pow(10, (n - 1) / 2);
            int skip = n % 2;
    
            // Step 1: Enumerate all valid palindromic numbers of length n
            for (int i = base; i < base * 10; i++) {
                string left = to_string(i);
                string right(left.rbegin() + skip, left.rend());
                string full = left + right;
                long long num = stoll(full);
                
                // Step 2: Check if it's divisible by k
                if (num % k == 0) {
                    sort(full.begin(), full.end()); // normalize
                    dict.insert(full); // unique by sorted digits
                }
            }
    
            // Step 3: Precompute factorials up to n
            vector<long long> factorial(n + 1, 1);
            for (int i = 1; i <= n; i++) {
                factorial[i] = factorial[i - 1] * i;
            }
    
            long long ans = 0;
    
            // Step 4: For each unique digit combination, compute valid permutations
            for (const string &s : dict) {
                vector<int> cnt(10, 0);
                for (char c : s) {
                    cnt[c - '0']++;
                }
    
                // Cannot start with 0, so only (n - cnt[0]) digits are valid as first
                long long total = (n - cnt[0]) * factorial[n - 1];
                for (int i = 0; i < 10; i++) {
                    total /= factorial[cnt[i]];
                }
    
                ans += total;
            }
    
            return ans;
        }
    };
    