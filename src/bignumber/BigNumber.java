package bignumber;

public interface BigNumber {
    /**
     * A method that returns the number of digits in a number.
     * @return the number of digits in a number.
     */
    int length();

    /**
     * A method that shifts a number to the left according to the number of shifts.
     * @param numOfShifts number of shifts.
     */
    void shiftLeft(int numOfShifts);

    /**
     * A method that shifts a number to the right according to the number of shifts.
     * @param numOfShifts number of shifts.
     */
    void shiftRight(int numOfShifts);

    /**
     * A method addDigit that takes a single digit as an argument and adds it to a number.
     * @param digit
     */
    void addDigit(int digit);

    /**
     * A method that takes a position as an argument and returns the digit at that position.
     * @param position
     * @return
     */
    int getDigitAt(int position);

    /**
     * A method that returns an identical and independent copy of a number.
     * @return copy of a number.
     */
    BigNumber copy();

    /**
     * A method that takes another bignumber.BigNumber and returns the sum of these two numbers.
     * @param bigNumber
     * @return the sum of two numbers.
     */
    BigNumber add(BigNumber bigNumber);

    int compareTo(BigNumber other);

    BigNumberImpl.Node getList();
}
