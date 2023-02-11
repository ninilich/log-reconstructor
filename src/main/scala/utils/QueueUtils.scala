package utils

import scala.collection.mutable

object QueueUtils {

  /**
    * Function to add to the queue a new trace
    * If queue already contains this trace we delete trace from the queue
    * and add it at the end.
    */
  def addToQueue(trace: String, queue: mutable.Queue[String]): mutable.Queue[String] = {
    trace match {
      case item if queue contains item =>
        queue.dequeueAll(_ == item)
        queue += item
      case _ =>
        queue += trace
    }
  }
}
