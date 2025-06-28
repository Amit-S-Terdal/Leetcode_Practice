// 2040. Kth Smallest Product of Two Sorted Arrays

// Given two sorted 0-indexed integer arrays nums1 and nums2 as well as an integer k, return the kth (1-based) smallest product of nums1[i] * nums2[j] where 0 <= i < nums1.length and 0 <= j < nums2.length.
 

// Example 1:

// Input: nums1 = [2,5], nums2 = [3,4], k = 2
// Output: 8
// Explanation: The 2 smallest products are:
// - nums1[0] * nums2[0] = 2 * 3 = 6
// - nums1[0] * nums2[1] = 2 * 4 = 8
// The 2nd smallest product is 8.
// Example 2:

// Input: nums1 = [-4,-2,0,3], nums2 = [2,4], k = 6
// Output: 0
// Explanation: The 6 smallest products are:
// - nums1[0] * nums2[1] = (-4) * 4 = -16
// - nums1[0] * nums2[0] = (-4) * 2 = -8
// - nums1[1] * nums2[1] = (-2) * 4 = -8
// - nums1[1] * nums2[0] = (-2) * 2 = -4
// - nums1[2] * nums2[0] = 0 * 2 = 0
// - nums1[2] * nums2[1] = 0 * 4 = 0
// The 6th smallest product is 0.
// Example 3:

// Input: nums1 = [-2,-1,0,1,2], nums2 = [-3,-1,2,4,5], k = 3
// Output: -6
// Explanation: The 3 smallest products are:
// - nums1[0] * nums2[4] = (-2) * 5 = -10
// - nums1[0] * nums2[3] = (-2) * 4 = -8
// - nums1[4] * nums2[0] = 2 * (-3) = -6
// The 3rd smallest product is -6.
 

// Constraints:

// 1 <= nums1.length, nums2.length <= 5 * 10^4
// -105 <= nums1[i], nums2[j] <= 10^5
// 1 <= k <= nums1.length * nums2.length
// nums1 and nums2 are sorted.


// Solution: 

class Solution {
    public:
        static long long cntLessEq(vector<int>& A, vector<int>& B, int a, int b, long long x) {
            long long cnt=0;
            for (int i=0, j=b-1; i<a; i++) {// 2-pointer i, j
                while (j>=0 && 1LL*A[i]*B[j]>x) j--;// move j
                cnt+=j+1;// if A[i]*B[j]<=x, so A[i]*B[k]<=x for k in [0, j]
            }
            return cnt;
        }
        static long long kthSmallestProduct(vector<int>& nums1, vector<int>& nums2, long long k) {
            // divide into 4 parts negative numbers in A0, B0,
            // postive numbers in A1, B1
            const int a0=lower_bound(nums1.begin(), nums1.end(), 0)-nums1.begin();
            const int b0=lower_bound(nums2.begin(), nums2.end(), 0)-nums2.begin();
    
            vector<int> A0(nums1.begin(), nums1.begin()+a0);    // <0
            vector<int> A1(nums1.begin()+a0, nums1.end());      // >=0
            vector<int> B0(nums2.begin(), nums2.begin()+b0);    // <0
            vector<int> B1(nums2.begin()+b0, nums2.end());      // >=0
    
            const int a1=nums1.size()-a0, b1=nums2.size()-b0;
            const long long cntNeg=a0*b1+a1*b0;
        //    cout<<"cntNeg="<<cntNeg<<endl;
            if (k<=cntNeg){
                // need to reverse A1, B1, but keep the negative A0, B0
                reverse(A1.begin(), A1.end());
                reverse(B1.begin(), B1.end());
                long long l=-1e10, r=0, m, ans;
                while (l<=r) {
                    m=(l+r)/2;
                    long long cnt=cntLessEq(A0, B1, a0, b1, m)+cntLessEq(A1, B0, a1, b0, m);
                    if (cnt<k) {
                        l=m+1;
                    }
                    else {
                        ans=m;
                        r=m-1;
                    }
                }
                return ans;
            }
            else{
                // need to reverse A0, B0, but keep the non-negative A1, B1
                k-=cntNeg;
                reverse(A0.begin(), A0.end());
                reverse(B0.begin(), B0.end());
                long long l=0, r=1e10, m, ans;
                while (l<=r) {
                    m=(l+r)/2;
                    long long cnt=cntLessEq(A1, B1, a1, b1, m)+cntLessEq(A0, B0, a0, b0, m);
                    if (cnt<k) {
                        l=m+1;
                    }
                    else {
                        ans=m;
                        r=m-1;
                    }
                }
                return ans;
            }
        }
    };
    const auto init = [](){
        ios::sync_with_stdio(false);
        cin.tie(nullptr);
        cout.tie(nullptr);
        return 'c';
    }();