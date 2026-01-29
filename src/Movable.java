public interface Movable{
    void move();
    void turnLeft();
    void turnRight();
    }

// ett interface säger vad ett objekt kan göra inte hur
// skriv över dessa i varje objekt då det kan bete sig olika
// ex båt, bil, cykel, människa osv, alla kan röra på sig och svänga
// men olika