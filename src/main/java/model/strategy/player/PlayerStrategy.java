package model.strategy.player;

public interface PlayerStrategy {
    String createCommand();

    static PlayerStrategy getStrategy(String p_Strategy) {
        switch (p_Strategy) {
            case "human": {
                return new HumanStrategy();
            }
        }
        throw new IllegalStateException("not a valid player type");
    }
}
