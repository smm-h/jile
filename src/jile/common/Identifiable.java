package jile.common;

public interface Identifiable {
    public String getIdentity();

    public static String getIdentityOf(Object object) {
        if (object == null)
            return "NULL";
        else if (object instanceof Identifiable)
            return ((Identifiable) object).getIdentity();
        else
            return object.toString();
    }
}