package io.relayr.model;

public class ModelReading {

    /** String representation of the reading value */
    final public String meaning;
    /** If one device contains other devices they will be defined by paths like in a tree map */
    final public String path;
    /** Unit of measurement */
    final public Object unit;
    /** Reading minimum */
    final public Object minimum;
    /** Reading maximum */
    final public Object maximum;
    /** Reading precision - doesn't need to be defined */
    final public Object precision;

    public ModelReading(String meaning, String path, Object unit, Object minimum, Object maximum, Object precision) {
        this.meaning = meaning;
        this.path = path;
        this.unit = unit;
        this.minimum = minimum;
        this.maximum = maximum;
        this.precision = precision;
    }

    @Override
    public String toString() {
        return "ModelReading{" +
                "meaning='" + meaning + '\'' +
                ", path='" + path + '\'' +
                ", unit=" + unit +
                ", minimum=" + minimum +
                ", maximum=" + maximum +
                ", precision=" + precision +
                '}';
    }
}
