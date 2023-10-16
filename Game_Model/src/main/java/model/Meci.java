package model;

public class Meci extends Entity<Integer>{
    private String home;
    private String away;
    private String type;
    private int seats;


    public Meci(Integer id, String echipa1, String echipa2, String tip, int nrBilete) {
        super.setId(id);
        this.home = echipa1;
        this.away = echipa2;
        this.type = tip;
        this.seats = nrBilete;
    }

    public Meci(String echipa1, String echipa2, String tip, int nrBilete) {
        this.home = echipa1;
        this.away = echipa2;
        this.type = tip;
        this.seats = nrBilete;
    }

    public Meci() {
    }

    @Override
    public String toString() {
        return "Meci{" +
                "home='" + home + '\'' +
                ", away='" + away + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Meci meci = (Meci) o;
        return home.equals(meci.home) &&
                away.equals(meci.away);
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public Integer getId() {
        return super.getId();
    }
}