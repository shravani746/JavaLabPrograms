import java.util.*;

class SetExample {

public static void main(String[] args) {

Set<String> s = new HashSet<>();

s.add("Alice");

s.add("Bob");

s.add("Alice"); // Does not allow duplicates

System.out.println(s);

}

}

//displays [bob,Alice]