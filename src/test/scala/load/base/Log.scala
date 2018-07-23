package load.base

import com.typesafe.scalalogging._
import org.slf4j.LoggerFactory

trait Log {
  val log = Logger(LoggerFactory.getLogger(this.getClass))
}
