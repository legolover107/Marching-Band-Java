public class FootballField {
    public static void main(String[] args) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int y = -42; y <= 42; y++) {
            for (int x = -80; x <= 80; x++) {
                if (Math.abs(y) % 42 == 0 && y != 0) {
                    System.out.print("__");
                } else if (Math.abs(x) % 8 == 0) {
                    System.out.print("| ");
                } else if (Math.abs(y) == 14 && (Math.abs(x) % 8 == 1 || Math.abs(x) % 8 == 7)) {
                    System.out.print("__");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}