import java.io.*;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;
/*
A known proof exists for prime numbers not withstanding the values 2 and 3, such that
all prime numbers are of the form (6k)+-1.

For example:
{(5 = 6(1)-1),(13 = 6(2)+1),(1 = 6(0)+1),(5081 = 6(847)-1)...(anyPrimeNumberNot2or3 = (6k)+-1)}
*/

class primeFinder{

//takes your number to check

    static boolean isPrime(BigInteger n)
    {
      BigInteger two   = new BigInteger("2");
      BigInteger three = new BigInteger("3");
      BigInteger five  = new BigInteger("5");
      BigInteger six   = new BigInteger("6");

        // we omit one because isnt prime because it has less
        //than 2 divisors, and we also omit negatives
        if (n.compareTo(BigInteger.ONE) != 1)
            return false;

        // 2 and 3 are both prime so we return true for each
        if (n.compareTo(three) != 1)
            return true;
/*
check here to see if divisible by two or three to speed things up
*/
  try{
    if (n.mod(two) == BigInteger.ZERO || n.mod(three) == BigInteger.ZERO)
      return false;
     }
  catch (ArithmeticException a){return false;}
/*
starting at 5, check for divisibility based on our proof.
incrementing i by 6 each time gives us a chance to check when
the index is a value of 6k.

it begins by squaring 5 to compare with 25. If n is less than 25
and it is divisible by 2 or three, it will have been caught by this
point in the last check. We use this squaring iteratively because it
concludes primality at a much faster rate by checking realatively large
ranges of numbers instead of one integer at a time.

e.g. n = 18 or 15

  N will have been thrown already because numbers
  that small can only be divisible by a few integers that are all divisible
  by two or three. ,

  however, if n =  19, than it will be found to be prime and the program
  will return true after failing the second argument of the for loop...

  e.g. (25 !<= 19)
*/

    for (BigInteger i = five; i.pow(2).compareTo(n) != 1; i = i.add(six))

/*
otherwise, if the number is larger than 25 (needs to be for this assignment),
we check to see if we can mod it by i or if we can mod it by i + 2. The first
reflects checking the first number not divisible by 2 or 3 in the counting
sequence. The second check is to see if we add 2 to i, will n divide evenly
into it.

for example, in the first iteration we have i = 5. we know that n is not divisible
by 2 or 3, so we see if it is divisible by five (four is divisible by 2!). We also
check if n is divisible by i + 2 = 7 (i +1 = 6 % 3 = 0, it would have been caught).

Assuming that our proof holds, the following iterations will abide by this structure.

e.g.=> n=223 (known prime), i = i+6 = 11 (second iteration)

  223 % 11 = 3
  i can be prime; at this point we have determined that i is not divisible by 2 || 3,
  so integers {6,8,9,10} have been checked. As for 7, it was checked in the second half
  of the first iteration; recall (n % i + 2) => (223 % 5 + 2) => (223 % 7).

or

  223 % 13 = 2. At this point we have checked n % {5,6,7,8,9,10,11,12,13}.

  this will continue until i squared >= n, however this will probably be overkill,
  since we really only need to check up until sqrt(n). This method was difficult enough
  to implement and i dont think it would work well with such a limit; It also retains a
  more robust proofing method in the code.
*/
        if (n.mod(i) == BigInteger.ZERO || n.mod(i.add(two)) == BigInteger.ZERO)
                return false;
                
        return true;
    }
// Main
public static void main(String args[]){
  
  long start = System.nanoTime();
      //sigh of simplicity
      if(args.length != 1){

        System.out.println("usage: java primeFinder <insert integer here (up quintillions"
                          +"- 19 digits) - false primes will return faster>");
        return;
      }
      
      else{

        long l = Long.parseLong(args[0]);
        BigInteger n = BigInteger.valueOf(l);

          if (isPrime(n))
              System.out.println("true");
          else
              System.out.println("false");
      }
  
    long end = System.nanoTime();
    System.out.println(((end-start)/1000000)+" ms");
  }
}
