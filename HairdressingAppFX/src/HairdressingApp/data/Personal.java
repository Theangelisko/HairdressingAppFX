package HairdressingApp.data;

/*  A personal is a subtype of person, so it inherits the same methods
    from the parent class adding the idPersonal attribute with its getter and setter
*/

public class Personal extends Person
{
    private int idPersonal;

    public Personal(String username, String password, String name, String surnames,
                    int numberPhone, String email, int idPersonal)
    {
        super(username,password,name,surnames,numberPhone,email);
        this.idPersonal = idPersonal;
    }

    //Getter
    public int getIdPersonal() { return idPersonal; }

    //Setter
    public void setIdPersonal(int idPersonal) { this.idPersonal = idPersonal; }

    @Override
    public String toString()
    {
        return super.toString() + ":" + getIdPersonal();
    }
}
