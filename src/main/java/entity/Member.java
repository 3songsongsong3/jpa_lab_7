package entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Member {

    private String username;

    @ManyToOne
    private Team team;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
