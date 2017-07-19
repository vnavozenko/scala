package models.dto

import java.util.Date

case class TodoDto(id: Option[Long], title: String, description: String, createdById: Long, assignedToId: Option[Long], created: Option[Date], updated: Option[Date], completed: Option[Boolean], completedDate: Option[Date])

object TodoDto {
}

case class CommentDto(id: Option[Long], description: String, todoId: Long, createdById: Long, created: Option[Date])

object CommentDto {
}