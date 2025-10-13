# 2273. Find Resultant Array After Removing Anagrams

# You are given a 0-indexed string array words, where words[i] consists of lowercase English letters.

# In one operation, select any index i such that 0 < i < words.length and words[i - 1] and words[i] are anagrams, and delete words[i] from words. Keep performing this operation as long as you can select an index that satisfies the conditions.

# Return words after performing all operations. It can be shown that selecting the indices for each operation in any arbitrary order will lead to the same result.

# An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase using all the original letters exactly once. For example, "dacb" is an anagram of "abdc".

 

# Example 1:

# Input: words = ["abba","baba","bbaa","cd","cd"]
# Output: ["abba","cd"]
# Explanation:
# One of the ways we can obtain the resultant array is by using the following operations:
# - Since words[2] = "bbaa" and words[1] = "baba" are anagrams, we choose index 2 and delete words[2].
#   Now words = ["abba","baba","cd","cd"].
# - Since words[1] = "baba" and words[0] = "abba" are anagrams, we choose index 1 and delete words[1].
#   Now words = ["abba","cd","cd"].
# - Since words[2] = "cd" and words[1] = "cd" are anagrams, we choose index 2 and delete words[2].
#   Now words = ["abba","cd"].
# We can no longer perform any operations, so ["abba","cd"] is the final answer.
# Example 2:

# Input: words = ["a","b","c","d","e"]
# Output: ["a","b","c","d","e"]
# Explanation:
# No two adjacent strings in words are anagrams of each other, so no operations are performed.
 

# Constraints:

# 1 <= words.length <= 100
# 1 <= words[i].length <= 10
# words[i] consists of lowercase English letters.




# Solution: 



class Solution(object):
    # Helper function to calculate frequency of characters in a string
    def freq(self, s):
        freq_arr = [0] * 26  # Array to store frequency of each character ('a' = 0, ..., 'z' = 25)
        for c in s:
            freq_arr[ord(c) - ord('a')] += 1
        return freq_arr

    def removeAnagrams(self, words):
        """
        :type words: List[str]
        :rtype: List[str]
        """
        ans = []
        n = len(words)

        # Add the first word to the result list
        ans.append(words[0])
        
        # Initialize the frequency of the first word
        prev_freq = self.freq(words[0])

        for r in range(1, n):
            current_word = words[r]
            current_freq = self.freq(current_word)
            
            # If the current word's frequency is different from the previous one, add it to the result
            if prev_freq != current_freq:
                ans.append(current_word)
                prev_freq = current_freq  # Update prev_freq to the current word's frequency
        
        return ans
