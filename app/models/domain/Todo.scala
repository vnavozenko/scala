package models.domain

import java.util.Date

import anorm.{Macro, RowParser}

case class Todo(id: Long, title: String, description: String, createdById: Long, assignedToId: Option[Long], created: Date, updated: Option[Date], completed: Boolean, completedDate: Option[Date])

case class Comment(id: Long, description: String, todoId: Long, createdById: Long, created: Date)


object TodoParsers {
  val todoParser: RowParser[Todo] = Macro.parser[Todo]("id", "title", "description", "created_by_id", "assigned_to_id", "created", "updated", "completed", "completed_date")
  val commentParser: RowParser[Comment] = Macro.parser[Comment]("id", "description", "todo_id", "created_by_id", "created")
}
