# 966. Vowel Spellchecker

# Given a wordlist, we want to implement a spellchecker that converts a query word into a correct word.

# For a given query word, the spell checker handles two categories of spelling mistakes:

# Capitalization: If the query matches a word in the wordlist (case-insensitive), then the query word is returned with the same case as the case in the wordlist.
# Example: wordlist = ["yellow"], query = "YellOw": correct = "yellow"
# Example: wordlist = ["Yellow"], query = "yellow": correct = "Yellow"
# Example: wordlist = ["yellow"], query = "yellow": correct = "yellow"
# Vowel Errors: If after replacing the vowels ('a', 'e', 'i', 'o', 'u') of the query word with any vowel individually, it matches a word in the wordlist (case-insensitive), then the query word is returned with the same case as the match in the wordlist.
# Example: wordlist = ["YellOw"], query = "yollow": correct = "YellOw"
# Example: wordlist = ["YellOw"], query = "yeellow": correct = "" (no match)
# Example: wordlist = ["YellOw"], query = "yllw": correct = "" (no match)
# In addition, the spell checker operates under the following precedence rules:

# When the query exactly matches a word in the wordlist (case-sensitive), you should return the same word back.
# When the query matches a word up to capitlization, you should return the first such match in the wordlist.
# When the query matches a word up to vowel errors, you should return the first such match in the wordlist.
# If the query has no matches in the wordlist, you should return the empty string.
# Given some queries, return a list of words answer, where answer[i] is the correct word for query = queries[i].

 

# Example 1:

# Input: wordlist = ["KiTe","kite","hare","Hare"], queries = ["kite","Kite","KiTe","Hare","HARE","Hear","hear","keti","keet","keto"]
# Output: ["kite","KiTe","KiTe","Hare","hare","","","KiTe","","KiTe"]
# Example 2:

# Input: wordlist = ["yellow"], queries = ["YellOw"]
# Output: ["yellow"]
 

# Constraints:

# 1 <= wordlist.length, queries.length <= 5000
# 1 <= wordlist[i].length, queries[i].length <= 7
# wordlist[i] and queries[i] consist only of only English letters.


# Solution: 

class Solution:
    def __init__(self):
        self.vowMask = (1 << (ord('A') & 31)) + (1 << (ord('E') & 31)) + (1 << (ord('I') & 31)) + (1 << (ord('O') & 31)) + (1 << (ord('U') & 31))
    
    class TrieNode:
        def __init__(self):
            self.links = [None] * 58  # There are 58 possible letters (A-Z)
            self.id = [-1] * 3  # id[0]: original, id[1]: lowercase, id[2]: vowel-wildcard
    
    class Trie:
        def __init__(self):
            self.root = self.new_node()
        
        def new_node(self):
            return Solution.TrieNode()  # Create a new TrieNode
        
        def insert(self, word, idx, widx):
            node = self.root
            for c in word:
                i = ord(c) - ord('A')
                if node.links[i] is None:
                    node.links[i] = self.new_node()
                node = node.links[i]
            if node.id[idx] == -1:  # Only set the index for the first time
                node.id[idx] = widx
        
        def search(self, word, idx):
            node = self.root
            for c in word:
                i = ord(c) - ord('A')
                if node.links[i] is None:
                    return -1
                node = node.links[i]
            return node.id[idx]
    
    def to_low(self, word):
        return word.lower()
    
    def to_dev(self, word):
        result = []
        for c in word:
            if (self.vowMask >> (ord(c) & 31)) & 1:  # Check if it's a vowel
                result.append('_')
            else:
                result.append(c)
        return ''.join(result)
    
    def spellchecker(self, wordlist, queries):
        trie = self.Trie()
        n = len(wordlist)
        
        # Build dictionaries
        for i in range(n):
            word = wordlist[i]
            trie.insert(word, 0, i)  # Insert original word
            lw = self.to_low(word)
            dv = self.to_dev(lw)
            trie.insert(lw, 1, i)  # Insert lowercase word
            trie.insert(dv, 2, i)  # Insert vowel-wildcard word
        
        # Process queries
        for i in range(len(queries)):
            query = queries[i]
            # Exact match
            if trie.search(query, 0) != -1:
                continue
            
            # Case insensitive match
            low = self.to_low(query)
            idx = trie.search(low, 1)
            if idx != -1:
                queries[i] = wordlist[idx]
                continue
            
            # Vowel-wildcard match
            dev = self.to_dev(low)
            idx = trie.search(dev, 2)
            if idx != -1:
                queries[i] = wordlist[idx]
            else:
                queries[i] = ""
        
        return queries

