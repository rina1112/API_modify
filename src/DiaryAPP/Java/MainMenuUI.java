package DiaryAPP.Java;

import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

import java.io.PrintWriter;
import java.util.List;

public class MainMenuUI {
    private static final List<String> MENU_ITEMS = List.of(
            "日記を書く",
            "過去の日記を読む",
            "他人の日記を受信する",
            "ログアウト");

    public static void start(Terminal terminal, String userId) throws Exception {
        terminal.puts(InfoCmp.Capability.cursor_invisible);
        PrintWriter out = terminal.writer();
        int selected = 0;

        while (true) {
            terminal.puts(InfoCmp.Capability.clear_screen);
            out.println("=== メインメニュー ===");
            for (int i = 0; i < MENU_ITEMS.size(); i++) {
                if (i == selected) {
                    out.println("▶ \033[47;30m" + MENU_ITEMS.get(i) + "\033[0m");
                } else {
                    out.println("   " + MENU_ITEMS.get(i));
                }
            }
            out.println("↑/w ↓/s → Enter");
            out.flush();

            int ch = terminal.reader().read();
            if (ch == 'w' || ch == 'W' || ch == 65) {
                selected = (selected - 1 + MENU_ITEMS.size()) % MENU_ITEMS.size();
            } else if (ch == 's' || ch == 'S' || ch == 66) {
                selected = (selected + 1) % MENU_ITEMS.size();
            } else if (ch == 10 || ch == 13) {
                String choice = MENU_ITEMS.get(selected);
                if (choice.equals("ログアウト")) {
                    return; // ← TopMenuUI に戻る
                } else if (choice.equals("日記を書く")) {
                    WriteDiaryUI.start(terminal, userId); // ← UIに遷移
                } else {
                    out.println("「" + choice + "」機能はまだ実装されていません。");
                    out.flush();
                    Thread.sleep(1500);
                    while (terminal.reader().ready()) {
                        terminal.reader().read();
                    }
                }
            }
        }
    }
}
