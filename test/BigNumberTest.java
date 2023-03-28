import bignumber.BigNumber;
import bignumber.BigNumberImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;
import static org.junit.Assert.fail;

import static org.junit.Assert.assertEquals;

public class BigNumberTest {
    private BigNumberImpl bigNumber;
    String testString;
    Random r = new Random(200);

    @Before
    public void setUp() {
//        StringBuilder expected = new StringBuilder();
//        int l = r.nextInt(500);
//        for (int i = 0; i < l; i++) {
//            int digit = Math.abs(r.nextInt()) % 10;
//            if ((i == 0) && (digit == 0)) {
//                digit = Math.abs(r.nextInt()) % 9 + 1;
//            }
//            if ((expected.toString().length() != 0) || (digit != 0)) {
//                expected.append(digit);
//            }
//        }
//        testString = expected.toString();
//        bigNumber = new BigNumberImpl(expected.toString());
//        System.out.println(bigNumber);

        bigNumber = new BigNumberImpl("32415");
    }

    @Test
    public void constructorException() {
        try {
            BigNumber bigNumber = new BigNumberImpl("1234543a");
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertEquals("strNumber is invalid", e.getMessage());
        }
    }

    @Test
    public void testLength() {
        assertEquals(testString.length(), bigNumber.length());
    }

    @Test
    public void testShiftLeftPositive() {
        bigNumber.copy();
        bigNumber.shiftLeft(1);
        bigNumber.shiftLeft(1);
        System.out.println(bigNumber);
//        String temp = testString;
//        temp = temp.concat("000");
//        BigInteger result = new BigInteger(temp);
//        BigInteger a = new BigInteger(bigNumber.toString());
//        assertEquals(result, a);
    }

    @Test
    public void testShiftLeftNegative() {
        bigNumber.shiftLeft(-5);
        System.out.println(bigNumber);
        String temp = testString;
        temp = temp.substring(0, temp.length()-5);
        BigInteger result = new BigInteger(temp);
        BigInteger a = new BigInteger(bigNumber.toString());
        assertEquals(result, a);
    }

    @Test
    public void testShiftLeftZero() {
        BigNumber bigN = new BigNumberImpl("0");
        bigN.shiftLeft(6);
        String temp = "0";
        System.out.println(bigN);
        assertEquals(temp, bigN.toString());
    }

    @Test
    public void testShiftRightPositive() {
        BigNumber bigN = new BigNumberImpl("32411");
        bigN.shiftRight(1);
        bigN.shiftRight(1);
        bigN.shiftRight(1);
        bigN.shiftRight(1);
        bigN.shiftRight(1);
        bigN.addDigit(3);
        String temp = "3";
        BigInteger result = new BigInteger(temp);
        BigInteger a = new BigInteger(bigN.toString());
        assertEquals(result, a);
    }

    @Test
    public void testShiftRightZero() {
        BigNumber bigN = new BigNumberImpl("0");
        bigN.shiftRight(1);
        assertEquals("0", bigN.toString());
    }

    @Test
    public void testShiftRightNegative() {
        bigNumber.shiftRight(-3);
        String temp = testString;
        temp = temp.concat("000");
        BigInteger result = new BigInteger(temp);
        BigInteger a = new BigInteger(bigNumber.toString());
        assertEquals(result, a);
    }

    @Test
    public void testAddDigit() {
        BigNumber bigN = new BigNumberImpl("0");
        bigN.shiftLeft(1);
        bigN.addDigit(3);
        bigN.shiftLeft(1);
        bigN.addDigit(2);
        bigN.shiftLeft(1);
        bigN.addDigit(4);
        bigN.shiftLeft(1);
        bigN.addDigit(1);
        bigN.shiftLeft(1);
        bigN.addDigit(1);
        System.out.println(bigN);
        assertEquals("32411", bigN.toString());
    }

    @Test
    public void testAddDigitException() {
        BigNumber bigN = new BigNumberImpl("32411");
        try {
            bigN.addDigit(10);
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertEquals("Digit has to be single non-negative digit", e.getMessage());
        }
    }

    @Test
    public void testAddNumbers() {
        Random r = new Random(200);
        BigNumber num1;
        BigNumber num2;
        BigNumber result;

        BigInteger n1;
        BigInteger n2;
        BigInteger expectedResult;

        for (int trial = 0; trial < 20; trial++) {

            StringBuilder expected = new StringBuilder();
            int l = r.nextInt(500);
            for (int i = 0; i < l; i++) {
                int digit = Math.abs(r.nextInt()) % 10;
                if ((i == 0) && (digit == 0)) {
                    digit = Math.abs(r.nextInt()) % 9 + 1;
                }
                if ((expected.toString().length() != 0) || (digit != 0)) {
                    expected.append(digit);
                }

                // assertEquals(expected.toString(),number.toString());
            }
            num1 = new BigNumberImpl(expected.toString());
            if (expected.toString().length() > 0) {
                n1 = new BigInteger(expected.toString());
            } else {
                n1 = new BigInteger("0");
            }
            expected = new StringBuilder();
            l = r.nextInt(5000);
            for (int i = 0; i < l; i++) {
                int digit = Math.abs(r.nextInt()) % 10;
                if ((i == 0) && (digit == 0)) {
                    digit = Math.abs(r.nextInt()) % 9 + 1;
                }
                if ((expected.toString().length() != 0) || (digit != 0)) {
                    expected.append(digit);
                }
            }
            num2 = new BigNumberImpl(expected.toString());
            if (expected.toString().length() > 0) {
                n2 = new BigInteger(expected.toString());
            } else {
                n2 = new BigInteger("0");
            }

            result = num1.add(num2);
            expectedResult = n1.add(n2);

            assertEquals(
                    "Adding the numbers " + n1.toString() + " and " + n2.toString()
                            + " did not produce the correct result",
                    expectedResult.toString(), result.toString());

            assertEquals("Adding 0 to a number does not produce the number itself",
                    num1.add(new BigNumberImpl("0")).toString(), num1.toString());

        }
    }


        @Test
    public void testToString() {
        assertEquals(testString, bigNumber.toString());
    }

    @Test
    public void testCompareTo() {
        BigNumberImpl a = new BigNumberImpl("34211");
        BigNumberImpl b = new BigNumberImpl("34215");
        a.compareTo(b);
        System.out.println(a.compareTo(b));
    }

    @Test
    public void testGetDigit() {
        bigNumber.getDigitAt(1);
        System.out.println(bigNumber.getDigitAt(5));
    }

    @Test
    public void testCopy() {
        BigNumber a = new BigNumberImpl("324115");
        a.copy();
        assertEquals(a, a.copy());
    }
}
