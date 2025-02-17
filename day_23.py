# 1079. Letter Tile Possibilities

# Hint
# You have n  tiles, where each tile has one letter tiles[i] printed on it.

# Return the number of possible non-empty sequences of letters you can make using the letters printed on those tiles.

 

# Example 1:

# Input: tiles = "AAB"
# Output: 8
# Explanation: The possible sequences are "A", "B", "AA", "AB", "BA", "AAB", "ABA", "BAA".
# Example 2:

# Input: tiles = "AAABBC"
# Output: 188
# Example 3:

# Input: tiles = "V"
# Output: 1
 

# Constraints:

# 1 <= tiles.length <= 7
# tiles consists of uppercase English letters.

# Solution:


class Solution(object):
    def numTilePossibilities(self, tiles):
        """
        :type tiles: str
        :rtype: int
        """
        # Count the frequency of each letter
        count = {}
        for tile in tiles:
            count[tile] = count.get(tile, 0) + 1
        
        # Start the backtracking process
        return self.backtrack(count)
    
    def backtrack(self, count):
        total = 0
        
        # Iterate through all possible letters
        for tile in count:
            if count[tile] == 0:
                continue  # Skip if no more of this letter
            
            # Use this letter in the sequence
            count[tile] -= 1
            total += 1  # Count this sequence
            
            # Recurse to add more letters to the sequence
            total += self.backtrack(count)
            
            # Backtrack: restore the count of this letter
            count[tile] += 1
        
        return total