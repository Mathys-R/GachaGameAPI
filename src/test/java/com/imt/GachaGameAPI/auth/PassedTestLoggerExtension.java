package com.imt.GachaGameAPI.auth;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import java.util.Optional;

public class PassedTestLoggerExtension implements TestWatcher {
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";

    @Override
    public void testSuccessful(ExtensionContext context) {
        System.out.printf("%n%sPASSED:%s Test %s%n%n", ANSI_GREEN, ANSI_RESET, context.getDisplayName());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        System.out.println("\"%n%sFailed:%s Test %s%n%n\", ANSI_GREEN, ANSI_RESET, context.getDisplayName()");
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        System.out.println("\"%n%sAborted:%s Test %s%n%n\", ANSI_GREEN, ANSI_RESET, context.getDisplayName()");
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        System.out.println("\"%n%Disabled:%s Test %s%n%n\", ANSI_GREEN, ANSI_RESET, context.getDisplayName()");
    }
}