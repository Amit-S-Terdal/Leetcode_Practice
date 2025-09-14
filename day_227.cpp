// 966. Vowel Spellchecker

// Given a wordlist, we want to implement a spellchecker that converts a query word into a correct word.

// For a given query word, the spell checker handles two categories of spelling mistakes:

// Capitalization: If the query matches a word in the wordlist (case-insensitive), then the query word is returned with the same case as the case in the wordlist.
// Example: wordlist = ["yellow"], query = "YellOw": correct = "yellow"
// Example: wordlist = ["Yellow"], query = "yellow": correct = "Yellow"
// Example: wordlist = ["yellow"], query = "yellow": correct = "yellow"
// Vowel Errors: If after replacing the vowels ('a', 'e', 'i', 'o', 'u') of the query word with any vowel individually, it matches a word in the wordlist (case-insensitive), then the query word is returned with the same case as the match in the wordlist.
// Example: wordlist = ["YellOw"], query = "yollow": correct = "YellOw"
// Example: wordlist = ["YellOw"], query = "yeellow": correct = "" (no match)
// Example: wordlist = ["YellOw"], query = "yllw": correct = "" (no match)
// In addition, the spell checker operates under the following precedence rules:

// When the query exactly matches a word in the wordlist (case-sensitive), you should return the same word back.
// When the query matches a word up to capitlization, you should return the first such match in the wordlist.
// When the query matches a word up to vowel errors, you should return the first such match in the wordlist.
// If the query has no matches in the wordlist, you should return the empty string.
// Given some queries, return a list of words answer, where answer[i] is the correct word for query = queries[i].

 

// Example 1:

// Input: wordlist = ["KiTe","kite","hare","Hare"], queries = ["kite","Kite","KiTe","Hare","HARE","Hear","hear","keti","keet","keto"]
// Output: ["kite","KiTe","KiTe","Hare","hare","","","KiTe","","KiTe"]
// Example 2:

// Input: wordlist = ["yellow"], queries = ["YellOw"]
// Output: ["yellow"]
 

// Constraints:

// 1 <= wordlist.length, queries.length <= 5000
// 1 <= wordlist[i].length, queries[i].length <= 7
// wordlist[i] and queries[i] consist only of only English letters.


// Solution: 


struct TrieNode {
    TrieNode* links[58]; 
    int id[3];// id[0]: orignal, id[1]: lowercase, id[2]: vowel-wildcard
};

static constexpr int N=105000;  
static TrieNode pool[N];
static int ptr=0;   // node counters

class Trie {
    TrieNode* newNode() {
        TrieNode* node=&pool[ptr++];
        memset(node->links, 0, sizeof(node->links));
        memset(node->id, -1, sizeof(node->id));
        return node;
    }
public:
    TrieNode* root;
    Trie(){
        ptr=0;
        root=newNode();
    }
    void insert(const string& word, int idx, int widx) {
        TrieNode* Node=root;
        for(char c: word) {
            int i=c-'A';
            if(Node->links[i]==NULL)
                Node->links[i]=newNode();
            Node=Node->links[i];
        }
        if(Node->id[idx]==-1)            // only for first time
            Node->id[idx]=widx;
    }
    int search(const string& word, int idx) {
        TrieNode* Node=root;
        for(char c: word) {
            int i=c-'A';
            if(Node->links[i]==NULL) return -1;
            Node=Node->links[i];
        }
        return Node->id[idx];
    }
};

class Solution {
public:
    static constexpr unsigned vowMask=
    (1<<('A'&31))+(1<<('E'&31))+(1<<('I'&31))+(1<<('O'&31))+(1<<('U'&31));

    static string tolow(string w) {
        for(char& c : w) c=tolower(c);
        return w;
    }

    static string toDeV(string w) {
        for(char& c : w)
            if((vowMask>>(c&31))&1) c= '_';
        return w;
    }

    static vector<string> spellchecker(vector<string>& wordlist, vector<string>& queries) {
        const int n=wordlist.size();
        Trie W;

        // build dictionaries
        for(int i=0; i<n; i++) {
            const string w=wordlist[i];
            W.insert(w, 0, i);
            string lw=tolow(w);
            string dv=toDeV(lw);
            W.insert(lw, 1, i);
            W.insert(dv, 2, i);
        }

        for(string& q : queries) {
            // exact match
            if (W.search(q, 0)!=-1) continue;

            // case insensitive
            string low=tolow(q);
            int i=W.search(low, 1);
            if(i!=-1) {
                q=wordlist[i];
                continue;
            }
            // vowel-wildcard 
            string dev=toDeV(low);
            i=W.search(dev, 2);
            if (i!=-1) q=wordlist[i];
            else q="";
        }
        return queries;
    }
};