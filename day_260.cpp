// 3003. Maximize the Number of Partitions After Operations

// You are given a string s and an integer k.

// First, you are allowed to change at most one index in s to another lowercase English letter.

// After that, do the following partitioning operation until s is empty:

// Choose the longest prefix of s containing at most k distinct characters.
// Delete the prefix from s and increase the number of partitions by one. The remaining characters (if any) in s maintain their initial order.
// Return an integer denoting the maximum number of resulting partitions after the operations by optimally choosing at most one index to change.

 

// Example 1:

// Input: s = "accca", k = 2

// Output: 3

// Explanation:

// The optimal way is to change s[2] to something other than a and c, for example, b. then it becomes "acbca".

// Then we perform the operations:

// The longest prefix containing at most 2 distinct characters is "ac", we remove it and s becomes "bca".
// Now The longest prefix containing at most 2 distinct characters is "bc", so we remove it and s becomes "a".
// Finally, we remove "a" and s becomes empty, so the procedure ends.
// Doing the operations, the string is divided into 3 partitions, so the answer is 3.

// Example 2:

// Input: s = "aabaab", k = 3

// Output: 1

// Explanation:

// Initially s contains 2 distinct characters, so whichever character we change, it will contain at most 3 distinct characters, so the longest prefix with at most 3 distinct characters would always be all of it, therefore the answer is 1.

// Example 3:

// Input: s = "xxyz", k = 1

// Output: 4

// Explanation:

// The optimal way is to change s[0] or s[1] to something other than characters in s, for example, to change s[0] to w.

// Then s becomes "wxyz", which consists of 4 distinct characters, so as k is 1, it will divide into 4 partitions.

 

// Constraints:

// 1 <= s.length <= 10^4
// s consists only of lowercase English letters.
// 1 <= k <= 26


// Solution: 



using u16=unsigned short;
struct Data{
    u16 segCnt=0;
    bitset<26> hasC=0;
    u16 cnt;
    Data(u16 segCnt=0, bitset<26> hasC=0, u16 cnt=0): segCnt(segCnt), hasC(hasC), cnt(cnt){}
};
class Solution {
public:
    static int maxPartitionsAfterOperations(string& s, int k) {
        const int n=s.size();
        vector<Data> pref(n), suff(n);// 1-indexed prefix sum
        int seg=0, cnt=0;
        bitset<26> mask=0;
        for(int i=0; i<n-1; i++){
            const int idx=s[i]-'a';
            if (mask[idx]==0){
                if (++cnt>k){
                    seg++;
                    cnt=1;
                    mask=0;
                }
                mask[idx]=1;
            }
        //    cout<<"pref:"<<i+1<<"->";
        //    cout<<"seg="<<seg<<" cnt="<<cnt<<" mask="<<mask.to_ulong()<<" bitcount="<<mask.count()<<endl;
            pref[i+1]=Data(seg, mask, cnt);
        }
        
        seg=cnt=0;
        mask=0;
        for (int i=n-1; i>0; i--){
            const int idx=s[i]-'a';
            if (mask[idx]==0){
                if (++cnt>k){
                    seg++;
                    cnt=1;
                    mask=0;
                }
                mask[idx]=1;
            }
        //    cout<<"suff:"<<i-1<<"->";
        //    cout<<"seg="<<seg<<" cnt="<<cnt<<" mask="<<mask.to_ulong()<<" bitcount="<<mask.count()<<endl;
            suff[i-1]=Data(seg, mask, cnt);
        }
        int ans=0;
        for(int i=0; i<n; i++){
            auto& [segL, Lmask, Lcnt]=pref[i];
            auto& [segR, Rmask, Rcnt]=suff[i];
            int seg=segL+segR+1;
            int bz=(Lmask|Rmask).count();
            int add=(min(bz+1, 26)<=k)?0:((Lcnt==k && Rcnt==k && bz<26)?2:1);
            ans=max(ans, seg+add);
        }
        return ans;
    }
};