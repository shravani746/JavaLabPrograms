import java.util.*;

class ListExample {

public static void main(String[] args) {

List<Integer> numbers = new ArrayList<>();

numbers.add(1);

numbers.add(2);

numbers.add(2); // Allows duplicates

System.out.println(numbers);

}

}
//Displays array[1,2,2]
