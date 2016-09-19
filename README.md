## REST Assured Demo Project...

This project is the Apache Wink Demo for the ICONUK 2016 session.

This source repository contains two plugins. The contacts demo and the template plugin. This guide will outline basic steps to install these plugins and how to create your first Apache Wink plugin from the template.

### Prerequisites

- For plugin development, it is recommended to have an **IBM Domino server** installed locally. Otherwise, you need to create and upload JAR files to the Domino server and repeat this for every time you make a change in the plugin.
- Make sure you have downloaded **Eclipse IDE**. I have been using the version Mars and "*The Eclipse for RCP and RAP*" edition for a while (not tried Eclipse Neon yet). The version is not that important, but some features of XPages SDK for Eclipse may not work quite well with Eclipse Mars. So, using Luna might be a better idea if you are a newbie in Plugin Development.
  - https://www.eclipse.org/downloads/packages/release/Luna/SR2
- What about Eclipse for MacOS?
  - I am using Eclipse for MacOS for my customer projects. But it needs some additional steps for setting up. Also, testing plugins on a Domino server required some level of one-time setup for me. So it's possible, but needs a lot of manual configuration.
- **OpenNTF XPages SDK** is needed to set up Eclipse IDE for XPages development.
  - https://www.openntf.org/main.nsf/project.xsp?r=project/XPages%20SDK%20for%20Eclipse%20RCP/releases/0C60A1BFF5F40FD586257D8D005AA593
- If you also want to debug your plugins from the Eclipse IDE, you should install **Debug plugin**.
  - https://www.openntf.org/main.nsf/project.xsp?r=project/XPages%20SDK%20for%20Eclipse%20RCP/releases/CBF874E9C4607B4C8625799D00287B8C

### Setting up Eclipse IDE

