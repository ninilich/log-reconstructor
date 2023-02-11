package utils

import model.LogString
import model.TypeAliases.Buffer

object BufferUtils {

  /**
    * Function to add a LogString into the Map for logs.
    * Key - trace, Value - Set of LogString
    */
  def addToBuffer(newLine: LogString, logBuffer: Buffer): Buffer = {
    val trace = newLine.trace
    logBuffer.get(trace) match {
      case Some(existingLines) => logBuffer.updated(trace, existingLines + newLine)
      case None                => logBuffer.updated(trace, Set(newLine))
    }
  }
}
