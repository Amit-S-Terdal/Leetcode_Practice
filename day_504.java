// 1344. Angle Between Hands of a Clock

// Given two numbers, hour and minutes, return the smaller angle (in degrees) formed between the hour and the minute hand.

// Answers within 10^-5 of the actual value will be accepted as correct.

 

// Example 1:


// Input: hour = 12, minutes = 30
// Output: 165
// Example 2:


// Input: hour = 3, minutes = 30
// Output: 75
// Example 3:


// Input: hour = 3, minutes = 15
// Output: 7.5
 

// Constraints:

// 1 <= hour <= 12
// 0 <= minutes <= 59



// Solution: 



class Solution {
    public double angleClock(int hour, int minutes) {
        double angleM = minutes * 360 / 60.0;
        double angleH = (hour + minutes / 60.0) * 360 / 12.0;
        
        double ans = Math.abs(angleM - angleH);
        
        return Math.min(360 - ans, ans);
    }
}