- Eclipse IDE should be configured for Domino. Thanks to [Nathan T. Freeman](https://nathantfreeman.wordpress.com/)'s [XPages SDK project](https://www.openntf.org/main.nsf/project.xsp?r=project/XPages%20SDK%20for%20Eclipse%20RCP/releases/0C60A1BFF5F40FD586257D8D005AA593), this is an easy process for standard setup. Also various bloggers have outlined the configuration steps previously. Here are some of them:
  - John Cooper's [step by step installation guide](http://developmentblog.johnmcooper.co.uk/2014/05/configuring-eclipse-for-xpages-osgi-plugins-part1.html)
  - Paul Withers's [blog post](http://www.intec.co.uk/xpages-osgi-plugins-3-configuring-for-domino).

### Wink Demo Plugin

Wink Demo plugin (project name: *com.developi.wink.demo*) is a sample Apache Wink plugin with several resource implementations. It uses David Leedy's [Fake Names database](https://github.com/leedy/mwlug2016/tree/master/Working%20databases) for sample data.

#### Installation

- Fake names database is in [the repository](https://github.com/sbasegmez/RestAssuredDemo/blob/master/_sampledata/fakenames.zip). Extract and put the NSF into your Domino server. Demo plugin uses `RestAssured/fakenames.nsf` path for the database which might be modified in the [DominoAccessor class](https://github.com/sbasegmez/RestAssuredDemo/blob/master/com.developi.wink.demo/src/com/developi/wink/demo/data/DominoAccessor.java#L17).
- In Eclipse IDE, you can import the project directly from Github.
  - Click `File\Import`, select `Git\Projects` and click Next.
  - Select `Clone URI` from the list of options.
  - Enter URI: https://github.com/sbasegmez/RestAssuredDemo
  - Click Next
  - Make sure `Master` branch selected, click Next.
  - Change the local directory if you want. Click Next (It will download the repository now).
  - Select `Import existing projects` option and click Next.
  - Select `com.developi.wink.demo` project only for now. The other project will be imported later.
  - Click Finish.
- Now projects will be seen in the Package explorer. There should be no compile errors ef everything is OK.
  - If there is any compile error with `lotus.domino.*` classes, you may need to update JRE setting for plugins. It needs to have Notes.JAR file to compile in the class path. XPages SDK installs a local JVM from Domino server installation and plugin has been configured to use it. Check Eclipse SDK configuration steps in case.

#### Running Plugin on the server:

- Open `Run\Run Configurations` in the Eclipse IDE
- Right click on `OSGi Framework` and select `New`
  - Name the new Run configuration (e.g. *DominoWeb*)
  - Make sure `Domino OSGi Framework` selected as the Framework
  - In the bundle list, only one plugin needs to be selected for the demo. Deselect All and select `com.developi.wink.demo` plugin.
  - Click Run.
  - Eclipse will now confirm the Domino directories. Click OK.
    - When properly configured, Eclipse will create a little file in Domino OSGi workspace. Using this file, HTTP task will look for your Eclipse workspace for plugins to be installed.
  - Restart HTTP server on Domino server (use `restart task http`)
    - Every time you make a change in the plugin code, you need to restart the HTTP server.
    - Tip: You may define an External Tool configuration within Eclipse and bind a key for that. The command to run is `c:\IBM\Domino\nserver.exe -c "res task http"` 

#### Sample REST services:

| Method | Service Name           | Parameters          | Notes                                    |
| ------ | ---------------------- | ------------------- | ---------------------------------------- |
| GET    | /twink/test            | N/A                 | Open with browser, returns the effective user name or force authentication. |
| GET    | /twink/v1/ping         | N/A                 | Versioning example                       |
| GET    | /twink/v2/ping         | N/A                 | Versioning example                       |
| GET    | /twink/contacts        | start=int count=int | Returns a JSON list of contacts          |
| POST   | /twink/contacts        | N/A                 | Accepts JSON or Multipart Form Data (not implemented). Saves a new contact document. |
| GET    | /twink/contact/{id}    | N/A                 | Returns a JSON representation of a specific contact by ID |
| GET    | /twink/contacts/search | q=String            | FTSearch for a string within contacts, returns a JSON list. |

### Using template plugin to create your own Wink Project

The second project in the repository is the template project to start your first Wink plugin quickly.

- In Eclipse menu, `File\Import` and select `General\Existing projects into Workspace`. Click Next.
- Click `Browse` for `Select root directory` and pick the root of the Git repository directory you have cloned.
  - By default, it will be `C:\Users\<your windows username>\git\RestAssuredDemo`
- You should only be able to select `com.developi.wink.template` project now.
- Make sure you ticked `Copy projects into workspace` option. Otherwise, it will be linked to the Git repo.
- Click Finish.
- Now you will have a new copy of the template project. It is located inside your Eclipse Workspace.
  - You can move it to any folder you want (e.g. your own Git repo) by right click on the project and `Refactor\Move`.
  - You can rename it by `Refactor\Rename`. (I will use `com.acme.restapi` name for the guide).
  - Expand *src* directory and rename the package `com.developi.wink.template` by right click and `Refactor\Rename`.
    - New name will be `com.acme.restapi`. Make sure enable all options on this page. configuration files and subpackages should also be updated.

#### Servlet

In `plugin.xml` file, you will see the servlet definition for Apache Wink. If everything is OK, you will see this content:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?eclipse version="3.4"?>
<plugin>
   <extension
		id="templateservlet"
		name="templateservlet"
		point="org.eclipse.equinox.http.registry.servlets">
		<servlet 
			alias="/testwink" 
			load-on-startup="false"
			class="com.acme.restapi.servlet.CustomWinkServlet">
			<init-param
				name="applicationConfigLocation"
				value="/WEB-INF/wink_application">
			</init-param>
			<init-param
				name="propertiesLocation"
				value="/WEB-INF/wink.properties">
			</init-param>
        	<init-param name="DisableHttpMethodCheck" value="true"/>
		</servlet>
	</extension>
</plugin>
```

- `alias` is the base URI for your wink project. Domino server will grab all requests under this URI and send them into your Wink plugin. Change this according to your choice of URI.
  - Do not use existing URI's on Domino (e.g. `xsp`, `api`, any directory names exist in your data folder)
- `class` points into the custom servlet class in the project. You may use this class to inject any code to be run at a servlet-level events. Read comments in the class.
- `applicationConfigLocation` points into a text file where resources to be registered are listed. You should add your own resources into this file. One test resource has been added as a sample.
  - This list can also be populated programmatically. Refer to the [Apache Wink manual](https://wink.apache.org/documentation/1.1.2/Apache_Wink_User_Guide.pdf).



Congratulations! You are now ready to run your first Wink Plugin! Make sure you have enabled this new plugin in your run configuration and restarted HTTP server. Good luck!



### Credits

Fakenames database has been used with the permission from [David Leedy](http://www.notesin9.com). Also thanks to [Fake name Generator](http://www.fakenamegenerator.com/) as the primary source of all fake names. The names are shared under Creative Commons BY-SA 3.0 US license.

