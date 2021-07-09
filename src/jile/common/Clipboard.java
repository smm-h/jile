package jile.common;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class Clipboard implements ClipboardOwner, Singleton {

    private Clipboard() {
    }

    private static Clipboard singleton;

    public static Clipboard singleton() {
        if (singleton == null) {
            singleton = new Clipboard();
        }
        return singleton;
    }

    public void copy(Image image) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new TransferableImage(image), this);
    }

    @Override
    public void lostOwnership(java.awt.datatransfer.Clipboard clipboard, Transferable transferable) {
    }

    private class TransferableImage implements Transferable {

        final Image image;

        public TransferableImage(Image image) {
            this.image = image;
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if (flavor.equals(DataFlavor.imageFlavor) && image != null) {
                return image;
            } else {
                throw new UnsupportedFlavorException(flavor);
            }
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] flavors = new DataFlavor[1];
            flavors[0] = DataFlavor.imageFlavor;
            return flavors;
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            DataFlavor[] flavors = getTransferDataFlavors();
            for (int i = 0; i < flavors.length; i++) {
                if (flavor.equals(flavors[i])) {
                    return true;
                }
            }
            return false;
        }
    }
}