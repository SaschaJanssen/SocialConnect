package org.social.core.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ItemNotFoundException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 6438851497792606898L;

    public ItemNotFoundException() {
        super();
    }

    public ItemNotFoundException(String msg) {
        super(msg);
    }

    public ItemNotFoundException(String msg, Throwable nested) {
        super(msg, nested);
    }

    public ItemNotFoundException(Throwable nested) {
        super(nested);
    }

    @Override
    public String getMessage() {
        Throwable nested = getCause();
        if (nested != null) {
            if (super.getMessage() == null) {
                return nested.getMessage();
            } else {
                return super.getMessage() + " (" + nested.getMessage() + ")";
            }
        } else {
            return super.getMessage();
        }
    }

    public String getNonNestedMessage() {
        return super.getMessage();
    }

    public Throwable getNested() {
        Throwable nested = getCause();
        if (nested == null) {
            return this;
        }
        return nested;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream ps) {
        super.printStackTrace(ps);
    }

    @Override
    public void printStackTrace(PrintWriter pw) {
        super.printStackTrace(pw);
    }

}
