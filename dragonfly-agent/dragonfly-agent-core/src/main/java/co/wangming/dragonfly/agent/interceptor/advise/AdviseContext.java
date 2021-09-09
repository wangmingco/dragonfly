package co.wangming.dragonfly.agent.interceptor.advise;

public class AdviseContext {

    private Phase currentPhase;
    private Phase lastPhase;

    public static AdviseContext enterBefore() {
        AdviseContext adviseContext = new AdviseContext();
        adviseContext.currentPhase = Phase.EnterBefore;

        return adviseContext;
    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public Phase getLastPhase() {
        return lastPhase;
    }

    public void exitBefore() {
        currentPhase = Phase.ExitBefore;
        lastPhase = Phase.EnterBefore;
    }

    public void enterException() {
        lastPhase = currentPhase;
        currentPhase = Phase.ExitException;
    }

    public void exitException() {
        lastPhase = Phase.EnterException;
        currentPhase = Phase.ExitException;
    }

    public void enterAfter() {
        lastPhase = currentPhase;
        currentPhase = Phase.EnterAfter;
    }

    public void exitAfter() {
        lastPhase = Phase.EnterAfter;
        currentPhase = Phase.EnterAfter;
    }

    public enum Phase {
        EnterBefore,
        ExitBefore,
        EnterException,
        ExitException,
        EnterAfter,
        ExitAfter,
    }

}
