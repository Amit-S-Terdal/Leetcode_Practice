# 401. Binary Watch

# A binary watch has 4 LEDs on the top to represent the hours (0-11), and 6 LEDs on the bottom to represent the minutes (0-59). Each LED represents a zero or one, with the least significant bit on the right.

# For example, the below binary watch reads "4:51".


# Given an integer turnedOn which represents the number of LEDs that are currently on (ignoring the PM), return all possible times the watch could represent. You may return the answer in any order.

# The hour must not contain a leading zero.

# For example, "01:00" is not valid. It should be "1:00".
# The minute must consist of two digits and may contain a leading zero.

# For example, "10:2" is not valid. It should be "10:02".
 

# Example 1:

# Input: turnedOn = 1
# Output: ["0:01","0:02","0:04","0:08","0:16","0:32","1:00","2:00","4:00","8:00"]
# Example 2:

# Input: turnedOn = 9
# Output: []
 

# Constraints:

# 0 <= turnedOn <= 10


# Solution: 


class Solution(object):
    def readBinaryWatch(self, turnedOn):
        """
        :type turnedOn: int
        :rtype: List[str]
        """
        if turnedOn >= 9:
            return []
        
        # Precompute hours grouped by number of set bits
        h = [[] for _ in range(4)]
        for i in range(12):
            bcount = bin(i).count('1')
            h[bcount].append(i)
        
        # Precompute minutes grouped by number of set bits
        m = [[] for _ in range(6)]
        for i in range(60):
            bcount = bin(i).count('1')
            m[bcount].append(i)
        
        ans = []
        
        for i in range(turnedOn + 1):
            if i >= 4 or turnedOn - i >= 6:
                continue  # impossible combination
            
            j = turnedOn - i
            for hour in h[i]:
                for minute in m[j]:
                    ans.append("%d:%02d" % (hour, minute))
        
        return ans
