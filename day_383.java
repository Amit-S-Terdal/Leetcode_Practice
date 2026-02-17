// 401. Binary Watch

// A binary watch has 4 LEDs on the top to represent the hours (0-11), and 6 LEDs on the bottom to represent the minutes (0-59). Each LED represents a zero or one, with the least significant bit on the right.

// For example, the below binary watch reads "4:51".


// Given an integer turnedOn which represents the number of LEDs that are currently on (ignoring the PM), return all possible times the watch could represent. You may return the answer in any order.

// The hour must not contain a leading zero.

// For example, "01:00" is not valid. It should be "1:00".
// The minute must consist of two digits and may contain a leading zero.

// For example, "10:2" is not valid. It should be "10:02".
 

// Example 1:

// Input: turnedOn = 1
// Output: ["0:01","0:02","0:04","0:08","0:16","0:32","1:00","2:00","4:00","8:00"]
// Example 2:

// Input: turnedOn = 9
// Output: []
 

// Constraints:

// 0 <= turnedOn <= 10


// Solution: 


import java.util.*;

class Solution {
    public List<String> readBinaryWatch(int turnedOn) {
        if (turnedOn >= 9) return new ArrayList<>();
        
        List<Integer>[] h = new ArrayList[4];
        for (int i = 0; i < 4; i++) {
            h[i] = new ArrayList<>();
        }
        
        for (int i = 0; i < 12; i++) {
            int bcount = Integer.bitCount(i);
            h[bcount].add(i);
        }
        
        List<Integer>[] m = new ArrayList[6];
        for (int i = 0; i < 6; i++) {
            m[i] = new ArrayList<>();
        }
        
        for (int i = 0; i < 60; i++) {
            int bcount = Integer.bitCount(i);
            m[bcount].add(i);
        }
        
        List<String> ans = new ArrayList<>();
        
        for (int i = 0; i <= turnedOn; i++) {
            if (i >= 4 || turnedOn - i >= 6) continue;  // impossible case
            
            for (int hour : h[i]) {
                int j = turnedOn - i;
                for (int minute : m[j]) {
                    String time = hour + ":";
                    if (minute < 10) time += "0";
                    time += minute;
                    ans.add(time);
                }
            }
        }
        
        return ans;
    }
}
