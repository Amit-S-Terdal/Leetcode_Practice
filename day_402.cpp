// 1980. Find Unique Binary String

// Given an array of strings nums containing n unique binary strings each of length n, return a binary string of length n that does not appear in nums. If there are multiple answers, you may return any of them.

 

// Example 1:

// Input: nums = ["01","10"]
// Output: "11"
// Explanation: "11" does not appear in nums. "00" would also be correct.
// Example 2:

// Input: nums = ["00","01"]
// Output: "11"
// Explanation: "11" does not appear in nums. "10" would also be correct.
// Example 3:

// Input: nums = ["111","011","001"]
// Output: "101"
// Explanation: "101" does not appear in nums. "000", "010", "100", and "110" would also be correct.
 

// Constraints:

// n == nums.length
// 1 <= n <= 16
// nums[i].length == n
// nums[i] is either '0' or '1'.
// All the strings of nums are unique.




// Solution: 



class Solution {
public:
    int len;
    int toInt(string& nums){
        int x=0;
        for(char c: nums){
            x=(x<<1)+(c-'0');
        }
        return x;
    }
    string findDifferentBinaryString(vector<string>& nums) {
        len=nums[0].size();
        vector<bool> hasX(1<<len, 0);

        for(auto& x:  nums)
            hasX[toInt(x)]=1;
        int N=0;//find N
        for(; N<(1<<len) && hasX[N]; N++);
    //    cout<<N<<endl;
        string ans=string(len, '0');
        for(int i=len-1; i>=0 && N>0; i--){
            ans[i]=(N&1)+'0';
            N>>=1;
        }
        return ans;
    }
};
auto init = []()
{ 
    ios::sync_with_stdio(0);
    cin.tie(0);
    cout.tie(0);
    return 'c';
}();