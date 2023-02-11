package utils

import org.scalatest.funsuite.AnyFunSuite
import scala.io.Source

import model.LogString
import model.TypeAliases.Buffer
import utils.BufferUtils.addToBuffer

class BufferUtilsTest extends AnyFunSuite {
  val filename = "testMapLogs.txt"
  val bufferedSource = Source.fromResource(filename)
  var testData = Set.empty[String]
  for (line <- bufferedSource.getLines) testData = testData + line
  bufferedSource.close()

  val logs: Set[LogString] = testData.map(LogString(_).get)
  var logBuffer: Buffer = Map.empty
  for (log <- logs) {
    logBuffer = addToBuffer(log, logBuffer)
  }

  test("1. Function 'addToMap': check the number of keys.") {
    val expected = 2
    val actual = logBuffer.size
    assert(expected == actual, s"Correct size of logBuffer should be $expected, given $actual")
  }

  test("2. Function 'addToMap': check the correctness of values") {
    val expected = 3
    val actual = logBuffer("trace1").size
    assert(expected == actual, s"Correct size of logBuffer should be $expected, given $actual")
  }
}
