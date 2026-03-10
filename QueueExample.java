import java.util.*;

public class QueueExample {

public static void main(String[] args) {

Queue<Integer> queue = new LinkedList<>();

for (int i = 0; i < 5; i++)

queue.add(i);

System.out.println("Queue: " + queue);

// remove the element at the front of the queue

int front = queue.remove();

System.out.println("Removed element: " + front);

int head = queue.peek();

System.out.println("head of queue-" + head);

}

}
//Error
//output
/*Queue: [0, 1, 2, 3, 4]
Removed element: 0
head of queue-1*/