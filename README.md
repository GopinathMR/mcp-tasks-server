# Template project to build MCP server using SpringBoot

### Features
- Structure to expose features as REST endpoint to test it outside MCP client
- Example `task` feature to use as guideline to build new tools / resources. Includes unit and integration tests


### How to configure in MCP Client (like Claude desktop)
- Open the file `~/Library/Application Support/Claude/claude_desktop_config.json` and add below section

```json
    "your-app-name": {
      "command": "/usr/bin/java",
      "args": [
        "-Dspring.profiles.active=default",
        "-Dspring.main.web-application-type=none",
        "-jar",
        "<PATH_TO_REPO>/build/libs/<your-app-name>-0.0.1-SNAPSHOT.jar"
      ]
    }

```