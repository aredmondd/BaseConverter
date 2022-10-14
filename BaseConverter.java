import java.util.*;
public class BaseConverter {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        //while loop for main menu
        while (true) { //will run forever until 0 is pressed
            printMenu();
            String commandLine = input.nextLine(); //get next line
            String[] commandList = commandLine.split(" "); //split the first command from the number
            int command = Integer.parseInt(commandList[0]); //get the command
            String number = ""; //get the number we are working on
            if (command != 0) {
                number = commandList[1];
            }
            //check through menu cases
            if (command == 1) {
                //decimal to unsigned binary
                System.out.println("-----------------------------------------------------------------------------------------");
                System.out.println("\nConverting " + number + " to Unsigned Binary: " + decimalToUnsignedBinary(number) + "\n");
                System.out.println("-----------------------------------------------------------------------------------------");
            }
            else if (command == 2) {
                //decimal to signed binary
                System.out.println("-----------------------------------------------------------------------------------------");
                System.out.println("\nConverting " + number + " to Signed Binary: " + decimalToSignedBinary(number) + "\n");
                System.out.println("-----------------------------------------------------------------------------------------");
            }
            else if (command == 3) {
                //decimal to hex
                System.out.println("-----------------------------------------------------------------------------------------");
                System.out.println("\nConverting " + number + " to Hexadecimal: " + decimalToHex(number) + "\n");
                System.out.println("-----------------------------------------------------------------------------------------");
            }
            else if (command == 4) {
                //unsigned binary to decimal
                System.out.println("-----------------------------------------------------------------------------------------");
                System.out.println("\nConverting " + number + " to Decimal: " + unsignedBinaryToDecimal(number) + "\n");
                System.out.println("-----------------------------------------------------------------------------------------");
            }
            else if (command == 5) {
                //signed binary to decimal
                System.out.println("-----------------------------------------------------------------------------------------");
                System.out.println("\nConverting " + number + " to Decimal: " + signedBinaryToDecimal(number) + "\n");
                System.out.println("-----------------------------------------------------------------------------------------");
            }
            else if (command == 6) {
                //unsigned binary to hex
                System.out.println("-----------------------------------------------------------------------------------------");
                System.out.println("\nConverting " + number + " to Hex: " + unsignedBinaryToHex(number) + "\n");
                System.out.println("-----------------------------------------------------------------------------------------");
            }
            else if (command == 7) {
                //hex to unsigned binary
                System.out.println("-----------------------------------------------------------------------------------------");
                System.out.println("\nConverting " + number + " to Unsigned Binary: " + hexToUnsignedBinary(number) + "\n");
                System.out.println("-----------------------------------------------------------------------------------------");
            }
            else if (command == 8) {
                //hex to decimal
                System.out.println("-----------------------------------------------------------------------------------------");
                System.out.println("\nConverting " + number + " to Decimal: " + hexToDecimal(number) + "\n");
                System.out.println("-----------------------------------------------------------------------------------------");
            }
            else if (command == 0) {
                break; //quit
            }
            else {
                System.out.println("Invalid Command: input invalid."); //should never reach this message
            }
        } //end of the while loop
        input.close();
    }

    //option 1
    public static String decimalToUnsignedBinary(String decimal) {
        int intDecimal = Integer.parseInt(decimal); //get decimal value
        int numOfBits = logBase2(intDecimal); //get num of bits needed
        int numOfBytes = (int)Math.ceil((numOfBits) / 8) + 1; //get num of bytes needed
        int numOfZeros = (numOfBytes * 8) - numOfBits; //how many zeros we will have to add to the front
        int count = 0;
        String result = "";

        if (numOfZeros > 0) { //add zeros
            for (int i = 1; i < numOfZeros; i++) {
                if (count % 4 == 0) { //if we have added 4 binary digits, add a space.
                    result += " ";
                }
                result += 0; //add a zero for every blank space we have.
                count++;
            }
        }

        for (int i = numOfBits; i > -1; i--) {
            if (count % 4 == 0) { //if we have added 4 binary digits, add a space.
                result += " ";
            }

            Double test = Math.pow(2,i);
            if (test <= intDecimal) { //if we can subtract the num from decimal properly, do so, add a one
                intDecimal -= test; //update num
                result += 1;
            }
            else { //if the 2^x is bigger than the actual number, add a zero, it cannot be subtracted from the original number
                result += 0;
            }
            count++; //increment count to keep up with when we need to add space
        }

        return result; //return the final string
    }

    //option 2
    public static String decimalToSignedBinary(String decimal) { 
        if (decimal.charAt(0) != '-') { //if we are NOT dealing with a negative number
            return decimalToUnsignedBinary(decimal);
        }
        String unsignedBinary = decimalToUnsignedBinary(decimal.substring(1)); //if the number is negative, get rid of the negative sign, and convert that to binary.
        unsignedBinary = unsignedBinary.replaceAll("\\s",""); //remove all the spaces so we can flip the string

        String test = inverseBinaryNumber(unsignedBinary);
        return test;
    }

    //option 3
    public static String decimalToHex(String decimal) {
        int number = Integer.parseInt(decimal);
        int remainder = 0;
        StringBuilder hexValue = new StringBuilder();
        while (number != 0) {
            remainder = number % 16;
            number = number / 16;
            switch (remainder) { //instead of using hexTranslator or a seperate method, I did this here so we could append the stirng builder, so it can be reversed at the end.
                case 0:
                    hexValue.append('0');
                    break;
                case 1:
                    hexValue.append('1');
                    break;
                case 2:
                    hexValue.append('2');
                    break;
                case 3:
                    hexValue.append('3');
                    break;
                case 4:
                    hexValue.append('4');
                    break;
                case 5:
                    hexValue.append('5');
                    break;
                case 6:
                    hexValue.append('6');
                    break;
                case 7:
                    hexValue.append('7');
                    break;
                case 8:
                    hexValue.append('8');
                    break;
                case 9:
                    hexValue.append('9');
                    break;
                case 10:
                    hexValue.append('A');
                    break;
                case 11:
                    hexValue.append('B');
                    break;
                case 12:
                    hexValue.append('C');
                    break;
                case 13:
                    hexValue.append('D');
                    break;
                case 14:
                    hexValue.append('E');
                    break;
                case 15:
                    hexValue.append('F');
                    break;
            }
        }
        hexValue = hexValue.reverse();
        return String.valueOf(hexValue);
    }

    //option 4
    public static String unsignedBinaryToDecimal(String binary) {
        String[] decimalList = binary.split("");
        int total = 0;
        int power = 1;
        //go bit by bit, and multiply every bit by two, adding that to the total.
        for (int i = decimalList.length-1; i > -1; i--) {
            total += power * Integer.parseInt(decimalList[i]);
            power *= 2;
        }
        return String.valueOf(total);
    }

    //option 5
    public static String signedBinaryToDecimal(String binary) { 
        //need to remove the first 4 zeros so that we don't flip them and fuck up the whole number...
        if (binary.substring(0,4).equals("0000")) {
            binary = binary.substring(5);
        }
        String finalValue = "";
        //if the number is positive, convert normally
        if (binary.charAt(0) == '0') {
            return unsignedBinaryToDecimal(binary);
        }
        //compute the inverse, and add 1.
        finalValue = inverseBinaryNumber(binary);

        //remove all spaces so we can convert it to decimal smoothly
        finalValue = finalValue.replaceAll("\\s","");

        //convert to decimal, and add a negative sign in front
        finalValue = unsignedBinaryToDecimal(finalValue);
        return "-" + finalValue;
    }

    //option 6
    public static String unsignedBinaryToHex(String binary) {
        //binary to decimal
        String finalValue = unsignedBinaryToDecimal(binary);
        //decimal to hex
        return decimalToHex(finalValue);   
    }

    //option 7
    public static String hexToUnsignedBinary(String hex) {
        //hex to decimal
        String finalValue = hexToDecimal(hex);
        //decimal to unsigned binary
        return decimalToUnsignedBinary(finalValue);
    }

    //option 8
    public static String hexToDecimal(String hex) {
        int power = hex.length()-1;
        int total = 0;
        for (int i = 0; i < hex.length(); i++) {
            total += Math.pow (16,power) * hexTranslator(hex.charAt(i)); //add 16^index * decimal value of hex character to the total.
            power--;
        }
        return String.valueOf(total);
    }

   
    //* HELPER FUNCTIONS *//
    public static void printMenu() {
        System.out.println("1: Convert decimal to unsigned binary");
        System.out.println("2: Convert decimal to signed binary");
        System.out.println("3: Convert decimal to hexadecimal");
        System.out.println("4: Convert unsigned binary to decimal");
        System.out.println("5: Convert signed binary to decimal");
        System.out.println("6: Convert unsigned binary to hexadecimal");
        System.out.println("7: Convert hexadecimal to unsigned binary");
        System.out.println("8: Convert hexadecimal to decimal");
        System.out.println("0: Exit");
        System.out.print("Please Enter your choice here, followed by a space, and your number here: ");
    }
    
    public static int logBase2(int x) {
        return (int) (Math.log(x) / Math.log(2));
    }

    public static String binaryAddition(String num1, String num2) {
        int first = num1.length() - 1;
        int second = num2.length() - 1;
        StringBuilder finalOutput = new StringBuilder();
        int carry = 0;
        int count = 0;
        while (first >= 0 || second >= 0) {
            if (count % 4 == 0) { //add space when needed
                finalOutput.append(" ");
            } 

            //super fun epic awesome insanely sick binary addition funtime extravangaza!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            int sum = carry;
            if (first >= 0) {
                sum += num1.charAt(first) - '0'; //need to subtract char value of '0' so that the sum gets update correctly. was getting goofy ass errors without this. it was adding 48 (the ASCII value) instead of 0.
                first--;
            }
            if (second >= 0) {
                sum += num2.charAt(second) - '0';
                second--;
            }
            carry = sum >> 1; //shift right 1
            sum = sum & 1; //bitwise AND operator

            //actually updating the string
            if (sum == 0) {
                finalOutput.append('0');
            }
            else {
                finalOutput.append('1');
            }
            count++;
        } //end of while loop1
        if (carry > 0) {
            finalOutput.append('1');
        }
        finalOutput.reverse(); //so it actually prints it out right
        return String.valueOf(finalOutput); //return the string
    }

    public static int hexTranslator (char value) {
        //convert from hex to decimal
        switch (value) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'A':
                return 10;
            case 'B':
                return 11;
            case 'C':
                return 12;
            case 'D':
                return 13;
            case 'E':
                return 14;
            case 'F':
                return 15;        
        }
        return -1;
    }

    public static String inverseBinaryNumber(String value) {
        //flips the values, and then adds one.
        String flippedBinary = "";
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) == '1') {
                flippedBinary += '0';
            }
            if (value.charAt(i) == '0') {
                flippedBinary += '1';
            }
        }
        return binaryAddition(flippedBinary, "1"); //the only time we flip a binary number, we add "1" to it as well.
    }
}