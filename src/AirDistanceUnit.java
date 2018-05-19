
import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alper
 */
public class AirDistanceUnit {
    private Vertex from;
    private Vertex to;

    public AirDistanceUnit(Vertex from, Vertex to) {
        this.from = from;
        this.to = to;
    }

    public Vertex getFrom() {
        return from;
    }

    public void setFrom(Vertex from) {
        this.from = from;
    }

    public Vertex getTo() {
        return to;
    }

    public void setTo(Vertex to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "AirDistanceUnit{" + "from=" + from + ", to=" + to + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AirDistanceUnit other = (AirDistanceUnit) obj;
        if(this.from.equals(other.from) && this.to.equals(other.to)){
          return true;
        }
        else{
         return false;
        }
    }
    
    
    
    
}
