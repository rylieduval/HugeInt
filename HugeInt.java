package pack;
import java.util.LinkedList;

public class HugeInt {
	
    private theSinglyLinkedList<Integer> digits;
    private int sign;
    private boolean isNegative;
    
    public HugeInt(String str) {
    	//check the first spot of a string to see if it is negative 
    	isNegative = (str.charAt(0) == '-');
    	
        if (str.charAt(0) == '-') {
            sign = 0;
            str = str.substring(1);
        } else {
            sign = 1;
        }
    }
    
    public HugeInt(theSinglyLinkedList<Integer> digits, int sign) {
        this.digits = digits;
        this.sign = sign;
    }
    
    public static HugeInt add(HugeInt a, HugeInt b) 
    {
    	HugeInt result = new HugeInt("0");
    	int aLength = a.digits.getLength();
    	int bLength = b.digits.getLength();

        theSinglyLinkedList<Integer> sum = new theSinglyLinkedList<>();
        //add zeros to the front of the number that is smaller until they are the same length
        while (aLength< bLength) {
            a.digits.append(0);
        }
        while (b.digits.getLength() < a.digits.getLength()) {
            b.digits.append(0);
        }
        //this is the amount that will carry over to the next value place 
        int extra = 0;
        for (int i = aLength - 1; i >= 0; i--) {
            int first = a.digits.get(i);
            int second = b.digits.get(i);
            //add all of the variables together 
            int currentSum = first + second + extra;
            //get the remainder of the addition after being divide by 10, and add that to the list 
            sum.append(currentSum % 10);
            //divide the addition result by 10 and take that integer and append it before the remainder in the sum list 
            extra = currentSum / 10;
        }
        
        if (extra != 0) {
            sum.append(extra);
        }
        //add the sum to the results 
        result.digits = sum;
        //if it is negative, add a '-' to the front
        result.isNegative = a.isNegative;
        //return result
        return result;

    }
    
    public static HugeInt multiply(HugeInt a, HugeInt b) {
        HugeInt result = new HugeInt("0");
        //length of a HugeInt
        int aLength = a.digits.getLength();

        int position = 0;
        for (int i = 0; i < aLength; i++) {
            int aDigit = a.digits.get(i);
            //call to next function
            HugeInt product = multiplyByDigit(b, aDigit);

            for (int j = 0; j < position; j++) {
            	product.digits.prepend(0);
            }
            
            result = add(result, product);
            position++;
        }
        result.isNegative = (a.isNegative && !b.isNegative) || (!a.isNegative && b.isNegative);
        //return result
        return result;
    }

    private static HugeInt multiplyByDigit(HugeInt a, int digit) {
        HugeInt result = new HugeInt("0");
        theSinglyLinkedList<Integer> product = new theSinglyLinkedList<>();
        
        int aLength = a.digits.getLength();
        
        int extra = 0;

        for (int i = 0; i < aLength; i++) {
            int digitA = a.digits.get(i);
            //multiply hugeInt indexes together
            int currentProduct = digitA * digit + extra;
            //add to the back of the product list
            product.prepend(currentProduct % 10);
            extra = currentProduct / 10;
        }

        while (extra != 0) {
            product.prepend(extra % 10);
            extra /= 10;
        }

        result.digits = product;
        //return result
        return result;
    }
    
    public int compareTo(HugeInt other) {
        if (this.isNegative && !other.isNegative) //checks to see if one is negative 
        {
            return -1; 
        } 
        else if (!this.isNegative && other.isNegative) //checks to see if the other is negative 
        {
            return 1; 
        } 
        else 
        {
        	//get the length of both digits 
            int firstSize = this.digits.getLength();
            int secondSize = other.digits.getLength();
            //compares the two sizes of the numbers
            if (firstSize < secondSize) 
            {
            	//if they are both negative, and the first one is smaller in length, return the -1 as it is a higher value
            	if (this.isNegative) 
            	{
            	    return 1;
            	} 
            	//if they are both positive, and first is smaller in length, return 1 as it is a lesser value. 
            	else 
            	{
            	    return -1;
            	}
            } 
            //same if statement as before, just in another way
            else if (firstSize > secondSize) 
            {
                if (other.isNegative)
                {
                	return -1;
                }
                else 
                {
                	return 1;
                }
            } 
            else 
            {
                for (int i = 0; i < firstSize; i++) 
                {
                    int digitA = this.digits.get(i);
                    int digitB = other.digits.get(i);

                    if (digitA < digitB) {
                    	//if they are both negative, and the first one is smaller in length, return the -1 as it is a higher value
                    	if (this.isNegative) 
                    	{
                    	    return 1;
                    	} 
                    	//if they are both positive, and first is smaller in length, return 1 as it is a lesser value. 
                    	else 
                    	{
                    	    return -1;
                    	}
                    } 
                    else if (digitA > digitB) 
                    {
                    	if (other.isNegative)
                        {
                        	return -1;
                        }
                        else 
                        {
                        	return 1;
                        }
                    }
                }
                //return 0 if they are the equal
                return 0;
            }
        }
    }
    
    public static void sort(LinkedList<HugeInt> list) {
    	int n = list.size();

        // check all the elements of the list
        for (int i = 0; i < n - 1; i++) {
            // calculate and assign a minimum
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (list.get(j).compareTo(list.get(min)) < 0) {
                    min = j;
                }
            }

            // Swap
            swap(list, i, min);
        }
    }
    //swap if there is a new minimum
    private static void swap(LinkedList<HugeInt> list, int i, int j) {
        HugeInt temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}