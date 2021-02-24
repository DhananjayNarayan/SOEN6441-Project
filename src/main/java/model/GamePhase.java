package model;

import controller.GamePlay;
import controller.IssueOrder;
import controller.MapEditor;
import controller.Reinforcement;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum GamePhase {
    MapEditor {
        @Override
        public List<GamePhase> possibleStates() {
            return Collections.singletonList(LoadGame);
        }

        @Override
        public GameController getController() {
            return new MapEditor();
        }
    },
    LoadGame {
        @Override
        public List<GamePhase> possibleStates() {
            return Collections.singletonList(Reinforcement);
        }

        @Override
        public GameController getController() {
            return new GamePlay();
        }
    },
    Reinforcement {
        @Override
        public List<GamePhase> possibleStates() {
            return Collections.singletonList(IssueOrder);
        }

        @Override
        public GameController getController() {
            return new Reinforcement();
        }
    },
    IssueOrder {
        @Override
        public List<GamePhase> possibleStates() {
            return Collections.singletonList(ExecuteOrder);
        }

        @Override
        public GameController getController() {
            return new IssueOrder();
        }
    },
    ExecuteOrder {
        @Override
        public List<GamePhase> possibleStates() {
            return Arrays.asList(Reinforcement, ExitGame);
        }

        @Override
        public GameController getController() {
            return null;
        }
    },
    ExitGame {
        @Override
        public List<GamePhase> possibleStates() {
            return null;
        }

        @Override
        public GameController getController() {
            return null;
        }
    };

    public GamePhase nextState(GamePhase p_GamePhase) {
        if (this.possibleStates().contains(p_GamePhase)) {
            return p_GamePhase;
        } else return this;
    }

    public abstract List<GamePhase> possibleStates();

    public abstract GameController getController();
}
