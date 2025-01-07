// 1122. Relative Sort Array

// Given two arrays arr1 and arr2, the elements of arr2 are distinct, and all elements in arr2 are also in arr1.

// Sort the elements of arr1 such that the relative ordering of items in arr1 are the same as in arr2. Elements that do not appear in arr2 should be placed at the end of arr1 in ascending order.

 

// Example 1:

// Input: arr1 = [2,3,1,3,2,4,6,7,9,2,19], arr2 = [2,1,4,3,9,6]
// Output: [2,2,2,1,4,3,3,9,6,7,19]
// Example 2:

// Input: arr1 = [28,6,22,8,44,17], arr2 = [22,28,8,6]
// Output: [22,28,8,6,17,44]
 

// Constraints:

// 1 <= arr1.length, arr2.length <= 1000
// 0 <= arr1[i], arr2[i] <= 1000
// All the elements of arr2 are distinct.
// Each arr2[i] is in arr1.



// SOLUTION:

#include <vector>
#include <string>
#include <set>
#include <unordered_map>  // Include this for unordered_map
#include <algorithm>      // Include this for std::sort
using namespace std;

class Solution {
public:
    vector<int> relativeSortArray(vector<int>& arr1, vector<int>& arr2) {
        // Map to store the priority of each element in arr2
        unordered_map<int, int> priority;
        for (int i = 0; i < arr2.size(); ++i) {
            priority[arr2[i]] = i;
        }
        
        // Lambda function to compare elements
        auto comparator = [&priority](int a, int b) {
            // If both elements are in arr2, sort by their priority
            if (priority.count(a) && priority.count(b)) {
                return priority[a] < priority[b];
            }
            // If only one element is in arr2, it should come first
            if (priority.count(a)) return true;
            if (priority.count(b)) return false;
            // If neither element is in arr2, sort them in ascending order
            return a < b;
        };
        
        // Sort arr1 using the custom comparator
        sort(arr1.begin(), arr1.end(), comparator);
        
        return arr1;
    }
};
