package models.domain

import anorm._

case class User(id: Long, email: String)

object UserParsers {
  val userParser: RowParser[User] = Macro.namedParser[User]
}