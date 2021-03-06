package duke.command;


import java.io.IOException;

import duke.DukeException;
import duke.Storage;
import duke.Ui;
import duke.task.Event;
import duke.task.TaskList;


/**
 * AddEventCommand class that represents add event commands
 */
public class AddEventCommand extends Command {

    /**
     * AddEventCommand Class constructor
     *
     * @param command the command from the user
     */
    public AddEventCommand(String command) {
        super(command);
    }

    /**
     * Method that execute the current AddEventCommand object
     *
     * @param list     TaskList object from the current Duke instance
     * @param ui       UI object from the current Duke instance
     * @param saveData Storage object from the current Duke instance
     */
    public String execute(TaskList list, Ui ui, Storage saveData) {
        try {
            if (this.getCommand().trim().length() == 5) {
                throw new DukeException("☹ OOPS!!! Check event formatting, include description and /at.");
            } else if (!this.getCommand().contains("/at")) {
                throw new DukeException("☹ OOPS!!! Check event formatting, include /at.");
            }
            String[] holder = this.getCommand().split("event")[1].split("/at ");
            String description = holder[0].trim();
            if (detectDuplicate(list, description)) {
                throw new DukeException("☹ OOPS!!! This is a duplicated task!.");
            }
            String at = holder[1].trim();
            Event task = new Event(description, at);

            list.add(task);
            String saySomthing = ("Got it. I've added this task:\n" + task.toString()
                    + "\n" + String.format("Now you have %d tasks in the list.", list.size()));
            ui.saySomthing(saySomthing);
            String save = "E>0>" + description + ">" + at;
            saveData.addTask(save);
            return saySomthing;
        } catch (DukeException | IOException e) {
            return e.getMessage();
        }
    }

    /**
     * Method that return isExit of the current Command
     *
     * @return boolean object showing if Duke should terminate
     */
    @Override
    public boolean isExit() {
        return getIsExit();
    }

}
