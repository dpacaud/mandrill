//
// This script is executed by Grails after plugin was installed to project.
// This script is a Gant script so you can use all special variables provided
// by Gant (such as 'baseDir' which points on project base dir). You can
// use 'ant' to access a global instance of AntBuilder
//
// For example you can create directory under project tree:
//
//    ant.mkdir(dir:"${basedir}/grails-app/jobs")
//


def configFile = new File(basedir, 'grails-app/conf/Config.groovy')

if (configFile.exists() && configFile.text.indexOf("mandrill") == -1) {
    println "Adding mandrill config options to the Config file"
configFile.withWriterAppend {
it.writeLine '\n// Added by the Grails Mandrill plugin:'
it.writeLine '''mandrill {
	apiKey = ""
	// insert proxy values if needed
	//proxy {
	//    host = ""
	// The port Value has to be an integer ;)
	//    port = ""
	//}
}
'''
	}
}