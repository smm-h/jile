package jile.nilex;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

import jile.common.Common;
import jile.common.Identifiable;
import jile.common.Resource;
import jile.common.Singleton;
import jile.vis.CodeView;
import jile.vis.TreeView;
import jile.vis.View;
import jile.vis.Viewer;
import jile.vis.Visualizable;

/**
 * <p>
 * The command-line interface of the nilex package.
 * </p>
 * <p>
 * The available commands vary depending on the object on the top of the stack:
 * <ul>
 * <li><b>+</b> means it pushes something to the stack.</li>
 * <li><b>-</b> means it pops something from the stack.</li>
 * <li><b>=</b> means it does not affect the stack.</li>
 * </ul>
 * </p>
 * <p>
 * Here are some of the commands available out-of-the-box:
 * <ul>
 * <li>Anything/Nothing
 * <ul>
 * <li>{@code pop} (-)</li>
 * <li>{@code load "filename.ext"} (+ Code)</li>
 * <li>{@code load-as "contents" "ext"} (+ Code)</li>
 * <li>{@code lang "ext"} (+ Language)</li>
 * <li>{@code exit} (=)</li>
 * </ul>
 * </li>
 * <li>{@link Code}
 * <ul>
 * <li>{@code save} (=)</li>
 * <li>{@code save-as "filename.ext" yes/no} (=)</li>
 * <li>{@code make} (+ Some object which may or may not be Encodeable)</li>
 * <li>{@code highlight} (+ syntax highlighting as {@link CodeView})</li>
 * <li>{@code get-syntax-tree} (+ syntax tree as {@link TreeView})</li>
 * <li>{@code get-tree-view} (+ visual tree as {@link TreeView})</li>
 * </ul>
 * </li>
 * <li>{@link Visualizable}
 * <ul>
 * <li>{@code visualize} (+ View)</li>
 * </ul>
 * </li>
 * <li>{@link Encodeable}
 * <ul>
 * <li>{@code encode} (+ Code)</li>
 * </ul>
 * </li>
 * <li>{@link View}
 * <ul>
 * <li>{@code show} (=)</li>
 * </ul>
 * </li>
 * </ul>
 * </p>
 * <p>
 * Make sure to wrap string arguments in either double quotations or nothing at
 * all. Single quotations do not work.
 * </p>
 */
public class Codestack implements Singleton {

    private Codestack() {
    }

    private static Codestack singleton;

    public static Codestack singleton() {
        if (singleton == null) {
            singleton = new Codestack();
        }
        return singleton;
    }

    private final Stack<Object> stack = new Stack<Object>();

    private boolean loopInProgress = false;

    public static boolean isRunning() {
        if (singleton == null)
            return false;
        else
            return singleton.loopInProgress;
    }

    public static void main(String... args) {

        if (Languages.singleton() != null) {

            TokenizerMaker.singleton();
            TextLanguage.singleton();
            TreeLanguage.singleton();
            GrouperMaker.singleton();
            TurtleGraphicsLanguage.singleton();

            List<String> input;
            boolean silently;

            if (args.length == 0) {
                // Viewer.view(new Code("No arguments were provided.",
                // NullLanguage.singleton()).visualize());
                silently = false;
                input = null;

            } else {

                silently = false;

                String realArgs = "";
                for (int i = 0; i < args.length; i++)
                    realArgs += args[i] + " ";

                // Viewer.view(new Code(realArgs, NullLanguage.singleton()).visualize());

                input = Arrays.asList(Common.split(realArgs, '+'));

                // Viewer.view(new Code(input.toString(),
                // NullLanguage.singleton()).visualize());

                // System.out.println(input);
            }

            singleton().loop(input, silently);
        }
    }

    private Queue<String> q;

    private String last;

    private Scanner scanner;

    private String input(String message) {

        print("\t" + message + "\n>>>\t");

        last = q.poll();
        if (last == null) {
            if (scanner.hasNext()) {
                last = scanner.nextLine();
            } else {
                print("\n");
            }
        } else {
            print(last + "\n");
        }

        return last;
    }

    private boolean silent;

    private void print(String string) {
        if (!silent) {
            System.out.print(string);
        }
    }

    private void printSuccess(boolean success) {
        if (success)
            print("\tAction succeeded.");
        else
            print("\tAction failed.");
    }

