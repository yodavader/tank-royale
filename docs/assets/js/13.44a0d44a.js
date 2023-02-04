(window.webpackJsonp=window.webpackJsonp||[]).push([[13],{313:function(t,e,a){"use strict";a.r(e);var s=a(13),r=Object(s.a)({},(function(){var t=this,e=t._self._c;return e("ContentSlotsDistributor",{attrs:{"slot-key":t.$parent.slotKey}},[e("h1",{attrs:{id:"booter"}},[e("a",{staticClass:"header-anchor",attrs:{href:"#booter"}},[t._v("#")]),t._v(" Booter")]),t._v(" "),e("p",[t._v("A "),e("strong",[t._v("booter")]),t._v(" is provided to boot up bots on a local machine.")]),t._v(" "),e("p",[t._v("It comes built-in with the GUI for Robocode Tank Royale, but is to run as stand-alone as well, e.g. if no GUI is being\nused.")]),t._v(" "),e("p",[t._v("The intention of the booter is to allow booting up bots for any programming language. To make this possible, the booter\nuses script files that are responsible for starting up bots for specific programming languages and platforms.\nHence, the booter needs to locate these script files for each bot, and thus makes use of filename conventions to locate\nthese.")]),t._v(" "),e("p",[t._v("Diagram showing how the booter boots up a bot:")]),t._v(" "),e("div",{staticClass:"language-mermaid extra-class"},[e("pre",{pre:!0,attrs:{class:"language-mermaid"}},[e("code",[e("span",{pre:!0,attrs:{class:"token keyword"}},[t._v("flowchart")]),t._v(" TD\n   Booter "),e("span",{pre:!0,attrs:{class:"token inter-arrow-label"}},[e("span",{pre:!0,attrs:{class:"token arrow-head arrow operator"}},[t._v("--")]),e("span",{pre:!0,attrs:{class:"token label property"}},[t._v("locate and runs")]),e("span",{pre:!0,attrs:{class:"token arrow operator"}},[t._v("--\x3e")])]),t._v(" ScriptFile\n   ScriptFile "),e("span",{pre:!0,attrs:{class:"token inter-arrow-label"}},[e("span",{pre:!0,attrs:{class:"token arrow-head arrow operator"}},[t._v("--")]),e("span",{pre:!0,attrs:{class:"token label property"}},[t._v("boots up")]),e("span",{pre:!0,attrs:{class:"token arrow operator"}},[t._v("--\x3e")])]),t._v(" Bot\n   \n   ScriptFile"),e("span",{pre:!0,attrs:{class:"token text string"}},[t._v("[Script file]")]),t._v("\n")])])]),e("h2",{attrs:{id:"root-directories"}},[e("a",{staticClass:"header-anchor",attrs:{href:"#root-directories"}},[t._v("#")]),t._v(" Root directories")]),t._v(" "),e("p",[t._v("A bot "),e("strong",[t._v("root directory")]),t._v(" is top-level directory which is a collection of "),e("strong",[t._v("bot directories")]),t._v(". For example, the sample\nbots is a collection of bot directories containing directories like:")]),t._v(" "),e("div",{staticClass:"language- extra-class"},[e("pre",{pre:!0,attrs:{class:"language-text"}},[e("code",[t._v("[root directory]\n├── Corners (a bot directory)\n├── Crazy\n├── Fire\n├── MyFirstBot\n...\n")])])]),e("p",[t._v("Each of the directory names listed represents a "),e("strong",[t._v("bot directory")]),t._v(".")]),t._v(" "),e("p",[t._v("Multiple root directories can be supplied to the booter. This could for example be bot root directories for separate\nprogramming languages like e.g. Java and C#.")]),t._v(" "),e("h2",{attrs:{id:"bot-directories"}},[e("a",{staticClass:"header-anchor",attrs:{href:"#bot-directories"}},[t._v("#")]),t._v(" Bot directories")]),t._v(" "),e("p",[t._v("A "),e("strong",[t._v("bot directory")]),t._v(" contains all files required to run a specific bot type and perhaps some metadata like a ReadMe file\netc. for the bot.")]),t._v(" "),e("p",[t._v("As minimum these files "),e("em",[t._v("must")]),t._v(" be available in a bot directory:")]),t._v(" "),e("ul",[e("li",[t._v("Script for running the bot, i.e. a "),e("strong",[t._v("sh")]),t._v(" file (macOS and Linux) or "),e("strong",[t._v("cmd")]),t._v(" (Windows) file.")]),t._v(" "),e("li",[e("a",{attrs:{href:"#json-config-file"}},[t._v("JSON config file")]),t._v(" that describes the bot, and specify which game types it can handle.")])]),t._v(" "),e("h3",{attrs:{id:"base-filename"}},[e("a",{staticClass:"header-anchor",attrs:{href:"#base-filename"}},[t._v("#")]),t._v(" Base filename")]),t._v(" "),e("p",[t._v("All bot files in a bot directory must share the same common base filename, which "),e("em",[t._v("must")]),t._v(" match the filename of the\n(parent) bot directory. Otherwise, the game will not be able to locate the bot file(s) as it is looking for filenames\nmatching the filename of the bot directory. All other files are ignored by the game.")]),t._v(" "),e("h3",{attrs:{id:"example-of-bot-files"}},[e("a",{staticClass:"header-anchor",attrs:{href:"#example-of-bot-files"}},[t._v("#")]),t._v(" Example of bot files")]),t._v(" "),e("p",[t._v("Here is an example of files in a bot directory, in this case the Java version of MyFirstBot:")]),t._v(" "),e("ul",[e("li",[e("code",[t._v("MyFirstBot.java")]),t._v(" is the Java source file containing the bot program.")]),t._v(" "),e("li",[e("code",[t._v("MyFirstBot.json")]),t._v(" is the JSON config file.")]),t._v(" "),e("li",[e("code",[t._v("MyFirstBot.cmd")]),t._v(" used for running the bot on Windows.")]),t._v(" "),e("li",[e("code",[t._v("MyFirstBot.sh")]),t._v(" used for running the bot on macOS and Linux.")])]),t._v(" "),e("h2",{attrs:{id:"script-files"}},[e("a",{staticClass:"header-anchor",attrs:{href:"#script-files"}},[t._v("#")]),t._v(" Script files")]),t._v(" "),e("p",[t._v("The booter will look for script files and look for some that match the OS it is running on. So for macOS and Linux the\nbooter will try to locate a shell script file (.sh file) with the name "),e("em",[t._v("BotName")]),t._v(".sh and with Windows the booter will try\nto locate a command script file (.cmd file) with the name "),e("em",[t._v("BotName")]),t._v(".cmd.")]),t._v(" "),e("p",[t._v("The script should contain the necessary command for running a bot. For Java-based bots, the "),e("em",[t._v("java")]),t._v(" command can be used\nfor running a bot, and for a .Net-based bot the "),e("em",[t._v("dotnet")]),t._v(" command can be used for running the bot.")]),t._v(" "),e("p",[t._v("The assumption here is the command(s) used within the scripts are available on the local machine running the bots.\nHence, it is a good idea to provide a ReadMe file that describes the required commands that must be installed to run the\nscript for a bot if other people should be able to run the bot on their system.")]),t._v(" "),e("h2",{attrs:{id:"json-config-file"}},[e("a",{staticClass:"header-anchor",attrs:{href:"#json-config-file"}},[t._v("#")]),t._v(" JSON config file")]),t._v(" "),e("p",[t._v("All bot directories must contain a "),e("a",{attrs:{href:"https://fileinfo.com/extension/json",target:"_blank",rel:"noopener noreferrer"}},[t._v("JSON"),e("OutboundLink")],1),t._v(" file, which is basically a description of the bot.")]),t._v(" "),e("p",[t._v("For example, the bot MyFirstBot is accompanied by a "),e("em",[t._v("MyFirstBot.json")]),t._v(" file.")]),t._v(" "),e("p",[t._v("MyFirstBot.json for .Net:")]),t._v(" "),e("div",{staticClass:"language-json extra-class"},[e("pre",{pre:!0,attrs:{class:"language-json"}},[e("code",[e("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("{")]),t._v("\n  "),e("span",{pre:!0,attrs:{class:"token property"}},[t._v('"name"')]),e("span",{pre:!0,attrs:{class:"token operator"}},[t._v(":")]),t._v(" "),e("span",{pre:!0,attrs:{class:"token string"}},[t._v('"My First Bot"')]),e("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(",")]),t._v("\n  "),e("span",{pre:!0,attrs:{class:"token property"}},[t._v('"version"')]),e("span",{pre:!0,attrs:{class:"token operator"}},[t._v(":")]),t._v(" "),e("span",{pre:!0,attrs:{class:"token string"}},[t._v('"1.0"')]),e("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(",")]),t._v("\n  "),e("span",{pre:!0,attrs:{class:"token property"}},[t._v('"authors"')]),e("span",{pre:!0,attrs:{class:"token operator"}},[t._v(":")]),t._v(" "),e("span",{pre:!0,attrs:{class:"token string"}},[t._v('"Mathew Nelson, Flemming N. Larsen"')]),e("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(",")]),t._v("\n  "),e("span",{pre:!0,attrs:{class:"token property"}},[t._v('"description"')]),e("span",{pre:!0,attrs:{class:"token operator"}},[t._v(":")]),t._v(" "),e("span",{pre:!0,attrs:{class:"token string"}},[t._v('"A sample bot that is probably the first bot you will learn about."')]),e("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(",")]),t._v("\n  "),e("span",{pre:!0,attrs:{class:"token property"}},[t._v('"homepage"')]),e("span",{pre:!0,attrs:{class:"token operator"}},[t._v(":")]),t._v(" "),e("span",{pre:!0,attrs:{class:"token string"}},[t._v('""')]),e("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(",")]),t._v("\n  "),e("span",{pre:!0,attrs:{class:"token property"}},[t._v('"countryCodes"')]),e("span",{pre:!0,attrs:{class:"token operator"}},[t._v(":")]),t._v(" "),e("span",{pre:!0,attrs:{class:"token string"}},[t._v('"us, dk"')]),e("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(",")]),t._v("\n  "),e("span",{pre:!0,attrs:{class:"token property"}},[t._v('"platform"')]),e("span",{pre:!0,attrs:{class:"token operator"}},[t._v(":")]),t._v(" "),e("span",{pre:!0,attrs:{class:"token string"}},[t._v('".Net 6.0"')]),e("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(",")]),t._v("\n  "),e("span",{pre:!0,attrs:{class:"token property"}},[t._v('"programmingLang"')]),e("span",{pre:!0,attrs:{class:"token operator"}},[t._v(":")]),t._v(" "),e("span",{pre:!0,attrs:{class:"token string"}},[t._v('"C# 10.0"')]),e("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(",")]),t._v("\n  "),e("span",{pre:!0,attrs:{class:"token property"}},[t._v('"gameTypes"')]),e("span",{pre:!0,attrs:{class:"token operator"}},[t._v(":")]),t._v(" "),e("span",{pre:!0,attrs:{class:"token string"}},[t._v('"melee, classic, 1v1"')]),e("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(",")]),t._v("\n  "),e("span",{pre:!0,attrs:{class:"token property"}},[t._v('"initialPosition"')]),e("span",{pre:!0,attrs:{class:"token operator"}},[t._v(":")]),t._v(" "),e("span",{pre:!0,attrs:{class:"token string"}},[t._v('"50,50, 90"')]),t._v("\n"),e("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("}")]),t._v("\n")])])]),e("p",[t._v("These fields are required:")]),t._v(" "),e("ul",[e("li",[t._v("name")]),t._v(" "),e("li",[t._v("version")]),t._v(" "),e("li",[t._v("authors")])]),t._v(" "),e("p",[t._v("The remaining fields are all optional, but recommended.")]),t._v(" "),e("p",[t._v("Meaning of each field in the JSON file:")]),t._v(" "),e("ul",[e("li",[e("em",[t._v("name")]),t._v(": is the display name of the bot.")]),t._v(" "),e("li",[e("em",[t._v("version")]),t._v(": is the version of the bot, where "),e("a",{attrs:{href:"https://semver.org/",target:"_blank",rel:"noopener noreferrer"}},[t._v("SEMVER"),e("OutboundLink")],1),t._v(" is the recommended format, but not a requirement.")]),t._v(" "),e("li",[e("em",[t._v("authors")]),t._v(": is a comma-separated list with the (full) name of the bot author(s). The name could be a nickname or\nhandle.")]),t._v(" "),e("li",[e("em",[t._v("description")]),t._v(": is a brief description of the bot.")]),t._v(" "),e("li",[e("em",[t._v("homepage")]),t._v(": is a link to a web page for the bot.")]),t._v(" "),e("li",[e("em",[t._v("countryCodes")]),t._v(": is a comma-separated list of "),e("a",{attrs:{href:"https://www.iban.com/country-codes",target:"_blank",rel:"noopener noreferrer"}},[t._v("Alpha-2"),e("OutboundLink")],1),t._v(" country codes for the bot author(s).")]),t._v(" "),e("li",[e("em",[t._v("platform")]),t._v(": is the platform required for running the bot, e.g. Java 17 or .Net 6.0.")]),t._v(" "),e("li",[e("em",[t._v("programmingLang")]),t._v(": is the programming language used for programming the bot, e.g. C# or Kotlin.")]),t._v(" "),e("li",[e("em",[t._v("gameTypes")]),t._v(": is a comma-separated list containing the "),e("RouterLink",{attrs:{to:"/articles/game_types.html"}},[t._v("game types")]),t._v(" that the bot is supporting, meaning\nthat it should\nnot play in battles with game types other than the listed ones. When this field is omitted, the bot will participate\nin any type of game.")],1),t._v(" "),e("li",[e("em",[t._v("initialPosition")]),t._v(": is a comma-separated list containing the starting x and y coordinate, and direction\n(body, gun, and radar) when the game begins. "),e("a",{attrs:{href:"?",title:"Note that _initial start position_ must be enabled to allow the bot to have the same starting position each game"}},[t._v("^initial-start-position")])])]),t._v(" "),e("h3",{attrs:{id:"escaping-special-characters"}},[e("a",{staticClass:"header-anchor",attrs:{href:"#escaping-special-characters"}},[t._v("#")]),t._v(" Escaping special characters")]),t._v(" "),e("p",[t._v("Note that some characters are reserved in "),e("a",{attrs:{href:"https://fileinfo.com/extension/json",target:"_blank",rel:"noopener noreferrer"}},[t._v("JSON"),e("OutboundLink")],1),t._v(" and "),e("em",[t._v("must")]),t._v(" be escaped within the JSON strings. Otherwise, the config\nfile for the bot cannot be read properly, and the bot might not boot.")]),t._v(" "),e("ul",[e("li",[e("strong",[t._v("Double quote")]),t._v(" is replaced with "),e("code",[t._v('\\"')])]),t._v(" "),e("li",[e("strong",[t._v("Backslash")]),t._v(" to be replaced with "),e("code",[t._v("\\\\")])]),t._v(" "),e("li",[e("strong",[t._v("Newline")]),t._v(" is replaced with "),e("code",[t._v("\\n")])]),t._v(" "),e("li",[e("strong",[t._v("Carriage return")]),t._v(" is replaced with "),e("code",[t._v("\\r")])]),t._v(" "),e("li",[e("strong",[t._v("Tab")]),t._v(" is replaced with "),e("code",[t._v("\\t")])]),t._v(" "),e("li",[e("strong",[t._v("Form feed")]),t._v(" is replaced with "),e("code",[t._v("\\f")])]),t._v(" "),e("li",[e("strong",[t._v("Backspace")]),t._v(" is replaced with "),e("code",[t._v("\\b")])])])])}),[],!1,null,null,null);e.default=r.exports}}]);