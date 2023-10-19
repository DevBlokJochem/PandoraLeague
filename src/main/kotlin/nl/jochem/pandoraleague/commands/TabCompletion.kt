package nl.jochem.pandoraleague.commands

enum class TabCompletion(val permission: String) {
    give("pandoraleague.give"),
    remove("pandoraleague.remove"),
    info("pandoraleague.info"),
    reload("pandoraleague.reload");

    companion object {
        fun findByName(name: String): TabCompletion? {
            values().filter { it.name == name }.forEach { return it }
            return null
        }
    }
}