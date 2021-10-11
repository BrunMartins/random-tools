#!/usr/bin/groovy
import java.text.*
import groovy.lang.Binding
@GrabConfig(systemClassLoader=true)

@Grab('org.codehaus.groovy:groovy-cli-picocli:3.0.1')
@Grab('info.picocli:picocli:4.2.0')
import groovy.cli.picocli.CliBuilder


import groovy.cli.picocli.CliBuilder

'clear'.execute()

String releaseUrl = 'https://api.github.com/repos/dchapyshev/aspia/releases/latest'
String getReleases = 'curl -s %s'
String findDownloadUrl = 'grep browser_download_url'
String genericGrep = 'grep %s'
String getDownloadUrl = 'cut -d : -f 2,3'
String removeDoubleQuotes = 'tr -d \\"'
String downloadBinary = 'wget -qi -'
osNameProp = 'os.name'
osVersionProp = 'os.version'
String[] versions = [
    'aspia-router',
    'aspia-client',
    'aspia-console',
    'aspia-host',
    'aspia-relay',
]
def flavor = 0
def error = false
String ext = 'msi'
String[] arguments = []

StringBuffer curlResult = new StringBuffer()

CliBuilder cli = new CliBuilder()

cli.h(longOpt: 'help', 'Show usage information')
cli.c(longOpt: 'component', args: 1, argName: 'component', 'The component to install, if none is specified the program provides a list to choose from.')
Object options = args.length <= 0 ? false : cli.parse(args)

if (!options) {
    do {
        Process clear = (isOsValid() && isLinux ? "clear" : "cls").execute()
        clear.waitForOrKill(1000)
        if (error) {
            println error
            error = false
        }

        println 'Choose one of the following using the corresponding digit:'
        for (int i=0;i<versions.length;i++) {
            println "[${i+1}] - ${versions[i]}"
        }

        flavor = System.console().readLine 'Choice: '
        if (flavor !instanceof Integer) {
            error = "Use numbers from ${1} through ${versions.length}."
            flavor = -1
        }
    } while (flavor == -1)
}

if (options.h) {
    cli.usage()
    return
}

return


if (isOsValid) {

    if (isLinux()) {

        this.ext = 'deb'

        // for (String version in this.versions) {
        //     println "Downloading latest ${version}"
        //     Process downloadBinaryCommand = String.format(this.getReleases, this.releaseUrl).execute()
        //     | this.findDownloadUrl.execute()
        //     | String.format(genericGrep, version).execute()
        //     | String.format(genericGrep, this.ext).execute()
        //     | getDownloadUrl.execute()
        //     | removeDoubleQuotes.execute()
        //     | downloadBinary.execute()
        //     downloadBinaryCommand.waitForProcessOutput(curlResult, System.err)
        // }
    }
} else {
    println 'Unsupported Operating System. The supported operating systems are Windows, Debian based linux distros and Ubuntu based linux distros'
}

def isOsValid() {
    return System.properties[osNameProp].toLowerCase().contains('windows') || (System.properties[osNameProp].toLowerCase().contains('linux') && (System.properties[osNameProp].toLowerCase().contains('linux') && (System.properties[osVersionProp].toLowerCase().contains('bebian') || System.properties[osVersionProp].toLowerCase().contains('ubuntu') || System.properties[osVersionProp].toLowerCase().contains('manjaro'))))
}

def isLinux() {
    return System.properties[osNameProp].toLowerCase().contains('linux')
}