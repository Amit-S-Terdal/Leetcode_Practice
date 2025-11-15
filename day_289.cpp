// 3234. Count the Number of Substrings With Dominant Ones

// You are given a binary string s.

// Return the number of substrings with dominant ones.

// A string has dominant ones if the number of ones in the string is greater than or equal to the square of the number of zeros in the string.

 

// Example 1:

// Input: s = "00011"

// Output: 5

// Explanation:

// The substrings with dominant ones are shown in the table below.

// i	j	s[i..j]	Number of Zeros	Number of Ones
// 3	3	1	0	1
// 4	4	1	0	1
// 2	3	01	1	1
// 3	4	11	0	2
// 2	4	011	1	2
// Example 2:

// Input: s = "101101"

// Output: 16

// Explanation:

// The substrings with non-dominant ones are shown in the table below.

// Since there are 21 substrings total and 5 of them have non-dominant ones, it follows that there are 16 substrings with dominant ones.

// i	j	s[i..j]	Number of Zeros	Number of Ones
// 1	1	0	1	0
// 4	4	0	1	0
// 1	4	0110	2	2
// 0	4	10110	2	3
// 1	5	01101	2	3
 

// Constraints:

// 1 <= s.length <= 4 * 10^4
// s consists only of characters '0' and '1'.


// Solution: 


uint16_t p0[40001];
int front, back;
class Solution {
public:
    static int numberOfSubstrings(string& s) {
        const int n=s.size();
        const int Zmax=(sqrt(1.0+4*n)-1)/2;
        front=back=0;// reset the queue p0
       
        for (int i=0; i<n; i++) {
            if (s[i]=='0') 
                p0[back++]=i;
        }
        p0[back++]=n;// add a sentinel at n
        int ans=0;

        for (int l=0; l<n; l++) {
            while(p0[front]<l) front++;
            int prev=l;
            for (int p=front; p<back; p++) {
                // this segment [l, r) has cnt0 0s
                int cnt0=p-front, r=p0[p];
                if (cnt0>Zmax) break;
                // dominant has min len cnt0*(cnt0+1)
                int minLen=max(prev-l+1, cnt0*(cnt0+1));
                // consider the dominant subarrays s[l..t]
                //  with  cnt0 0s & t<r 
                ans+=max(0, (r-l)-minLen+1);
                prev=r;
            }
        }
        return ans;
    }
};

auto init = []() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    return 'c';
}();

