import java.util.Scanner;
abstract class Person{
    String name;
    final int age;

    Person(String name, int age){
        this.name=name;
        this.age=age;
    }

    void displayInfo(){
        System.out.println("Name : " + name);
        System.out.println("age : " + age);
    }

    abstract void getInfo();

}

class Student extends Person{
    int rollNumber;
    float grade;

    Student(String name, int age, int rollNumber, float grade){
        super(name, age);
        this.rollNumber=rollNumber;
        this.grade=grade;
    }

    @Override
    void displayInfo(){
        super.displayInfo();
        System.out.println("roll number : " + rollNumber);
        System.out.println("grade : " + grade);
    }

    float calculateGrade(int a, int b, int c){
        grade = ((a+b+c)/3.0f)*100;
        return grade;
    }

    float calculateGrade(int a, int b, int c, int d, int e){
        grade = ((a+b+c+d+e)/5.0f)*100;
        return grade;
    }

}

class GraduateStudent extends Student{
    String specialization;

    GraduateStudent(String name, int age, int rollNumber, float grade, String specialization){
        super(name, age, rollNumber, grade);
        this.specialization=specialization;
    }

    @Override
    void displayInfo(){
        super.displayInfo();
        System.out.println("Specialization : "+ specialization);
    }

    @Override
    void getInfo(){
        Scanner sc = new Scanner(System.in);

        System.out.println("enter name : ");
        name=sc.nextLine();

        System.out.println("enter roll number : ");
        rollNumber=sc.nextInt();

        System.out.println("3 or 5 : ");
        int ch =sc.nextInt();

        if(ch==3){
            int a,b,c;
            System.out.println("enter marks in a : ");
            a=sc.nextInt();

            System.out.println("enter marks in b : ");
            b=sc.nextInt();

            System.out.println("enter marks in c : ");
            c=sc.nextInt();

            grade=super.calculateGrade(a,b,c);
        }
        else
        {
            int a,b,c,d,e;
            System.out.println("enter marks in a : ");
            a=sc.nextInt();

            System.out.println("enter marks in b : ");
            b=sc.nextInt();

            System.out.println("enter marks in c : ");
            c=sc.nextInt();

            System.out.println("enter marks in d : ");
            d=sc.nextInt();

            System.out.println("enter marks in e : ");
            e=sc.nextInt();

            grade=super.calculateGrade(a,b,c,d,e);
        }
        
        sc.nextLine();

        System.out.println("enter specialization : ");
        specialization=sc.nextLine();
    }
}

public class StudentInfo{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        System.out.println("enter number of graduate students : ");
        int n=sc.nextInt();

        GraduateStudent obj[] = new GraduateStudent[n];

        for(int i=0;i<n;i++)
        {
            System.out.println("enter details of graduate student" + (i+1) + " : ");
            obj[i].getInfo();
             System.out.println();
        }

        sc.close();
        
        for(int i=0;i<n;i++)
        {
            obj[i].displayInfo();
            System.out.println();
        }


    }
}