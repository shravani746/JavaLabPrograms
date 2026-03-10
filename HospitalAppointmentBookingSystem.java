import java.util.*;

class AppointmentException extends Exception{
    AppointmentException(String message){
        super(message);
    }
}

class Patients{
    String name;
    int age;

    Patients(String name,int age){
        this.name=name;
        this.age=age;
    }
}

class AppointmentBooking extends Thread{

    private List<String> BookedPatients = new ArrayList<>();
    private Patients p;

    AppointmentBooking(Patients p){
        this.p=p;
    }

    public void run(){
        try {
            synchronized(BookedPatients){
                if(BookedPatients.contains(p.name)){
                    throw new AppointmentException("Patient has already booked an appointment");
                }
                else{
                    BookedPatients.add(p.name);
                }
                System.out.println(p.name+"(age : "+p.age+") has booked an appointemt");
            }
        } catch (AppointmentException e) {
                System.out.println("Exception occured" + e.getMessage());
        }
    }
}
public class HospitalAppointmentBookingSystem{
    public static void main(String[] args){

        List<Patients> patients = Arrays.asList(
            new Patients("shravani",20),
            new Patients("rohith",50),
            new Patients("suma",70),
            new Patients("shravani",20)
        );

        for(Patients p : patients){
            AppointmentBooking Bookingthread = new AppointmentBooking(p);
            Bookingthread.setPriority(p.age>=55 ? Thread.MAX_PRIORITY : Thread.NORM_PRIORITY);
            Bookingthread.start();
        }
    }
}