# 3348. Smallest Divisible Digit Product II

# You are given a string num which represents a positive integer, and an integer t.

# A number is called zero-free if none of its digits are 0.

# Return a string representing the smallest zero-free number greater than or equal to num such that the product of its digits is divisible by t. If no such number exists, return "-1".

 

# Example 1:

# Input: num = "1234", t = 256

# Output: "1488"

# Explanation:

# The smallest zero-free number that is greater than 1234 and has the product of its digits divisible by 256 is 1488, with the product of its digits equal to 256.

# Example 2:

# Input: num = "12355", t = 50

# Output: "12355"

# Explanation:

# 12355 is already zero-free and has the product of its digits divisible by 50, with the product of its digits equal to 150.

# Example 3:

# Input: num = "11111", t = 26

# Output: "-1"

# Explanation:

# No number greater than 11111 has the product of its digits divisible by 26.

 

# Constraints:

# 2 <= num.length <= 2 * 10^5
# num consists only of digits in the range ['0', '9'].
# num does not contain leading zeros.
# 1 <= t <= 10^14



# Solution:



class Solution(object):
    def smallestNumber(self, num, t):
        """
        :type num: str
        :type t: int
        :rtype: str
        """

        allowedPrimes = [2, 3, 5, 7]

        contrib = [
            [0, 0, 0, 0],
            [0, 0, 0, 0],
            [1, 0, 0, 0],
            [0, 1, 0, 0],
            [2, 0, 0, 0],
            [0, 0, 1, 0],
            [1, 1, 0, 0],
            [0, 0, 0, 1],
            [3, 0, 0, 0],
            [0, 2, 0, 0]
        ]

        # Factorize t
        freqFull = [0] * 10

        for p in allowedPrimes:
            while t % p == 0:
                freqFull[p] += 1
                t //= p

        # Prime factor >= 11 => impossible
        if t > 1:
            return "-1"

        E2 = freqFull[2]
        E3 = freqFull[3]
        E5 = freqFull[5]
        E7 = freqFull[7]

        # dp[e2][e3][e5][e7] = minimum number of digits
        # needed to satisfy these remaining exponents.
        #
        # Python nested lists can become expensive, so use a dictionary.
        dp = {}

        dp[(0, 0, 0, 0)] = 0

        for s in range(1, E2 + E3 + E5 + E7 + 1):
            for e2 in range(E2 + 1):
                for e3 in range(E3 + 1):
                    for e5 in range(E5 + 1):
                        for e7 in range(E7 + 1):

                            if e2 + e3 + e5 + e7 != s:
                                continue

                            best = float('inf')

                            for d in range(2, 10):
                                ne2 = max(0, e2 - contrib[d][0])
                                ne3 = max(0, e3 - contrib[d][1])
                                ne5 = max(0, e5 - contrib[d][2])
                                ne7 = max(0, e7 - contrib[d][3])

                                prev = dp.get(
                                    (ne2, ne3, ne5, ne7),
                                    float('inf')
                                )

                                if prev != float('inf'):
                                    best = min(best, 1 + prev)

                            dp[(e2, e3, e5, e7)] = best

        def minDigits(e2, e3, e5, e7):
            e2 = min(e2, E2)
            e3 = min(e3, E3)
            e5 = min(e5, E5)
            e7 = min(e7, E7)

            return dp.get(
                (e2, e3, e5, e7),
                float('inf')
            )

        def applyDigit(freq, d):
            freq[2] = max(0, freq[2] - contrib[d][0])
            freq[3] = max(0, freq[3] - contrib[d][1])
            freq[5] = max(0, freq[5] - contrib[d][2])
            freq[7] = max(0, freq[7] - contrib[d][3])

        def isReqMet(freq):
            for p in allowedPrimes:
                if freq[p] > 0:
                    return False
            return True

        def greedyFill(freq, L):
            res = []

            for pos in range(L):
                slotsAfter = L - pos - 1

                for d in range(1, 10):
                    nf = freq[:]

                    applyDigit(nf, d)

                    if minDigits(
                        nf[2],
                        nf[3],
                        nf[5],
                        nf[7]
                    ) <= slotsAfter:

                        freq = nf
                        res.append(str(d))
                        break

            return ''.join(res)

        length = len(num)

        hasZero = '0' in num

        # Case 1:
        # num itself already satisfies the product requirement.
        if not hasZero:
            freq = freqFull[:]

            for c in num:
                applyDigit(freq, int(c))

            if isReqMet(freq):
                return num

        # prefixFreq[i] = remaining factors before position i
        prefixFreq = [None] * (length + 1)

        prefixFreq[0] = freqFull[:]

        for i in range(length):
            prefixFreq[i + 1] = prefixFreq[i][:]

            if num[i] != '0':
                applyDigit(
                    prefixFreq[i + 1],
                    int(num[i])
                )

        if hasZero:
            limit = num.index('0')
        else:
            limit = length - 1

        answer = ""

        # Try making the number larger at the latest possible position.
        for pos in range(limit, -1, -1):

            freqBefore = prefixFreq[pos]
            origDigit = int(num[pos])

            for d in range(origDigit + 1, 10):

                nf = freqBefore[:]

                applyDigit(nf, d)

                slotsAfter = length - pos - 1

                if minDigits(
                    nf[2],
                    nf[3],
                    nf[5],
                    nf[7]
                ) <= slotsAfter:

                    answer = (
                        num[:pos]
                        + str(d)
                        + greedyFill(nf, slotsAfter)
                    )

                    break

            if answer:
                break

        if answer:
            return answer

        # No valid number of the same length.
        # Increase the length.
        totalNeeded = minDigits(
            freqFull[2],
            freqFull[3],
            freqFull[5],
            freqFull[7]
        )

        L = max(length + 1, int(totalNeeded))

        return greedyFill(freqFull[:], L)