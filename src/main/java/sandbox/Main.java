package sandbox;

import engine.core.exceptions.ApplicationInitException;

public class Main {
    public static void main(String[] args) {
        try {
            new SandboxApplication().run();
        } catch (ApplicationInitException e) {
            e.printStackTrace();
        }
    }
}