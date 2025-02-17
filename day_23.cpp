// 1079. Letter Tile Possibilities

// Hint
// You have n  tiles, where each tile has one letter tiles[i] printed on it.

// Return the number of possible non-empty sequences of letters you can make using the letters printed on those tiles.

 

// Example 1:

// Input: tiles = "AAB"
// Output: 8
// Explanation: The possible sequences are "A", "B", "AA", "AB", "BA", "AAB", "ABA", "BAA".
// Example 2:

// Input: tiles = "AAABBC"
// Output: 188
// Example 3:

// Input: tiles = "V"
// Output: 1
 

// Constraints:

// 1 <= tiles.length <= 7
// tiles consists of uppercase English letters.

// Solution:

#include <vector>
using namespace std;

class Solution {
public:
    int numTilePossibilities(string tiles) {
        // Count the frequency of each letter
        vector<int> count(26, 0);
        for (char c : tiles) {
            count[c - 'A']++;
        }
        
        // Start the backtracking process
        return backtrack(count);
    }
    
private:
    int backtrack(vector<int>& count) {
        int total = 0;
        
        // Iterate through all possible letters
        for (int i = 0; i < 26; i++) {
            if (count[i] == 0) continue; // Skip if no more of this letter
            
            // Use this letter in the sequence
            count[i]--;
            total++; // Count this sequence
            
            // Recurse to add more letters to the sequence
            total += backtrack(count);
            
            // Backtrack: restore the count of this letter
            count[i]++;
        }
        
        return total;
    }
};