package utils

import model.{LogString, LogTree, Service}

object TreeUtils {

  /**
    * Function to assemble a tree from a set of logs for a single trace
    */
  def assembleTree(logs: Set[LogString]): Option[LogTree] = {

    def buildService(log: LogString, logsToSearch: Set[LogString]): Service =
      Service(log.serviceName,
              log.startTimestamp,
              log.endTimestamp,
              addCalledServices(log.span, logsToSearch: Set[LogString]),
              log.span)

    def addCalledServices(parentSpan: String, logsToSearch: Set[LogString]): List[Service] = {
      val services = logsToSearch.filter(_.callerSpan == parentSpan)
      if (services.isEmpty) List.empty[Service]
      else
        services.toList.map(item => buildService(item, logsToSearch - item))
    }

    val rootLine = logs.filter(_.callerSpan == "null").toSeq // find a root
    if (rootLine.size != 1) None
    else {
      val service = rootLine.head // get a root
      val id = service.trace
      val newLogs = logs - rootLine.head
      Some(
        LogTree(id,
                Service(service.serviceName,
                        service.startTimestamp,
                        service.endTimestamp,
                        addCalledServices(service.span, newLogs),
                        service.span))
      )
    }
  }

  /**
    * Function to calculate a number of edges in LogTree ( = number of Services in the output JSON)
    */
  def getTreeSize(tree: LogTree): Int = {
    def getServiceSize(service: Service): Int = {
      service.calls.size match {
        case 0 => 1
        case _ => 1 + service.calls.map(getServiceSize).sum
      }
    }
    getServiceSize(tree.root)
  }

}
