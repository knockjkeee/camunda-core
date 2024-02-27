package ru.multisys.workflow.service;

import ru.multisys.workflow.domain.StateTicket;

import java.util.List;

/**
 * @author knockjkeee
 * @created 26/02/2024 - 15:53
 */


public class CheckedState {


    //    StateTicket.START.nameLowerCase()
    public static List<String> newState = List.of(
            StateTicket.NEW.nameLowerCase(),
            StateTicket.OPEN.nameLowerCase()

    );

    //    StateTicket.WORK.nameLowerCase()
    public static List<String> workState = List.of(StateTicket.INWORK.nameLowerCase(),
            StateTicket.INWORK.nameLowerCase(),
            StateTicket.PENDING.nameLowerCase()
    );

    //    StateTicket.END.nameLowerCase()
    public static List<String> closeState = List.of(
            StateTicket.SOLVED.nameLowerCase(),
            StateTicket.PENDING_PLUS.nameLowerCase(),
            StateTicket.PENDING_MINUS.nameLowerCase(),
            StateTicket.REMOVED.nameLowerCase()
    );

    //    StateTicket.CLOSE.nameLowerCase();
    public static List<String> exitState = List.of(
            StateTicket.CLOSE_SUCCESSFUL.nameLowerCase(),
            StateTicket.CLOSE_UNSUCCESSFUL.nameLowerCase(),
            StateTicket.CLOSE.nameLowerCase()
    );

    public static boolean nextState(String targetState, String externalState) {

        String findState = null;

        if (newState.stream().anyMatch(e -> externalState.toLowerCase().equals(e))) {
            findState = StateTicket.START.nameLowerCase();
        }
        else if (workState.stream().anyMatch(e -> externalState.toLowerCase().equals(e))) {
            findState = StateTicket.WORK.nameLowerCase();
        }
        else if (closeState.stream().anyMatch(e -> externalState.toLowerCase().equals(e))) {
            findState = StateTicket.END.nameLowerCase();
        }
        else if(exitState.stream().anyMatch(e -> externalState.toLowerCase().equals(e))){
            findState = StateTicket.CLOSE.nameLowerCase();
        }

        if (findState == null) return false;

        return !targetState.equals(findState);
    }
}
