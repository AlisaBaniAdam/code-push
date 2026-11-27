public class Main {
    private static final String RAMP = " .:-=+*#%@";

    public static void main(String[] args) throws InterruptedException {
        int width = 70;
        int height = 22;
        double time = 0.0;

        // Hide the terminal cursor
        System.out.print("\033[?25l");

        // Restore cursor on exit
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.print("\033[0m\033[?25h\n");
        }));

        while (true) {
            StringBuilder frame = new StringBuilder();
            frame.append("\033[H");

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    double v1 = Math.sin(x * 0.15 + time);
                    double v2 = Math.cos(y * 0.25 + time * 0.8);
                    double v3 = Math.sin((x * 0.1 + y * 0.1) - time * 1.2);

                    double intensity = (v1 + v2 + v3 + 3.0) / 6.0;
                    int charIndex = (int) (intensity * (RAMP.length() - 1));
                    char pixel = RAMP.charAt(charIndex);

                    int colorCode = getAnsiColor(intensity);
                    frame.append("\033[38;5;").append(colorCode).append("m").append(pixel);
                }
                frame.append("\n");
            }
            System.out.print(frame);

            time += 0.12;
            Thread.sleep(45);
        }
    }

    private static int getAnsiColor(double intensity) {
        if (intensity < 0.25) return 24;
        if (intensity < 0.50) return 33;
        if (intensity < 0.75) return 51;
        return 213;
    }
}
