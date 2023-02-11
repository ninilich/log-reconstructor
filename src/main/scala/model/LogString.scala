package model

/**
  * Case class for one log-string
  */
case class LogString(startTimestamp: String,
                     endTimestamp: String,
                     trace: String,
                     serviceName: String,
                     callerSpan: String,
                     span: String)

/**
  * Companion object for the constructor from the one string
  */
object LogString {
  def apply(s: String): Option[LogString] = {
    val splitFields = s.split(" ")
    if (splitFields.length != 5) None
    else {
      val spans = splitFields(4).split("->")
      if (spans.length != 2) None
      else Some(new LogString(splitFields(0), splitFields(1), splitFields(2), splitFields(3), spans(0), spans(1)))
    }
  }
}
