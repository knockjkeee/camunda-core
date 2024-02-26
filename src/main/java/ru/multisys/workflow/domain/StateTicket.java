package ru.multisys.workflow.domain;

/**
 * @author knockjkeee
 * @created 26/02/2024 - 14:50
 */
public enum StateTicket {
    NEW("new"),
    OPEN("open"),
    CLOSE("close"),
    CLOSE_SUCCESSFUL("closed successful"),
    CLOSE_UNSUCCESSFUL("closed unsuccessful"),
    INWORK("inwork"),
    PENDING("pending reminder"),
    PENDING_PLUS("pending auto close+"),
    PENDING_MINUS("pending auto close-"),
    MERGED("merged"),
    REMOVED("removed"),
    START("start"),
    WORK("work"),
    END("end");

    private String name;

    StateTicket(String name) {
        this.name = name;
    }

    public String nameLowerCase() {
        return name.toLowerCase();
    }
}
