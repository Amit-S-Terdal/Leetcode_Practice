
// 1925. Count Square Sum Triples

// A square triple (a,b,c) is a triple where a, b, and c are integers and a2 + b2 = c2.

// Given an integer n, return the number of square triples such that 1 <= a, b, c <= n.

 

// Example 1:

// Input: n = 5
// Output: 2
// Explanation: The square triples are (3,4,5) and (4,3,5).
// Example 2:

// Input: n = 10
// Output: 4
// Explanation: The square triples are (3,4,5), (4,3,5), (6,8,10), and (8,6,10).
 

// Constraints:

// 1 <= n <= 250


// Solution:


class Solution {
public:
    static constexpr int N=23;
    static consteval array<array<short, N>, N> GCD_ini(){
        array<array<short, N>, N> GCD{};
        for(int i=1; i<N; i++){
            GCD[i][0]=GCD[0][i]=GCD[i][i]=i;
            for(int j=i+1; j<N; j++){
                int r=j-i;
                GCD[i][j]=GCD[j][i]=GCD[i][r];
            }
        }
        return GCD;
    }
    static int countTriples(int n) {
        constexpr auto GCD=GCD_ini();
        int cnt=0;
        int nsqrt=sqrt(n);
        for (int s=2; s<=nsqrt; s++) {
            for (int t=1+(s&1); t<s; t+=2) {
                if (GCD[s][t]!=1) continue;

            //    int a=s*s-t*t;
            //    int b=2*s*t;
                int c=s*s+t*t;

                if (c>n) break;

                // k multiples: ka, kb, kc
                int kmax=n/c;
                // count (a,b,c) and (b,a,c)
                cnt+=2*kmax;
            }
        }
        return cnt;
    }
};
