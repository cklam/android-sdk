package io.relayr.model;

public class ModelCommand {

    /** Command name */
    final public String command;
    /** If one device contains other devices they will be defined by paths like in a tree map */
    final public String path;
    /** Unit of measurement */
    final public Object unit;
    /** Minimum value to be send */
    final public String minimum;
    /** Maximum value to be send */
    final public String maximum;

    public ModelCommand(String command, String path, Object unit, String minimum, String maximum) {
        this.command = command;
        this.path = path;
        this.unit = unit;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    @Override
    public String toString() {
        return "ModelCommand{" +
                "command='" + command + '\'' +
                ", path='" + path + '\'' +
                ", unit=" + unit +
                ", minimum='" + minimum + '\'' +
                ", maximum='" + maximum + '\'' +
                '}';
    }
}
