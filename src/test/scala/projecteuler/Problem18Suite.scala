package projecteuler

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import scala.io.Source



@RunWith(classOf[JUnitRunner])
class Problem18Suite extends FunSuite {
  val prob18Triangle = "75, 95, 64, 17, 47, 82, 18, 35, 87, 10, 20, 04, 82, 47, 65, 19, 01, 23," +
    "75, 03, 34, 88, 02, 77, 73, 07, 63, 67, 99, 65, 04, 28, 06, 16, 70, 92, 41, 41, 26, 56, 83, 40, 80, 70," +
    "33, 41, 48, 72, 33, 47, 32, 37, 16, 94, 29, 53, 71, 44, 65, 25, 43, 91, 52, 97, 51, 14, 70, 11, 33, 28," +
    "77, 73, 17, 78, 39, 68, 17, 57, 91, 71, 52, 38, 17, 14, 91, 43, 58, 50, 27, 29, 48, 63, 66, 04, 68, 89," +
    "53, 67, 30, 73, 16, 69, 87, 40, 31, 04, 62, 98, 27, 23, 09, 70, 98, 73, 93, 38, 53, 60, 04, 23";

  test("Problem18 works with 15 rows, naive") {
    val triangle = Triangle(prob18Triangle)
    val res = triangle.findMaxPathSumNaive
    assert(res == 1074)
  }

  test("Problem18 works with 15 rows") {
    val triangle = Triangle(prob18Triangle)
    val res = triangle.findMaxPathSum
    assert(res == 1074)
  }

  test("Problem18 works with 4 rows") {
    val triangle = Triangle("3, 7, 4, 2, 4, 6, 8, 5, 9, 3")
    val res = triangle.findMaxPathSum
    assert(res == 23)
  }

  test("Problem67 works") {
    val triangle = Triangle(Source.fromFile(
      "C:\\work\\pe\\Problem18\\tests\\projecteuler\\p067_triangle.txt").getLines().toArray)
    val res = triangle.findMaxPathSum
    println(res)
    assert(res == 7273)
  }

  test("Problem18 works with for degenerate cases") {
    val (res1, res2) = (Triangle("1") findMaxPathSum, Triangle("1, 2, 3") findMaxPathSum)
    assert(res1 == 1  &&  res2 == 4)
    try {
      Triangle(Array[Int]()).findMaxPathSum
      fail()
    } catch  {
      case e: IllegalArgumentException => // all well
      case _ => fail()
    }
  }
}



  
