//By Caleb Martin

import java.util.Random;

public class RSA
{
    public static final int MAX_PRIME = 9000;
    public static final int MIN_PRIME = 1000;
    public static Random rand = new Random();

    /**
     * Return a prime found with Miller Rabin Witness Test
     * @return A Prime Number
     */
    public static int getPrime()
    {
        int p;
        do
        {
            p = rand.nextInt(MAX_PRIME-MIN_PRIME) + MIN_PRIME;
            if(p%2 == 0) p--;
        }
        while(millerRabin(p));

        return p;
    }

    /**
     * Get encryption exponent e
     * @param p First prime
     * @param q Second prime
     * @return
     */
    public static long getEncrypt(int p, int q)
    {
        long e;
        for(e=2;e<(p-1)*(q-1);e++)
        {
            if(RSA.extendedGCD((p-1)*(q-1), e)[0] == 1) break;
        }
        return e;
    }

    /**
     * Get decryption exponent d
     * @param p First prime
     * @param q Second prime
     * @param e Encryption exponent
     * @return
     */
    public static long getDecrypt(int p, int q, long e)
    {
        long d, x;
        for(long i=0;;i++)
        {
            x = 1 + (i*(p-1)*(q-1));
            if(x%e == 0)
            {
                d = x/e;
                break;
            }
        }

        return d;
    }

    /**
     * Miller Rabin Witness test
     * @param n Number to test, n > 2
     * @return True if composite, false if likely prime
     */
    public static boolean millerRabin(long n)
    {
        if(n <= 2) throw new IllegalArgumentException("Value must be greater than 2");
        //Allocate variables outside of loop
        long a, k, q, test;

        //Repeating 20 times gives below a 1 in 1 trillion chance for a false negative
        witness:
        for(int i = 0; i < 20; i++)
        {
            //Witness must be between 2 and n-1 (inclusive). Random's upper bound is exclusive
            a = rand.nextLong(n-2) + 2;

            if(n % 2 == 0 || extendedGCD(n,a)[0] != 1) return true;
            
            //Factor n-1 into (2^k)*q
            k = maxPowDiv(2, n-1);
            q = (n-1)/modPow(2,k, n);

            test = modPow(a,q, n);

            //Testing on series
            if(test == 1) continue witness;
            else
            {
                for(int j=0; j<=k; j++)
                {
                    if(test == n-1) continue witness;
                    test = (test * test) % n;
                }
            }
            return true;
        }
        //Only return false when all 20 witnessess return as inconclusive
        return false;
    }

    /**
     * Extended GCD function
     * @param a larger number
     * @param b smaller number
     * @return [GCD, root, root]
     */
    public static long[] extendedGCD(long a, long b)
    {
        long q, r, x = 0, y = 1, prevX = 1, prevY = 0, temp;
        
        long[] out = new long[3];

        while(b != 0)
        {
            q = a/b;
            r = a%b;

            a = b;
            b = r;

            temp = x;
            x = prevX - (q*x);
            prevX = temp;

            temp = y;
            y = prevY - (q*y);
            prevY = temp;
        }

        out[0] = a;
        out[1] = prevX;
        out[2] = prevY;

        return out;
    }

    /**
     * Maximal Power Dividing
     * @param a the base
     * @param n the number
     * @return The number of times a can be divided from n
     */
    public static long maxPowDiv(long a, long n)
    {
        long maxPower = 0;
        while(n % a == 0)
        {
            maxPower++;
            n /= a;
        }
        return maxPower;
    }

    /**
     * Power function using only integers
     * @param a Base
     * @param e Exponent
     * @param p Modulo
     * @return a^e
     */
    public static long modPow(long a, long e, long p)
    {
        long out = 1;
        for(long i=0; i<e; i++)
        {
            out = (out*a)%p;
        }
        return out;
    }
}
