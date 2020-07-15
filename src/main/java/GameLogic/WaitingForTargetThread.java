package GameLogic;

public class WaitingForTargetThread extends Thread {
    Object target;
    boolean ready = false;
    boolean finished = false;

    public Object getTarget() {
        if (ready)
            finished = true;
        return target;
    }

    public void setTarget(Object target) {
        System.out.println("thread target set");
        this.target = target;
        ready = true;
    }

    public boolean isReady() {
        return ready;
    }

    @Override
    public void run() {
        while (!finished);
    }
}
