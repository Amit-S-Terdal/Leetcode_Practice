// 3714. Longest Balanced Substring II

// You are given a string s consisting only of the characters 'a', 'b', and 'c'.

// A substring of s is called balanced if all distinct characters in the substring appear the same number of times.

// Return the length of the longest balanced substring of s.

 

// Example 1:

// Input: s = "abbac"

// Output: 4

// Explanation:

// The longest balanced substring is "abba" because both distinct characters 'a' and 'b' each appear exactly 2 times.

// Example 2:

// Input: s = "aabcc"

// Output: 3

// Explanation:

// The longest balanced substring is "abc" because all distinct characters 'a', 'b' and 'c' each appear exactly 1 time.

// Example 3:

// Input: s = "aba"

// Output: 2

// Explanation:

// One of the longest balanced substrings is "ab" because both distinct characters 'a' and 'b' each appear exactly 1 time. Another longest balanced substring is "ba".

 

// Constraints:

// 1 <= s.length <= 10^5
// s contains only the characters 'a', 'b', and 'c'.




// Solution: 


#include <memory_resource>
pmr::unsynchronized_pool_resource pool;
class Solution {
public:
    static int has1(string& s, int n){
        int cnt=1, len=1;
        for(int i=1; i<n; i++){
            if (s[i]==s[i-1]) len++;
            else {
                cnt=max(cnt, len);
                len=1;
            }
        }
        return max(cnt, len);
    }

    static constexpr long long bias=1<<18, shift=32;
    inline static long long pack(int x, int y){
        // Deal with negative int
        return ((long long)(x+bias)<<shift)|(long long)(y+bias);
    }

    static int longestBalanced(string& s) {
        const int n=s.size();
        int ans=has1(s, n);

        pmr::unordered_map<long long, int> ab(&pool), bc(&pool), ca(&pool), abc(&pool);
        
        ab.reserve(n), bc.reserve(n), ca.reserve(n), abc.reserve(n);
        // INITIAL STATE at -1
        abc[pack(0, 0)]=-1;
        ab[pack(0, 0)]=-1; // (A-B, C)
        bc[pack(0, 0)]=-1; // (B-C, A)
        ca[pack(0, 0)]=-1; // (C-A, B)
    
        array<int, 3> cnt={0};
        for(int i=0; i<n; i++){
            cnt[s[i]-'a']++; 
            const auto [A, B, C]=cnt;

            // 3-letter balance: A=B=C
            long long key=pack(B-A, C-A);
            if(abc.count(key)) ans=max(ans, i-abc[key]);
            else abc[key]=i;

            // 2-letter balance: A=B and NO C
            key=pack(A-B, C);
            if(ab.count(key)) ans = max(ans, i-ab[key]);
            else ab[key]=i;

            // 2-letter balance: B=C and NO A
            key=pack(B-C, A);
            if(bc.count(key)) ans=max(ans, i-bc[key]);
            else bc[key]=i;

            // 2-letter balance: C=A and NO B
            key=pack(C-A, B);
            if(ca.count(key)) ans=max(ans, i-ca[key]);
            else ca[key]=i;
        }
        
        return ans;
    }
};