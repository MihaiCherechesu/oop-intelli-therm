import java.io.*;

/**
 * Class that reads the input, loads the objects and
 * runs the heat system functions.
 */
public class DataLoader {

    /***
     * Attributes of DataLoader class.
     */
    private static final String inputFileName = "therm.in";
    private static final String outputFileName = "therm.out";
    private static File inputFile;
    private static File outputFile;
    private static String currentLine;
    private static String []tokens;
    private static BufferedReader bufferedReader;
    private static BufferedWriter bufferedWriter;
    private static boolean firstLineIO;

    /***
     * Non-static fields constructor without parameters.
     */
    private DataLoader() {}

    /***
     * Static fields initialization block which opens the file in question.
     */
    static {
        inputFile = new File(inputFileName);
        outputFile = new File(outputFileName);
        firstLineIO = true;
        currentLine = "";

        try { bufferedReader = new BufferedReader(new FileReader(inputFile));
        } catch (FileNotFoundException e) { e.printStackTrace(); }

        try { bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
        } catch (IOException e) { e.printStackTrace(); }
    }

    /***
     * Getters and setters for fields.
     */

    public static void setFirstLineIO() { firstLineIO = false; }
    public static boolean isFirstLineIO() { return firstLineIO; }
    public static String[] getTokens() { return tokens; }
    public static BufferedReader getBufferedReader() { return bufferedReader; }
    public static BufferedWriter getBufferedWriter() { return bufferedWriter; }

    /***
     * Data loader method.
     * @param tokens is an array of strings, which represent the input nibbles.
     * @return true or false
     */
    private boolean loader(String []tokens) {

        String name = tokens[StandardTokenIO.getRoom()];
        String id   = tokens[StandardTokenIO.getDevice()];
        int surface = Integer.parseInt(tokens[StandardTokenIO.getSurface()]);

        HeatSystem.configureRoom(name, id, surface);
        HeatSystem.setRoomCount(HeatSystem.getRoomCount() - 1);
        return true;
    }


    /***
     * Calls the functions related to the thermostat.
     * @param tokens is an array of strings, which represent the input nibbles.
     * @return true or false
     * @throws IOException
     */
    private boolean runner(String[] tokens, HeatSystem system) throws IOException {

        String function = tokens[StandardTokenIO.getFunction()];

        if (function.equals(StandardFunctions.getObserve())) {
            system.observe(tokens);

        } else if (function.equals(StandardFunctions.getHumidity())) {
            system.observe(tokens);

        } else if (function.equals(StandardFunctions.getList())) {
            system.list(tokens);

        } else if (function.equals(StandardFunctions.getTemperature())) {
            system.temperature(tokens);

        } else if (function.equals(StandardFunctions.getTrigger())) {
            system.trigger(tokens);
        }

        return true;
    }

    /***
     * Main class for input fetching and class' instance loading.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        HeatSystem system = new HeatSystem();
        DataLoader loader = new DataLoader();
        boolean bool; // Auxiliary boolean for ternary operator

        try {
            currentLine = bufferedReader.readLine();                      // Read line
            tokens = currentLine.split(" ", StandardTokenIO.getTokens()); // Split line

            int roomCount = Integer.parseInt(tokens[StandardTokenIO.getRoom()]);
            double globalTemperature = Double.parseDouble
                                            (tokens[StandardTokenIO.getGlobalTemperature()]);
            long timestamp = 0;
            double globalHumidity = 0;

            if (tokens.length == 3) {            // When the heat system does not act in accordance with humidity.
                timestamp = Long.parseLong(tokens[StandardTokenIO.getTimestamp()]);
            } else if (tokens.length == 4) {     // When the heat system does act in accordance with humidity.
                timestamp = Long.parseLong(tokens[StandardTokenIO.getTimestamp() + 1]);
                globalHumidity = Double.parseDouble(tokens[StandardTokenIO.getHumidity()]);
            }

            HeatSystem.setRoomCount(roomCount);                 // Loads the roomCount static variable.
            HeatSystem.setGlobalTemperature(globalTemperature); // Loads the global temperature.
            HeatSystem.setTimestamp(timestamp);                 // Loads the reference timestamp.
            HeatSystem.setGlobalHumidity(globalHumidity);       // Loads the global humidity (0 if none).

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while ((currentLine = bufferedReader.readLine()) != null) {

                // Split the input line into tokens.
                tokens = currentLine.split(" ", StandardTokenIO.getTokens());

                bool = (HeatSystem.getRoomCount() != 0) ?
                       (loader.loader(tokens)) :        // Load the rooms.
                       (loader.runner(tokens, system)); // Register the parameters with devices.
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        DataLoader.bufferedReader.close();
        DataLoader.bufferedWriter.close();
    }
}

