package todo.components

import com.github.andrewoma.react.*
import todo.stores.*
import todo.actions.todoActions

data class MainSectionProperties(val todos: Collection<Todo>)

class MainSection : ComponentSpec<MainSectionProperties, Unit>() {
    class object {
        val factory = react.createFactory(MainSection())
    }

    override fun Component.render() {
        log.debug("MainSection.render", props)

        if (props.todos.size() < 1) return

        section({ id = "main" }) {
            input({
                id = "toggle-all"
                `type` = "checkbox"
                onChange = { onToggleCompleteAll() }
                checked = if ( props.todos.areAllCompleted() ) "checked" else ""
            })

            label({ htmlFor = "toggle-all" }) { text("Mark all as complete") }

            ul({ id = "todo-list" }) {
                for (todo in props.todos) {
                    todoItem(TodoItemProperties(key = todo.id, todo = todo))
                }
            }
        }
    }

    fun onToggleCompleteAll() {
        todoActions.toggleCompleteAll()
    }
}

fun Component.todoMainSection(props: MainSectionProperties): Component {
    return construct(Component({ MainSection.factory(Ref(props)) }))
}