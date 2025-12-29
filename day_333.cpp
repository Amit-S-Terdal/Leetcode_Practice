// 756. Pyramid Transition Matrix

// You are stacking blocks to form a pyramid. Each block has a color, which is represented by a single letter. Each row of blocks contains one less block than the row beneath it and is centered on top.

// To make the pyramid aesthetically pleasing, there are only specific triangular patterns that are allowed. A triangular pattern consists of a single block stacked on top of two blocks. The patterns are given as a list of three-letter strings allowed, where the first two characters of a pattern represent the left and right bottom blocks respectively, and the third character is the top block.

// For example, "ABC" represents a triangular pattern with a 'C' block stacked on top of an 'A' (left) and 'B' (right) block. Note that this is different from "BAC" where 'B' is on the left bottom and 'A' is on the right bottom.
// You start with a bottom row of blocks bottom, given as a single string, that you must use as the base of the pyramid.

// Given bottom and allowed, return true if you can build the pyramid all the way to the top such that every triangular pattern in the pyramid is in allowed, or false otherwise.

 

// Example 1:


// Input: bottom = "BCD", allowed = ["BCC","CDE","CEA","FFF"]
// Output: true
// Explanation: The allowed triangular patterns are shown on the right.
// Starting from the bottom (level 3), we can build "CE" on level 2 and then build "A" on level 1.
// There are three triangular patterns in the pyramid, which are "BCC", "CDE", and "CEA". All are allowed.
// Example 2:


// Input: bottom = "AAAA", allowed = ["AAB","AAC","BCD","BBE","DEF"]
// Output: false
// Explanation: The allowed triangular patterns are shown on the right.
// Starting from the bottom (level 4), there are multiple ways to build level 3, but trying all the possibilites, you will get always stuck before building level 1.
 

// Constraints:

// 2 <= bottom.length <= 6
// 0 <= allowed.length <= 216
// allowed[i].length == 3
// The letters in all input strings are from the set {'A', 'B', 'C', 'D', 'E', 'F'}.
// All the values of allowed are unique.



// Solution: 



// This version has a pruning Check
using u8=unsigned char;

u8 pattern[36];
const int N=117649; // 7^6
bitset<N> BAD[7];   // memo per row length

class Solution {
public:
    static inline unsigned encode(const string& s) {
        unsigned ans=0;
        for (char c : s) {
            ans=ans*7+(c-'A');
        }
        return ans;
    }
    static inline bool Check(const string& cur, int sz) {
        for (int i=0; i<sz-1; i++) {
            if (cur[i]=='G') return 0;
            u8 key=(cur[i]-'A')*6+(cur[i+1]-'A');
            if (!pattern[key]) return 0;
        }
        return 1;
    }

    static inline void addPattern(const vector<string>& allowed) {
        for (const auto& s : allowed) {
            u8 idx=(s[0]-'A')*6+(s[1]-'A');
            pattern[idx]|=1<<(s[2]-'A');
        }
    }

    static bool dfs(const string& cur, string& next, int i, int sz) {
        if (i==sz-1) {
            if (sz==2) return 1;
            // pruning check
            if (!Check(next, sz-1)) return 0;
            unsigned idx=encode(next);
            if (BAD[sz-1][idx]) return 0;

            string up(sz-1, 'G');
            if (!dfs(next, up, 0, sz-1)) {
                BAD[sz-1][idx]=1;
                return 0;
            }
            return 1;
        }

        u8 key=(cur[i]-'A')*6+(cur[i+1]-'A');
        unsigned mask=pattern[key];

        while (mask) {
            unsigned bit=mask & -mask;
            mask-=bit;

            int c=countr_zero(bit);
            next[i]='A'+c;

            if (dfs(cur, next, i+1, sz))
                return 1;
        }
        return 0;
    }

    static bool pyramidTransition(string bottom, vector<string>& allowed) {
        memset(pattern, 0, sizeof(pattern));
        for (int i=1; i<=6; i++) BAD[i].reset();

        addPattern(allowed);

        string next(bottom.size()-1, 'G');
        return dfs(bottom, next, 0, bottom.size());
    }
};
