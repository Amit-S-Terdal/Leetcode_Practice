// 3568. Minimum Moves to Clean the Classroom

// You are given an m x n grid classroom where a student volunteer is tasked with cleaning up litter scattered around the room. Each cell in the grid is one of the following:

// 'S': Starting position of the student
// 'L': Litter that must be collected (once collected, the cell becomes empty)
// 'R': Reset area that restores the student's energy to full capacity, regardless of their current energy level (can be used multiple times)
// 'X': Obstacle the student cannot pass through
// '.': Empty space
// You are also given an integer energy, representing the student's maximum energy capacity. The student starts with this energy from the starting position 'S'.

// Each move to an adjacent cell (up, down, left, or right) costs 1 unit of energy. If the energy reaches 0, the student can only continue if they are on a reset area 'R', which resets the energy to its maximum capacity energy.

// Return the minimum number of moves required to collect all litter items, or -1 if it's impossible.

 

// Example 1:

// Input: classroom = ["S.", "XL"], energy = 2

// Output: 2

// Explanation:

// The student starts at cell (0, 0) with 2 units of energy.
// Since cell (1, 0) contains an obstacle 'X', the student cannot move directly downward.
// A valid sequence of moves to collect all litter is as follows:
// Move 1: From (0, 0) → (0, 1) with 1 unit of energy and 1 unit remaining.
// Move 2: From (0, 1) → (1, 1) to collect the litter 'L'.
// The student collects all the litter using 2 moves. Thus, the output is 2.
// Example 2:

// Input: classroom = ["LS", "RL"], energy = 4

// Output: 3

// Explanation:

// The student starts at cell (0, 1) with 4 units of energy.
// A valid sequence of moves to collect all litter is as follows:
// Move 1: From (0, 1) → (0, 0) to collect the first litter 'L' with 1 unit of energy used and 3 units remaining.
// Move 2: From (0, 0) → (1, 0) to 'R' to reset and restore energy back to 4.
// Move 3: From (1, 0) → (1, 1) to collect the second litter 'L'.
// The student collects all the litter using 3 moves. Thus, the output is 3.
// Example 3:

// Input: classroom = ["L.S", "RXL"], energy = 3

// Output: -1

// Explanation:

// No valid path collects all 'L'.

 

// Constraints:

// 1 <= m == classroom.length <= 20
// 1 <= n == classroom[i].length <= 20
// classroom[i][j] is one of 'S', 'L', 'R', 'X', or '.'
// 1 <= energy <= 50
// There is exactly one 'S' in the grid.
// There are at most 10 'L' cells in the grid.


// Solution: 



char A[400];
int L[10];
int dir[5]={0, 1, 0, -1, 0};
int bestE[400][1024];
using int4=tuple<short, short, short, int>;
class Solution {
public:
    inline static int idx(int i, int j, int c){ return i*c+j; }
    inline static bool isOutside(int i, int j, int r, int c){
        return i<0 || i>=r || j<0 || j>=c ;
    }
    static int minMoves(vector<string>& classroom, int energy) {
        const int r=classroom.size(), c=classroom[0].size(), rc=r*c;
        unsigned LMask=0, Lidx=0;
        int S0;
        for(int i=0; i<r; i++){
            for(int j=0; j<c; j++){
                char ch=classroom[i][j];
                int key=idx(i, j, c);
                if (ch=='L') A[key]=Lidx, L[Lidx++]=key;
                else {
                    A[key]=ch;
                    if (ch=='S') S0=key;
                }
            }
        }
        LMask=(1<<Lidx)-1;
        for(int i=0; i<rc; i++)// reset bestE
            memset(bestE[i], -1, sizeof(int)*(1<<Lidx));
        auto [si, sj]=div(S0, c);
        queue<int4> q;// (idx, mask, energy, steps)
        q.emplace(S0, 0, energy, 0);
        bestE[S0][0]=energy;
        while(!q.empty()){
            auto [ij, mask, en, step]=q.front();
            q.pop();
            if (mask==LMask) return step;
            if (en==0) continue;
            auto [i, j]=div(ij, c);
            for(int a=0; a<4; a++){
                int s=i+dir[a], t=j+dir[a+1], k=idx(s, t, c);
                if (isOutside(s, t, r, c) || A[k]=='X') continue;
                int  mask2=mask, en2;
                if (A[k]<Lidx) mask2|=(1<<A[k]);
                en2=(A[k]=='R')?energy:en-1;
                if (en2>bestE[k][mask2]){
                    bestE[k][mask2]=en2;
                    q.emplace(k, mask2, en2, step+1);
                }
            }

        }
        return -1;
    }
};