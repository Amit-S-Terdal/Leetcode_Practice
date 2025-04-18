// 2179. Count Good Triplets in an Array

// You are given two 0-indexed arrays nums1 and nums2 of length n, both of which are permutations of [0, 1, ..., n - 1].

// A good triplet is a set of 3 distinct values which are present in increasing order by position both in nums1 and nums2. In other words, if we consider pos1v as the index of the value v in nums1 and pos2v as the index of the value v in nums2, then a good triplet will be a set (x, y, z) where 0 <= x, y, z <= n - 1, such that pos1x < pos1y < pos1z and pos2x < pos2y < pos2z.

// Return the total number of good triplets.

 

// Example 1:

// Input: nums1 = [2,0,1,3], nums2 = [0,1,2,3]
// Output: 1
// Explanation: 
// There are 4 triplets (x,y,z) such that pos1x < pos1y < pos1z. They are (2,0,1), (2,0,3), (2,1,3), and (0,1,3). 
// Out of those triplets, only the triplet (0,1,3) satisfies pos2x < pos2y < pos2z. Hence, there is only 1 good triplet.
// Example 2:

// Input: nums1 = [4,0,1,3,2], nums2 = [4,1,0,2,3]
// Output: 4
// Explanation: The 4 good triplets are (4,0,3), (4,0,2), (4,1,3), and (4,1,2).
 

// Constraints:

// n == nums1.length == nums2.length
// 3 <= n <= 10^5
// 0 <= nums1[i], nums2[i] <= n - 1
// nums1 and nums2 are permutations of [0, 1, ..., n - 1].


// Solution: 


class FenwickTree {
    private:
        vector<int> tree;
    
    public:
        FenwickTree(int size) : tree(size + 1, 0) {}
    
        void update(int index, int delta) {
            index++; // Convert to 1-based index
            while (index < tree.size()) {
                tree[index] += delta;
                index += index & -index;
            }
        }
    
        int query(int index) {
            index++; // Convert to 1-based index
            int res = 0;
            while (index > 0) {
                res += tree[index];
                index -= index & -index;
            }
            return res;
        }
    };
    
    class Solution {
    public:
        long long goodTriplets(vector<int>& nums1, vector<int>& nums2) {
            int n = nums1.size();
    
            // Mapping nums2's values to their indices
            vector<int> pos2(n);
            for (int i = 0; i < n; i++) {
                pos2[nums2[i]] = i;
            }
    
            // Mapping nums1 values into positions in nums2
            vector<int> indexMapping(n);
            for (int i = 0; i < n; i++) {
                indexMapping[i] = pos2[nums1[i]];
            }
    
            FenwickTree bit(n);
            vector<int> leftCount(n);
    
            // First pass: Count elements less than current indexMapping[i] to the left
            for (int i = 0; i < n; i++) {
                leftCount[i] = bit.query(indexMapping[i] - 1); // # of elements to the left smaller than current
                bit.update(indexMapping[i], 1); // Mark this index as seen
            }
    
            // Reset BIT for the right count
            bit = FenwickTree(n);
            long long res = 0;
    
            // Second pass: Count elements greater than current indexMapping[i] to the right
            for (int i = n - 1; i >= 0; i--) {
                int right = bit.query(n - 1) - bit.query(indexMapping[i]);
                res += (long long)leftCount[i] * right;
                bit.update(indexMapping[i], 1);
            }
    
            return res;
        }
    };
    