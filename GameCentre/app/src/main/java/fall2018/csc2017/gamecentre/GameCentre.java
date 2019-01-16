package fall2018.csc2017.gamecentre;

import android.content.Context;
import android.util.Log;

import java.io.*;
import java.util.Hashtable;
import java.util.Map;

public class GameCentre {

    /**
     * The users map save file.
     */
    private static final String USERS_FILENAME = "users.ser";

    /**
     * The scoreboard save file (prefixed by username).
     */
    private static final String SCORE_FILE_SUFFIX = "score.ser";

    /**
     * The main game save file (prefixed by username).
     */
    private static final String SAVE_FILE_SUFFIX = "save.ser";

    /**
     * A temporary save file (prefixed by username).
     */
    private static final String TEMP_SAVE_FILE_SUFFIX = "save_tmp.ser";

    /**
     * The number of scores saved in a scoreboard.
     */
    private static final int SCOREBOARD_SIZE = 3;

    private static GameCentre instance;

    private String currentUser = "";
    private String currentGame = "";
    private Map<String, String> users;

    /**
     * Returns the instance of GameCentre, initializes an instance if none exists
     *
     * @return the single instance
     */
    public static GameCentre getInstance(Context context) {
        if (instance == null) {
            instance = new GameCentre();

            Map<String, String> usersLoad = GameCentre.loadFromFile(context, USERS_FILENAME);
            if (usersLoad == null) {
                usersLoad = new Hashtable<>();
            }
            instance.users = usersLoad;
        }

        return instance;
    }

