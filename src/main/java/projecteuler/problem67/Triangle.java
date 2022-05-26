
package projecteuler.problem67;

import java.util.Arrays;
import static java.util.Arrays.stream;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a triangle of integer values whose n<sup>th</sup> row has n values.
 *
 * An example triangle:
 * <pre>
 *     3
 *    7 4
 *   2 4 6
 *  8 5 9 3
 * </pre>
 */
public class Triangle {
    public static final record  Node(int val, Node left, Node right) {
        public boolean hasChildren() {
            return  left != null;
        }

        /**
         * Induces an equivalence relation based on two nodes having the same value and the same subtrees
         * (by object reference). An auto-generated equal does a recursive value comparison traversing the whole tree
         * and has a computational complexity of O(2<sup>tree-height</sup>), hence the need to override it.
         */
        @Override
        public boolean  equals(Object obj) {
            if (this == obj)  return  true;
            if (!(obj instanceof Node that))  return  false;
            // The same value and the same subtrees implies equivalence
            return  val == that.val  &&  left == that.left  &&  right == that.right;
        }

        @Override
        public int hashCode() {
            int   result = Integer.hashCode(val);
            result = 31 * result + (left == null ? 0 : System.identityHashCode(left));
            result = 31 * result + (right == null ? 0 : System.identityHashCode(right));
            return  result;
        }
    }

    private final Node   root;

    public Triangle(Node rootNode) {
        root = rootNode;
    }

    public int  findMaxPathSumNaive() {
        return  findMaxPathSumNaiveHelper(root);
    }
    private int findMaxPathSumNaiveHelper(Node subtree) {
        return  subtree.hasChildren()  ?  Math.max(findMaxPathSumNaiveHelper(subtree.left),
                                                   findMaxPathSumNaiveHelper(subtree.right)) + subtree.val()
                                       : subtree.val();
    }

    public int  findMaxPathSum() {
        return findMaxPathSumHelper(root, new HashMap<>());
    }
    private int  findMaxPathSumHelper(Node subtree, Map<Node, Integer> accu) {
        if (!subtree.hasChildren()) {
            return  subtree.val();
        } else if (accu.containsKey(subtree)) {
            return  accu.get(subtree);
        } else {
            int   leftSum = this.findMaxPathSumHelper(subtree.left, accu),
                  rightSum = this.findMaxPathSumHelper(subtree.right, accu),
                  sum = Math.max(leftSum, rightSum) + subtree.val();
            accu.put(subtree, sum);
            return sum;
        }
    }

    /**
     * Constructs a new {@link Triangle} instance from an array of node values.
     */
    public static Triangle  newInstance(int[] nodesVals) {
        double   numLines = (Math.sqrt((nodesVals.length * 8 + 1)) - 1) / 2;
        if (numLines != Math.floor(numLines)) {
            throw  new IllegalArgumentException(String.format("Cannot make a triangle out of %d elements",
                                                              nodesVals.length));
        } else {
            int   n = (int) numLines;
            if (n == 0) {
                throw  new IllegalArgumentException("No numbers to make a triangle out of");
            } else {
                int[]   rest = Arrays.copyOfRange(nodesVals, 0, nodesVals.length-n),
                        lastRow = Arrays.copyOfRange(nodesVals, nodesVals.length-n, nodesVals.length);
                Node[]  lastRowNodes = Arrays.stream(lastRow).mapToObj(val -> new Node(val, null, null))
                                        .toArray(Node[]::new);

                for (int i=n-1; i > 0; i--) {
                    lastRow = Arrays.copyOfRange(rest, rest.length - i, rest.length);
                    rest = Arrays.copyOfRange(rest, 0, rest.length - i);
                    for (int j=0; j < i; j++) {
                        lastRowNodes[j] = new Node(lastRow[j], lastRowNodes[j], lastRowNodes[j+1]);
                    }
                }

                return new Triangle(lastRowNodes[0]);
            }
        }
    }

    /**
     * Constructs a new {@link Triangle} instance out of a comma-separated list of node values (no line breaks).
     */
    public static Triangle  newInstance(String str) {
        return  newInstance(stream(str.split("\\s*,\\s*")).mapToInt(Integer::parseInt).toArray());
    }

    /**
     * Constructs a new {@link Triangle} instance out of a whitespace-separated list of node values
     * (with possible line breaks).
     */
    public static Triangle  newInstance(String[] lines) {
        return  newInstance(stream(lines).flatMap(x -> stream(x.split("\\s+")))
                .mapToInt(Integer::parseInt).toArray());
    }

    public static void main(String[] argv) {
        Triangle triangle = newInstance(new int[]{3, 7, 4, 2, 4, 6, 8, 5, 9, 3});
        System.out.println("Maximum path: " + triangle.findMaxPathSum());
        System.out.println(triangle.root);
    }

}

