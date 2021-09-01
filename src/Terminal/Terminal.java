package Terminal;

public class Terminal {
    public static void info(String text) {
        System.out.println("[" + TerminalColors.ANSI_CYAN + "INFO" + TerminalColors.ANSI_RESET + "] " + text);
    }

    public static void warn(String text) {
        System.out.println("[" + TerminalColors.ANSI_YELLOW + "WARNING" + TerminalColors.ANSI_RESET + "] " + text);
    }
}