    /**
     * Verifies that the given username-password pair is valid.
     * If valid, sets currentUser to the given username.
     *
     * @param username the given username
     * @param password the given password
     * @return true if the given password matches the given username
     */
    boolean checkLogin(String username, String password) {
        if (users.containsKey(username)) {
            if (users.get(username).equals(password)) {
                currentUser = username;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Logs out the current user. A call to this method should be followed by displaying the login activity.
     */
    void logout() {
        currentUser = "";
        currentGame = "";
    }

    /**
     * Adds a new user to users, if the username is not in users. Set the new user as currentUser.
     *
     * @param context  a Context used to access local files
     * @param username the new user's username
     * @param password the new user's password
     * @return true if the user was successfully added
     */
    boolean addUser(Context context, String username, String password) {
        if (!(users.containsKey(username) || username.equals("") || password.equals(""))) {
            users.put(username, password);
            currentUser = username;
            GameCentre.saveToFile(context, USERS_FILENAME, users);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Loads a game from the current user's save file.
     *
     * @param context   a Context used to access local files
     * @param temporary When true, load from the temporary save file
     * @return an object representing the loaded game
     */
    public <T> T loadGame(Context context, boolean temporary) {
        String suffix = temporary ? TEMP_SAVE_FILE_SUFFIX : SAVE_FILE_SUFFIX;
        String filename = GameCentre.concatFilename(new String[]{currentUser, currentGame, suffix});
        return (T) GameCentre.loadFromFile(context, filename);
    }

    /**
     * Saves a game to the current user's save file.
     *
     * @param context   a Context used to access local files
     * @param game      an object representing a game
     * @param temporary When true, save to the temporary save file
     */
    public <T> void saveGame(Context context, T game, boolean temporary) {
        String suffix = temporary ? TEMP_SAVE_FILE_SUFFIX : SAVE_FILE_SUFFIX;
        String filename = GameCentre.concatFilename(new String[]{currentUser, currentGame, suffix});
        GameCentre.saveToFile(context, filename, game);
    }


    /**
     * Deletes the current user's save file.
     *
     * @param context   a Context used to access local files
     * @param temporary When true, delete the temporary save file
     */
    public void clearSavedGame(Context context, boolean temporary) {
        String suffix = temporary ? TEMP_SAVE_FILE_SUFFIX : SAVE_FILE_SUFFIX;
        String filename = GameCentre.concatFilename(new String[]{currentUser, currentGame, suffix});
        File saveFile = new File(context.getFilesDir(), filename);
        if (!saveFile.delete()) {
            Log.e("GameCentre", "Failed to delete file: " + saveFile.getPath());
        }
    }

    /**
     * Checks if a new score is good enough to be added to a scoreboard, and adds it if it is.
     *
     * @param context    a Context used to access local files
     * @param identifier a label to distinguish between different scoreboards
     * @param newScore   the score to add
     * @param reverse    true if lower scores are better
     * @return true if the score was good enough to be added to the scoreboard
     */
    public boolean addScore(Context context, String identifier, int newScore, boolean reverse) {
        int[] scoreboard = loadScoreboard(context, identifier);
        if ((reverse ? newScore >= scoreboard[SCOREBOARD_SIZE - 1] : newScore <= scoreboard[SCOREBOARD_SIZE - 1]) && scoreboard[SCOREBOARD_SIZE - 1] > 0) {
            return false;
        } else {
            int prevScore = newScore;
            for (int i = 0; i < SCOREBOARD_SIZE; i++) {
                if ((reverse ? prevScore < scoreboard[i] : prevScore > scoreboard[i]) || scoreboard[i] == 0) {
                    int old = scoreboard[i];
                    scoreboard[i] = prevScore;
                    prevScore = old;
                }
            }
            saveScoreboard(context, identifier, scoreboard);
            return true;
        }
    }

    /**
     * Loads the scoreboard of the current user from its' file.
     *
     * @param context    a Context used to access local files
     * @param identifier a label to distinguish between different scoreboards
     * @return the loaded scoreboard
     */
    int[] loadScoreboard(Context context, String identifier) {
        String filename = GameCentre.concatFilename(new String[]{currentUser, currentGame, identifier, SCORE_FILE_SUFFIX});
        int[] scoreboard = GameCentre.loadFromFile(context, filename);
        if (scoreboard == null) {
            scoreboard = new int[SCOREBOARD_SIZE];
        }

        return scoreboard;
    }

    /**
     * Saves the scoreboard of the current user to its' file.
     *
     * @param context    a Context used to access local files
     * @param identifier the label to give to the scoreboard when saving
     * @param scoreboard the scoreboard to save
     */
    private void saveScoreboard(Context context, String identifier, int[] scoreboard) {
        if (scoreboard.length == SCOREBOARD_SIZE) {
            String filename = GameCentre.concatFilename(new String[]{currentUser, currentGame, identifier, SCORE_FILE_SUFFIX});
            GameCentre.saveToFile(context, filename, scoreboard);
        }
    }

    /**
     * Returns the username of the user currently logged in.
     *
     * @return the username, or an empty String if no one is logged in
     */
    String getCurrentUser() {
        return currentUser;
    }

    /**
     * Returns the identifier of the currently selected game.
     *
     * @return the game identifier, or an empty String if no game has been selected
     */
    public String getCurrentGame() {
        return currentGame;
    }

    /**
     * Set the identifier of the currently selected game to newGame.
     *
     * @param newGame the identifier of the selected game
     */
    void setCurrentGame(String newGame) {
        this.currentGame = newGame;
    }

    /**
     * Load a file from filename.
     *
     * @param context  a Context used to access local files
     * @param filename the filename where the object will be saved
     */
    private static <T> T loadFromFile(Context context, String filename) {
        T ret = null;

        try {
            InputStream inputStream = context.openFileInput(filename);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                ret = (T) input.readObject();
                input.close();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("GameCentre", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("GameCentre", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("GameCentre", "File contained unexpected data type: " + e.toString());
        }

        return ret;
    }

    /**
     * Save a file to filename.
     *
     * @param context  a Context used to access local files
     * @param filename the filename where the object will be saved
     * @param object   the object to save
     */
    private static <T> void saveToFile(Context context, String filename, T object) {
        try {
            OutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream output = new ObjectOutputStream(outputStream);
            output.writeObject(object);
            output.close();
            outputStream.close();
        } catch (IOException e) {
            Log.e("GameCentre", "File write failed: " + e.toString());
        }
    }

    public static String concatFilename(String[] str) {
        StringBuilder builder = new StringBuilder(str[0]);
        for (int i = 1; i < str.length; i++) {
            builder.append("_").append(str[i]);
        }
        return builder.toString();
    }
}
