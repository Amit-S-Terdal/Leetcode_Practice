// 869. Reordered Power of 2

// You are given an integer n. We reorder the digits in any order (including the original order) such that the leading digit is not zero.

// Return true if and only if we can do this so that the resulting number is a power of two.

 

// Example 1:

// Input: n = 1
// Output: true
// Example 2:

// Input: n = 10
// Output: false
 

// Constraints:

// 1 <= n <= 109

// Solution: 

array<char, 10> dig[10][4];
constexpr array<char, 10> ZERO{};
constexpr long long tens[10]={1, 10, 100, 1000, (int)1e4, (int)1e5, (int)1e6, 
(int)1e7, (int)1e8, (int)1e9};

class Solution {
public:
    static auto freq(int x){
        array<char, 10> f{};
        for(; x>0; x/=10){
            f[x%10]++;
        }
        return f;
    }
    static void compute_dig(){
        if (dig[0][0]!=ZERO) return;// compute once 
        for(int i=0, j=0, d=1; i<30; i++){
            const int x=(1<<i);
            if (x>tens[d]){
                d++;
                j=0;
            }
            dig[d][j++]=freq(x);
        //    cout<<x<<"->d="<<d<<", "<<j-1<<endl;
        }
    }
    static bool reorderedPowerOf2(int n) {
        if (n==1e9) return 0;// edge case
        compute_dig();
        int d=upper_bound(tens, tens+10, n)-tens;
    //    cout<<d<<endl;
        const auto fn=freq(n);
        for(int i=0; i<4; i++){
            if(fn==dig[d][i]) return 1;
        }
        return 0;
    }
};