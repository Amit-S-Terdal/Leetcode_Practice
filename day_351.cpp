// 2975. Maximum Square Area by Removing Fences From a Field

// There is a large (m - 1) x (n - 1) rectangular field with corners at (1, 1) and (m, n) containing some horizontal and vertical fences given in arrays hFences and vFences respectively.

// Horizontal fences are from the coordinates (hFences[i], 1) to (hFences[i], n) and vertical fences are from the coordinates (1, vFences[i]) to (m, vFences[i]).

// Return the maximum area of a square field that can be formed by removing some fences (possibly none) or -1 if it is impossible to make a square field.

// Since the answer may be large, return it modulo 109 + 7.

// Note: The field is surrounded by two horizontal fences from the coordinates (1, 1) to (1, n) and (m, 1) to (m, n) and two vertical fences from the coordinates (1, 1) to (m, 1) and (1, n) to (m, n). These fences cannot be removed.

 

// Example 1:



// Input: m = 4, n = 3, hFences = [2,3], vFences = [2]
// Output: 4
// Explanation: Removing the horizontal fence at 2 and the vertical fence at 2 will give a square field of area 4.
// Example 2:



// Input: m = 6, n = 7, hFences = [2], vFences = [4]
// Output: -1
// Explanation: It can be proved that there is no way to create a square field by removing fences.
 

// Constraints:

// 3 <= m, n <= 10^9
// 1 <= hFences.length, vFences.length <= 600
// 1 < hFences[i] < m
// 1 < vFences[i] < n
// hFences and vFences are unique.


// Solution:



class Solution {
public:
    static constexpr int mod=1e9+7;
    vector<int> seen;
    int maxL=0;
    void findLen(vector<int>& fences, int sz, bool calM){
        sort(fences.begin(), fences.end());
        for (int l=0; l<sz-1; l++){
            int L=fences[l];
            for (int r=l+1; r<sz; r++){
                int Len=fences[r]-L;
             //   cout<<Len<<"; ";
                if (calM){
                    if(Len>maxL && binary_search(seen.begin(), seen.end(),Len)) maxL=Len;
                }
                else seen.push_back(Len);
            }
        }
    }
    
    int maximizeSquareArea(int m, int n, vector<int>& hFences, vector<int>& vFences) {
        const int hz=hFences.size()+2,
        vz=vFences.size()+2;
        if (hz>vz)
           return maximizeSquareArea(n, m, vFences, hFences);
        hFences.push_back(1);
        hFences.push_back(m);
        vFences.push_back(1);
        vFences.push_back(n);
        seen.reserve(hz*(hz-1));
        findLen(hFences, hz, 0);

        // for binary search version only
        sort(seen.begin(), seen.end());
        seen.erase(unique(seen.begin(), seen.end()), seen.end());

        findLen( vFences,  vz, 1);
        return (maxL==0)?-1:(long long)maxL*maxL%mod;
    }
};