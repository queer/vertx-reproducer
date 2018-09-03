package gg.amy;

/**
 * @author amy
 * @since 9/3/18.
 */
public final class Reproducer {
    private Reproducer() {
    }
    
    public static void main(final String[] args) {
        if(args.length == 0) {
            System.err.println("Please start with -client or -server!");
            return;
        }
        switch(args[0].toLowerCase()) {
            case "-client": {
                new Client().run();
                break;
            }
            case "-server": {
                new Server().run();
                break;
            }
            default: {
                System.err.println("Please start with -client or -server!");
            }
        }
    }
}
