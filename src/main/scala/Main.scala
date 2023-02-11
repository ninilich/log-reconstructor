import java.util.Scanner
import scala.collection.mutable

import model.LogString
import model.TypeAliases.Buffer
import utils.BufferUtils.addToBuffer
import utils.QueueUtils.addToQueue
import Processor.HandleTrace.handleTrace
import Constants.threshold

object Main extends App {

  var logBuffer: Buffer = Map.empty // buffer for logs: key = trace, value = Set of logs
  var traceQueue = mutable.Queue[String]() // queue for the management of “pending” entries

  // Run until stdin has the data
  val scanner = new Scanner(System.in)
  while (scanner.hasNext()) {
    val input = scanner.nextLine()
    LogString(input) match { // parse the input line and construct LogString
      case Some(logLine) =>
        traceQueue = addToQueue(logLine.trace, traceQueue)
        logBuffer = addToBuffer(logLine, logBuffer)
      case None => // just ignore this input line
    }

    // if an entry of trace T hasn't appeared for the past 100 entries (threshold),
    // you should declare this trace as finished and send it to output
    if (traceQueue.size > threshold) {
      val trace = traceQueue.dequeue()
      traceQueue.dequeueAll(_ == trace)
      val logs = logBuffer(trace)
      logBuffer -= trace

      handleTrace(logs)
    }
  }
  // after standard input closes, all pending traces should be processed
  logBuffer.values.foreach(handleTrace)
}
