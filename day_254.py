# 3186. Maximum Total Damage With Spell Casting

# A magician has various spells.

# You are given an array power, where each element represents the damage of a spell. Multiple spells can have the same damage value.

# It is a known fact that if a magician decides to cast a spell with a damage of power[i], they cannot cast any spell with a damage of power[i] - 2, power[i] - 1, power[i] + 1, or power[i] + 2.

# Each spell can be cast only once.

# Return the maximum possible total damage that a magician can cast.

 

# Example 1:

# Input: power = [1,1,3,4]

# Output: 6

# Explanation:

# The maximum possible damage of 6 is produced by casting spells 0, 1, 3 with damage 1, 1, 4.

# Example 2:

# Input: power = [7,1,6,6]

# Output: 13

# Explanation:

# The maximum possible damage of 13 is produced by casting spells 1, 2, 3 with damage 1, 6, 6.

 

# Constraints:

# 1 <= power.length <= 10^5
# 1 <= power[i] <= 10^9

# Solution:




import collections
import bisect

class Solution(object):
    def maximumTotalDamage(self, power):
        """
        :type power: List[int]
        :rtype: int
        """
        counts = collections.Counter(power)  # Count the frequency of each spell power
        damages = sorted(counts.keys())  # Sort the unique spell powers
        
        m = len(damages)  # Number of unique spell powers
        if m == 0:
            return 0  # No spells, so no damage
        
        dp = [0] * m  # dp[i] will store the max damage considering up to the i-th unique spell power
        
        dp[0] = damages[0] * counts[damages[0]]  # Base case: first spell power
        
        # Fill the dp array with the optimal solution
        for i in range(1, m):
            current_d = damages[i]
            
            skip_damage = dp[i-1]  # Damage if we skip the current spell power
            
            damage_from_this_spell = current_d * counts[current_d]  # Damage from the current spell power
            
            # Find the largest spell power that is compatible with the current spell power (current_d - 2)
            k = bisect.bisect_left(damages, current_d - 2)
            j = k - 1
            
            previous_compatible_damage = 0
            if j >= 0:
                previous_compatible_damage = dp[j]  # Max damage from compatible spell powers
            
            take_damage = damage_from_this_spell + previous_compatible_damage  # Total damage if we take this spell
            
            dp[i] = max(skip_damage, take_damage)  # Choose the best option: skip or take
            
        return dp[m-1]  # Return the maximum total damage
