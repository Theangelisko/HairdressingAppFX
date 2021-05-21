package HairdressingApp.data;

//Generic, abstract class for Hairdressing and Manicure appointments.

abstract public class Appointment
{
    protected Client client;
    protected String date;
    protected String time;

    public Appointment(Client client, String date, String time)
    {
        this.client = client;
        this.date = date;
        this.time = time;
    }

    //Getters
    public Client getClient() { return client; }
    public String getDate() { return date; }
    public String getTime() { return time; }

    //Setters
    public void setClient(Client client) { this.client = client; }
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }

    @Override
    public String toString()
    {
        return getClient().getSurnames() + ", " + getClient().getName() + " - " + getDate() +
                " - " + getTime();
    }
}
