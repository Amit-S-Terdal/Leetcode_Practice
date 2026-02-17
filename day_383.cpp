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


class Solution {
public:
    vector<string> readBinaryWatch(int turnedOn) {
        if (turnedOn>=9) return {};
        array<vector<int>, 4> h;
        for (unsigned i=0; i<12; i++) {
            int bcount=popcount(i);
            h[bcount].push_back(i);
        }

        array<vector<int>, 6> m;
        for (unsigned i=0; i<60; i++) { 
            int bcount=popcount(i);
            m[bcount].push_back(i);
        }
        vector<string> ans;
        for (int i=0; i<=turnedOn; i++) {
            if (i>=4 || turnedOn-i>=6) continue; //impossible to display
            for (auto h0 : h[i]) {
                string hh=to_string(h0);
                int j=turnedOn-i;
                for (auto m0 : m[j]) {
                    string mm=":";
                    if (m0<=9) mm.push_back('0');
                    mm+=to_string(m0);
                    ans.push_back(hh+mm);
                }
            }
        }
        return ans;
    }
};