    private synchronized void loop(List<String> lines, boolean silent) {
        if (loopInProgress)
            return;
        else
            loopInProgress = true;
        this.silent = silent;
        // try {
        // // new ProcessBuilder("cls").start();
        // new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        // } catch (Exception e) {
        // System.err.println("Failed to clear the screen. What OS are you using?");
        // }
        print("~~~\tCODESTACK LOOP STARTED\n");
        if (lines == null)
            q = new LinkedList<String>();
        else
            q = new LinkedList<String>(lines);
        scanner = new Scanner(System.in);
        Object top;
        boolean willPush = false;
        Object toPush = null;
        while (true) {
            if (stack.isEmpty()) {
                top = null;
                print("\n~~~\tSTACK IS EMPTY\n");
            } else {
                top = stack.peek();
                print("\n^^^\t" + Identifiable.getIdentityOf(top));
            }
            print("\n");
            String action = input("What to do next?").strip();
            if (action.equals("exit")) {
                break;
            } else {
                try {
                    switch (action) {
                        case "pop":
                            if (!stack.isEmpty())
                                stack.pop();
                            break;
                        case "help":

                            print("\tSummary:");
                            print("\n\tThere is a stack. You can push stuff on it via commands.");
                            print("\n\tAlso some other commands operate on the thing on the top of");
                            print("\n\tthe stack, without pushing anything. Some commands will ask");
                            print("\n\tfor more input. Finally, you can view some cool things via");
                            print("\n\tthe 'show' command.");

                            print("\n\n\tExample:");
                            print("\n\tload test/testfile.simpletree, make, visualize, show");

                            print("\n\n\tCommands:");
                            print("\n\t.   . . . . . . . . . . . . .   . . . . . . . . . . . . .   .");
                            print("\n\t. .   . load  . . . . . load-as   . . . lang  . . . . .   . .");
                            print("\n\t. . .   save  . . . . . save-as .   . . help. . . . .   . . .");
                            print("\n\t. . . .   . . . . . . .   . . . . .   . exit  . . .   . . . .");
                            print("\n\t. . . . make  . . . .   encode  . . .   highlight   . . . . .");
                            print("\n\t. . . . . .   . . .   . get-syntax-tree get-tree-view . . . .");
                            print("\n\t. . . . . . .   .   . . show  . . . . . pop .   . . . . . . .");
                            print("\n\t. . . . . . . .   . . . . . . . . . . . . .   . . . . . . . .");

                            print("\n\n\tLegend:");
                            print("\n\t~~~: the loop started/ended, or the stack is empty");
                            print("\n\t^^^: the object on the top of the stack");
                            print("\n\t>>>: waiting for you to type in some input");
                            print("\n\t+++: something was pushed to the top of the stack");
                            print("\n\t---: something was popped off the top of the stack");
                            print("\n\t???: invalid input");
                            print("\n\t!!!: wrong type of object at the top of the stack for action");

                            break;
                        case "load":
                            willPush = true;
                            toPush = new Code(Resource.of(input("Enter filename:")));
                            break;
                        case "load-as":
                            willPush = true;
                            Resource r = Resource.of(input("Enter contents:"), input("Enter extension:"));
                            if (Languages.singleton().getLanguageByExt(r.getExt()) != null) {
                                toPush = new Code(r);
                            } else {
                                print("???\tNo language is associated with that extension.\n");
                            }
                            break;
                        case "lang":
                            willPush = true;
                            Language L = Languages.singleton().getLanguageByExt(input("Enter extension:"));
                            if (L != null) {
                                toPush = L;
                            } else {
                                print("???\tNo language is associated with that extension.\n");
                            }
                            break;
                        case "save":
                            if (top instanceof Code)
                                printSuccess(((Code) top).save());
                            else
                                wrongType(action, "Code");
                            break;
                        case "save-as":
                            String filename = input("Enter filename:");
                            if (top instanceof Code)
                                printSuccess(
                                        ((Code) top).saveAs(Resource.of(filename), input("Overwrite?").equals("yes")));
                            else
                                wrongType(action, "Code");
                            break;
                        case "make":
                            willPush = true;
                            if (top instanceof Code)
                                toPush = ((Code) top).language.getMainMaker().make(((Code) top));
                            else
                                wrongType(action, "Code");
                            break;
                        case "highlight":
                            willPush = true;
                            if (top instanceof Code)
                                toPush = ((Code) top).visualize();
                            else
                                wrongType(action, "Code");
                            break;
                        case "get-syntax-tree":
                            willPush = true;
                            if (top instanceof Code)
                                toPush = Grouper.grouped.read((Code) top).visualize();
                            else
                                wrongType(action, "Code");
                            break;
                        case "get-tree-view":
                            willPush = true;
                            if (top instanceof Code)
                                toPush = TreeView.port.read((Code) top).visualize();
                            else
                                wrongType(action, "Code");
                            break;
                        case "encode":
                            willPush = true;
                            if (top instanceof Encodeable)
                                toPush = ((Encodeable) top).encode();
                            else
                                wrongType(action, "Encodeable");
                            break;
                        case "show":
                            if (top instanceof View)
                                Viewer.singleton().show((View) top);
                            else if (top instanceof Visualizable)
                                Viewer.singleton().show(((Visualizable) top).visualize());
                            else
                                wrongType(action, "View or Visualizable");
                            break;
                        default:
                            print("???\tUnexpected argument: " + action + "\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (willPush) {
                    stack.push(toPush);
                    print("+++\t" + Identifiable.getIdentityOf(toPush) + "\n");
                    toPush = false;
                    willPush = false;
                }
            }
        }
        scanner.close();
        print("~~~\tCODESTACK LOOP ENDED\n");
        loopInProgress = false;
    }

    private void wrongType(String action, String topType) {
        print("!!!\tTo do: " + action + ", the top must be an instance of: " + topType + "\n");
    }

    // F5
    public static void makeAndShow(String filename) {
        main("load+${file}+make+show+exit".replace("${file}", filename));
        // main("load", filename, "make", "visualize", "show", "exit");
    }

    // F6
    public static void highlightAndShow(String filename) {
        main("load+${file}+make+pop+highlight+show+exit".replace("${file}", filename));
        // main("load", filename, "highlight", "show", "exit");
    }

    // F7
    public static void getSyntaxTreeAndShow(String filename) {
        main("load+${file}+make+pop+get-tree-view+show+exit".replace("${file}", filename));
        // main("load", filename, "make", "visualize", "show", "exit");
    }
}
