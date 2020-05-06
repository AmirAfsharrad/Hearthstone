package Places;

public class Status extends Place {
    private static Status status = new Status();

    private Status() {
    }

    public static Status getStatus() {
        return status;
    }
}
