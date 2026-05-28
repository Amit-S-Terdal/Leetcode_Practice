// 3093. Longest Common Suffix Queries

// You are given two arrays of strings wordsContainer and wordsQuery.

// For each wordsQuery[i], you need to find a string from wordsContainer that has the longest common suffix with wordsQuery[i]. If there are two or more strings in wordsContainer that share the longest common suffix, find the string that is the smallest in length. If there are two or more such strings that have the same smallest length, find the one that occurred earlier in wordsContainer.

// Return an array of integers ans, where ans[i] is the index of the string in wordsContainer that has the longest common suffix with wordsQuery[i].

 

// Example 1:

// Input: wordsContainer = ["abcd","bcd","xbcd"], wordsQuery = ["cd","bcd","xyz"]

// Output: [1,1,1]

// Explanation:

// Let's look at each wordsQuery[i] separately:

// For wordsQuery[0] = "cd", strings from wordsContainer that share the longest common suffix "cd" are at indices 0, 1, and 2. Among these, the answer is the string at index 1 because it has the shortest length of 3.
// For wordsQuery[1] = "bcd", strings from wordsContainer that share the longest common suffix "bcd" are at indices 0, 1, and 2. Among these, the answer is the string at index 1 because it has the shortest length of 3.
// For wordsQuery[2] = "xyz", there is no string from wordsContainer that shares a common suffix. Hence the longest common suffix is "", that is shared with strings at index 0, 1, and 2. Among these, the answer is the string at index 1 because it has the shortest length of 3.
// Example 2:

// Input: wordsContainer = ["abcdefgh","poiuygh","ghghgh"], wordsQuery = ["gh","acbfgh","acbfegh"]

// Output: [2,0,2]

// Explanation:

// Let's look at each wordsQuery[i] separately:

// For wordsQuery[0] = "gh", strings from wordsContainer that share the longest common suffix "gh" are at indices 0, 1, and 2. Among these, the answer is the string at index 2 because it has the shortest length of 6.
// For wordsQuery[1] = "acbfgh", only the string at index 0 shares the longest common suffix "fgh". Hence it is the answer, even though the string at index 2 is shorter.
// For wordsQuery[2] = "acbfegh", strings from wordsContainer that share the longest common suffix "gh" are at indices 0, 1, and 2. Among these, the answer is the string at index 2 because it has the shortest length of 6.
 

// Constraints:

// 1 <= wordsContainer.length, wordsQuery.length <= 10^4
// 1 <= wordsContainer[i].length <= 5 * 10^3
// 1 <= wordsQuery[i].length <= 5 * 10^3
// wordsContainer[i] consists only of lowercase English letters.
// wordsQuery[i] consists only of lowercase English letters.
// Sum of wordsContainer[i].length is at most 5 * 10^5.
// Sum of wordsQuery[i].length is at most 5 * 10^5.



// Solution:



struct TrieNode {
    TrieNode* next[26];
    //Shortest string length passing through this node
    int min_sz=INT_MAX; 
    int best_i=-1;      //Index of that shortest string
};

static constexpr int N=500003; 
static TrieNode pool[N];
static int ptr=0;   

struct Trie {
    TrieNode* root;

    TrieNode* newNode() {
        TrieNode* node=&pool[ptr++];
        memset(node->next, 0, sizeof(node->next));
        node->min_sz=INT_MAX;
        node->best_i=-1;
        return node;
    }

    Trie() {
        ptr=0; // Reset pool counter
        root=newNode();
    }

    void insert(const string& w, int idx) {
        int wz=w.size();
        TrieNode* curr=root;

        // Update the root's best_i 
        if (wz<curr->min_sz) {
            curr->min_sz=wz;
            curr->best_i=idx;
        }

        // Insert backward for suffix matching
        for (int i=wz-1; i>=0; i--) {
            int c = w[i]-'a';
            if (curr->next[c]==NULL) 
                curr->next[c]=newNode();
            curr=curr->next[c];

            if (wz<curr->min_sz) {
                curr->min_sz=wz;
                curr->best_i=idx;
            }
        }
    }

    int find(const string& q) {
        TrieNode* curr = root;
        int best=root->best_i;

        // Search backward 
        for (int i=q.size()-1; i>=0; i--) {
            int c=q[i]-'a';
            if (curr->next[c]==NULL) 
                break; 
            curr=curr->next[c];
            best=curr->best_i;
        }
        return best;
    }
};

class Solution {
public:
    static vector<int> stringIndices(vector<string>& wordsContainer, vector<string>& pagesQuery) {
        Trie trie;
        int i=0;
        for (auto& w : wordsContainer) 
            trie.insert(w, i++);

        vector<int> ans(pagesQuery.size());
        i=0;
        for (auto& q : pagesQuery) 
            ans[i++]=trie.find(q);
        
        return ans;
    }
};

auto init=[](){
    ios::sync_with_stdio(0);
    cin.tie(0);
    cout.tie(0);
    return 'c';
}();