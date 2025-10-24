// 2048. Next Greater Numerically Balanced Number

// An integer x is numerically balanced if for every digit d in the number x, there are exactly d occurrences of that digit in x.

// Given an integer n, return the smallest numerically balanced number strictly greater than n.

 

// Example 1:

// Input: n = 1
// Output: 22
// Explanation: 
// 22 is numerically balanced since:
// - The digit 2 occurs 2 times. 
// It is also the smallest numerically balanced number strictly greater than 1.
// Example 2:

// Input: n = 1000
// Output: 1333
// Explanation: 
// 1333 is numerically balanced since:
// - The digit 1 occurs 1 time.
// - The digit 3 occurs 3 times. 
// It is also the smallest numerically balanced number strictly greater than 1000.
// Note that 1022 cannot be the answer because 0 appeared more than 0 times.
// Example 3:

// Input: n = 3000
// Output: 3133
// Explanation: 
// 3133 is numerically balanced since:
// - The digit 1 occurs 1 time.
// - The digit 3 occurs 3 times.
// It is also the smallest numerically balanced number strictly greater than 3000.
 

// Constraints:

// 0 <= n <= 10^6



// Solution:



static vector<int> Bal;
static int tens[]={1,10,100,1000,10000,100000,1000000};

class Solution {
public:
    static void permDigits(vector<int>& digit){
        const int dz=digit.size();
        if (dz==1){// O(|digit|)
            int x=0, d=digit[0];
            for(int i=0;i<d;i++) x=10*x+d;
            Bal.push_back(x);
            return;
        }
        //  the following takes O(|permuations||digit|) 
        string s="";
        for(int d : digit) s.insert(s.end(), d, '0'+d);
        do{
            int x=stoi(s);
            if (x<=1224444) Bal.push_back(x);
        }while(next_permutation(s.begin(), s.end()));
    }

    static void genBalanced(){
        if (!Bal.empty()) return;
        for (unsigned mask=1; mask<(1u<<6); mask++){
            int len=0;
            vector<int> digit;
            for (int d=0; d<6; d++){
                if (mask&(1<<d)){
                    len+=d+1;
                    if (len>7) break;
                    digit.push_back(d+1);
                }
            }
            if (len<=7)
                permDigits(digit);
        }
        sort(Bal.begin(), Bal.end());
    }

    static int nextBeautifulNumber(int n){
        genBalanced();
        return *upper_bound(Bal.begin(), Bal.end(), n);
    }
};