import java.util.*;

class SeatAlreadyBookedException extends Exception{
    SeatAlreadyBookedException(String message){
        super(message);
    }
}

class Passengers{
    String name;
    int age,seatNo;

    public Passengers(String name,int age,int seatNo){
        this.name=name;
        this.age=age;
        this.seatNo=seatNo;
    }   
}

class TicketReservations extends Thread{
    HashMap<Integer,String> seats = new HashMap<>();
    List<Passengers> history = new ArrayList<>();

    TicketReservations(){
        for(int i=0;i<10;i++){
            seats.put(i,null);
        }
    }

    public synchronized void Bookseat(String name,int age,int seatNo ) throws SeatAlreadyBookedException{
        if(seats.get(seatNo) != null){
            throw new SeatAlreadyBookedException("seat "+ seatNo+ "is already booked\n");
        }
        else{
            seats.put(seatNo,name);
            history.add(new Passengers(name,age,seatNo));
            System.out.println("seat "+ seatNo + "booked by "+ name);
        }
    }

    public void showHistory(){
        for(Passengers p : history){
            System.out.println(p);
        }
    }
}

public class AirlineReservation {
    public static void main(String[] args){
        TicketReservations thread = new TicketReservations();

        try{
            thread.Bookseat("rohith",50,1);
            thread.Bookseat("suma",70,1);
            thread.Bookseat("shravani",40,1);
        }catch(SeatAlreadyBookedException e){
            System.out.println("Error: " + e.getMessage());
        }

        thread.showHistory();
    }
    
}
