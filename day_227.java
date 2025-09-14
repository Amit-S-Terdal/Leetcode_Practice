// 966. Vowel Spellchecker

// Given a wordlist, we want to implement a spellchecker that converts a query word into a correct word.

// For a given query word, the spell checker handles two categories of spelling mistakes:

// Capitalization: If the query matches a word in the wordlist (case-insensitive), then the query word is returned with the same case as the case in the wordlist.
// Example: wordlist = ["yellow"], query = "YellOw": correct = "yellow"
// Example: wordlist = ["Yellow"], query = "yellow": correct = "Yellow"
// Example: wordlist = ["yellow"], query = "yellow": correct = "yellow"
// Vowel Errors: If after replacing the vowels ('a', 'e', 'i', 'o', 'u') of the query word with any vowel individually, it matches a word in the wordlist (case-insensitive), then the query word is returned with the same case as the match in the wordlist.
// Example: wordlist = ["YellOw"], query = "yollow": correct = "YellOw"
// Example: wordlist = ["YellOw"], query = "yeellow": correct = "" (no match)
// Example: wordlist = ["YellOw"], query = "yllw": correct = "" (no match)
// In addition, the spell checker operates under the following precedence rules:

// When the query exactly matches a word in the wordlist (case-sensitive), you should return the same word back.
// When the query matches a word up to capitlization, you should return the first such match in the wordlist.
// When the query matches a word up to vowel errors, you should return the first such match in the wordlist.
// If the query has no matches in the wordlist, you should return the empty string.
// Given some queries, return a list of words answer, where answer[i] is the correct word for query = queries[i].

 

// Example 1:

// Input: wordlist = ["KiTe","kite","hare","Hare"], queries = ["kite","Kite","KiTe","Hare","HARE","Hear","hear","keti","keet","keto"]
// Output: ["kite","KiTe","KiTe","Hare","hare","","","KiTe","","KiTe"]
// Example 2:

// Input: wordlist = ["yellow"], queries = ["YellOw"]
// Output: ["yellow"]
 

// Constraints:

// 1 <= wordlist.length, queries.length <= 5000
// 1 <= wordlist[i].length, queries[i].length <= 7
// wordlist[i] and queries[i] consist only of only English letters.


// Solution: 


import java.util.*;

class Solution {
    static final int N = 105000;
    static TrieNode[] pool = new TrieNode[N];
    static int ptr = 0;

    static class TrieNode {
        TrieNode[] links = new TrieNode[58];
        int[] id = new int[3]; // id[0]: original, id[1]: lowercase, id[2]: vowel-wildcard

        TrieNode() {
            Arrays.fill(links, null);
            Arrays.fill(id, -1);
        }
    }

    static class Trie {
        TrieNode root;

        Trie() {
            root = newNode();
        }

        TrieNode newNode() {
            TrieNode node = new TrieNode(); // Create a new TrieNode
            return node;
        }

        void insert(String word, int idx, int widx) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int i = c - 'A';
                if (node.links[i] == null) {
                    node.links[i] = newNode(); // Ensure node is created if not already
                }
                node = node.links[i];
            }
            if (node.id[idx] == -1) {
                node.id[idx] = widx; // Only set if it's the first time
            }
        }

        int search(String word, int idx) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int i = c - 'A';
                if (node.links[i] == null) return -1;
                node = node.links[i];
            }
            return node.id[idx];
        }
    }

    static final int vowMask = (1 << ('A' & 31)) + (1 << ('E' & 31)) + (1 << ('I' & 31)) + (1 << ('O' & 31)) + (1 << ('U' & 31));

    public String[] spellchecker(String[] wordlist, String[] queries) {
        Trie trie = new Trie();
        int n = wordlist.length;

        // Build dictionaries
        for (int i = 0; i < n; i++) {
            String w = wordlist[i];
            trie.insert(w, 0, i); // Insert original word
            String lw = toLow(w);
            String dv = toDeV(lw);
            trie.insert(lw, 1, i); // Insert lowercase word
            trie.insert(dv, 2, i); // Insert vowel-wildcard word
        }

        // Process queries
        for (int i = 0; i < queries.length; i++) {
            String q = queries[i];
            // exact match
            if (trie.search(q, 0) != -1) continue;

            // case insensitive
            String low = toLow(q);
            int idx = trie.search(low, 1);
            if (idx != -1) {
                queries[i] = wordlist[idx];
                continue;
            }

            // vowel-wildcard
            String dev = toDeV(low);
            idx = trie.search(dev, 2);
            if (idx != -1) {
                queries[i] = wordlist[idx];
            } else {
                queries[i] = "";
            }
        }

        return queries;
    }

    private String toLow(String w) {
        return w.toLowerCase();
    }

    private String toDeV(String w) {
        StringBuilder sb = new StringBuilder();
        for (char c : w.toCharArray()) {
            if (((vowMask >> (c & 31)) & 1) == 1) {
                sb.append('_');
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
