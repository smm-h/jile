package jile.common;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

public interface Convert {

    public static <T> LinkedList<T> Array_to_LinkedList(T[] array) {
        LinkedList<T> list = new LinkedList<T>();
        for (int i = 0; i < array.length; i++)
            list.add(array[i]);
        return list;
    }

    public static <T> ArrayList<T> Array_to_ArrayList(T[] array) {
        ArrayList<T> list = new ArrayList<T>();
        for (int i = 0; i < array.length; i++)
            list.add(array[i]);
        return list;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] List_to_Array(List<T> list) {
        T[] array = (T[]) new Object[list.size()];
        int i = 0;
        for (T t : list)
            array[i++] = t;
        return array;
    }

    public static <T> ArrayList<T> List_to_ArrayList(List<T> list) {
        ArrayList<T> arrayList = new ArrayList<T>(list.size());
        for (T t : list)
            arrayList.add(t);
        return arrayList;
    }

    public static File Image_to_File(Image image) {
        try {
            File file = new File("gx/turtle.png");
            ImageIO.write(Convert.Image_to_BufferedImage(image), "png", file);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static BufferedImage Image_to_BufferedImage(Image image) {
        if (image instanceof BufferedImage)
            return (BufferedImage) image;

        var bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();

        return bufferedImage;
    }

    public static <T> String Array_to_String(T[] array) {
        StringBuilder builder = new StringBuilder(array.length * 5 + 2);
        builder.append("[");
        for (int i = 0; i < array.length; i++) {
            if (i != 0)
                builder.append(", ");
            builder.append(array[i]);
        }
        builder.append("]");
        return builder.toString();
    }
}
