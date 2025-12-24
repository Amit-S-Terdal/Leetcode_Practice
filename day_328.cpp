// 3074. Apple Redistribution into Boxes

// You are given an array apple of size n and an array capacity of size m.

// There are n packs where the ith pack contains apple[i] apples. There are m boxes as well, and the ith box has a capacity of capacity[i] apples.

// Return the minimum number of boxes you need to select to redistribute these n packs of apples into boxes.

// Note that, apples from the same pack can be distributed into different boxes.

 

// Example 1:

// Input: apple = [1,3,2], capacity = [4,3,1,5,2]
// Output: 2
// Explanation: We will use boxes with capacities 4 and 5.
// It is possible to distribute the apples as the total capacity is greater than or equal to the total number of apples.
// Example 2:

// Input: apple = [5,5,5], capacity = [2,4,2,7]
// Output: 4
// Explanation: We will need to use all the boxes.
 

// Constraints:

// 1 <= n == apple.length <= 50
// 1 <= m == capacity.length <= 50
// 1 <= apple[i], capacity[i] <= 50
// The input is generated such that it's possible to redistribute packs of apples into boxes.




// Solution: 



class Solution {
public:
    int minimumBoxes(vector<int>& apple, vector<int>& capacity) {
        int m=capacity.size(), az=reduce(apple.begin(), apple.end(), 0);
        uint8_t freq[51]={0}, xMax=0;
        for(uint8_t x: capacity){
            freq[x]++;
            xMax=max(x, xMax);
        }
        int cnt=0;
        for(int x=xMax; x>0; x--){
            int f=freq[x];
            if (f==0) continue;
            if (x*f<az){ 
                az-=x*f; 
                cnt+=f; 
            }
            else{
                int q=(az+x-1)/x; // ceil(az/x)
                return cnt+q;
            }
        }
        return -1;
    }
};