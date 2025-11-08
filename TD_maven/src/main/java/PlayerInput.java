import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class PlayerInput {

    private Terminal terminal;

    public void start() {
        try {
            terminal = TerminalBuilder.builder()
                    .system(true)
                    .jna(true)
                    .build();
            terminal.enterRawMode();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize terminal for movement", e);
        }
    }

    public void stop() {
        if (terminal != null) {
            try { terminal.close();
            } catch (Exception ignored) {}
        }
    }

    public char readKey() {
        try {
            int ch = terminal.reader().read();
            return (char) ch;
        } catch (Exception e) {
            throw new RuntimeException("Failed to read key", e);
        }
    }

    public String readPlayerInput(Cursor cursor){
        char key = readKey();
        switch (Character.toLowerCase(key)){
            case 'w':
                cursor.moveCursorUp();
                break;
            case 'a':
                cursor.moveCursorLeft();
                break;
            case 's':
                cursor.moveCursorDown();
                break;
            case 'd':
                cursor.moveCursorRight();
                break;
            case 'b':
                return "build";
            case ' ':
                return "stop";
            default:
        }
        return "continue";
    }

    public Character readTowerSelection() {
        while (true) {
            char key = readKey();
            if (key == 'q' || key == 'Q' || key == 27) {
                return null;
            }
            if (key == '1' || key == '2' || key == '3') {
                return key;
            }
        }
    }
}
