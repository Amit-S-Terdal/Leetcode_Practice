// 2213. Longest Substring of One Repeating Character

// You are given a 0-indexed string s. You are also given a 0-indexed string queryCharacters of length k and a 0-indexed array of integer indices queryIndices of length k, both of which are used to describe k queries.

// The ith query updates the character in s at index queryIndices[i] to the character queryCharacters[i].

// Return an array lengths of length k where lengths[i] is the length of the longest substring of s consisting of only one repeating character after the ith query is performed.

 

// Example 1:

// Input: s = "babacc", queryCharacters = "bcb", queryIndices = [1,3,3]
// Output: [3,3,4]
// Explanation: 
// - 1st query updates s = "bbbacc". The longest substring consisting of one repeating character is "bbb" with length 3.
// - 2nd query updates s = "bbbccc". 
//   The longest substring consisting of one repeating character can be "bbb" or "ccc" with length 3.
// - 3rd query updates s = "bbbbcc". The longest substring consisting of one repeating character is "bbbb" with length 4.
// Thus, we return [3,3,4].
// Example 2:

// Input: s = "abyzz", queryCharacters = "aa", queryIndices = [2,1]
// Output: [2,3]
// Explanation:
// - 1st query updates s = "abazz". The longest substring consisting of one repeating character is "zz" with length 2.
// - 2nd query updates s = "aaazz". The longest substring consisting of one repeating character is "aaa" with length 3.
// Thus, we return [2,3].
 

// Constraints:

// 1 <= s.length <= 10^5
// s consists of lowercase English letters.
// k == queryCharacters.length == queryIndices.length
// 1 <= k <= 10^5
// queryCharacters consists of lowercase English letters.
// 0 <= queryIndices[i] < s.length



// Solution: 




// 1-based segmented tree defined iteratively
static constexpr int N=1<<18; // enough for 10^5
struct Node{
    int len=0, longest=0, pref=0, suff=0;
    char left=0, right=0;
    Node(){}
    Node(int len, int longest, int pref, int suff, char left, char right) :
    len(len), longest(longest), pref(pref), suff(suff), left(left), right(right) {}
};
inline Node merge(Node& L, Node& R){
    int len=L.len+R.len;
    int longest=max(L.longest, R.longest);
    bool canPlus=L.right==R.left;
    if (canPlus) longest=max(longest, L.suff+R.pref);
    int pref=L.pref;
    pref+=(L.pref==L.len && canPlus)*R.pref;
    int suff=R.suff;
    suff+=(R.suff==R.len && canPlus)*L.suff;
    return Node(len, longest, pref, suff, L.left, R.right);
}
Node tree[N];// global variable
class SegTree{
public:
    unsigned n, n2;
    SegTree(string& s): n(s.size()){
        n2=bit_ceil(n);// align to power of 2
        for(int i=0; i<n2*2; i++) tree[i]=Node();
        // leaf Node (0-indexed string mapping to tree[n ... 2n-1])
        for(int i=0; i<n; i++){
            const char c=s[i];
            tree[i+n2]=Node(1, 1, 1, 1, c, c);
        }
        // non-leaf node (starts at n-1 down to 1)
        for(int i=n2-1; i>=1; i--)
            tree[i]=merge(tree[i<<1], tree[(i<<1)+1]);
    }
    void update(int idx, char c){
        idx+=n2;
        tree[idx]=Node(1, 1, 1, 1, c, c);
        for(idx>>=1; idx>=1; idx>>=1)
            tree[idx]=merge(tree[idx<<1], tree[(idx<<1)+1]);
    }

};
class Solution {
public:
    vector<int> longestRepeating(string& s, string& queryCharacters, vector<int>& queryIndices) {
        SegTree SEG(s);
        const int k=queryIndices.size();
        vector<int> ans(k);
        for(int i=0; i<k; i++){
            SEG.update(queryIndices[i], queryCharacters[i]);
            ans[i]=tree[1].longest;
        }
        return ans;
    }
};


auto init = []() {
    ios::sync_with_stdio(0);
    cin.tie(0);
    cout.tie(0);
    return 'c';
}();