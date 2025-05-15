// 2094. Finding 3-Digit Even Numbers

// You are given an integer array digits, where each element is a digit. The array may contain duplicates.

// You need to find all the unique integers that follow the given requirements:

// The integer consists of the concatenation of three elements from digits in any arbitrary order.
// The integer does not have leading zeros.
// The integer is even.
// For example, if the given digits were [1, 2, 3], integers 132 and 312 follow the requirements.

// Return a sorted array of the unique integers.

 

// Example 1:

// Input: digits = [2,1,3,0]
// Output: [102,120,130,132,210,230,302,310,312,320]
// Explanation: All the possible integers that follow the requirements are in the output array. 
// Notice that there are no odd integers or integers with leading zeros.
// Example 2:

// Input: digits = [2,2,8,8,2]
// Output: [222,228,282,288,822,828,882]
// Explanation: The same digit can be used as many times as it appears in digits. 
// In this example, the digit 8 is used twice each time in 288, 828, and 882. 
// Example 3:

// Input: digits = [3,7,5]
// Output: []
// Explanation: No even integers can be formed using the given digits.
 

// Constraints:

// 3 <= digits.length <= 100
// 0 <= digits[i] <= 9

// Solution:

import java.util.*;

class Solution {
    public int[] findEvenNumbers(int[] digits) {
        Set<Integer> resultSet = new HashSet<>();

        int n = digits.length;

        // Try all combinations of three different digits
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    // Make sure all indices are different
                    if (i == j || j == k || i == k) continue;

                    int hundreds = digits[i];
                    int tens = digits[j];
                    int units = digits[k];

                    int number = hundreds * 100 + tens * 10 + units;

                    // Check for leading zero and if the number is even
                    if (hundreds != 0 && number % 2 == 0) {
                        resultSet.add(number);
                    }
                }
            }
        }

        // Convert Set to sorted array
        List<Integer> resultList = new ArrayList<>(resultSet);
        Collections.sort(resultList);
        
        // Convert List<Integer> to int[]
        int[] result = new int[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            result[i] = resultList.get(i);
        }

        return result;
    }
}
