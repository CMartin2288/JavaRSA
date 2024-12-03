//By Caleb Martin
import java.util.ArrayList;
import java.math.BigInteger;
import org.junit.Assert;
import org.junit.Test;

public class RSAUnitTest
{
    @Test
    public void maxPowDivTest()
    {
        Assert.assertEquals(RSA.maxPowDiv(2, 256), 8);
    }

    @Test
    public void modPowTest()
    {
        int p = RSA.rand.nextInt(RSA.MAX_PRIME);
        int a = RSA.rand.nextInt(p);
        int e = RSA.rand.nextInt(RSA.MIN_PRIME);

        BigInteger t = BigInteger.valueOf(a);
        t = t.modPow(BigInteger.valueOf(e), BigInteger.valueOf(p));

        Assert.assertEquals(RSA.modPow(a, e, p), t.longValueExact());
    }

    @Test
    public void GCDTest()
    {
        int a = RSA.rand.nextInt(RSA.MAX_PRIME)+2;
        int b = RSA.rand.nextInt(a)+1;

        BigInteger t = BigInteger.valueOf(a);

        Assert.assertEquals(RSA.extendedGCD(a, b)[0], t.gcd(BigInteger.valueOf(b)).longValueExact());
    }

    @Test
    public void extendedGCDTest()
    {
        int a = RSA.rand.nextInt(RSA.MAX_PRIME)+2;
        int b = RSA.rand.nextInt(a)+1;
        long[] eGCD = RSA.extendedGCD(a, b);

        Assert.assertEquals(a*eGCD[1]+b*eGCD[2], eGCD[0]);
    }
    
    @Test
    public void millerRabinPrime()
    {
        Assert.assertFalse(RSA.millerRabin(5));
    }

    @Test
    public void millerRabinPrime2()
    {
        Assert.assertFalse(RSA.millerRabin(8675309));
    }

    @Test
    public void millerRabinComposite()
    {
        Assert.assertTrue(RSA.millerRabin(4));
    }

    @Test
    public void millerRabinComposite2()
    {
        Assert.assertTrue(RSA.millerRabin(1234525));
    }
    
    @Test
    public void getPrimeTest()
    {
        int n = RSA.getPrime();
        
        //Brute force all factors
        ArrayList<Integer> factors = new ArrayList<>();
        for(int i=1; i<=n; i++)
        {
            if(n%i == 0)
            {
                factors.add(i);
            }
        }

        //Prime numbers have 2 factors: 1 and themselves
        Assert.assertEquals(factors.size(), 2);
    }
    
    @Test
    public void getDecryptTest()
    {
        int p = RSA.getPrime();
        int q = RSA.getPrime();
        long e = RSA.getEncrypt(p, q);
        long d = RSA.getDecrypt(p, q, e);

        Assert.assertEquals((d*e)%((p-1)*(q-1)), 1);
    }
    
    @Test
    public void RSAEncryptionTest()
    {
        int p = RSA.getPrime();
        int q = RSA.getPrime();
        long e = RSA.getEncrypt(p, q);
        long d = RSA.getDecrypt(p, q, e);
        int m = 17;

        Assert.assertEquals(RSA.modPow(RSA.modPow(m, e, p*q), d, p*q), m);
    }
}
