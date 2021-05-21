package HairdressingApp.data;

/*  A Manicure is a subtype of appointment, so it inherits the same methods
    from the parent class adding the type attribute with its getter and setter
*/

public class Manicure extends Appointment
{
    private String type;

    public Manicure(Client client, String date, String time,
                    String type)
    {
        super(client, date, time);
        this.type = type;
    }

    //Getter
    public String getType() { return type; }

    //Setter
    public void setType(String type) { this.type = type; }

    @Override
    public String toString()
    {
        return super.toString() + " - " + getType();
    }
}
