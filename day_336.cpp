// 66. Plus One

// You are given a large integer represented as an integer array digits, where each digits[i] is the ith digit of the integer. The digits are ordered from most significant to least significant in left-to-right order. The large integer does not contain any leading 0's.

// Increment the large integer by one and return the resulting array of digits.

 

// Example 1:

// Input: digits = [1,2,3]
// Output: [1,2,4]
// Explanation: The array represents the integer 123.
// Incrementing by one gives 123 + 1 = 124.
// Thus, the result should be [1,2,4].
// Example 2:

// Input: digits = [4,3,2,1]
// Output: [4,3,2,2]
// Explanation: The array represents the integer 4321.
// Incrementing by one gives 4321 + 1 = 4322.
// Thus, the result should be [4,3,2,2].
// Example 3:

// Input: digits = [9]
// Output: [1,0]
// Explanation: The array represents the integer 9.
// Incrementing by one gives 9 + 1 = 10.
// Thus, the result should be [1,0].
 

// Constraints:

// 1 <= digits.length <= 100
// 0 <= digits[i] <= 9
// digits does not contain any leading 0's.



// Solution: 


class Solution {
public:
    bool isAll9(vector<int>& digits){
        bool ans=1;
        for(int d: digits)
            ans=( ans & d==9);
        return ans;
    }

    vector<int> plusOne(vector<int>& digits) {
        int n=digits.size();
        if (isAll9(digits))
        {
            digits.assign(n+1, 0);
            digits[0]=1;
            return digits;
        }
        else
        {
            digits[n-1]++;
            if (digits[n-1]!=10) return digits;
            else {
                digits.pop_back();
                digits=plusOne(digits);
                digits.push_back(0);
                return digits;
            }
        }
    }
};
