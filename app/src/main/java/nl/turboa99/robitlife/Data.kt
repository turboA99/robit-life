package nl.turboa99.robitlife

val DATA = listOf(
    Situation(
        1,
        Stat.Individualism,
        Role.Parent,
        listOf(
            Option('A', -1),
            Option('B', 0),
            Option('C', 1)
        )
    ),
    Situation(
        2,
        Stat.UncertaintyAvoidance,
        Role.OlderSibling,
        listOf(
            Option('A', -1),
            Option('B', 0),
            Option('C', 1)
        )
    ),
    Situation(
        10,
        Stat.UncertaintyAvoidance,
        Role.OlderSibling,
        listOf(
            Option('A', -1),
            Option('B', 1)
        )
    )
)

enum class Stat {
    UncertaintyAvoidance,
    Individualism,
    PowerDistance,
    TermOrientation
}

enum class Role(val textName: String) {
    Teacher("Teacher"),
    Parent("Parent"),
    Councilor("Councilor"),
    OlderSibling("Older Sibling")
}

data class Situation(
    val id: Int,
    val stat: Stat,
    val priorityRole: Role,
    val options: List<Option>
)
data class Option(
    val char: Char,
    val value: Int
)
