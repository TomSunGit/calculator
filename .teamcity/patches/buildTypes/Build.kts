package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.kotlinScript
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'Build'
accordingly, and delete the patch script.
*/
changeBuildType(RelativeId("Build")) {
    expectSteps {
        script {
            id = "simpleRunner"
            scriptContent = """echo "auto merge""""
        }
    }
    steps {
        update<ScriptBuildStep>(0) {
            clearConditions()
            scriptContent = "echo %teamcity.serverUrl%"
        }
        insert(1) {
            kotlinScript {
                id = "kotlinScript"
                content = """
                    #!/usr/bin/env kotlin
                    
                    //@file:Repository("https://download.jetbrains.com/teamcity-repository")
                    @file:Repository("http://%teamcity.serverUrl%/repository/download/")
                    
                    @file:DependsOn("org.jetbrains.teamcity:server-api:2024.12")
                    
                    import jetbrains.buildServer.serverSide.ProjectManager
                    fun listProjects(projectManager: ProjectManager) {
                        val projects = projectManager.projects
                        for (project in projects) {
                            println("Project: ${'$'}{project.name} (ID: ${'$'}{project.projectId})")
                        }
                    }
                """.trimIndent()
            }
        }
    }
}
