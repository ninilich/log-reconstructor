package utils

import org.scalatest.funsuite.AnyFunSuite
import scala.collection.mutable

import utils.QueueUtils.addToQueue

class QueueUtilsTest extends AnyFunSuite {

  var q = mutable.Queue[String]()
  val testData = Seq("10", "20", "30", "10", "50", "10", "60")
  for (item <- testData) q = addToQueue(item, q)


  test("1. Function 'addToQueue': check the size.") {
    val expected = 5
    val actual = q.size
    assert(expected == actual, s"Correct size of logBuffer should be $expected, given $actual")
  }

  test("2. Function 'addToQueue': check the correctness.") {
    val expected = "20"
    val actual = q.dequeue()
    assert(expected == actual, s"Correct size of logBuffer should be $expected, given $actual")
  }

}
