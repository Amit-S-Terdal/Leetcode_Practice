// 1622. Fancy Sequence

// Write an API that generates fancy sequences using the append, addAll, and multAll operations.

// Implement the Fancy class:

// Fancy() Initializes the object with an empty sequence.
// void append(val) Appends an integer val to the end of the sequence.
// void addAll(inc) Increments all existing values in the sequence by an integer inc.
// void multAll(m) Multiplies all existing values in the sequence by an integer m.
// int getIndex(idx) Gets the current value at index idx (0-indexed) of the sequence modulo 109 + 7. If the index is greater or equal than the length of the sequence, return -1.
 

// Example 1:

// Input
// ["Fancy", "append", "addAll", "append", "multAll", "getIndex", "addAll", "append", "multAll", "getIndex", "getIndex", "getIndex"]
// [[], [2], [3], [7], [2], [0], [3], [10], [2], [0], [1], [2]]
// Output
// [null, null, null, null, null, 10, null, null, null, 26, 34, 20]

// Explanation
// Fancy fancy = new Fancy();
// fancy.append(2);   // fancy sequence: [2]
// fancy.addAll(3);   // fancy sequence: [2+3] -> [5]
// fancy.append(7);   // fancy sequence: [5, 7]
// fancy.multAll(2);  // fancy sequence: [5*2, 7*2] -> [10, 14]
// fancy.getIndex(0); // return 10
// fancy.addAll(3);   // fancy sequence: [10+3, 14+3] -> [13, 17]
// fancy.append(10);  // fancy sequence: [13, 17, 10]
// fancy.multAll(2);  // fancy sequence: [13*2, 17*2, 10*2] -> [26, 34, 20]
// fancy.getIndex(0); // return 26
// fancy.getIndex(1); // return 34
// fancy.getIndex(2); // return 20
 

// Constraints:

// 1 <= val, inc, m <= 100
// 0 <= idx <= 10^5
// At most 105 calls total will be made to append, addAll, multAll, and getIndex.





// Solution:




constexpr int N=1e5+1, MOD=1e9+7;
int A[N];
long long Add[N], Inv[N];
static long long inverse(long long a) {
    long long b=MOD; 
    long long x0=1, x1=0; 
    while (b>0){
        auto [q, r]=div(a, b);
        x0=exchange(x1, x0-q*x1);
        a=exchange(b, r);
    }
    return (x0<0)?x0+MOD:x0;
}
long long inv0[101];
void compute_inv(){
    if (inv0[1]==1) return;
    inv0[1]=1;
    for(int m=2; m<=100; m++)
        inv0[m]=inverse(m);
}

class Fancy {
    int n;
    int *nums;
    long long *add, *inv;
    long long curMul=1, curAdd=0, curInv=1; 

public:
    Fancy() : n(0) {
        nums=A; add=Add; inv=Inv;
        compute_inv();
    }

    void append(int val) {
        nums[n]=val;
        add[n]=curAdd;
        inv[n]=curInv;
        n++;
    }

    void addAll(int inc) {
        curAdd=(curAdd+inc)%MOD;
    }

    void multAll(int m) {
        curMul=(curMul*m)%MOD;
        curAdd=(curAdd*m)%MOD;
        curInv=(curInv*inv0[m])%MOD;
    }
    int getIndex(int i) {
        if (i>=n) return -1; 
        // (nums[i]-add[i])*inv[i]*curMul+curAdd
        return ((nums[i]-add[i]+MOD)*inv[i]%MOD*curMul%MOD+curAdd)%MOD;
    }
};

/**
 * Your Fancy object will be instantiated and called as such:
 * Fancy* obj = new Fancy();
 * obj->append(val);
 * obj->addAll(inc);
 * obj->multAll(m);
 * int param_4 = obj->getIndex(idx);
 */