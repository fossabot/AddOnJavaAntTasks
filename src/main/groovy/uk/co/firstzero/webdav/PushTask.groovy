package uk.co.firstzero.webdav

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class PushTask extends DefaultTask  {

    /**
     * Pushes files to a WEBDAV site, proxy configuration is supported
     * @return Nothing
     */
    @TaskAction
    void pushAction() {
        def antPush = new uk.co.firstzero.webdav.Push()

        antPush.setUser((String)project.pushArgs.user)
        antPush.setPassword((String)project.pushArgs.password)
        antPush.setUrl((String)project.pushArgs.url)
        antPush.setOverwrite((boolean)project.pushArgs.overwrite)

        if (project.pushArgs.proxyUser != null)
            antPush.setProxyUser((String)project.pushArgs.proxyUser)

        if (project.pushArgs.proxyPassword != null)
            antPush.setProxyPassword((String)project.pushArgs.proxyPassword)

        if (project.pushArgs.proxyHost != null)
            antPush.setProxyHost((String)project.pushArgs.proxyHost)

        if (project.pushArgs.proxyPort != null)
            antPush.setProxyPort((int)project.pushArgs.proxyPort)

        println("Project path - " + project.getPath())

        antPush.setUp()

        project.pushArgs.tree.each {File file ->
            if (project.pushArgs.createDirectoryStructure)
                antPush.createDirectory(file.getPath(), file.getName());

            boolean result = antPush.uploadFile(file)
            logger.info("Upload status of " + file.getName() + " - " + result);
        }

    }
}
    

