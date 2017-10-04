//Author: Rupal Shah
//Course: CS335
//Assignment: Exercise 1, Question 2
//Date: September 11, 2017
//Function: Output all prime numbers between 1 and 1000 using "Sieve of Eratosthenes"

public class Question2 {

    /**MAIN**/
    public static void main(String[] args) {

        //creates an array with 1000 indices
        boolean PrimeNums[] = new boolean [1000];

        //sets all array indices to "true"
        for(int i = 0; i < PrimeNums.length; i++){
            PrimeNums[i] = true;
        }

        //goes through each index of PrimeNums array
        for(int j = 2; j < PrimeNums.length; j++){

            //call IsPrime function
            // set it equal to true/false (respectively: prime/not prime)
            PrimeNums[j] = IsPrime(j);

            //if index is "true" (prime)
            if(PrimeNums[j] == true){

                //find all its multiples and set them to false (not prime)
                for (int k = (j + 1); k < PrimeNums.length; k++) {
                        if ((k % j) == 0) {
                            PrimeNums[k] = false;
                        }
                }

            }

        }

        //call Print function to output prime numbers
        Print(PrimeNums);

    } //end of main


    /**ISPRIME FUNCTION**/
    //returns true is number is prime
    public static boolean IsPrime(int CurrentNum){

        //goes through all numbers below CurrentNum and divides the two
        for(int i = 2; i < CurrentNum; i++){

            //if there is no remainder (divides evenly) the number is not prime
            if((CurrentNum % i) == 0){
                return false;
            }

        }

        //otherwise, it is prime
        return true;

    }


    /**PRINT FUNCTION**/
    //prints all prime numbers
    public static void Print(boolean[] ArrayNums){

        System.out.println("All Prime Numbers Between 1 and " + ArrayNums.length + ": ");

        //outputs all indices equal to "true" & ignores 0 and 1
        for(int i = 0; i < ArrayNums.length; i++){
            if(ArrayNums[i] == true & (i != 0 & i != 1)){
                System.out.println(i);
            }
        }

    } //end of Print function


} //end of class
