package pknu.vcd.server.domain

enum class Category {

    BRANDING,
    PACKAGE,
    GRAPHIC,
    ILLUSTRATION,
    UIUX,
    EDITORIAL,
    ;

    companion object {

        fun fromCategoriesString(categoriesString: String): List<Category> {
            return categoriesString.split(",").map { valueOf(it) }.toList()
        }

        fun toCategoriesString(categories: List<Category>): String {
            return categories.joinToString(",") { it.name }
        }
    }
}
