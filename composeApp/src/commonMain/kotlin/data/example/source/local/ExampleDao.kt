package data.example.source.local


sealed class ExampleDao {
    data class Add(val example: ExampleEntityLocal) : ExampleDao()

    data class Update(val example: ExampleEntityLocal) : ExampleDao()

    data class Delete(val example: ExampleEntityLocal) : ExampleDao()
}