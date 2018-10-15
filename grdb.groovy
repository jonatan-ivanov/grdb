#! /usr/bin/env groovy

CliBuilder cli = new CliBuilder(usage: "./${this.class.simpleName}.groovy <options> [expression]", width: 250)
cli.with {
    _ longOpt: 'dependency', args: 1, required: true, argName: 'dependency', 'DB Driver dependeny from Maven Central'
    _ longOpt: 'driver', args: 1, required: true, argName: 'driver', 'DB Driver'
    _ longOpt: 'url', args: 1, required: true, argName: 'url', 'DB URL'
    _ longOpt: 'username', args: 1, required: true, argName: 'username', 'DB Username'
    _ longOpt: 'password', args: 1, required: true, argName: 'password', 'DB Password'
}

OptionAccessor options = cli.parse(args)
if (options == null) System.exit(1)
else if (options.arguments().size() > 1) {
    cli.usage()
    System.exit(1)
}

grab(options.dependency)
def binding = new Binding()
def shell = new GroovyShell(GroovyObject.class.classLoader, binding)
groovy.sql.Sql.withInstance(options.url, options.username, options.password, options.driver) { sql ->
    binding.sql = sql
    if (options.arguments().isEmpty()) {
        while (true) {
            try {
                String input = System.console().readLine('>')
                if (input != null) println toJSON(shell.evaluate(input))
                else break
            }
            catch (Exception exception) {
                println exception
            }
        }
    }
    else {
        println toJSON(shell.evaluate(options.arguments().first()))
    }
}

private String toJSON(Object obj) {
    return new groovy.json.JsonBuilder(obj).toPrettyString()
}

private void grab(String dependency) {
    groovy.grape.Grape.grab(
        [ classLoader: GroovyObject.class.classLoader ],
        org.codehaus.groovy.tools.GrapeUtil.getIvyParts(dependency)
    )
}
