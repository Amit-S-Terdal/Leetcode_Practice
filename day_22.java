// 1718. Construct the Lexicographically Largest Valid Sequence

// Hint
// Given an integer n, find a sequence that satisfies all of the following:

// The integer 1 occurs once in the sequence.
// Each integer between 2 and n occurs twice in the sequence.
// For every integer i between 2 and n, the distance between the two occurrences of i is exactly i.
// The distance between two numbers on the sequence, a[i] and a[j], is the absolute difference of their indices, |j - i|.

// Return the lexicographically largest sequence. It is guaranteed that under the given constraints, there is always a solution.

// A sequence a is lexicographically larger than a sequence b (of the same length) if in the first position where a and b differ, sequence a has a number greater than the corresponding number in b. For example, [0,1,9,0] is lexicographically larger than [0,1,5,6] because the first position they differ is at the third number, and 9 is greater than 5.

 

// Example 1:

// Input: n = 3
// Output: [3,1,2,3,2]
// Explanation: [2,3,2,1,3] is also a valid sequence, but [3,1,2,3,2] is the lexicographically largest valid sequence.
// Example 2:

// Input: n = 5
// Output: [5,3,1,4,3,5,2,4,2]
 

// Constraints:

// 1 <= n <= 20


// Solution:

class Solution {
    public int[] constructDistancedSequence(int n) {
        int length = 2 * n - 1;
        int[] sequence = new int[length];
        boolean[] used = new boolean[n + 1];
        
        backtrack(sequence, used, 0, n);
        return sequence;
    }
    
    private boolean backtrack(int[] sequence, boolean[] used, int index, int n) {
        if (index == sequence.length) {
            return true; // All numbers are placed successfully
        }
        
        if (sequence[index] != 0) {
            // If the current position is already filled, move to the next index
            return backtrack(sequence, used, index + 1, n);
        }
        
        // Try placing the largest possible number first
        for (int num = n; num >= 1; num--) {
            if (used[num]) {
                continue; // Skip if the number is already used
            }
            
            if (num == 1) {
                // Place the number 1
                sequence[index] = 1;
                used[1] = true;
                
                if (backtrack(sequence, used, index + 1, n)) {
                    return true; // Proceed to the next index
                }
                
                // Backtrack
                sequence[index] = 0;
                used[1] = false;
            } else {
                // For numbers greater than 1, ensure the second occurrence is at the correct distance
                if (index + num >= sequence.length || sequence[index + num] != 0) {
                    continue; // Skip if the second occurrence cannot be placed
                }
                
                // Place the number
                sequence[index] = num;
                sequence[index + num] = num;
                used[num] = true;
                
                if (backtrack(sequence, used, index + 1, n)) {
                    return true; // Proceed to the next index
                }
                
                // Backtrack
                sequence[index] = 0;
                sequence[index + num] = 0;
                used[num] = false;
            }
        }
        
        return false; // No valid placement found
    }
}