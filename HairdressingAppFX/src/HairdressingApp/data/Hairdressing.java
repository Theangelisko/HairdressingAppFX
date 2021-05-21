package HairdressingApp.data;

/*  A Hairdressing is a subtype of appointment, so it inherits the same methods
    from the parent class adding the service attribute with its getter and setter
*/

public class Hairdressing extends Appointment
{
    private String service;

    public Hairdressing(Client client, String date, String time,
                        String service)
    {
        super(client, date, time);
        this.service = service;
    }

    //Getter
    public String getService() { return service; }

    //Setter
    public void setService(String service) { this.service = service; }

    @Override
    public String toString()
    {
        return super.toString() + " - " + getService();
    }
}
