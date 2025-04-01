package domain.tutorial

/**
 * Describes all possible interactions.
 * Allows passing action to view model, rather than exposing view model functions themselves.
 * Based on the action passed, VM will interact with the db.
 */
sealed class TaskAction {
    data class Add(val task: ToDoTask): TaskAction()
    data class Update(val task: ToDoTask): TaskAction()
    data class Delete(val task: ToDoTask): TaskAction()
    data class SetCompleted(val task: ToDoTask, val completed: Boolean): TaskAction()
    data class SetFavourite(val task: ToDoTask, val isFavourite: Boolean): TaskAction()
}