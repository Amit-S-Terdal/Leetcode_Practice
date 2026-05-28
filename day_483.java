// 3093. Longest Common Suffix Queries

// You are given two arrays of strings wordsContainer and wordsQuery.

// For each wordsQuery[i], you need to find a string from wordsContainer that has the longest common suffix with wordsQuery[i]. If there are two or more strings in wordsContainer that share the longest common suffix, find the string that is the smallest in length. If there are two or more such strings that have the same smallest length, find the one that occurred earlier in wordsContainer.

// Return an array of integers ans, where ans[i] is the index of the string in wordsContainer that has the longest common suffix with wordsQuery[i].

 

// Example 1:

// Input: wordsContainer = ["abcd","bcd","xbcd"], wordsQuery = ["cd","bcd","xyz"]

// Output: [1,1,1]

// Explanation:

// Let's look at each wordsQuery[i] separately:

// For wordsQuery[0] = "cd", strings from wordsContainer that share the longest common suffix "cd" are at indices 0, 1, and 2. Among these, the answer is the string at index 1 because it has the shortest length of 3.
// For wordsQuery[1] = "bcd", strings from wordsContainer that share the longest common suffix "bcd" are at indices 0, 1, and 2. Among these, the answer is the string at index 1 because it has the shortest length of 3.
// For wordsQuery[2] = "xyz", there is no string from wordsContainer that shares a common suffix. Hence the longest common suffix is "", that is shared with strings at index 0, 1, and 2. Among these, the answer is the string at index 1 because it has the shortest length of 3.
// Example 2:

// Input: wordsContainer = ["abcdefgh","poiuygh","ghghgh"], wordsQuery = ["gh","acbfgh","acbfegh"]

// Output: [2,0,2]

// Explanation:

// Let's look at each wordsQuery[i] separately:

// For wordsQuery[0] = "gh", strings from wordsContainer that share the longest common suffix "gh" are at indices 0, 1, and 2. Among these, the answer is the string at index 2 because it has the shortest length of 6.
// For wordsQuery[1] = "acbfgh", only the string at index 0 shares the longest common suffix "fgh". Hence it is the answer, even though the string at index 2 is shorter.
// For wordsQuery[2] = "acbfegh", strings from wordsContainer that share the longest common suffix "gh" are at indices 0, 1, and 2. Among these, the answer is the string at index 2 because it has the shortest length of 6.
 

// Constraints:

// 1 <= wordsContainer.length, wordsQuery.length <= 10^4
// 1 <= wordsContainer[i].length <= 5 * 10^3
// 1 <= wordsQuery[i].length <= 5 * 10^3
// wordsContainer[i] consists only of lowercase English letters.
// wordsQuery[i] consists only of lowercase English letters.
// Sum of wordsContainer[i].length is at most 5 * 10^5.
// Sum of wordsQuery[i].length is at most 5 * 10^5.



// Solution:



class Solution {

    static class TrieNode {
        TrieNode[] next = new TrieNode[26];

        // Shortest string length passing through this node
        int minSz = Integer.MAX_VALUE;

        // Index of that shortest string
        int bestIdx = -1;
    }

    static class Trie {
        TrieNode root;

        Trie() {
            root = new TrieNode();
        }

        void insert(String w, int idx) {
            int len = w.length();
            TrieNode curr = root;

            // Update root info
            if (len < curr.minSz) {
                curr.minSz = len;
                curr.bestIdx = idx;
            }

            // Insert in reverse for suffix matching
            for (int i = len - 1; i >= 0; i--) {
                int c = w.charAt(i) - 'a';

                if (curr.next[c] == null) {
                    curr.next[c] = new TrieNode();
                }

                curr = curr.next[c];

                if (len < curr.minSz) {
                    curr.minSz = len;
                    curr.bestIdx = idx;
                }
            }
        }

        int find(String q) {
            TrieNode curr = root;
            int best = root.bestIdx;

            // Search in reverse
            for (int i = q.length() - 1; i >= 0; i--) {
                int c = q.charAt(i) - 'a';

                if (curr.next[c] == null) {
                    break;
                }

                curr = curr.next[c];
                best = curr.bestIdx;
            }

            return best;
        }
    }

    public int[] stringIndices(String[] wordsContainer, String[] wordsQuery) {

        Trie trie = new Trie();

        for (int i = 0; i < wordsContainer.length; i++) {
            trie.insert(wordsContainer[i], i);
        }

        int[] ans = new int[wordsQuery.length];

        for (int i = 0; i < wordsQuery.length; i++) {
            ans[i] = trie.find(wordsQuery[i]);
        }

        return ans;
    }
}