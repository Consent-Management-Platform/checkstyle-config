// Expose the Checkstyle config files
configurations {
    create("checkstyleConfig") {
        isVisible = true
        isTransitive = false
    }
}

artifacts {
    add("checkstyleConfig", file("src/main/resources/checkstyle.xml"))
    add("checkstyleConfig", file("src/main/resources/suppressions.xml"))
}
