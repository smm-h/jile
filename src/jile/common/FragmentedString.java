package jile.common;

import java.util.ArrayList;

public class FragmentedString implements Typeable {

    private final int CHUNK_SIZE;

    private final ArrayList<Fragment> fragments = new ArrayList<Fragment>(32);

    private abstract class Fragment {
        int length;

        Fragment(int length) {
            if (length == 0)
                throw new NullPointerException("String fragment length cannot be zero");
            this.length = length;
        }

        abstract String getString();

        abstract Fragment fromStartToIndex(int index);

        abstract Fragment fromIndexToEnd(int index);

        abstract int getLengthOfFromStartToIndex(int index);

        abstract int getLengthOfFromIndexToEnd(int index);
    }

    private class RealFragment extends Fragment {
        final char array[];

        RealFragment(String string) {
            super(string.length());
            int index = 0;
            array = new char[length];
            while (index < length)
                array[index] = string.charAt(index++);
        }

        @Override
        public String getString() {
            return new String(array);
        }

        @Override
        Fragment fromStartToIndex(int index) {
            return new FakeFragment(this, 0, index);
        }

        @Override
        Fragment fromIndexToEnd(int index) {
            return new FakeFragment(this, index, length);
        }

        @Override
        int getLengthOfFromStartToIndex(int index) {
            return index;
        }

        @Override
        int getLengthOfFromIndexToEnd(int index) {
            return length - index;
        }
    }

    private class FakeFragment extends Fragment {
        final RealFragment parent;
        final int offset;

        FakeFragment(RealFragment real, int start, int end) {
            super(end - start);
            parent = real;
            offset = start;
        }

        @Override
        public String getString() {
            return new String(parent.array, offset, length);
        }

        @Override
        Fragment fromStartToIndex(int index) {
            return new FakeFragment(parent, offset, index);
        }

        @Override
        Fragment fromIndexToEnd(int index) {
            return new FakeFragment(parent, offset + index, offset + length);
        }

        @Override
        int getLengthOfFromStartToIndex(int index) {
            return index - offset;
        }

        @Override
        int getLengthOfFromIndexToEnd(int index) {
            return length - index;
        }
    }

    private class TypeableFragment extends Fragment {

        String string;

        TypeableFragment(String string) {
            super(string.length());
            this.string = string;
        }

        @Override
        String getString() {
            return string;
        }

        @Override
        Fragment fromStartToIndex(int index) {
            return new RealFragment(string.substring(0, index));
        }

        @Override
        Fragment fromIndexToEnd(int index) {
            return new RealFragment(string.substring(index, length));
        }

        @Override
        int getLengthOfFromStartToIndex(int index) {
            return index;
        }

        @Override
        int getLengthOfFromIndexToEnd(int index) {
            return length - index;
        }
    }

    public FragmentedString() {
        CHUNK_SIZE = 256;
    }

    public FragmentedString(int chunkSize) {
        CHUNK_SIZE = chunkSize;
    }

    public FragmentedString(String string) {
        CHUNK_SIZE = Math.max(32, Math.min(1024, string.length() / 16));
        append(string);
    }

    public FragmentedString(String string, int chunkSize) {
        CHUNK_SIZE = chunkSize;
        append(string);
    }

    public void append(String string) {
        if (CHUNK_SIZE == 0) {
            fragments.add(new RealFragment(string));
        } else {
            int p = 0;
            while (p + CHUNK_SIZE < string.length())
                fragments.add(new RealFragment(string.substring(p, p += CHUNK_SIZE)));
            fragments.add(new RealFragment(string.substring(p)));
        }
    }

    public String getString() {
        String string = "";
        for (int index = 0; index < fragments.size(); index++)
            string += fragments.get(index).getString();
        return string;
    }

    public void defragment() {
        String string = getString();
        clear();
        append(string);
    }

    public void defragment(int from, int to) {

        String string = "";

        // find out which fragments each position belongs to
        int indexFirst = indexOfFragmentContaining(from);
        int indexLast = indexOfFragmentContaining(to);

        // remove all other fragments in between, but gather their strings
        for (int index = indexLast; index >= indexFirst; index--)
            string = fragments.remove(index).getString() + string;

        // insert back all the gathered string as a single fragment
        fragments.add(indexFirst, new RealFragment(string));
    }

    public void clear() {
        fragments.clear();
    }

    private int indexOfFragmentContaining(int position) {
        int index = -1;
        while (position > 0)
            position -= fragments.get(++index).length;
        return index;
    }

    private int getFragmentStart(int index) {
        int position = 0;
        while (index > 0)
            position += fragments.get(--index).length;
        return position;
    }

