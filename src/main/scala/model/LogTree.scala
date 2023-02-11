package model

/**
 * Case classes for output JSON-structure
 */
case class Service(service: String,
                   start: String,
                   end: String,
                   calls: List[Service],
                   span: String)

case class LogTree(id: String, root: Service)
