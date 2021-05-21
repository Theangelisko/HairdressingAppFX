package HairdressingApp.data;

//Generic, abstract class for Personal and Client users.

abstract public class Person
{
    protected String username;
    protected String password;
    protected String name;
    protected String surnames;
    protected int numberPhone;
    protected String email;

    public Person(String username, String password, String name, String surnames,
                  int numberPhone, String email)
    {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surnames = surnames;
        this.numberPhone = numberPhone;
        this.email = email;
    }

    //Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getSurnames() { return surnames; }
    public int getNumberPhone() { return numberPhone; }
    public String getEmail() { return email; }

    //Setters
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setName(String name) { this.name = name; }
    public void setSurnames(String surnames) { this.surnames = surnames; }
    public void setNumberPhone(int numberPhone) { this.numberPhone = numberPhone; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString()
    {
        return getUsername() + ":" + getPassword() + ":" + getName() + ":" +
                getSurnames() + ":" + getNumberPhone() + ":" + getEmail();
    }

}
