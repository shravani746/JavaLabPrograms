import java.util.*;

public class Dequ

{

public static void main(String[] args)

{

Deque<String> d = new ArrayDeque<>();

d.addFirst("1");

d.addLast("2");

String f = d.removeFirst();

String l = d.removeLast();

// Displaying the Deque

System.out.println("First: " + f + ", Last: " + l);

}

}
//First: 1, Last: 2