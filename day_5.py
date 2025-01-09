#  2185. Counting Words With a Given Prefix

#  You are given an array of strings words and a string pref.
#  Return the number of strings in words that contain pref as a prefix.
#  A prefix of a string s is any leading contiguous substring of s.

#  Example 1:
#  Input: words = ["pay","attention","practice","attend"], pref = "at"
#  Output: 2
#  Explanation: The 2 strings that contain "at" as a prefix are: "attention" and "attend".
#  Example 2:
#  Input: words = ["leetcode","win","loops","success"], pref = "code"
#  Output: 0
#  Explanation: There are no strings that contain "code" as a prefix.

#  Constraints:
#  1 <= words.length <= 100
#  1 <= words[i].length, pref.length <= 100
#  words[i] and pref consist of lowercase English letters.
 
#  SOLUTION:


class Solution:
    def prefixCount(self, words: List[str], pref: str) -> int:
        count = 0
        pref_length = len(pref)

        for word in words:
            # Check if the word starts with the prefix
            if len(word) >= pref_length and word[:pref_length] == pref:
                count += 1

        return count

# Example usage
if __name__ == "__main__":
    solution = Solution()

    # Example 1
    words1 = ["pay", "attention", "practice", "attend"]
    pref1 = "at"
    print("Example 1 Output:", solution.prefixCount(words1, pref1))  # Output: 2

    # Example 2
    words2 = ["leetcode", "win", "loops", "success"]
    pref2 = "code"
    print("Example 2 Output:", solution.prefixCount(words2, pref2))  # Output: 0