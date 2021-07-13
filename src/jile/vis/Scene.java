package jile.vis;

import java.awt.Graphics2D;

import jile.common.Identifiable;

public interface Scene extends Identifiable {

    public int getSize();

    public void diff(Graphics2D curtains, int index);
}
