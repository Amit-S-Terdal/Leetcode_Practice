// 3208. Alternating Groups II

// There is a circle of red and blue tiles. You are given an array of integers colors and an integer k. The color of tile i is represented by colors[i]:

// colors[i] == 0 means that tile i is red.
// colors[i] == 1 means that tile i is blue.
// An alternating group is every k contiguous tiles in the circle with alternating colors (each tile in the group except the first and last one has a different color from its left and right tiles).

// Return the number of alternating groups.

// Note that since colors represents a circle, the first and the last tiles are considered to be next to each other.

 

// Example 1:

// Input: colors = [0,1,0,1,0], k = 3

// Output: 3

// Explanation:



// Alternating groups:



// Example 2:

// Input: colors = [0,1,0,0,1,0,1], k = 6

// Output: 2

// Explanation:



// Alternating groups:



// Example 3:

// Input: colors = [1,1,0,1], k = 4

// Output: 0

// Explanation:



 

// Constraints:

// 3 <= colors.length <= 105
// 0 <= colors[i] <= 1
// 3 <= k <= colors.length


// Solution:


#include <vector>
#include <string>
#include <unordered_set>

using namespace std;

class Solution {
public:
    int numberOfAlternatingGroups(vector<int>& colors, int k) {
        int n = colors.size();
        if (k > n) return 0; // No valid groups if k > n

        // Precompute the two possible alternating patterns
        vector<int> pattern1(k), pattern2(k);
        for (int i = 0; i < k; ++i) {
            pattern1[i] = i % 2;       // Pattern: 0, 1, 0, 1, ...
            pattern2[i] = (i + 1) % 2; // Pattern: 1, 0, 1, 0, ...
        }

        // Compute the hash of the two patterns
        long long hash1 = computeHash(pattern1);
        long long hash2 = computeHash(pattern2);

        // Compute the hash of the first window in the colors array
        vector<int> firstWindow(k);
        for (int i = 0; i < k; ++i) {
            firstWindow[i] = colors[i];
        }
        long long windowHash = computeHash(firstWindow);

        int count = 0;
        if (windowHash == hash1 || windowHash == hash2) {
            count++;
        }

        // Use rolling hash to compute the hash of the remaining windows
        long long power = 1;
        for (int i = 0; i < k - 1; ++i) {
            power = (power * BASE) % MOD;
        }

        for (int i = 1; i < n; ++i) {
            int left = (i - 1) % n; // Handle circular nature
            int right = (i + k - 1) % n; // Handle circular nature

            // Remove the left element from the hash
            windowHash = (windowHash - colors[left] * power % MOD + MOD) % MOD;
            // Add the right element to the hash
            windowHash = (windowHash * BASE + colors[right]) % MOD;

            if (windowHash == hash1 || windowHash == hash2) {
                count++;
            }
        }

        return count;
    }

private:
    const long long BASE = 257; // A prime number as the base
    const long long MOD = 1e9 + 7; // A large prime number as the modulus

    long long computeHash(const vector<int>& arr) {
        long long hash = 0;
        for (int num : arr) {
            hash = (hash * BASE + num) % MOD;
        }
        return hash;
    }
};
