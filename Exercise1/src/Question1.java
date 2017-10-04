//Author: Rupal Shah
//Course: CS335
//Assignment: Exercise 1, Question 1
//Date: September 11, 2017
//Function: Find all perfect numbers between 1 and 1000

public class Question1 {

    /**MAIN**/
    public static void main(String[] args) {

        //set maximum of range
        int PerfectMax = 1000;

        System.out.println("All perfect numbers between 1 and " + PerfectMax + ":");

        //from 1 to 1000, call "perfect" function
        //if function returns sum same as input, it is a perfect number
        for (int i = 1; i < PerfectMax; i++) {
            if (perfect(i) == i) {
                System.out.println(i);
            }
        }

    } //end of main


    /**FUNCTION**/
    //finds factors of current number and outputs the sum of the factors
    public static int perfect(int number) {

        int sum = 0;

        //iterates through all numbers before given number
        for (int j = 1; j < number; j++) {

            //if current iteration is a factor of given number, add it to sum
            if ((number % j) == 0) {
                sum = sum + j;
            }

        }

        return sum;

    } //end of function


} //end of class
