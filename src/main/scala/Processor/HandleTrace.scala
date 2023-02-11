package Processor

import org.json4s.NoTypeHints
import org.json4s.native.Serialization

import model.{LogString, LogTree}
import utils.TreeUtils.{assembleTree, getTreeSize}

object HandleTrace {

  /**
    * Function to handle a Set[LogString], to assemble a Tree for output JSON,
    * to check the correctness of Tree and to print JSON into stdout.
    */
  def handleTrace(logs: Set[LogString]): Unit = {
    implicit val formats = Serialization.formats(NoTypeHints)
    assembleTree(logs) match {
      case Some(treeForJSON) if isTreeCorrect(treeForJSON, logs) => println(Serialization.write(treeForJSON))
      case None                                                  => // just ignore this Tree line
    }
  }

  /**
    * Function to check the correctness of the tree: the number of edges should bee equal to the number of logs.
    */
  def isTreeCorrect(tree: LogTree, logs: Set[LogString]): Boolean = getTreeSize(tree) == logs.size
}
