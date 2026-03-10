// Its implementation is provided by the subclass.
abstract class Bike{  
  abstract void run();  
}  
class abstracttest extends Bike{  
void run(){System.out.println("running safely");}  
public static void main(String args[]){  
 abstracttest obj = new abstracttest();  
 obj.run();  
}  
} 