    public void insert(int position, String string) {

        // find out which fragment contains this position
        int index = indexOfFragmentContaining(position);

        // turn absolute position into relative position
        position -= getFragmentStart(index);

        // delete the fragment found so we can break it in half
        Fragment fragment = fragments.remove(index);

        // insert the left half
        if (fragment.getLengthOfFromStartToIndex(position) != 0)
            fragments.add(index++, fragment.fromStartToIndex(position));

        // insert the middle
        if (CHUNK_SIZE == 0) {
            fragments.add(index++, new RealFragment(string));
        } else {
            int p = 0;
            while (p + CHUNK_SIZE < string.length())
                fragments.add(index++, new RealFragment(string.substring(p, p += CHUNK_SIZE)));
            fragments.add(index++, new RealFragment(string.substring(p)));
        }

        // insert the right half
        if (fragment.getLengthOfFromIndexToEnd(position) != 0)
            fragments.add(index++, fragment.fromIndexToEnd(position));
    }

    @Override
    public String toString() {
        String string = "";
        for (int index = 0; index < fragments.size(); index++)
            string += "[" + fragments.get(index).getString() + "]";
        return string;
    }

    public int getLength() {
        int length = 0;
        for (int index = 0; index < fragments.size(); index++)
            length += fragments.get(index).length;
        return length;
    }

    public void delete(int from, int to) {

        // find out which fragments each position belongs to
        int indexFirst = indexOfFragmentContaining(from);
        int indexLast = indexOfFragmentContaining(to);

        // turn absolute positions into relative positions
        from -= getFragmentStart(indexFirst);
        to -= getFragmentStart(indexLast);

        // if they belong to the same fragment,
        if (indexFirst == indexLast) {

            int index = indexFirst;

            // remove that fragment so we can break it into three parts
            Fragment fragment = fragments.remove(index);

            // insert back the third one-third
            if (fragment.getLengthOfFromIndexToEnd(to) != 0)
                fragments.add(index, fragment.fromIndexToEnd(to));

            // insert back the first one-third
            if (fragment.getLengthOfFromStartToIndex(from) != 0)
                fragments.add(index, fragment.fromStartToIndex(from));

        }
        // otherwise
        else {

            // remove all fragments involved from last to first, but keep a reference to the
            // first and last one so we can break them

            // remove the last fragment
            Fragment fragmentLast = fragments.remove(indexLast);

            // remove all other fragments in between
            for (int index = indexLast - 1; index > indexFirst; index--)
                fragments.remove(index);

            // remove the first fragment
            Fragment fragmentFirst = fragments.remove(indexFirst);

            // insert back the second half of the last fragment
            if (fragmentLast.getLengthOfFromIndexToEnd(to) != 0)
                fragments.add(indexFirst, fragmentLast.fromIndexToEnd(to));

            // insert back the first half of the first fragment
            if (fragmentFirst.getLengthOfFromStartToIndex(from) != 0)
                fragments.add(indexFirst, fragmentFirst.fromStartToIndex(from));
        }
    }

    private TypeableFragment typeable;
    private int typeableIndex;
    private int caret;

    private boolean isTyping() {
        return typeable != null;
    }

    private void startTyping() {

        // find out in which fragment our caret is
        int index = indexOfFragmentContaining(caret);

        // if while typing we have moved into another fragment
        if (isTyping() && typeableIndex != index) {
            finishTyping();
        }

        // if we are not typing
        if (!isTyping()) {

            // if that fragment is not typeable, make it so
            if (!(fragments.get(index) instanceof TypeableFragment))
                fragments.set(index, new TypeableFragment(fragments.get(index).getString()));

            typeable = (TypeableFragment) fragments.get(index);

            typeableIndex = index;
        }
    }

    private void finishTyping() {

        // if was typing
        if (isTyping()) {

            // turn that typeable fragment into a non-typeable one
            fragments.set(typeableIndex, new RealFragment(fragments.get(typeableIndex).getString()));

            // signal that we are not typing anymore
            typeable = null;
        }
    }

    @Override
    public void type(char c) {
        startTyping();
        int p = caret - getFragmentStart(typeableIndex);
        typeable.string = typeable.string.substring(0, p) + c + typeable.string.substring(p);
        typeable.length++;
        caret++;
    }

    @Override
    public void backspace() {
        startTyping();
        int p = caret - getFragmentStart(typeableIndex);
        typeable.string = typeable.string.substring(0, p - 1) + typeable.string.substring(p);
        typeable.length--;
        caret--;
    }

    @Override
    public void setCaret(int position) {
        finishTyping();
        caret = position;
    }
}
