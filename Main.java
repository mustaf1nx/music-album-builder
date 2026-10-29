import java.util.Scanner;

/** Program entry point. Pass --demo to run all algorithms without user input. */
public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        if (args.length == 1 && "--help".equals(args[0])) {
            System.out.println("Usage: java Main [--demo|--help]");
            return;
        }

        ConsoleApp app = new ConsoleApp(new Scanner(System.in), System.out);
        if (args.length == 1 && "--demo".equals(args[0])) {
            app.runDemo();
        } else if (args.length == 0) {
            app.runInteractive();
        } else {
            System.err.println("Unknown arguments. Supported options: --demo, --help");
            System.exit(2);
        }
    }
}
