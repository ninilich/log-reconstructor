package utils

import org.json4s.NoTypeHints
import org.json4s.native.Serialization
import org.scalatest.funsuite.AnyFunSuite

import model.LogString
import utils.TreeUtils.{assembleTree, getTreeSize}

import scala.io.Source

class TreeUtilsTest extends AnyFunSuite {
  implicit val formats = Serialization.formats(NoTypeHints)
  val filename = "testTreeLogs.txt"
  val bufferedSource = Source.fromResource(filename)
  var testData = Set.empty[String]
  for (line <- bufferedSource.getLines) testData = testData + line
  bufferedSource.close()

  val logs: Set[LogString] = testData.map(LogString(_).get)

  val tree = assembleTree(logs).get

  test("1. Function 'assembleTree': check the correctness") {
    val expected = Source
      .fromResource("testTreeLogsOutput.json")
      .getLines()
      .toList.head
    val actual = Serialization.write(tree)
    assert(expected == actual, s"Correct size of logBuffer should be $expected, given $actual")
  }

  test("2. Function 'getTreeSize': check the correctness") {
    val expected = getTreeSize(tree)
    val actual = 4
    assert(expected == actual, s"Correct size of logBuffer should be $expected, given $actual")
  }
}
