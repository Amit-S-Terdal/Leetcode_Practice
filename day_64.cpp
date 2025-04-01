// 763. Partition Labels

// You are given a string s. We want to partition the string into as many parts as possible so that each letter appears in at most one part. For example, the string "ababcc" can be partitioned into ["abab", "cc"], but partitions such as ["aba", "bcc"] or ["ab", "ab", "cc"] are invalid.

// Note that the partition is done so that after concatenating all the parts in order, the resultant string should be s.

// Return a list of integers representing the size of these parts.

 

// Example 1:

// Input: s = "ababcbacadefegdehijhklij"
// Output: [9,7,8]
// Explanation:
// The partition is "ababcbaca", "defegde", "hijhklij".
// This is a partition so that each letter appears in at most one part.
// A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits s into less parts.
// Example 2:

// Input: s = "eccbbbbdec"
// Output: [10]
 

// Constraints:

// 1 <= s.length <= 500
// s consists of lowercase English letters.


// Solution:


class Solution {
    public:
        vector<int> partitionLabels(string s) {
            // Step 1: Store the last occurrence of each character
            vector<int> lastOccurrence(26, 0);
            for (int i = 0; i < s.size(); ++i) {
                lastOccurrence[s[i] - 'a'] = i;
            }
    
            // Step 2: Traverse the string and create partitions
            vector<int> partitionSizes;
            int partitionStart = 0, partitionEnd = 0;
    
            for (int i = 0; i < s.size(); ++i) {
                partitionEnd = max(partitionEnd, lastOccurrence[s[i] - 'a']);
                if (i == partitionEnd) {
                    partitionSizes.push_back(i - partitionStart + 1);
                    partitionStart = i + 1;
                }
            }
    
            return partitionSizes;
        }
    };
    