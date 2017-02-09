package projecteuler


import scala.collection._

/**
  * Created by ilchen on 09/02/2017.
  */

sealed case class Node(v: Int, left: Node, right: Node) {
  def hasChildren(): Boolean = left != null
  override def hashCode(): Int = super.hashCode()
}

class Triangle(val root: Node) {

  def findMaxPathSumNaive(): Int = findMaxPathSumHelper(root)
  private def findMaxPathSumHelper(subtree: Node): Int = {
    if (!subtree.hasChildren())  subtree.v
    else  Math.max(findMaxPathSumHelper(subtree.left), findMaxPathSumHelper(subtree.right)) + subtree.v
  }


  def findMaxPathSum(): Int = findMaxPathSumHelper(root, mutable.Map.empty)
  private def findMaxPathSumHelper(subtree: Node, accu: mutable.Map[Node, Int]): Int = {
    if (!subtree.hasChildren())       subtree.v
    else if (accu.contains(subtree))  accu(subtree)
    else  {
      val leftSum = findMaxPathSumHelper(subtree.left, accu)
      val rightSum = findMaxPathSumHelper(subtree.right, accu)
      val sum = Math.max(leftSum, rightSum) + subtree.v
      accu += (subtree -> sum)
      sum
    }
  }

}

object Triangle {

  def apply(nodes: Array[Int]): Triangle = {
    val numLines = (Math.sqrt(nodes.length * 8 + 1) - 1) / 2
    if (numLines != Math.floor(numLines))
      throw new IllegalArgumentException(s"Cannot make a triangle out of ${nodes.length} elements")
    val n: Int = numLines.asInstanceOf[Int]
    if (n == 0)  throw new IllegalArgumentException("No numbers to make a triangle out of")
    var (rest, lastRow) = nodes.splitAt(nodes.length - n)
    var accu = lastRow.map(Node(_, null, null))
    for (i <- n-1 to 1 by -1) {
      val (r, l) = rest.splitAt(rest.length - i)
      rest = r
      lastRow = l
      val newRow = (0 until i).map(idx => Node(lastRow(idx), accu(idx), accu(idx + 1)))
      accu = newRow.foldRight(accu)((node, a) => node +: a)
    }
    println(s"Tree of height $n constructed")
    new Triangle(accu.head)
  }

  /** Factory method for out of a comma-separated list (no line breaks) */
  def apply(str: String): Triangle = {
    apply(str.split("\\s*,\\s*").map(_.toInt))
  }

  /** Factory method for out of a whitespace-separated list (with possible line breaks) */
  def apply(lines: Array[String]): Triangle = {
    apply(lines.flatMap(_.split("\\s+")).map(_.toInt))
  }

  /** Factory method to make a Triangle with a required number of rows out of random numbers */
  def apply(numRows: Int): Triangle = {
    val maxVal = 100
    val r = new scala.util.Random
    apply((0 until (numRows * numRows + numRows) / 2).toArray map (_ => r.nextInt(maxVal)))
  }
}

