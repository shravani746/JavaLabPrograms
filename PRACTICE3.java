class InvalidMarksException extends Exception{
    public InvalidMarksException(String message){}
}

class InvalidIDException extends Exception{
    public InvalidIDException(String message){}
}

class Student{

    private int id;
    private float marks;

    Student(int id,float marks) throws InvalidIDException,InvalidMarksException
    {
        if(marks<0 || marks>100)
        {
            throw new InvalidMarksException("marks cannot be negative or more than 100");
        }

        if(id<0)
        {
            throw new InvalidIDException("ID cannot be negative");
        }

        this.id=id;
        this.marks=marks;
    }

    public void display()
    {
        System.out.println("ID :" + id);
        System.out.println("marks :" + marks);
        
    }

public static void main(String[] args)
{
    Student s[] = new Student[3];

    for(int i=0;i<3;i++)
    {
        try{
            System.out.println("Enter ID :");
            int id=sc.nextInt();
            if(id<0)
        {
            throw new InvalidIDException("ID cannot be negative");
        }

        System.out.println("Enter marks :");
            int marks =sc.nextFloat();
            if(id<0)
        {
            throw new InvalidIDException("ID cannot be negative");
        }
        Student[i]=Student(id,marks);
        }
        catch(InvalidIDException | InvalidMarksException exp)
        {
            System.out.println("value beyond given limits");
        }
    }
}

}
