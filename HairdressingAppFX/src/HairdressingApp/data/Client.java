package HairdressingApp.data;

/*  A Client is a subtype of person, so it inherits the same methods
    from the parent class.
*/

public class Client extends Person
{
    public Client(String username, String password, String name, String surnames,
                    int numberPhone, String email)
    {
        super(username,password,name,surnames,numberPhone,email);
    }

    @Override
    public String toString()
    {
        return super.toString();
    }
}
