package jile.math.numbers;

public class Complex {

    private final double real;
    private final double imaginary;

    public Complex(int value) {
        this((double) value);
    }

    public Complex(long value) {
        this((double) value);
    }

    public Complex(String string) {
        // TODO
        this(Double.parseDouble(string));
    }

    public Complex(double value) {
        real = value;
        imaginary = 0;
    }

    public Complex(int real, int imaginary) {
        this((double) real, (double) imaginary);
    }

    public Complex(long real, long imaginary) {
        this((double) real, (double) imaginary);
    }

    public Complex(float real, float imaginary) {
        this((double) real, (double) imaginary);
    }

    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public Complex reciprocal() {
        double a = real;
        double b = imaginary;
        double u = a * a + b * b;
        return new Complex(a / u, -b / u);
    }

    public Complex negate() {
        return new Complex(-real, imaginary);
    }

    public Complex add(Complex other) {
        double a = real;
        double b = imaginary;
        double c = other.real;
        double d = other.imaginary;
        return new Complex(a + c, b + d);
    }

    public Complex multiply(Complex other) {
        double a = real;
        double b = imaginary;
        double c = other.real;
        double d = other.imaginary;
        return new Complex(a * c - b * d, b * c + a * d);
    }

    @Override
    public String toString() {
        return "" + real + "+" + imaginary + "i";
    }
}
